package com.ecmd;

public enum ECmd {
    JMP("JMP"),
    JZ("JZ"),
    SET("SET"),
    ADD("ADD"),
    SUB("SUB"),
    MUL("MUL"),
    DIV("DIV"),
    NOT("NOT"),
    AND("AND"),
    OR("OR"),
    CMPE("CMPE"),
    CMPNE("CMPNE"),
    CMPL("CMPL"),
    CMPG("CMPG"),
    OUT("OUT"),
    NULL("not a command");

    private final String text;

    ECmd(final String text) {
        this.text = text;
    }

    @java.lang.Override
    public String toString() {
        return text;
    }
}