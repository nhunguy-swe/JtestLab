package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BankTest {
	Currency SEK, DKK;
	Bank SweBank, Nordea, DanskeBank;
	
	@Before
	public void setUp() throws Exception {
		DKK = new Currency("DKK", 0.20);
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		Nordea = new Bank("Nordea", SEK);
		DanskeBank = new Bank("DanskeBank", DKK);
		SweBank.openAccount("Ulrika");
		SweBank.openAccount("Bob");
		Nordea.openAccount("Bob");
		DanskeBank.openAccount("Gertrud");
	}

	@Test
	public void testGetName() {
		assertEquals("SweBank", SweBank.getName());
		assertEquals("Nordea", Nordea.getName());
	}

	@Test
	public void testGetCurrency() {
		assertEquals(SEK, SweBank.getCurrency());
		assertEquals(DKK, DanskeBank.getCurrency());
	}

	@Test
	public void testOpenAccount() throws AccountExistsException, AccountDoesNotExistException {
		SweBank.openAccount("Alice");
		try {
			SweBank.openAccount("Alice");
			fail("Nên ném ra lỗi AccountExistsException");
		} catch (AccountExistsException e) {}
	}

	@Test
	public void testDeposit() throws AccountDoesNotExistException {
		SweBank.deposit("Ulrika", new Money(1000, SEK));
		assertEquals(Integer.valueOf(1000), SweBank.getBalance("Ulrika"));
	}

	@Test
	public void testWithdraw() throws AccountDoesNotExistException {
		SweBank.deposit("Ulrika", new Money(1000, SEK));
		SweBank.withdraw("Ulrika", new Money(400, SEK));
		assertEquals(Integer.valueOf(600), SweBank.getBalance("Ulrika"));
	}
	
	@Test
	public void testGetBalance() throws AccountDoesNotExistException {
		assertEquals(Integer.valueOf(0), SweBank.getBalance("Bob"));
	}
	
	@Test
	public void testTransfer() throws AccountDoesNotExistException {
		SweBank.deposit("Ulrika", new Money(1000, SEK));

		SweBank.transfer("Ulrika", "Bob", new Money(500, SEK));
		assertEquals(Integer.valueOf(500), SweBank.getBalance("Ulrika"));
		assertEquals(Integer.valueOf(500), SweBank.getBalance("Bob"));

		SweBank.transfer("Bob", Nordea, "Bob", new Money(200, SEK));
		assertEquals(Integer.valueOf(300), SweBank.getBalance("Bob"));
		assertEquals(Integer.valueOf(200), Nordea.getBalance("Bob"));
	}
	
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		SweBank.addTimedPayment("Ulrika", "rent", 1, 0, new Money(100, SEK), SweBank, "Bob");

		SweBank.deposit("Ulrika", new Money(1000, SEK));

		SweBank.tick();

		assertEquals(Integer.valueOf(900), SweBank.getBalance("Ulrika"));
		assertEquals(Integer.valueOf(100), SweBank.getBalance("Bob"));
	}
}