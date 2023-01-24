package com.tree_handling;

import com.lexema.Lexema;
import com.entries.EntryType;
import com.expression.Expression;
import com.poliz.Poliz;
import com.polizentry.PolizEntry;
import com.writefiles.WriteFiles;
import java.io.*;
import java.util.*;

public class TreeHandler {
    private List<Lexema> lexemaList;
    private List<Expression> expressionList;
    private Poliz polizHandler;
    private boolean errorTriggered;

    public TreeHandler(List<Lexema> lexemaList) {
        this.lexemaList = lexemaList;
        this.expressionList = new ArrayList<Expression>();
        this.polizHandler = new Poliz();
        this.errorTriggered = false;
    }

    public List<Expression> getExpressionList() {
        return expressionList;
    }

    public void setExpressionList(List<Expression> expressionList) {
        this.expressionList = expressionList;
    }

    public boolean isErrorTriggered() {
        return errorTriggered;
    }

    public void setErrorTriggered(boolean errorTriggered) {
        this.errorTriggered = errorTriggered;
    }

    public List<Lexema> getLexemaList() {
        return lexemaList;
    }

    public void setLexemaList(List<Lexema> lexemaList) {
        this.lexemaList = lexemaList;
    }

    public boolean workTheTree() {
        boolean res = ifStatement();
        this.polizHandler.removeLast();
        this.polizHandler.print();

        return res;
    }

