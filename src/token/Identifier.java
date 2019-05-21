package token;

import java.util.Map;

import exception.AbstractException;

public class Identifier implements Expression {
    private String string;
    private int line;

    public Identifier(String s, int line) {
        this.line = line;
        string = s;
    }

    public String getString() {
        return string;
    }

    @Override
    public int evaluate(Map<String, Integer> variables, Map<String, Function> functionMap) throws AbstractException {
        if (!variables.containsKey(string)) {
            throw new AbstractException("PARAMETER NOT FOUND", string, line);
        } else {
            return variables.get(string);
        }
    }

    @Override
    public String toString() {
        return string;
    }
}
