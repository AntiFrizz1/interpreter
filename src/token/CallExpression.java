package token;

import java.util.*;
import java.util.stream.Collectors;

import exception.AbstractException;

public class CallExpression implements Expression {

    private String functionName;
    private List<Expression> expressionList;
    private int line;

    public CallExpression(String functionName, ArgumentList argumentList, int line) {
        this.functionName = functionName;
        expressionList = argumentList.getExpressionList();
        this.line = line;
    }

    @Override
    public int evaluate(Map<String, Integer> variables, Map<String, Function> functionMap) throws AbstractException {
        if (!functionMap.containsKey(functionName)) {
            throw new AbstractException("FUNCTION NOT FOUND", functionName, line);
        }
        Function function = functionMap.get(functionName);

        List<String> parameters = function.getParameters();
        if (parameters.size() != expressionList.size()) {
            throw new AbstractException("ARGUMENT NUMBER MISMATCH", functionName, line);
        }


        Map<String, Integer> newVariables = new HashMap<>(variables);
        for (int i = 0; i < parameters.size(); i++) {
            newVariables.put(parameters.get(i), expressionList.get(i).evaluate(variables, functionMap));
        }

        return function.apply(newVariables, functionMap);
    }

    @Override
    public String toString() {
        return functionName + "(" + expressionList.stream().map(Objects::toString).collect(Collectors.joining(",")) + ")";
    }
}
