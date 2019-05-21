package token;

import java.util.List;
import java.util.stream.Collectors;

public class ParameterList {

    private List<String> parameterList;

    public ParameterList(List<Identifier> identifierList) {
        parameterList = identifierList.stream().map(Identifier::getString).collect(Collectors.toList());
    }

    public List<String> getParameterList() {
        return parameterList;
    }
}
