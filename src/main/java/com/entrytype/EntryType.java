package com.entries;

public enum EntryType {
    etCmd("command"),
    etVar("var"),
    etConst("const"),
    etCmdPtr("command pointer");

    private final String text;

    EntryType(final String text) {
        this.text = text;
    }

    @java.lang.Override
    public String toString() {
        return text;
    }
}