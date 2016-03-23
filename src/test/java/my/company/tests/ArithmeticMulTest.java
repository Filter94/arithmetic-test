package my.company.tests;

import org.junit.Test;
import org.junit.runners.Parameterized;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import ru.yandex.qatools.allure.annotations.Title;

public class ArithmeticMulTest extends ArithmeticTestBase {

    /**
     * Performs tests for multiplication operation
     */
    @Parameterized.Parameters()
    public static Iterable<Object[]> data() throws Exception {
        FIXTURES_NAME = "mul_fixture.txt";
        return ArithmeticTestBase.data();
    }
    public ArithmeticMulTest(String operand1,
                             String operand2,
                             String operation,
                             String result) throws Exception {
        super(operand1, operand2, operation, result);
    }

    @Title("Multiplication test")
    @Features("Arithmetic operations support")
    @Stories({"Support for multiplication"})
    @Test
    public void performTest() throws Exception {
        super.performTest();
    }
}
