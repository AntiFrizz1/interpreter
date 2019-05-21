package test;

import parser.Parser;

import java.util.ArrayList;
import java.util.List;

public abstract class SimpleTest implements Test {

    String[] testStrings;
    int[] testAnswers;
    private String testName;

    SimpleTest(String testName) {
        this.testName = testName;
    }

    @Override
    public String test() {
        List<String> filedTests = new ArrayList<>();
        Parser parser = new Parser();
        for (int i = 0; i < testStrings.length; i++) {
            try {
                int answer = parser.parseAndEvaluate(testStrings[i]);
                if (answer != testAnswers[i]) {
                    filedTests.add("Test: " + testStrings[i] + ". Expected: " + testAnswers[i] +
                            ". But was: " + answer + ".");
                }
            } catch (Exception e) {
                filedTests.add("Test: " + testStrings[i] + ". Expected: " + testAnswers[i] +
                        ". But was: " + e.getMessage() + ".");
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
