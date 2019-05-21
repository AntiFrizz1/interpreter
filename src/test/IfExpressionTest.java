package test;

public class IfExpressionTest extends SimpleTest {
    public IfExpressionTest() {
        super("IfExpressionTest");
        this.testStrings = new String[]{
                "[(11>12)]?{11}:{12}",
                "[((1+(3*4))>9)]?{(25*3)}:{(8*(13-9))}",
                "[(((1+2)>10)>(1-12))]?{(11+(12+3))}:{1}",
                "[((10+20)>(20+10))]?{1}:{0}"
        };

        this.testAnswers = new int[]{
                12,
                75,
                26,
                0
        };
    }

}
