package test;

import exception.AbstractException;
import exception.ParserException;
import parser.Parser;

import java.util.ArrayList;
import java.util.List;

public class ErrorTest implements Test {

    private String[] testStrings = new String[] {
            "1 + 2 + 3 + 4",

            "(1+--3)",

            "(1+2)\n" +
            "g(x)={(x+1)}",

            "g(x,y,z)={(r+e)}\n" +
            "g(1,2,3)",

            "g(x,y)={g(x,p)}\n" +
            "g(1,2,3)",

            "f(x)={x}\n" +
            "g(x)={(x+1)}\n" +
            "z(x)={g(f(p(x)))}\n" +
            "z(1)",

            "g(x)={F(x)}\n" +
            "g(1)",

            "g(x)={(x+1)}\n" +
            "g(1,2)",

            "f(x)={x}\n" +
            "g(x,y)={f(x,y)}\n" +
            "g(1,2)",

            "g(a,b)={(a/b)}\n" +
            "g(10,0)",

            "f(x)={(x*(x+2))}\n" +
            "g(y)={(y/f(y))}\n" +
            "g(0)",

            "a",

            "(1%0)",

            "f(x,y)={0}\n" +
            "(1/f(1,1))"
    };

    private Exception[] testAnswers = new Exception[] {
            new ParserException(),
            new ParserException(),
            new ParserException(),
            new AbstractException("PARAMETER NOT FOUND", "r", 1),
            new AbstractException("ARGUMENT NUMBER MISMATCH", "g", 2),
            new AbstractException("FUNCTION NOT FOUND", "p", 3),
            new AbstractException("FUNCTION NOT FOUND", "F", 1),
            new AbstractException("ARGUMENT NUMBER MISMATCH", "g", 2),
            new AbstractException("ARGUMENT NUMBER MISMATCH", "f", 2),
            new AbstractException("RUNTIME ERROR", "(a/b)", 1),
            new AbstractException("RUNTIME ERROR", "(y/f(y))", 2),
            new AbstractException("PARAMETER NOT FOUND", "a", 1),
            new AbstractException("RUNTIME ERROR", "(1%0)", 1),
            new AbstractException("RUNTIME ERROR", "(1/f(1,1))", 2)
    };
    private String testName = "ErrorTest";

    public ErrorTest() {
        this.testName = testName;
    }

    @Override
    public String test() {
        List<String> filedTests = new ArrayList<>();
        Parser parser = new Parser();
        for (int i = 0; i < testStrings.length; i++) {
            try {
                int answer = parser.parseAndEvaluate(testStrings[i]);
                filedTests.add("Test: " + testStrings[i] + ". Expected: " + testAnswers[i].getMessage() +
                        ". But was: " + answer + ".");
            } catch (Exception e) {
                if (!e.getMessage().equals(testAnswers[i].getMessage())) {
                    filedTests.add("Test: " + testStrings[i] + ". Expected: " + testAnswers[i].getMessage() +
                            ". But was: " + e.getMessage() + ".");
                }
            }
        }
        String report;
        if (filedTests.isEmpty()) {
            report = testName + ": All test passed";
        } else {
            report = testName + ": " + (testAnswers.length - filedTests.size()) + " from " +
                    testAnswers.length + " passed\n" + String.join("\n", filedTests);
        }
        return report;
    }
}
