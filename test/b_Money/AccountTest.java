package b_Money;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AccountTest {
	Currency SEK, DKK;
	Bank Nordea;
	Bank DanskeBank;
	Bank SweBank;
	Account testAccount;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		SweBank.openAccount("Alice");
		testAccount = new Account("Hans", SEK);
		testAccount.deposit(new Money(10000000, SEK));

		SweBank.deposit("Alice", new Money(1000000, SEK));
	}
	
	@Test
	public void testAddRemoveTimedPayment() {
		testAccount.addTimedPayment("rent", 10, 10, new Money(5000, SEK), SweBank, "Alice");
		assertTrue("Thanh toán định kỳ 'rent' phải tồn tại", testAccount.timedPaymentExists("rent"));

		testAccount.removeTimedPayment("rent");
		assertFalse("Thanh toán định kỳ 'rent' phải bị xóa", testAccount.timedPaymentExists("rent"));
	}
	
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		testAccount.addTimedPayment("sub", 1, 1, new Money(1000, SEK), SweBank, "Alice");
		testAccount.tick();

		assertEquals("Số dư Hans phải bị trừ sau khi tick", 9999000L, (long)testAccount.getBalance().getAmount());
	}

	@Test
	public void testAddWithdraw() {
		Account freshAccount = new Account("Alice", SEK);
		freshAccount.deposit(new Money(10000, SEK));
		freshAccount.withdraw(new Money(5000, SEK));

		assertEquals("Withdraw failed", 5000, (long)freshAccount.getBalance().getAmount());
	}
	
	@Test
	public void testGetBalance() {
		assertEquals("Thanh toán không đúng số dư hiện tại", 10000000L, (long)testAccount.getBalance().getAmount());
	}
}