    public boolean ifStatement() {
        int index = 0;
        int pointer = 0;
        int level = 0;
        Lexema currLex = new Lexema();
        Lexema prevLex = new Lexema();
        List<Lexema> exprList = new ArrayList<Lexema>();
        List<Expression> allExpressions = new ArrayList<Expression>();
        try {
            currLex = this.lexemaList.get(index);
        } catch ( IndexOutOfBoundsException e ) {
            Error("Ожидается if", 0);
            return false;
        }
        if(!currLex.getLexType().equals("if")) {
            Error("Ожидается if", 0);
            return false;
        }
        exprList.add(currLex);
        index++;
        try {
            prevLex = currLex;
            currLex = this.lexemaList.get(index);
        } catch ( IndexOutOfBoundsException e ) {
            if (!this.errorTriggered) {
                Error("Ожидается начало логического выражения", prevLex.getPosition() + prevLex.getLexema().length() + 1);
            }
            return false;
        }
        List<Integer> logicalExpr = LogicalExpr(index, level + 1);
        if(logicalExpr.get(0) == 0 && !this.errorTriggered) {
            Error("Ожидается начало логического выражения", currLex.getPosition());
            return false;
        }
        index = logicalExpr.get(1);
        allExpressions.add(this.expressionList.get(this.expressionList.size() - 1));
        try {
            prevLex = currLex;
            currLex = this.lexemaList.get(index);
        } catch ( IndexOutOfBoundsException e ) {
            if(!this.errorTriggered) {
                Error("Ожидается then", prevLex.getPosition() + prevLex.getLexema().length() + 1);
            }
            return false;
        }
        if(!currLex.getLexType().equals("then") && !this.errorTriggered) {
            Error("Ожидается then", currLex.getPosition());
            return false;
        }
        pointer = this.polizHandler.getAllPolizEntries().size() - 1;
        exprList.add(currLex);
        index++;
        try {
            prevLex = currLex;
            currLex = this.lexemaList.get(index);
        } catch ( IndexOutOfBoundsException e ) {
            if (!this.errorTriggered) {
                Error("Ожидается начало перечисления операций", prevLex.getPosition() + prevLex.getLexema().length() + 1);
            }
            return false;
        }
        List<Integer> statements = Statements(index, level + 1);
        if(statements.get(0) == 0 && !this.errorTriggered) {
            Error("Ожидается начало перечисления операций", currLex.getPosition());
            return false;
        }
        allExpressions.add(this.expressionList.get(this.expressionList.size() - 1));
        int indexOfJump = this.polizHandler.getAllPolizEntries().size();
        this.polizHandler.addNew(pointer + 1, EntryType.etCmdPtr, String.valueOf(indexOfJump + 2));
        this.polizHandler.addNew(pointer + 2, EntryType.etCmd, "JZ");
        index = statements.get(1);
        try {
            prevLex = currLex;
            currLex = this.lexemaList.get(index);
        } catch ( IndexOutOfBoundsException e ) {
            if(!this.errorTriggered) {
                Error("Ожидается elseif", prevLex.getPosition() + prevLex.getLexema().length() + 1);
            }
            return false;
        }
        while(!currLex.getLexType().equals("else")) {
            if(!currLex.getLexType().equals("elseif") && !this.errorTriggered) {
                Error("Ожидается elseif", currLex.getPosition());
                return false;
            }
            exprList.add(currLex);
            index++;
            try {
                prevLex = currLex;
                currLex = this.lexemaList.get(index);
            } catch ( IndexOutOfBoundsException e ) {
                if (!this.errorTriggered) {
                    Error("Ожидается начало логического выражения", prevLex.getPosition() + prevLex.getLexema().length() + 1);
                }
                return false;
            }
            logicalExpr = LogicalExpr(index, level + 1);
            if(logicalExpr.get(0) == 0 && !this.errorTriggered) {
                Error("Ожидается начало логического выражения", currLex.getPosition());
                return false;
            }
            allExpressions.add(this.expressionList.get(this.expressionList.size() - 1));
            index = logicalExpr.get(1);
            try {
                prevLex = currLex;
                currLex = this.lexemaList.get(index);
            } catch ( IndexOutOfBoundsException e ) {
                if(!this.errorTriggered) {
                    Error("Ожидается then", prevLex.getPosition() + prevLex.getLexema().length() + 1);
                }
                return false;
            }
            if(!currLex.getLexType().equals("then") && !this.errorTriggered) {
                Error("Ожидается then", currLex.getPosition());
                return false;
            }
            pointer = this.polizHandler.getAllPolizEntries().size() - 1;
            exprList.add(currLex);
            index++;
            try {
                prevLex = currLex;
                currLex = this.lexemaList.get(index);
            } catch ( IndexOutOfBoundsException e ) {
                if(!this.errorTriggered) {
                    Error("Ожидается начало перечисления операций", prevLex.getPosition() + prevLex.getLexema().length() + 1);
                }
                return false;
            }
            statements = Statements(index, level + 1);
            if(statements.get(0) == 0 && !this.errorTriggered) {
                Error("Ожидается начало перечисления операций", currLex.getPosition());
                return false;
            }
            allExpressions.add(this.expressionList.get(this.expressionList.size() - 1));
            indexOfJump = this.polizHandler.getAllPolizEntries().size();
            this.polizHandler.addNew(pointer + 1, EntryType.etCmdPtr, String.valueOf(indexOfJump + 2));
            this.polizHandler.addNew(pointer + 2, EntryType.etCmd, "JZ");
            index = statements.get(1);
            try {
                prevLex = currLex;
                currLex = this.lexemaList.get(index);
            } catch ( IndexOutOfBoundsException e ) {
                if(!this.errorTriggered) {
                    Error("Ожидается elseif", prevLex.getPosition() + prevLex.getLexema().length() + 1);
                }
                return false;
            }
        }
        try {
            prevLex = currLex;
            currLex = this.lexemaList.get(index);
        } catch ( IndexOutOfBoundsException e ) {
            if(!this.errorTriggered) {
                Error("Ожидается else", prevLex.getPosition() + prevLex.getLexema().length() + 1);
            }
            return false;
        }
        if(!currLex.getLexType().equals("else") && !this.errorTriggered) {
            Error("Ожидается else", currLex.getPosition());
            return false;
        }
        exprList.add(currLex);
        index++;
        try {
            prevLex = currLex;
            currLex = this.lexemaList.get(index);
        } catch ( IndexOutOfBoundsException e ) {
            if(!this.errorTriggered) {
                Error("Ожидается начало перечисления операций", prevLex.getPosition() + prevLex.getLexema().length() + 1);
            }
            return false;
        }
        statements = Statements(index, level + 1);
        if(statements.get(0) == 0 && !this.errorTriggered) {
            Error("Ожидается начало перечисления операций", currLex.getPosition());
            return false;
        }
        allExpressions.add(this.expressionList.get(this.expressionList.size() - 1));
        index = statements.get(1);
        try {
            prevLex = currLex;
            currLex = this.lexemaList.get(index);
        } catch ( IndexOutOfBoundsException e ) {
            if(!this.errorTriggered) {
                Error("Ожидается end", prevLex.getPosition() + prevLex.getLexema().length() + 1);
            }
            return false;
        }
        if(!currLex.getLexType().equals("end") && !this.errorTriggered) {
            Error("Ожидается end", currLex.getPosition());
            return false;
        }
        exprList.add(currLex);
        allExpressions.add(this.expressionList.get(this.expressionList.size() - 1));
        this.expressionList.add(new Expression(exprList, level, allExpressions));
        return true;
    }

