package a_Introductory;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PointTest_new {
    private Point p1, p2;

    @Before
    public void setUp() {
        // Khởi tạo các đối tượng Point trước mỗi bài kiểm tra
        p1 = new Point(10, 20);
        p2 = new Point(5, 5);
    }

    @Test
    public void testAdd() {
        Point result = p1.add(p2);
        // Kiểm tra tọa độ x: 10 + 5 = 15
        assertEquals("Tọa độ x sau khi cộng không chính xác", (Integer)15, result.x);
        // Kiểm tra tọa độ y: 20 + 5 = 25
        assertEquals("Tọa độ y sau khi cộng không chính xác", (Integer)25, result.y);
    }

    @Test
    public void testSub() {
        Point result = p1.sub(p2);
        // Kiểm tra tọa độ x: 10 - 5 = 5
        assertEquals("Tọa độ x sau khi trừ không chính xác", (Integer)5, result.x);
        // Kiểm tra tọa độ y: 20 - 5 = 15
        assertEquals("Tọa độ y sau khi trừ không chính xác", (Integer)15, result.y);
    }
}