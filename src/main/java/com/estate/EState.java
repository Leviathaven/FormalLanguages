package com.estate;

public enum EState {
    S("S"),
    Ai("Ai"),
    Ac("Ac"),
    As("As"),
    Bs("Bs"),
    Cs("Cs"),
    Ds("Ds"),
    Gs("Gs"),
    Ks("Ks"),
    Ms("Ms"),
    E("E"),
    F("F");

    private final String text;

    EState(final String text) {
        this.text = text;
    }

    @java.lang.Override
    public String toString() {
        return text;
    }
}