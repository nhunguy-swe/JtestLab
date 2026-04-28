package b_Money;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MoneyTest_new {
    Currency SEK, DKK, EUR;
    Money SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100;

    @Before
    public void setUp() throws Exception {
        SEK = new Currency("SEK", 0.15);
        DKK = new Currency("DKK", 0.20);
        EUR = new Currency("EUR", 1.5);

        SEK100 = new Money(10000, SEK);     // 100.00 SEK
        EUR10 = new Money(1000, EUR);       // 10.00 EUR
        SEK200 = new Money(20000, SEK);     // 200.00 SEK
        EUR20 = new Money(2000, EUR);       // 20.00 EUR
        SEK0 = new Money(0, SEK);           // 0 SEK
        EUR0 = new Money(0, EUR);           // 0 EUR
        SEKn100 = new Money(-10000, SEK);   // -100.00 SEK
    }

    @Test
    public void testGetAmount() {
        assertEquals("Số tiền của SEK100 không đúng", Integer.valueOf(10000), SEK100.getAmount());
        assertEquals("Số tiền của EUR10 không đúng", Integer.valueOf(1000), EUR10.getAmount());
    }

    @Test
    public void testGetCurrency() {
        assertEquals("Tiền tệ của SEK100 không đúng", SEK, SEK100.getCurrency());
        assertEquals("Tiền tệ của EUR10 không đúng", EUR, EUR10.getCurrency());
    }

    @Test
    public void testToString() {
        // Theo đặc tả: "(amount) (currencyname)"
        assertEquals("Định dạng toString của SEK100 không đúng", "10000 SEK", SEK100.toString());
        assertEquals("Định dạng toString của EUR10 không đúng", "1000 EUR", EUR10.toString());
    }

    @Test
    public void testGlobalValue() {
        // 10000 SEK * 0.15 = 1500
        assertEquals("Giá trị universal của SEK100 không đúng", Integer.valueOf(1500), SEK100.universalValue());
        // 1000 EUR * 1.5 = 1500
        assertEquals("Giá trị universal của EUR10 không đúng", Integer.valueOf(1500), EUR10.universalValue());
    }

    @Test
    public void testEqualsMoney() {
        // Kiểm tra bằng nhau dù khác loại tiền (dựa trên universal value)
        // Lưu ý: Trong lớp Money bạn gửi, equals đang so sánh trực tiếp 'amount == other.amount'
        // Đây có thể là một BUG vì 100 SEK (10000) không nên bằng 100 EUR (10000).
        // Nếu test fail, bạn hãy kiểm tra lại logic equals trong Money.java
        assertTrue("SEK100 nên có giá trị tương đương EUR10", SEK100.universalValue().equals(EUR10.universalValue()));
    }

    @Test
    public void testAdd() {
        // 100 SEK + 10 EUR (10 EUR = 100 SEK) = 200 SEK
        Money result = SEK100.add(EUR10);
        assertEquals("Kết quả phép cộng 100 SEK + 10 EUR không đúng", Integer.valueOf(20000), result.getAmount());
        assertEquals("Loại tiền tệ sau khi cộng phải là SEK", SEK, result.getCurrency());
    }

    @Test
    public void testSub() {
        // 200 SEK - 10 EUR (10 EUR = 100 SEK) = 100 SEK
        Money result = SEK200.sub(EUR10);
        assertEquals("Kết quả phép trừ 200 SEK - 10 EUR không đúng", Integer.valueOf(10000), result.getAmount());
    }

    @Test
    public void testIsZero() {
        assertTrue("SEK0 phải là zero", SEK0.isZero());
        assertFalse("SEK100 không được là zero", SEK100.isZero());
    }

    @Test
    public void testNegate() {
        Money negated = SEK100.negate();
        assertEquals("Giá trị phủ định của 10000 phải là -10000", Integer.valueOf(-10000), negated.getAmount());
    }

    @Test
    public void testCompareTo() {
        // SEK100 (1500 universal) vs EUR20 (3000 universal)
        assertTrue("SEK100 phải nhỏ hơn EUR20", SEK100.compareTo(EUR20) < 0);
        // EUR10 (1500) vs SEK100 (1500)
        assertEquals("EUR10 phải bằng SEK100", 0, EUR10.compareTo(SEK100));
        // EUR20 (3000) vs SEK100 (1500)
        assertTrue("EUR20 phải lớn hơn SEK100", EUR20.compareTo(SEK100) > 0);
    }
}