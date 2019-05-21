package token;

import exception.AbstractException;

import java.util.Map;

public class IfExpression implements Expression {

    private Expression conditionExpression;
    private Expression firstExpression;
    private Expression secondExpression;

    public IfExpression(Expression conditionExpression, Expression firstExpression, Expression secondExpression) {
        this.conditionExpression = conditionExpression;
        this.firstExpression = firstExpression;
        this.secondExpression = secondExpression;
    }

    @Override
    public int evaluate(Map<String, Integer> variables, Map<String, Function> functionMap) throws AbstractException {
        if (conditionExpression.evaluate(variables, functionMap) == 0) {
            return secondExpression.evaluate(variables, functionMap);
        } else {
            return firstExpression.evaluate(variables, functionMap);
        }
    }

    @Override
    public String toString() {
        return "[" + conditionExpression.toString() + "]?{" + firstExpression.toString() + "}:{" + secondExpression.toString() + "}";
    }
}