    public List<Integer> LogicalExpr(int index, int level) {

        int index1 = index;
        List<Integer> result = new ArrayList<Integer>();
        List<Lexema> exprList = new ArrayList<Lexema>();
        List<Expression> allExpressions = new ArrayList<Expression>();
        Lexema currLex;
        try {
            currLex = this.lexemaList.get(index1);
        } catch (IndexOutOfBoundsException e) {
            result.add(0);
            result.add(index1);
            return result;
        }
        List<Integer> logExpr1 = LogExpr1(index1, level + 1);
        if (logExpr1.get(0) == 0) {
            result.add(0);
            result.add(index1);
            return result;
        }
        allExpressions.add(this.expressionList.get(this.expressionList.size() - 1));
        index1 = logExpr1.get(1);
        try {
            currLex = this.lexemaList.get(index1);
        } catch (IndexOutOfBoundsException e) {
            result.add(0);
            result.add(index1);
            return result;
        }
        while(currLex.getLexType().equals("or")) {
            exprList.add(currLex);
            logExpr1 = LogExpr1(index1 + 1, level + 1);
            if (logExpr1.get(0) == 0) {
                result.add(0);
                result.add(index1);
                return result;
            }
            this.polizHandler.addNew(EntryType.etCmd, "OR");
            allExpressions.add(this.expressionList.get(this.expressionList.size() - 1));
            index1 = logExpr1.get(1);
            try {
                currLex = this.lexemaList.get(index1);
            } catch (IndexOutOfBoundsException e) {
                result.add(0);
                result.add(index1);
                return result;
            }
        }
        result.add(1);
        this.expressionList.add(new Expression(exprList, level, allExpressions));
        result.add(index1);
        return result;
    }

    public List<Integer> LogExpr1(int index, int level) {
        int index1 = index;
        List<Integer> result = new ArrayList<Integer>();
        List<Lexema> exprList = new ArrayList<Lexema>();
        List<Expression> allExpressions = new ArrayList<Expression>();
        Lexema currLex;
        try {
            currLex = this.lexemaList.get(index1);
        } catch (IndexOutOfBoundsException e) {
            result.add(0);
            result.add(index1);
            return result;
        }
        boolean flagNot = false;
        if(currLex.getLexType().equals("not")) {
            flagNot = true;
            exprList.add(currLex);
            index1++;
            try {
                currLex = this.lexemaList.get(index1);
            } catch ( IndexOutOfBoundsException e ) {
                result.add(0);
                result.add(index1);
                return result;
            }
        }

        List<Integer> relExpr = RelExpr(index1, level + 1);
        if (relExpr.get(0) == 0) {
            result.add(0);
            result.add(index1);
            return result;
        }
        if (flagNot) {
            this.polizHandler.addNew(EntryType.etCmd, "NOT");
        }
        allExpressions.add(this.expressionList.get(this.expressionList.size() - 1));
        index1 = relExpr.get(1);
        try {
            currLex = this.lexemaList.get(index1);
        } catch (IndexOutOfBoundsException e) {
            result.add(0);
            result.add(index1);
            return result;
        }
        while(currLex.getLexType().equals("and")) {
            flagNot = false;
            exprList.add(currLex);

            if(currLex.getLexType().equals("not")) {
                flagNot = true;
                exprList.add(currLex);
                index1++;
                try {
                    currLex = this.lexemaList.get(index1);
                } catch ( IndexOutOfBoundsException e ) {
                    result.add(0);
                    result.add(index1);
                    return result;
                }
            }
            relExpr = RelExpr(index1 + 1, level + 1);
            if (relExpr.get(0) == 0) {
                result.add(0);
                result.add(index1);
                return result;
            }
            if(flagNot) {
                this.polizHandler.addNew(EntryType.etCmd, "NOT");
            }
            this.polizHandler.addNew(EntryType.etCmd, "AND");
            allExpressions.add(this.expressionList.get(this.expressionList.size() - 1));
            index1 = relExpr.get(1);
            try {
                currLex = this.lexemaList.get(index1);
            } catch (IndexOutOfBoundsException e) {
                result.add(0);
                result.add(index1);
                return result;
            }
        }
        this.expressionList.add(new Expression(exprList, level, allExpressions));
        result.add(1);
        result.add(index1);
        return result;
    }

