package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MoneyTest {
	Currency SEK, DKK, NOK, EUR;
	Money SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
		SEK100 = new Money(10000, SEK);
		EUR10 = new Money(1000, EUR);
		SEK200 = new Money(20000, SEK);
		EUR20 = new Money(2000, EUR);
		SEK0 = new Money(0, SEK);
		EUR0 = new Money(0, EUR);
		SEKn100 = new Money(-10000, SEK);
	}

	@Test
	public void testGetAmount() {
		assertEquals((Object)10000, (Object)SEK100.getAmount());
		assertEquals((Object)0, (Object)SEK0.getAmount());
	}

	@Test
	public void testGetCurrency() {
		assertEquals(SEK, SEK100.getCurrency());
		assertEquals(EUR, EUR10.getCurrency());
	}

	@Test
	public void testToString() {
		assertEquals("10000 SEK", SEK100.toString());
		assertEquals("0 EUR", EUR0.toString());
	}

	@Test
	public void testGlobalValue() {
		assertEquals((Object)1500, (Object)SEK100.universalValue());
		assertEquals((Object)1500, (Object)EUR10.universalValue());
	}

	@Test
	public void testEqualsMoney() {
		assertTrue(SEK100.equals(new Money(10000, SEK)));
		assertFalse(SEK100.equals(EUR10));
	}

	@Test
	public void testAdd() {
		Money result = SEK100.add(SEK100);
		assertEquals((Object)20000, (Object)result.getAmount());

		Money resultCross = SEK100.add(EUR10);
		assertEquals((Object)20000, (Object)resultCross.getAmount());
	}

	@Test
	public void testSub() {
		Money result = SEK200.sub(SEK100);
		assertEquals((Object)10000, (Object)result.getAmount());
	}

	@Test
	public void testIsZero() {
		assertTrue(SEK0.isZero());
		assertFalse(SEK100.isZero());
	}

	@Test
	public void testNegate() {
		assertEquals((Object)(-10000), (Object)SEK100.negate().getAmount());
		assertEquals((Object)10000, (Object)SEKn100.negate().getAmount());
	}

	@Test
	public void testCompareTo() {
		assertEquals((Object)0, (Object)SEK100.compareTo(EUR10));
		assertTrue(SEK100.compareTo(SEK200) < 0);
		assertTrue(SEK200.compareTo(EUR10) > 0);
	}
}