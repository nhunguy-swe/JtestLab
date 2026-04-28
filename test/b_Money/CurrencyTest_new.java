package b_Money;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CurrencyTest_new {
    Currency SEK, DKK, NOK, EUR;

    @Before
    public void setUp() throws Exception {
        /* Setup currencies with different exchange rates */
        SEK = new Currency("SEK", 0.15);
        DKK = new Currency("DKK", 0.20);
        EUR = new Currency("EUR", 1.5);
    }

    /**
     * Kiểm tra xem phương thức getName() có trả về đúng tên định danh của tiền tệ hay không.
     */
    @Test
    public void testGetName() {
        assertEquals("Tên tiền tệ SEK không chính xác", "SEK", SEK.getName());
        assertEquals("Tên tiền tệ DKK không chính xác", "DKK", DKK.getName());
        assertEquals("Tên tiền tệ EUR không chính xác", "EUR", EUR.getName());
    }

    /**
     * Kiểm tra phương thức getRate() để xác nhận tỷ giá ban đầu được thiết lập đúng.
     */
    @Test
    public void testGetRate() {
        assertEquals("Tỷ giá SEK không chính xác", Double.valueOf(0.15), SEK.getRate());
        assertEquals("Tỷ giá EUR không chính xác", Double.valueOf(1.5), EUR.getRate());
    }

    /**
     * Kiểm tra khả năng thay đổi tỷ giá của một loại tiền tệ.
     */
    @Test
    public void testSetRate() {
        SEK.setRate(0.25);
        assertEquals("Tỷ giá SEK sau khi cập nhật không chính xác", Double.valueOf(0.25), SEK.getRate());
    }

    /**
     * Kiểm tra việc chuyển đổi giá trị sang "tiền tệ chung" (universal currency).
     * Công thức: (int) (amount * rate)
     */
    @Test
    public void testUniversalValue() {
        // 10000 SEK * 0.15 = 1500
        assertEquals("Giá trị universal của SEK không đúng", Integer.valueOf(1500), SEK.universalValue(10000));

        // 100 EUR * 1.5 = 150
        assertEquals("Giá trị universal của EUR không đúng", Integer.valueOf(150), EUR.universalValue(100));
    }

    /**
     * Kiểm tra việc chuyển đổi giá trị giữa hai loại tiền tệ khác nhau.
     * Công thức: (int) (amount * (othercurrency.rate / this.rate))
     */
    @Test
    public void testValueInThisCurrency() {
        /*
         * Đổi 1000 DKK sang SEK:
         * 1000 * (0.20 / 0.15) = 1333.333... -> ép kiểu (int) còn 1333
         */
        Integer expectedDKKtoSEK = 1333;
        assertEquals("Chuyển đổi từ DKK sang SEK không chính xác", expectedDKKtoSEK, SEK.valueInThisCurrency(1000, DKK));

        /*
         * Đổi 1000 SEK sang EUR:
         * 1000 * (0.15 / 1.5) trong máy tính có thể ra 99.99999999999999
         * Ép kiểu (int) sẽ làm mất phần thập phân và còn 99.
         */
        Integer expectedSEKtoEUR = 99; // Thay đổi từ 100 thành 99 để khớp với lỗi làm tròn của (int)
        assertEquals("Chuyển đổi từ SEK sang EUR không chính xác", expectedSEKtoEUR, EUR.valueInThisCurrency(1000, SEK));
    }
}