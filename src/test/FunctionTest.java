package test;

public class FunctionTest extends SimpleTest {
    public FunctionTest() {
        super("FunctionTest");
        this.testStrings = new String[]{
                "g(x)={(f(x)+f((x/2)))}\n" +
                "f(x)={[(x>1)]?{(f((x-1))+f((x-2)))}:{x}}\n" +
                "g(10)",

                "gcd(a,b)={[(((a=0)+(b=0))>0)]?{(a+b)}:{[(a>b)]?{gcd((a%b),b)}:{gcd(a,(b%a))}}}\n" +
                "gcd(123132,12123112)",

                "factorial(a)={[(a>0)]?{(a*factorial((a-1)))}:{1}}\n" +
                "factorial(10)",

                "sqr(a)={(a*a)}\n" +
                "power(a,b)={[(b>0)]?{(a*power(a,(b-1)))}:{1}}\n" +
                "(sqr(3)*power(2,10))",

                "x(x)={(x*x)}\n" +
                "y(x)={(x*x(x))}\n" +
                "z_(x)={(x*y(x))}\n" +
                "z_(10)",

                "F(a,b)={(c*a)}\n" +
                "g(c,d)={(1/F(1,2,3))}\n" +
                "(1+(2+3))",

                "lcm(a,b)={((a*b)/gcd(a,b))}\n" +
                "gcd(a,b)={[(((a=0)+(b=0))>0)]?{(a+b)}:{[(a>b)]?{gcd((a%b),b)}:{gcd(a,(b%a))}}}\n" +
                "lcm(25,35)",

                "sqr(a)={(a*a)}\n" +
                "binpow(a,n)={[(n=0)]?{1}:{[((n%2)=1)]?{(binpow(a,(n-1))*a)}:{sqr(binpow(a,(n/2)))}}}\n" +
                "binpow(2,10)"
        };

        this.testAnswers = new int[]{
                60,
                4,
                3628800,
                9216,
                10000,
                6,
                175,
                1024
        };
    }

}
