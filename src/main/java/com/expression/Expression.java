package com.expression;

import com.lexema.Lexema;

import java.util.*;

public class Expression {
    private List<Lexema> lexemasInExpression;
    int level;
    private List<Expression> child;

    public Expression(List<Lexema> lexemas, int level, List<Expression> child) {
        this.lexemasInExpression = lexemas;
        this.level = level;
        this.child = child;
    }

    public List<Expression> getChild() {
        return child;
    }

    public void setChild(List<Expression> child) {
        this.child = child;
    }

    public List<Lexema> getLexemasInExpression() {
        return lexemasInExpression;
    }

    public void setLexemasInExpression(List<Lexema> lexemasInExpression) {
        this.lexemasInExpression = lexemasInExpression;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @java.lang.Override
    public java.lang.String toString() {
        String connectLexemas = "";
        for(Lexema lex: this.lexemasInExpression) {
            connectLexemas += lex.getLexema() + " ";
        }
        if (connectLexemas.equals(""))
            connectLexemas = "specialexpr";
        if (child != null) {
            return "Expression{" +
                    connectLexemas +
                    ", level=" + level +
                    ", child=" + child.toString() +
                    '}';
        }
        else {
            return "Expression{" +
                    connectLexemas +
                    ", level=" + level +
                    ", child=" + "null" +
                    '}';
        }
    }
}