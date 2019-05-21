package token;

import java.util.Map;

public class ConstantExpression implements Expression {
    private int number;

    public ConstantExpression(int number) {
        this.number = number;
    }

    @Override
    public int evaluate(Map<String, Integer> variables, Map<String, Function> functionMap) {
        return number;
    }

    @Override
    public String toString() {
        return String.valueOf(number);
    }
}
