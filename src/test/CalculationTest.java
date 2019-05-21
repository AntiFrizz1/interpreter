package test;

public class CalculationTest extends SimpleTest {
    public CalculationTest() {
        super("CalculationTest");
        this.testStrings = new String[] {
                "(12+54)",
                "(5-78)",
                "(-10*-111)",
                "(12/8)",
                "(30%24)",
                "(30>12)",
                "(15<24)",
                "(12=13)",
                "(1+(2+(3+(4+5))))",
                "((((1+2)+3)+4)+5)",
                "(((12%3)*3)+((3/2)*4))",
                "((2*(12>1))+((3/2)*4))",
                "(12345679*9)",
                "((12345679*72)/8)",
                "((12345678-12345679)/-1)"
        };

        this.testAnswers = new int[]{
                66,
                -73,
                1110,
                1,
                6,
                1,
                1,
                0,
                15,
                15,
                4,
                6,
                111111111,
                111111111,
                1
        };
    }
}
