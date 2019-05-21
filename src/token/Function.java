package token;

import java.util.*;

import exception.AbstractException;

public class Function {

    private String name;
    private List<String> parameters;
    private Expression expression;

    public Function(String name, List<String> parameters, Expression expression) {
        this.name = name;
        this.parameters = parameters;
        this.expression = expression;
    }

    public String getName() {
        return name;
    }

    List<String> getParameters() {
        return parameters;
    }

    int apply(Map<String, Integer> variables, Map<String, Function> functionMap) throws AbstractException {
        return expression.evaluate(variables, functionMap);
    }
}
