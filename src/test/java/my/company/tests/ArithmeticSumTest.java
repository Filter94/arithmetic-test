package my.company.tests;

import org.junit.Test;
import org.junit.runners.Parameterized;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import ru.yandex.qatools.allure.annotations.Title;

/**
 * Performs tests for sum operation
 */
public class ArithmeticSumTest extends ArithmeticTestBase {

    @Parameterized.Parameters()
    public static Iterable<Object[]> data() throws Exception {
        FIXTURES_NAME = "sum_fixture.txt";
        return ArithmeticTestBase.data();
    }

    public ArithmeticSumTest(String operand1,
                             String operand2,
                             String operation,
                             String result) throws Exception{
        super(operand1, operand2, operation, result);
    }

    @Title("Sum test")
    @Features("Arithmetic operations support")
    @Stories({"Support for sum"})
    @Test
    public void performTest() throws Exception {
        super.performTest();
    }
}
