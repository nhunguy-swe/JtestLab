package a_Introductory;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class QuadrilateralTest_new {
    private Quadrilateral square;
    private Quadrilateral rectangle;
    private Quadrilateral randomQuad;

    @Before
    public void setUp() {
        // 1. Một hình vuông: (0,0), (2,0), (2,2), (0,2)
        square = new Quadrilateral(
                new Point(0, 0), new Point(2, 0),
                new Point(2, 2), new Point(0, 2)
        );

        // 2. Một hình chữ nhật (không phải hình vuông): (0,0), (4,0), (4,2), (0,2)
        rectangle = new Quadrilateral(
                new Point(0, 0), new Point(4, 0),
                new Point(4, 2), new Point(0, 2)
        );

        // 3. Một tứ giác lồi bình thường: (0,0), (4,1), (5,4), (1,3)
        randomQuad = new Quadrilateral(
                new Point(0, 0), new Point(4, 1),
                new Point(5, 4), new Point(1, 3)
        );
    }

    @Test
    public void testIsRectangle() {
        // Hình vuông và hình chữ nhật đều phải trả về true [cite: 96, 98]
        assertTrue("Hình vuông nên được nhận diện là hình chữ nhật", square.isRectangle());
        assertTrue("Hình chữ nhật nên được nhận diện đúng", rectangle.isRectangle());

        // Tứ giác ngẫu nhiên phải trả về false [cite: 51]
        assertFalse("Tứ giác thường không được là hình chữ nhật", randomQuad.isRectangle());
    }

    @Test
    public void testIsSquare() {
        // Chỉ có hình vuông mới trả về true [cite: 96, 99]
        assertTrue("Hình vuông nên được nhận diện đúng", square.isSquare());

        // Hình chữ nhật (cạnh không bằng nhau) phải trả về false
        assertFalse("Hình chữ nhật có cạnh khác nhau không được là hình vuông", rectangle.isSquare());

        // Tứ giác ngẫu nhiên phải trả về false
        assertFalse("Tứ giác thường không được là hình vuông", randomQuad.isSquare());
    }
}