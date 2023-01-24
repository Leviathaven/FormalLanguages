package com.polizentry;

import com.lexema.Lexema;
import com.ecmd.ECmd;
import com.entries.EntryType;

public class PolizEntry {

    EntryType entryType;
    String lexema;
    ECmd cmdType;

    public PolizEntry() {

    }

    public PolizEntry(EntryType entryType, String lexema) {
        this.entryType = entryType;
        this.lexema = lexema;
        if(entryType.toString().equals("command")) {
            if (lexema.equals("JMP")) {
                this.cmdType = ECmd.JMP;
            }
            else if (lexema.equals("JZ")) {
                this.cmdType = ECmd.JZ;
            }
            else if (lexema.equals("SET")) {
                this.cmdType = ECmd.SET;
            }
            else  if (lexema.equals("ADD")) {
                this.cmdType = ECmd.ADD;
            }
            else  if (lexema.equals("SUB")) {
                this.cmdType = ECmd.SUB;
            }
            else  if (lexema.equals("MUL")) {
                this.cmdType = ECmd.MUL;
            }
            else  if (lexema.equals("DIV")) {
                this.cmdType = ECmd.DIV;
            }
            else  if (lexema.equals("NOT")) {
                this.cmdType = ECmd.NOT;
            }
            else  if (lexema.equals("AND")) {
                this.cmdType = ECmd.AND;
            }
            else  if (lexema.equals("OR")) {
                this.cmdType = ECmd.OR;
            }
            else  if (lexema.equals("CMPE")) {
                this.cmdType = ECmd.CMPE;
            }
            else  if (lexema.equals("CMPNE")) {
                this.cmdType = ECmd.CMPNE;
            }
            else  if (lexema.equals("CMPL")) {
                this.cmdType = ECmd.CMPL;
            }
            else  if (lexema.equals("CMPG")) {
                this.cmdType = ECmd.CMPG;
            }
            else if (lexema.equals("OUT")) {
                this.cmdType = ECmd.OUT;
            }
        }
        else {
            this.cmdType = ECmd.NULL;
        }
    }

    public EntryType getEntryType() {
        return entryType;
    }

    public void setEntryType(EntryType entryType) {
        this.entryType = entryType;
    }

    public String getLexema() {
        return lexema;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public ECmd getCmdType() {
        return cmdType;
    }

    public void setCmdType(ECmd cmdType) {
        this.cmdType = cmdType;
    }

    @java.lang.Override
    public java.lang.String toString() {
        if (!this.entryType.equals("command"))
            return this.lexema;
        else
            return this.cmdType.toString();
    }
}