    public List<Integer> RelExpr(int index, int level) {
        int index1 = index;
        List<Integer> result = new ArrayList<Integer>();
        List<Lexema> exprList = new ArrayList<Lexema>();
        List<Expression> allExpressions = new ArrayList<Expression>();
        Lexema currLex;
        try {
            currLex = this.lexemaList.get(index1);
        } catch (IndexOutOfBoundsException e) {
            result.add(0);
            result.add(index1);
            return result;
        }
        List<Integer> operand = Operand(index1, level + 1);
        if (operand.get(0) == 0) {
            result.add(0);
            result.add(index1);
            return result;
        }
        allExpressions.add(this.expressionList.get(this.expressionList.size() - 1));
        index1 = operand.get(1);

        try {
            currLex = this.lexemaList.get(index1);
        } catch (IndexOutOfBoundsException e) {
            result.add(0);
            result.add(index1);
            return result;
        }
        while(currLex.getLexType().equals("rel")) {
            PolizEntry relSign = new PolizEntry();
            exprList.add(currLex);
            int currLexIndex = currLex.getIndex();
            if(currLexIndex == 13) {
                relSign = new PolizEntry(EntryType.etCmd, "CMPL");
            }
            else if (currLexIndex == 14) {
                relSign = new PolizEntry(EntryType.etCmd, "CMPG");
            }
            else if (currLexIndex == 15) {
                relSign = new PolizEntry(EntryType.etCmd, "CMPNE");
            }
            else if (currLexIndex == 16) {
                relSign = new PolizEntry(EntryType.etCmd, "CMPE");
            }
            operand = Operand(index1 + 1, level + 1);
            if (operand.get(0) == 0) {
                result.add(0);
                result.add(index1);
                return result;
            }
            allExpressions.add(this.expressionList.get(this.expressionList.size() - 1));
            this.polizHandler.addNew(relSign);
            index1 = operand.get(1);
            try {
                currLex = this.lexemaList.get(index1);
            } catch (IndexOutOfBoundsException e) {
                result.add(0);
                result.add(index1);
                return result;
            }
        }

        this.expressionList.add(new Expression(exprList, level, allExpressions));
        result.add(1);
        result.add(index1);
        return result;
    }

    public List<Integer> Operand(int index, int level) {
        int index1 = index;
        Lexema currLex = new Lexema();
        List<Integer> result = new ArrayList<Integer>();
        List<Lexema> exprList = new ArrayList<Lexema>();
        try {
            currLex = this.lexemaList.get(index1);
        } catch ( IndexOutOfBoundsException e ) {
            if(!this.errorTriggered) {
                Error("Ожидается идентификатор или константа", lexemaList.get(index1 - 1).getPosition());
            }
            result.add(0);
            result.add(index1);
            return result;
        }

        if(!currLex.getLexType().equals("var") && !currLex.getLexType().equals("const")) {
            if(!this.errorTriggered) {
                Error("Ожидается идентификатор или константа", currLex.getPosition());
            }
            result.add(0);
            result.add(index1);
            return result;
        }

        exprList.add(currLex);

        if(currLex.getLexType().equals("var")) {
            this.polizHandler.addNew(EntryType.etVar, currLex.getLexema());
        }
        else {
            this.polizHandler.addNew(EntryType.etConst, currLex.getLexema());
        }

        this.expressionList.add(new Expression(exprList, level, null));
        result.add(1);
        result.add(index1 + 1);
        return result;
    }

