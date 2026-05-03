package a_Introductory;

import org.junit.Test;

import static org.junit.Assert.*;

public class FibonacciTest_new {
    /**
     * Kiểm tra phương thức fib(n) để đảm bảo dãy số được tạo ra chính xác.
     * Theo đặc tả, chúng ta cần kiểm tra các số Fibonacci từ n = 0 đến n = 7.
     */
    @Test
    public void testFib() {
        Fibonacci fibonacci = new Fibonacci();

        // Mảng chứa các giá trị kỳ vọng (expected values) từ n=0 đến n=7
        int[] expectedResults = {0, 1, 1, 2, 3, 5, 8, 13};

        for (int n = 0; n < expectedResults.length; n++) {
            // assertEquals(message, expected, actual)
            // Thông báo lỗi sẽ giúp xác định n nào gây ra lỗi
            assertEquals("Failed to generate Fibonacci number at n = " + n,
                    expectedResults[n],
                    fibonacci.fib(n));
        }
    }
}