package my.company.tests;

import ru.yandex.qatools.allure.annotations.Parameter;
import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.annotations.Attachment;

import org.junit.Assert;
import static org.junit.Assert.fail;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;

@RunWith(Parameterized.class)
@Description("This is an example test suite")
public class ArithmeticTestBase {

    private static final double EPSILON = 1e-10;
    static String FIXTURES_NAME = "";

    public static Iterable<Object[]> data() throws Exception{
        ArithmeticTestBase.saveTxtAttachment();
        URL dir_url = ClassLoader.getSystemResource(FIXTURES_NAME);
        List<String> fixturesArray = Files.readAllLines(Paths.get(dir_url.toURI()));
        List<Object[]> parametersArray = new ArrayList<>(0);
        for (String string : fixturesArray) {
            Object[] testCaseParameters = string.split(";");
            parametersArray.add(testCaseParameters);
        }
        return parametersArray;
    }

    ArithmeticTestBase(String operand1,
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

    public void performTest() throws Exception{
        try {
            String expression = Double.toString(operand1) + operation + Double.toString(operand2);
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("js");
            Double result = (Double) engine.eval(expression);
            Assert.assertEquals(this.result, result, ArithmeticTestBase.EPSILON);
        }
        catch (Throwable e){
            ArithmeticTestBase.saveTxtAttachment();
            throw e;
        }
    }

    @Attachment(value = "Sample fixture attachment", type = "text/plain")
    static public byte[] saveTxtAttachment() throws Exception {
        URL resource = ClassLoader.getSystemResource(FIXTURES_NAME);
        if (resource == null) {
            fail(String.format("Couldn't find resource %s", FIXTURES_NAME));
        }
        return Files.readAllBytes(Paths.get(resource.toURI()));
    }
}