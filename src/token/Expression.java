package token;

import java.util.Map;

import exception.AbstractException;

public interface Expression {

    int evaluate(Map<String, Integer> variables, Map<String, Function> functionMap) throws AbstractException;
}
