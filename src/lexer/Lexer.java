package lexer;

import exception.ParserException;
import token.LexerToken;

import java.io.IOException;
import java.io.InputStream;

public class Lexer {
    private InputStream inputStream;
    private int curChar;
    private LexerToken curToken;
    private int curLine = 0;

    public Lexer(InputStream is, int line) throws ParserException {
        inputStream = is;
        nextToken();
        this.curLine = line;
    }

    private void nextChar() throws ParserException {
        try {
            curChar = inputStream.read();
        } catch (IOException e) {
            throw new ParserException();
        }
    }

    public int getCurLine() {
        return curLine;
    }

    public int getCurChar() {
        return curChar;
    }

    public LexerToken getCurToken() {
        return curToken;
    }

    public void nextToken() throws ParserException {
        nextChar();
        if (curChar >= '0' && curChar <= '9') {
            curToken = LexerToken.digit;
        } else if ((curChar >= 'a' && curChar <= 'z') || (curChar >= 'A' && curChar <= 'Z') || curChar == '_') {
            curToken = LexerToken.character;
        } else if (curChar == '+' || curChar == '-' || curChar == '*' || curChar == '/' || curChar == '%' ||
                curChar == '>' || curChar == '<' || curChar == '=') {
            curToken = LexerToken.operation;
        } else if (curChar == '(' || curChar == ')' || curChar == '[' || curChar == ']' || curChar == '{' ||
                curChar == '}') {
            curToken = LexerToken.bracket;
        } else if (curChar == ',') {
            curToken = LexerToken.comma;
        } else if (curChar == '?') {
            curToken = LexerToken.questionMark;
        } else if (curChar == ':') {
            curToken = LexerToken.colon;
        } else if (curChar == '\n') {
            curToken = LexerToken.eol;
        } else if (curChar == -1) {
            curToken = LexerToken.eof;
        } else {
            curToken = LexerToken.error;
        }

    }

}
