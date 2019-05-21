package main;

import exception.AbstractException;
import exception.ParserException;
import parser.Parser;

public class Main {
    public static void main(String[] args) {
        // insert your code hear
        String programText =
                "lcm(a,b)={((a*b)/gcd(a,b))}\n" +
                "gcd(a,b)={[(((a=0)+(b=0))>0)]?{(a+b)}:{[(a>b)]?{gcd((a%b),b)}:{gcd(a,(b%a))}}}\n" +
                "lcm(25,35)";
        Parser parser = new Parser();
        try {
            System.out.println(parser.parseAndEvaluate(programText));
        } catch (AbstractException | ParserException e) {
            System.out.println(e.getMessage());
        }
    }
}
