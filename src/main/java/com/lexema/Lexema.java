package com.lexema;

import com.elextype.ELexType;
import java.util.*;

public class Lexema {
    private String lexema;
    private ELexType lexType;
    private int index;
    private int position;

    public Lexema() {

    }

    public Lexema(String lexema, int position) {
        List<String> allVariants = Arrays.asList("if", "then", "elseif", "else", "not"
                                                 ,"and", "or", "output", "end", "+", "-", "*", "\\"
                                                 ,"<",">","<>","==","=",";");
        this.lexema = lexema;
        List<String> allArithmetic1 = Arrays.asList("+", "-");
        List<String> allArithmetic2 = Arrays.asList("*", "\\");
        List<String> allLogic = Arrays.asList(">", "<", "<>", "==");
        if (lexema.equals("if"))
            this.lexType = ELexType.lIf;
        else if (lexema.equals("then"))
            this.lexType = ELexType.lThen;
        else if (lexema.equals("elseif"))
            this.lexType = ELexType.lElseif;
        else if (lexema.equals("else"))
            this.lexType = ELexType.lElse;
        else if (lexema.equals("not"))
            this.lexType = ELexType.lNot;
        else if (lexema.equals("and"))
            this.lexType = ELexType.lAnd;
        else if (lexema.equals("or"))
            this.lexType = ELexType.lOr;
        else if (lexema.equals("output"))
            this.lexType = ELexType.lOutput;
        else if (lexema.equals("end"))
            this.lexType = ELexType.lEnd;
        else if (allArithmetic1.contains(lexema))
            this.lexType = ELexType.lAo1;
        else if (allArithmetic2.contains(lexema))
            this.lexType = ELexType.lAo2;
        else if (allLogic.contains(lexema))
            this.lexType = ELexType.lRel;
        else if (lexema.equals("="))
            this.lexType = ELexType.lAs;
        else if (lexema.matches("[-+]?\\d+"))
            this.lexType = ELexType.lConst;
        else if (lexema.equals(";"))
            this.lexType = ELexType.lAdd;
        else
            this.lexType = ELexType.lVar;
        int posInAllVars = allVariants.indexOf(lexema);
        this.index = posInAllVars;
        this.position = position;
    }

    public String getLexema() {
        return lexema;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public String getLexType() {
        return lexType.toString();
    }

    public void setLexType(ELexType lexType) {
        this.lexType = lexType;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @java.lang.Override
    public java.lang.String toString() {
        if (index != -1) {
            return  "lexema='" + lexema + "\'" +
                    ", lexType=" + lexType +
                    ", index=" + index +
                    ", position=" + position;
        }
        else {
            return "lexema='" + lexema + "\'" +
                    ", lexType=" + lexType +
                    ", index=" + "" +
                    ", position=" + position;
        }
    }
}