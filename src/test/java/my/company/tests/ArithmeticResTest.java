package my.company.tests;

import org.junit.Test;
import org.junit.runners.Parameterized;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import ru.yandex.qatools.allure.annotations.Title;

public class ArithmeticResTest extends ArithmeticTestBase {

    @Parameterized.Parameters(name = "Parameters name")
    public static Iterable<Object[]> data() throws Exception {
        FIXTURES_NAME = "res_fixture.txt";
        return ArithmeticTestBase.data();
    }
    public ArithmeticResTest(String operand1,
                             String operand2,
                             String operation,
                             String result) throws Exception {
        super(operand1, operand2, operation, result);
    }

    @Title("Residual test")
    @Features("Arithmetic operations support")
    @Stories({"Support for residual"})
    @Test
    public void performTest() throws Exception {
        super.performTest();
    }
}
