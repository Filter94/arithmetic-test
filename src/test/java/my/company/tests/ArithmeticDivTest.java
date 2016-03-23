package my.company.tests;

import org.junit.Test;
import org.junit.runners.Parameterized;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import ru.yandex.qatools.allure.annotations.Title;

public class ArithmeticDivTest extends ArithmeticTestBase {

    /**
     * Performs tests for division operation
     */
    @Parameterized.Parameters()
    public static Iterable<Object[]> data() throws Exception {
        FIXTURES_NAME = "div_fixture.txt";
        return ArithmeticTestBase.data();
    }
    public ArithmeticDivTest(String operand1,
                             String operand2,
                             String operation,
                             String result) throws Exception {
        super(operand1, operand2, operation, result);
    }

    @Title("Division test")
    @Features("Arithmetic operations support")
    @Stories({"Support for division"})
    @Test
    public void performTest() throws Exception {
        super.performTest();
    }
}
