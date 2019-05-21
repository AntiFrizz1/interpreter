package token;

import java.util.List;

public class ArgumentList {
    private  List<Expression> expressionList;

    public ArgumentList(List<Expression> expressionList) {
        this.expressionList = expressionList;
    }

    List<Expression> getExpressionList() {
        return expressionList;
    }
}
