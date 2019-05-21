package test;

import java.util.ArrayList;
import java.util.Arrays;

public class TestMain {
    public static void main(String[] args) {
        ArrayList<Test> tests = new ArrayList<>(Arrays.asList(new CalculationTest(), new IfExpressionTest(),
                new FunctionTest(), new ErrorTest()));

        tests.forEach(test -> System.out.println(test.test()));
    }
}
