package parser;

import exception.AbstractException;
import exception.ParserException;
import lexer.Lexer;
import token.*;
import token.Number;

import java.io.ByteArrayInputStream;
import java.util.*;


public class Parser {
    private Lexer lexer;

    public Parser() {
    }

    public int parseAndEvaluate(String string) throws ParserException, AbstractException {
        Map<String, Function> functionMap;

        functionMap = new HashMap<>();
        String[] lines = string.split("\n");
        for (int i = 0; i < lines.length - 1; i++) {
            lexer = new Lexer(new ByteArrayInputStream(lines[i].getBytes()), i + 1);
            Function func = function();
            functionMap.put(func.getName(), func);
            if (lexer.getCurToken() != LexerToken.eof) {
                throw new ParserException();
            }
        }

        lexer = new Lexer(new ByteArrayInputStream(lines[lines.length - 1].getBytes()), lines.length);
        Expression expr = expression();
        if (lexer.getCurToken() != LexerToken.eof) {
            throw new ParserException();
        }

        return expr.evaluate(new HashMap<>(), functionMap);

    }

    private Number number() throws ParserException {
        if (lexer.getCurToken() != LexerToken.digit) {
            throw new ParserException();
        }

        int number = 0;
        while (lexer.getCurToken() == LexerToken.digit) {
            number = number * 10 + (lexer.getCurChar() - '0');
            lexer.nextToken();
        }

        return new Number(number);
    }

    private Identifier identifier() throws ParserException {
        if (lexer.getCurToken() != LexerToken.character) {
            throw new ParserException();
        }

        StringBuilder string = new StringBuilder();
        while (lexer.getCurToken() == LexerToken.character) {
            string.append((char) lexer.getCurChar());
            lexer.nextToken();
        }

        /*switch (lexer.getCurToken()) {

        }*/
        return new Identifier(string.toString(), lexer.getCurLine());
    }

    private ConstantExpression constantExpression() throws ParserException {
        ConstantExpression num;
        switch (lexer.getCurToken()) {
            case operation:
                if (lexer.getCurChar() != '-') {
                    throw new ParserException();
                }
                lexer.nextToken();
                if (lexer.getCurToken() != LexerToken.digit) {
                    throw new ParserException();
                }
                num = new ConstantExpression(-number().getNumber());
                break;
            case digit:
                num = new ConstantExpression(number().getNumber());
                break;
            default:
                throw new ParserException();
        }
        return num;
    }

    private BinaryExpression binaryExpression() throws ParserException {
        if (lexer.getCurToken() != LexerToken.bracket) {
            throw new ParserException();
        }

        if (lexer.getCurChar() != '(') {
            throw new ParserException();
        }

        lexer.nextToken();
        Expression first = expression();

        if (lexer.getCurToken() != LexerToken.operation) {
            throw new ParserException();
        }
        char op = (char) lexer.getCurChar();

        lexer.nextToken();
        Expression second = expression();

        if (lexer.getCurToken() != LexerToken.bracket || lexer.getCurChar() != ')') {
            throw new ParserException();
        }
        lexer.nextToken();

        return new BinaryExpression(op, first, second, lexer.getCurLine());
    }

    private ArgumentList argumentList() throws ParserException {
        //проверить когда это возможно
        List<Expression> expressionList = new ArrayList<>();
        expressionList.add(expression());
        while (lexer.getCurToken() == LexerToken.comma) {
            lexer.nextToken();
            expressionList.add(expression());
        }
        //проверить когда можно вернуть
        return new ArgumentList(expressionList);
    }

    private CallExpression callExpression(Identifier identifier) throws ParserException {
        if (lexer.getCurToken() != LexerToken.bracket || lexer.getCurChar() != '(') {
            throw new ParserException();
        }
        lexer.nextToken();

        ArgumentList argumentList = argumentList();

        if (lexer.getCurToken() != LexerToken.bracket || lexer.getCurChar() != ')') {
            throw new ParserException();
        }
        lexer.nextToken();

        return new CallExpression(identifier.getString(), argumentList, lexer.getCurLine());
    }

    private Expression expressionBetweenBrackets(char openBracket, char closeBracket) throws ParserException {
        if (lexer.getCurToken() != LexerToken.bracket || lexer.getCurChar() != openBracket) {
            throw new ParserException();
        }
        lexer.nextToken();

        Expression expr = expression();

        if (lexer.getCurToken() != LexerToken.bracket || lexer.getCurChar() != closeBracket) {
            throw new ParserException();
        }
        lexer.nextToken();
        return expr;
    }

    private IfExpression ifExpression() throws ParserException {
        Expression conditionExpression = expressionBetweenBrackets('[', ']');

        if (lexer.getCurToken() != LexerToken.questionMark) {
            throw new ParserException();
        }
        lexer.nextToken();

        Expression firstExpression = expressionBetweenBrackets('{', '}');

        if (lexer.getCurToken() != LexerToken.colon) {
            throw new ParserException();
        }
        lexer.nextToken();

        Expression secondExpression = expressionBetweenBrackets('{', '}');

        return new IfExpression(conditionExpression, firstExpression, secondExpression);

    }

    private Expression expression() throws ParserException {
        if (lexer.getCurToken() == LexerToken.digit || (lexer.getCurToken() == LexerToken.operation && lexer.getCurChar() == '-')) {
            return constantExpression();
        } else if (lexer.getCurToken() == LexerToken.bracket && lexer.getCurChar() == '(') {
            return binaryExpression();
        } else if (lexer.getCurToken() == LexerToken.character) {
            Identifier id = identifier();
            if (lexer.getCurToken() == LexerToken.bracket && lexer.getCurChar() == '(') {
                return callExpression(id);
            }
            return id;
        } else if (lexer.getCurToken() == LexerToken.bracket && lexer.getCurChar() == '[') {
            return ifExpression();
        } else {
            throw new ParserException();
        }
    }

    private ParameterList parameterList() throws ParserException {
        if (lexer.getCurToken() != LexerToken.character) {
            throw new ParserException();
        }
        List<Identifier> identifiers = new ArrayList<>();
        identifiers.add(identifier());

        while (lexer.getCurToken() == LexerToken.comma) {
            lexer.nextToken();
            identifiers.add(identifier());
        }

        return new ParameterList(identifiers);
    }

    private Function function() throws ParserException {
        if (lexer.getCurToken() != LexerToken.character) {
            throw new ParserException();
        }

        Identifier name = identifier();

        if (lexer.getCurToken() != LexerToken.bracket || lexer.getCurChar() != '(') {
            throw new ParserException();
        }

        lexer.nextToken();
        ParameterList pList = parameterList();

        if (lexer.getCurToken() != LexerToken.bracket || lexer.getCurChar() != ')') {
            throw new ParserException();
        }

        lexer.nextToken();

        if (lexer.getCurToken() != LexerToken.operation || lexer.getCurChar() != '=') {
            throw new ParserException();
        }

        lexer.nextToken();

        Expression expr = expressionBetweenBrackets('{', '}');

        return new Function(name.getString(), pList.getParameterList(), expr);
    }

}
