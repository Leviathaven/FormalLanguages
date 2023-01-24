package com.elextype;

public enum ELexType {
    lIf("if"),
    lThen("then"),
    lElseif("elseif"),
    lElse("else"),
    lNot("not"),
    lAnd("and"),
    lOr("or"),
    lOutput("output"),
    lEnd("end"),
    lRel("rel"),
    lAo1("ao1"),
    lAo2("ao2"),
    lAs("as"),
    lVar("var"),
    lAdd("add"),
    lConst("const");

    private final String text;

    ELexType(final String text) {
        this.text = text;
    }

    @java.lang.Override
    public String toString() {
        return text;
    }
}