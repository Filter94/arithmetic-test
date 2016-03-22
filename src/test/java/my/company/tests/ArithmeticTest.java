package my.company.tests;

import ru.yandex.qatools.allure.annotations.Parameter;
import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.annotations.Title;
import ru.yandex.qatools.allure.annotations.Attachment;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.fail;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

@RunWith(Parameterized.class)
@Description("This is an example test suite")
public class ArithmeticTest {
    private static final double EPSILON = 1e-10;
    private static final String FIXTURES_NAME = "fixture.txt";
    private static final int OPERAND_1_POSITION = 0;
    private static final int OPERAND_2_POSITION = 1;
    private static final int OPERATOR_POSITION = 2;
    private static final int RESULT_POSITION = 3;

    @Parameters(name = "Parameters name")
    public static Iterable<Object[]> data() throws Exception{
        URL dir_url = ClassLoader.getSystemResource(FIXTURES_NAME);
        List<String> fixturesArray = Files.readAllLines(Paths.get(dir_url.toURI()));
        ArrayList<Object[]> parametersArray = new ArrayList<>(0);
        for (String string: fixturesArray){
            String[] splitted = string.split(";");
            double operand1 = Double.parseDouble(splitted[OPERAND_1_POSITION]);
            double operand2 = Double.parseDouble(splitted[OPERAND_2_POSITION]);
            String operator = splitted[OPERATOR_POSITION];
            double result = Double.parseDouble(splitted[RESULT_POSITION]);
            Object[] testCaseParameters = {
                    operand1,
                    operand2,
                    operator,
                    result,
            };
            parametersArray.add(testCaseParameters);
        }
        return parametersArray;
    }

    public ArithmeticTest(double operand1,
                          double operand2,
                          String operation,
                          double result) {
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.operation = operation;
        this.result = result;
    }

    @Parameter("Operand 1")
    private double operand1;

    @Parameter("Operand 2")
    private double operand2;

    @Parameter("Operation")
    private String operation;

    @Parameter("Expected result")
    private double result;

    @Title("Operation test")
    @Test
    public void performTest() throws Exception{
        String expression = Double.toString(operand1) + operation + Double.toString(operand2);
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");
        Double result = Double.NaN;
        try {
            result = (Double) engine.eval(expression);
        }
        catch (ScriptException e) {
            fail("Fatal evaluation error. Message: " + e.getMessage());
        }
        Assert.assertEquals(this.result, result,  ArithmeticTest.EPSILON);
        this.saveTxtAttachment();
    }

    @Attachment(value = "Sample fixture attachment", type = "text")
    public byte[] saveTxtAttachment() throws URISyntaxException, IOException, NullPointerException {
        URL resource = getClass().getClassLoader().getResource("fixture.txt");
        if (resource == null) {
            fail("Couldn't find resource 'fixture.txt'");
        }
        return Files.readAllBytes(Paths.get(resource.toURI()));
    }
}