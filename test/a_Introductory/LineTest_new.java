package a_Introductory;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LineTest_new {
    private Point p1, p2, p3, p4;
    private Line line1, line2, line3;

    @Before
    public void setUp() {
        // Khởi tạo các điểm và đường thẳng để dùng chung cho các test case
        p1 = new Point(0, 0);
        p2 = new Point(3, 4); // Độ dài = 5 (theo bộ số Pitago 3-4-5)
        p3 = new Point(1, 1);
        p4 = new Point(4, 5); // Độ dài cũng = 5

        line1 = new Line(p1, p2);
        line2 = new Line(p3, p4);
        line3 = new Line(p1, p3); // Độ dài khác 5
    }

    @Test
    public void testGetLength() {
        /* * Theo hướng dẫn, khi so sánh giá trị double hoặc float,
         * ta phải thêm tham số thứ ba (delta) để chỉ định sai số cho phép.
         */
        double expectedLength = 5.0;
        assertEquals("Độ dài của line1 không chính xác", expectedLength, line1.getLength(), 0.00001);
    }

    @Test
    public void testIsSameLengthAs() {
        // Kiểm tra xem hai đường thẳng có cùng độ dài (5.0) có được nhận diện đúng không
        assertTrue("line1 và line2 nên có cùng độ dài", line1.isSameLengthAs(line2));

        // Kiểm tra xem hai đường thẳng khác độ dài có bị từ chối không
        assertFalse("line1 và line3 không nên có cùng độ dài", line1.isSameLengthAs(line3));
    }

    @Test
    public void testGetVector() {
        Vector2D vector = line1.getVector();
        /*
         * Kiểm tra vector tạo bởi p1(0,0) và p2(3,4).
         * Giả định Vector2D có các thuộc tính x và y (hoặc phương thức getter tương ứng).
         */
        assertNotNull("Vector trả về không được null", vector);
        assertEquals("Tọa độ x của vector không đúng", 3.0, vector.x, 0.00001);
        assertEquals("Tọa độ y của vector không đúng", 4.0, vector.y, 0.00001);
    }
}