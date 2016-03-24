package my.company.tests;

import org.junit.Test;
import ru.yandex.qatools.allure.annotations.*;

import org.junit.Assert;
import static org.junit.Assert.fail;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for arithmetic tests.
 * Implements tests parametrisation, execution and also attachment saving logic.
 */
@RunWith(Parameterized.class)
@Description("This is an example test suite")
public class ArithmeticTestCase {
    // Epsilon for float point comparison
    private static final double EPSILON = 1e-10;
    // Name of file with fixtures. Must be set by child class.
    final private static String FIXTURES_NAME = "fixture.txt";

    /**
     * Function for tests parametrisation
     * @return Iterable with constructor parameters
     * @throws Exception
     */
    @Parameterized.Parameters()
    public static Iterable<Object[]> data() throws Exception{
        ArithmeticTestCase.saveTxtAttachment();
        URL dir_url = ClassLoader.getSystemResource(FIXTURES_NAME);
        List<String> fixturesArray = Files.readAllLines(Paths.get(dir_url.toURI()));
        List<Object[]> parametersArray = new ArrayList<>(0);
        for (String string : fixturesArray) {
            Object[] testCaseParameters = string.split(";");
            parametersArray.add(testCaseParameters);
        }
        return parametersArray;
    }

    /**
     * @param operand1 raw string with operand 1
     * @param operand2 raw string with operand 2
     * @param operation raw string with operation
     * @param result raw string with expected result of test
     * @throws Exception
     */
    public ArithmeticTestCase(String operand1,
                              String operand2,
                              String operation,
                              String result) throws Exception{
        this.operand1 = Double.parseDouble(operand1);
        this.operand2 = Double.parseDouble(operand2);
        this.operation = operation;
        this.result = Double.parseDouble(result);
    }

    @Parameter("Operand 1")
    private double operand1;

    @Parameter("Operand 2")
    private double operand2;

    @Parameter("Operation")
    private String operation;

    @Parameter("Expected result")
    private double result;

    /**
     * Performs test. Saves text attachment if something goes wrong.
     * Gets result by evaluating expression by js engine.
     * @throws Exception
     */
    @Title("Operation test")
    @Features("Arithmetic operations support")
    @Stories({"Support for arithmetic operations"})
    @Test
    public void performTest() throws Exception{
        Double result;
        try {
            switch(operation){
                case "+":{
                    result = operand1 + operand2;
                    break;
                }
                case "-":{
                    result = operand1 - operand2;
                    break;
                }
                case "/":{
                    result = operand1 / operand2;
                    break;
                }
                case "*":{
                    result = operand1 * operand2;
                    break;
                }
                default:{
                    throw new Exception("Unknown operation");
                }
            }
            Assert.assertEquals(this.result, result, ArithmeticTestCase.EPSILON);
        }
        catch (Throwable e){
            ArithmeticTestCase.saveTxtAttachment();
            throw e;
        }
    }

    /**
     * Saves text attachment for current test.
     * @return Attachment file containment.
     * @throws Exception
     */
    @Attachment(value = "Sample fixture attachment", type = "text/plain")
    static public byte[] saveTxtAttachment() throws Exception {
        URL resource = ClassLoader.getSystemResource(FIXTURES_NAME);
        if (resource == null) {
            fail(String.format("Couldn't find resource %s", FIXTURES_NAME));
        }
        return Files.readAllBytes(Paths.get(resource.toURI()));
    }
}