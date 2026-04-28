package b_Money;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BankTest_new {
    Currency SEK, DKK;
    Bank SweBank, Nordea, DanskeBank;

    @Before
    public void setUp() throws Exception {
        SEK = new Currency("SEK", 0.15);
        DKK = new Currency("DKK", 0.20);
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
        assertEquals("Tên ngân hàng không khớp", "SweBank", SweBank.getName());
    }

    @Test
    public void testGetCurrency() {
        assertEquals("Tiền tệ của ngân hàng không khớp", SEK, SweBank.getCurrency());
    }

    @Test
    public void testOpenAccount() throws AccountExistsException, AccountDoesNotExistException {
        // Kiểm tra mở tài khoản mới
        SweBank.openAccount("Alice");
        // Nếu không ném ra ngoại lệ và tài khoản tồn tại (balance không lỗi) là thành công
        assertNotNull("Tài khoản Alice nên tồn tại", SweBank.getBalance("Alice"));

        /* * KIỂM TRA LỖI: Thử mở tài khoản đã tồn tại.
         * Phải ném ra AccountExistsException.
         */
        try {
            SweBank.openAccount("Ulrika");
            fail("Lẽ ra phải ném AccountExistsException khi mở tài khoản trùng tên");
        } catch (AccountExistsException e) {
            // Thành công
        }
    }

    @Test
    public void testDeposit() throws AccountDoesNotExistException {
        Money money = new Money(100000, SEK); // 1000.00 SEK
        SweBank.deposit("Ulrika", money);
        assertEquals("Số dư sau khi nạp không đúng", Integer.valueOf(100000), SweBank.getBalance("Ulrika"));
    }

    @Test
    public void testWithdraw() throws AccountDoesNotExistException {
        Money depositMoney = new Money(200000, SEK);
        SweBank.deposit("Ulrika", depositMoney);

        Money withdrawMoney = new Money(50000, SEK);
        SweBank.withdraw("Ulrika", withdrawMoney);
        assertEquals("Số dư sau khi rút không đúng", Integer.valueOf(150000), SweBank.getBalance("Ulrika"));
    }

    @Test
    public void testGetBalance() throws AccountDoesNotExistException {
        // Tài khoản mới mở nên có số dư bằng 0
        assertEquals("Số dư mặc định phải là 0", Integer.valueOf(0), SweBank.getBalance("Bob"));
    }

    @Test
    public void testTransfer() throws AccountDoesNotExistException {
        Money money = new Money(100000, SEK);
        SweBank.deposit("Ulrika", money);

        // 1. Chuyển tiền nội bộ (cùng ngân hàng)
        SweBank.transfer("Ulrika", "Bob", new Money(50000, SEK));
        assertEquals("Số dư người gửi (nội bộ) không đúng", Integer.valueOf(50000), SweBank.getBalance("Ulrika"));
        assertEquals("Số dư người nhận (nội bộ) không đúng", Integer.valueOf(50000), SweBank.getBalance("Bob"));

        // 2. Chuyển tiền liên ngân hàng
        SweBank.transfer("Bob", Nordea, "Bob", new Money(30000, SEK));
        assertEquals("Số dư người gửi (liên ngân hàng) không đúng", Integer.valueOf(20000), SweBank.getBalance("Bob"));
        assertEquals("Số dư người nhận (ngân hàng khác) không đúng", Integer.valueOf(30000), Nordea.getBalance("Bob"));
    }

    @Test
    public void testTimedPayment() throws AccountDoesNotExistException {
        Money initialMoney = new Money(100000, SEK);
        SweBank.deposit("Ulrika", initialMoney);

        // Thêm thanh toán định kỳ: mỗi 1 tick chuyển 100.00 SEK từ Ulrika sang Bob
        SweBank.addTimedPayment("Ulrika", "pay1", 1, 0, new Money(10000, SEK), SweBank, "Bob");

        /* * PHÁT HIỆN LỖI (Bug Spotted):
         * Trong Account.java, phương thức tick() gọi tp.tick() hai lần.
         * Điều này khiến Ulrika bị trừ tiền 2 lần (200.00 SEK) thay vì 1 lần cho mỗi nhịp tick của Bank.
         */
        SweBank.tick();

        // Nếu code đúng, số dư Ulrika phải là 90000. Nếu là 80000 là do lỗi gọi tick() 2 lần.
        assertEquals("Lỗi: Thanh toán định kỳ bị thực hiện sai số lần do lỗi tick() trong Account",
                Integer.valueOf(90000), SweBank.getBalance("Ulrika"));
    }
}