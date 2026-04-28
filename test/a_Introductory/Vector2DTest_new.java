package a_Introductory;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class Vector2DTest_new {
    private Vector2D v1, v2, v3;

    @Before
    public void setUp() {
        // Khởi tạo các vector để dùng cho việc kiểm tra tích vô hướng và tính vuông góc
        // v1 = (3, 4)
        v1 = new Vector2D(3, 4);
        // v2 = (-4, 3) -> Tích vô hướng: 3*(-4) + 4*3 = 0 (Vuông góc)
        v2 = new Vector2D(-4, 3);
        // v3 = (1, 2) -> Tích vô hướng: 3*1 + 4*2 = 11 (Không vuông góc)
        v3 = new Vector2D(1, 2);
    }

    @Test
    public void testPointConstructor() {
        Point p1 = new Point(1, 1);
        Point p2 = new Point(4, 5);
        // Vector tạo từ p1(1,1) đến p2(4,5) phải là (3, 4)
        Vector2D v = new Vector2D(p1, p2);

        assertEquals("Tọa độ x của vector tạo từ Point không đúng", (Integer)3, v.x);
        assertEquals("Tọa độ y của vector tạo từ Point không đúng", (Integer)4, v.y);
    }

    @Test
    public void testDotProduct() {
        /* * Phương thức kiểm tra tích vô hướng (dot product).
         */
        assertEquals("Tích vô hướng của v1 và v2 không chính xác", 0, v1.dotProduct(v2));
        assertEquals("Tích vô hướng của v1 và v3 không chính xác", 11, v1.dotProduct(v3));
    }

    @Test
    public void testIsOrthogonalTo() {
        /*
         * Kiểm tra tính vuông góc giữa các vector dựa trên tích vô hướng bằng 0.
         */
        assertTrue("v1 và v2 nên vuông góc với nhau", v1.isOrthogonalTo(v2));
        assertFalse("v1 và v3 không được vuông góc với nhau", v1.isOrthogonalTo(v3));
    }
}