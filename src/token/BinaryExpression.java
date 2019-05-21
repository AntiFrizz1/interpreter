package token;

import java.util.Map;

import exception.AbstractException;

public class BinaryExpression implements Expression {
    private char op;
    private Expression first;
    private Expression second;
    private int line;

    public BinaryExpression(char op, Expression first, Expression second, int line) {
        this.op = op;
        this.first = first;
        this.second = second;
        this.line = line;
    }

    private int doOperation(char op, int first, int second) throws AbstractException {
        switch (op) {
            case '+':
                return first + second;
            case '*':
                return first * second;
            case '-':
                return first - second;
            case '/':
                if (second == 0) {
                    throw new AbstractException("RUNTIME ERROR", toString(), line);
                }
                return first / second;
            case '%':
                if (second == 0) {
                    throw new AbstractException("RUNTIME ERROR", toString(), line);
                }
                return first % second;
            case '=':
                return first == second ? 1 : 0;
            case '<':
                return first < second ? 1 : 0;
            case '>':
                return first > second ? 1 : 0;
            default:
                throw new AbstractException("RUNTIME ERROR", toString(), line);
        }
    }

    @Override
    public int evaluate(Map<String, Integer> variables, Map<String, Function> functionMap) throws AbstractException {
        return doOperation(op, first.evaluate(variables, functionMap), second.evaluate(variables, functionMap));
    }

    @Override
    public String toString() {
        return "(" + first.toString() + op + second.toString() + ")";
    }
}