    public List<Integer> Statements(int index, int level) {
        int index1 = index;
        List<Integer> result = new ArrayList<Integer>();
        List<Lexema> exprList = new ArrayList<Lexema>();
        List<Expression> allExpressions = new ArrayList<Expression>();
        Lexema currLex;
        try {
            currLex = this.lexemaList.get(index1);
        } catch ( IndexOutOfBoundsException e ) {
            result.add(0);
            result.add(index1);
            return result;
        }
        List<Integer> statement = Statement(index1, level + 1);
        if(statement.get(0) == 0) {
            result.add(0);
            result.add(index1);
            return result;
        }
        allExpressions.add(this.expressionList.get(this.expressionList.size() - 1));
        index1 = statement.get(1);
        try {
            currLex = this.lexemaList.get(index1);
        } catch (IndexOutOfBoundsException e) {
            result.add(0);
            result.add(index1);
            return result;
        }
        while(currLex.getLexType().equals("add")) {
            exprList.add(currLex);
            statement = Statement(index1 + 1, level + 1);
            if (statement.get(0) == 0) {
                result.add(0);
                result.add(index1);
                return result;
            }
            allExpressions.add(this.expressionList.get(this.expressionList.size() - 1));
            index1 = statement.get(1);
            try {
                currLex = this.lexemaList.get(index1);
            } catch (IndexOutOfBoundsException e) {
                result.add(0);
                result.add(index1);
                return result;
            }
        }
        this.polizHandler.addNew(EntryType.etCmd, "JMP");

        this.expressionList.add(new Expression(exprList, level, allExpressions));
        result.add(1);
        result.add(index1);
        return result;
    }

    public List<Integer> Statement(int index, int level) {
        int index1 = index;
        List<Integer> result = new ArrayList<Integer>();
        List<Lexema> exprList = new ArrayList<Lexema>();
        List<Expression> allExpressions = new ArrayList<Expression>();
        Lexema currLex = new Lexema();
        try {
            currLex = this.lexemaList.get(index1);
        } catch ( IndexOutOfBoundsException e ) {
            if(!this.errorTriggered) {
                Error("Ожидается идентификатор или output", lexemaList.get(index1 - 1).getPosition());
            }
            result.add(0);
            result.add(index1);
            return result;
        }
        if(currLex.getLexType().equals("var")) {
            exprList.add(currLex);
            this.polizHandler.addNew(EntryType.etVar, currLex.getLexema());
            index1++;
            try {
                currLex = this.lexemaList.get(index1);
            } catch ( IndexOutOfBoundsException e ) {
                if(!this.errorTriggered) {
                    Error("Ожидается знак присвоения", lexemaList.get(index1 - 1).getPosition());
                }
                result.add(0);
                result.add(index1);
                return result;
            }
            if(!currLex.getLexType().equals("as")) {
                if(!this.errorTriggered) {
                    Error("Ожидается знак присвоения", currLex.getPosition());
                }
                result.add(0);
                result.add(index1);
                return result;
            }
            exprList.add(currLex);
            index1++;
            try {
                currLex = this.lexemaList.get(index1);
            } catch ( IndexOutOfBoundsException e ) {
                if(!this.errorTriggered) {
                    Error("Ожидается арифметическое выражение", lexemaList.get(index1 - 1).getPosition());
                }
                result.add(0);
                result.add(index1);
                return result;
            }
            List<Integer> arithExpr = ArithExpr(index1, level + 1);
            if (arithExpr.get(0) == 0) {
                result.add(0);
                result.add(index1);
                return result;
            }
            allExpressions.add(this.expressionList.get(this.expressionList.size() - 1));
            this.polizHandler.addNew(EntryType.etCmd, "SET");
            this.expressionList.add(new Expression(exprList, level, allExpressions));
            index1 = arithExpr.get(1);
            result.add(1);
            result.add(index1);
            return result;
        }
        else if(currLex.getLexType().equals("output")) {
            exprList.add(currLex);
            index1++;
            try {
                currLex = this.lexemaList.get(index1);
            } catch ( IndexOutOfBoundsException e ) {
                if(!this.errorTriggered) {
                    Error("Ожидается идентификатор или константа", lexemaList.get(index1 - 1).getPosition());
                }
                result.add(0);
                result.add(index1);
                return result;
            }
            List<Integer> operand = Operand(index1, level + 1);
            if (operand.get(0) == 0) {
                result.add(0);
                result.add(index1);
                return result;
            }
            allExpressions.add(this.expressionList.get(this.expressionList.size() - 1));
            this.polizHandler.addNew(EntryType.etCmd, "OUT");
            this.expressionList.add(new Expression(exprList, level, allExpressions));
            index1 = operand.get(1);
            result.add(1);
            result.add(index1);
            return result;
        }
        else {
            if(!this.errorTriggered) {
                Error("Ожидается идентификатор или output", lexemaList.get(index1 - 1).getPosition());
            }
            result.add(0);
            result.add(index1);
            return result;
        }

    }

