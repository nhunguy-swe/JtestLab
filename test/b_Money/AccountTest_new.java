package b_Money;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTest_new {
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
        testAccount.deposit(new Money(10000000, SEK)); // Nạp 100,000.00 SEK
        SweBank.deposit("Alice", new Money(1000000, SEK)); // Nạp 10,000.00 SEK cho Alice
    }

    /**
     * Kiểm tra việc thêm và xóa thanh toán định kỳ (Timed Payment).
     */
    @Test
    public void testAddRemoveTimedPayment() {
        testAccount.addTimedPayment("tp1", 10, 5, new Money(1000, SEK), SweBank, "Alice");
        assertTrue("Thanh toán định kỳ tp1 phải tồn tại", testAccount.timedPaymentExists("tp1"));

        testAccount.removeTimedPayment("tp1");
        assertFalse("Thanh toán định kỳ tp1 lẽ ra phải bị xóa", testAccount.timedPaymentExists("tp1"));
    }

    /**
     * Kiểm tra phương thức nạp tiền (deposit) và rút tiền (withdraw).
     */
    @Test
    public void testDepositWithdraw() {
        Money depositAmount = new Money(200000, SEK); // 2,000.00 SEK
        testAccount.deposit(depositAmount);
        assertEquals("Số dư sau khi nạp không chính xác", Integer.valueOf(10200000), testAccount.getBalance().getAmount());

        Money withdrawAmount = new Money(100000, SEK); // 1,000.00 SEK
        testAccount.withdraw(withdrawAmount);
        assertEquals("Số dư sau khi rút không chính xác", Integer.valueOf(10100000), testAccount.getBalance().getAmount());
    }

    /**
     * Kiểm tra số dư tài khoản (getBalance).
     */
    @Test
    public void testGetBalance() {
        assertEquals("Số dư ban đầu không khớp", Integer.valueOf(10000000), testAccount.getBalance().getAmount());
        assertEquals("Đơn vị tiền tệ không khớp", SEK, testAccount.getBalance().getCurrency());
    }

    /**
     * Kiểm tra cơ chế tick() và thực hiện thanh toán định kỳ.
     * Lưu ý: Đây là nơi thường phát hiện ra lỗi logic trong mã nguồn.
     */
    @Test
    public void testTimedPayment() throws AccountDoesNotExistException {
        // Thiết lập thanh toán 10.00 SEK mỗi 2 ticks, bắt đầu sau 0 ticks.
        testAccount.addTimedPayment("tp2", 2, 0, new Money(1000, SEK), SweBank, "Alice");

        // Thực hiện tick
        testAccount.tick();

        /* * Phát hiện lỗi: Trong Account.java, phương thức tick() gọi tp.tick() hai lần.
         * Điều này khiến tiến trình thời gian bị đẩy nhanh gấp đôi hoặc thực hiện giao dịch sai số lần.
         */
        Integer expectedBalance = 10000000 - 1000;
        assertEquals("Số dư tài khoản nguồn không đúng sau khi tick", expectedBalance, testAccount.getBalance().getAmount());
    }
}