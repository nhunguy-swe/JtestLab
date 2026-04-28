package a_Introductory;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PointTest {
	Point p1, p2, p3;
	
	@Before
	public void setUp() throws Exception {
		p1 = new Point(7, 9);
		p2 = new Point(-3, -30);
		p3 = new Point(-10, 3);
	}

	@Test
	public void testAdd() {
		Point res1 = p1.add(p2);
		Point res2 = p1.add(p3);
		
//		assertEquals(4, res1.x);
//		assertEquals(-21, res1.y);
//		assertEquals(-3, res2.x);
//		assertEquals(12, res2.x);

		assertEquals("Add x1", 4, (long)res1.x);
		assertEquals("Add y1", -21, (long)res1.y);
		assertEquals("Add x2", -3, (long)res2.x);
		assertEquals("Add y2", 12, (long)res2.y);
	}

	@Test
	public void testSub() {
		Point res1 = p1.sub(p2);
		Point res2 = p1.sub(p3);
		
//		assertEquals(4, res1.x);
//		assertEquals(-21, res1.y);
//		assertEquals(-3, res2.x);
//		assertEquals(12, res2.x);

		assertEquals("Sub x1", 10, (long)res1.x);
		assertEquals("Sub y1", 39, (long)res1.y);
		assertEquals("Sub x2", 17, (long)res2.x);
		assertEquals("Sub y2", 6, (long)res2.y);
	}
}