    public List<Integer> ArithExpr(int index, int level) {
        int index1 = index;
        List<Integer> result = new ArrayList<Integer>();
        List<Lexema> exprList = new ArrayList<Lexema>();
        List<Expression> allExpressions = new ArrayList<Expression>();
        Lexema currLex;
        try {
            currLex = this.lexemaList.get(index1);
        } catch (IndexOutOfBoundsException e) {
            result.add(0);
            result.add(index1);
            return result;
        }

        List<Integer> arithExpr1 = ArithExpr1(index1, level + 1);
        if (arithExpr1.get(0) == 0) {
            result.add(0);
            result.add(index1);
            return result;
        }
        allExpressions.add(this.expressionList.get(this.expressionList.size() - 1));
        index1 = arithExpr1.get(1);
        try {
            currLex = this.lexemaList.get(index1);
        } catch (IndexOutOfBoundsException e) {
            result.add(0);
            result.add(index1);
            return result;
        }
        while(currLex.getLexType().equals("ao1")) {
            exprList.add(currLex);
            PolizEntry arSign = new PolizEntry();
            int currLexIndex = currLex.getIndex();
            if(currLexIndex == 9) {
                arSign = new PolizEntry(EntryType.etCmd, "ADD");
            }
            else if (currLexIndex == 10) {
                arSign = new PolizEntry(EntryType.etCmd, "SUB");
            }
            arithExpr1 = ArithExpr1(index1 + 1, level + 1);
            if (arithExpr1.get(0) == 0) {
                result.add(0);
                result.add(index1);
                return result;
            }
            this.polizHandler.addNew(arSign);
            allExpressions.add(this.expressionList.get(this.expressionList.size() - 1));
            index1 = arithExpr1.get(1);
            try {
                currLex = this.lexemaList.get(index1);
            } catch (IndexOutOfBoundsException e) {
                result.add(0);
                result.add(index1);
                return result;
            }
        }
        this.expressionList.add(new Expression(exprList, level, allExpressions));
        result.add(1);
        result.add(index1);
        return result;
    }

    public List<Integer> ArithExpr1(int index, int level) {
        int index1 = index;
        List<Integer> result = new ArrayList<Integer>();
        List<Lexema> exprList = new ArrayList<Lexema>();
        List<Expression> allExpressions = new ArrayList<Expression>();
        Lexema currLex = new Lexema();
        try {
            currLex = this.lexemaList.get(index1);
        } catch (IndexOutOfBoundsException e) {
            result.add(0);
            result.add(index1);
            return result;
        }

        List<Integer> operand = Operand(index1, level + 1);
        if (operand.get(0) == 0) {
            result.add(0);
            result.add(index1);
            return result;
        }
        allExpressions.add(this.expressionList.get(this.expressionList.size() - 1));
        index1 = operand.get(1);
        try {
            currLex = this.lexemaList.get(index1);
        } catch (IndexOutOfBoundsException e) {
            result.add(0);
            result.add(index1);
            return result;
        }
        while(currLex.getLexType().equals("ao2")) {
            exprList.add(currLex);
            PolizEntry arSign = new PolizEntry();
            int currLexIndex = currLex.getIndex();
            if(currLexIndex == 11) {
                arSign = new PolizEntry(EntryType.etCmd, "MUL");
            }
            else if (currLexIndex == 12) {
                arSign = new PolizEntry(EntryType.etCmd, "DIV");
            }
            operand = Operand(index1 + 1, level + 1);
            if (operand.get(0) == 0) {
                result.add(0);
                result.add(index1);
                return result;
            }
            this.polizHandler.addNew(arSign);
            allExpressions.add(this.expressionList.get(this.expressionList.size() - 1));
            index1 = operand.get(1);
            try {
                currLex = this.lexemaList.get(index1);
            } catch (IndexOutOfBoundsException e) {
                result.add(0);
                result.add(index1);
                return result;
            }
        }
        this.expressionList.add(new Expression(exprList, level, allExpressions));
        result.add(1);
        result.add(index1);
        return result;
    }

    public void Error(String message, int position) {
        WriteFiles.writeFile("tree.txt", message + " в позиции " + position);
        this.errorTriggered = true;
    }

}