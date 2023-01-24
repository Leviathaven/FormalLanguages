package com.main;

import com.lexema.*;
import com.estate.*;
import com.expression.Expression;
import com.readfiles.ReadFiles;
import com.writefiles.WriteFiles;
import com.tree_handling.TreeHandler;
import java.io.*;
import java.util.*;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Main {

    public static List<Expression> alreadyWrittenExpressions = new ArrayList<Expression>();
    public static List<String> toWrite = new ArrayList<String>();

    public static void main(String[] args)  throws IOException {
        List<Lexema> results = new ArrayList<Lexema>();
        List<String> readStructure = ReadFiles.readFile("structure.txt");

        String structureJoined = String.join(" ", readStructure);
        EState currentState = EState.S;
        boolean add = false;
        EState prevState;
        int index = 0;
        char currSymb = 'a';
        String currLexema = "";
        while((currentState != EState.E) && (currentState != EState.F) && (index <= structureJoined.length() - 1)) {
            prevState = currentState;
            add = true;
            currSymb = structureJoined.charAt(index);

            switch (currentState) {
                case S:
                    if (currSymb == ' ')
                        break;
                    else if (Character.isLetter(currSymb))
                        currentState = EState.Ai;
                    else if (Character.isDigit(currSymb))
                        currentState = EState.Ac;
                    else if (currSymb == '>')
                        currentState = EState.Ks;
                    else if (currSymb == '<')
                        currentState = EState.As;
                    else if ((currSymb == '+') || (currSymb == '-') || (currSymb == '*') || (currSymb == '\\'))
                        currentState = EState.Gs;
                    else if (currSymb == '=')
                        currentState = EState.Bs;
                    else if (currSymb == '\n')
                        currentState = EState.F;
                    else
                        currentState = EState.E;
                    add = false;
                    break;
                case Ai:
                    if (currSymb == ' ')
                        currentState = EState.S;
                    else if ((Character.isLetter(currSymb)) || (Character.isDigit(currSymb)))
                        add = false;
                    else if (currSymb == '>')
                        currentState = EState.Ks;
                    else if (currSymb == '<')
                        currentState = EState.As;
                    else if ((currSymb == '+') || (currSymb == '-') || (currSymb == '*') || (currSymb == '\\'))
                        currentState = EState.Gs;
                    else if (currSymb == '=')
                        currentState = EState.Bs;
                    else if (currSymb == ';')
                        currentState = EState.Ms;
                    else if (currSymb == '\n')
                        currentState = EState.F;
                    else {
                        currentState = EState.E;
                        add = false;
                    }
                    break;
                case Ac:
                    if (currSymb == ' ')
                        currentState = EState.S;
                    else if (Character.isDigit(currSymb))
                        add = false;
                    else if (currSymb == '>')
                        currentState = EState.Ks;
                    else if (currSymb == '<')
                        currentState = EState.As;
                    else if ((currSymb == '+') || (currSymb == '-') || (currSymb == '*') || (currSymb == '\\'))
                        currentState = EState.Gs;
                    else if (currSymb == '=')
                        currentState = EState.Bs;
                    else if (currSymb == ';')
                        currentState = EState.Ms;
                    else if (currSymb == '\n')
                        currentState = EState.F;
                    else {
                        currentState = EState.E;
                        add = false;
                    }
                    break;
                case Ks:
                    if (currSymb == ' ')
                        currentState = EState.S;
                    else if (Character.isLetter(currSymb))
                        currentState = EState.Ai;
                    else if (Character.isDigit(currSymb))
                        currentState = EState.Ac;
                    else if (currSymb == '\n')
                        currentState = EState.F;
                    else {
                        currentState = EState.E;
                        add = false;
                    }
                    break;
                case As:
                    if (currSymb == ' ')
                        currentState = EState.S;
                    else if (Character.isLetter(currSymb))
                        currentState = EState.Ai;
                    else if (Character.isDigit(currSymb))
                        currentState = EState.Ac;
                    else if (currSymb == '>') {
                        currentState = EState.Cs;
                        add = false;
                    } else if (currSymb == '\n')
                        currentState = EState.F;
                    else {
                        currentState = EState.E;
                        add = false;
                    }
                    break;
                case Bs:
                    if (currSymb == ' ')
                        currentState = EState.S;
                    else if (Character.isLetter(currSymb))
                        currentState = EState.Ai;
                    else if (Character.isDigit(currSymb))
                        currentState = EState.Ac;
                    else if (currSymb == '=') {
                        currentState = EState.Ds;
                        add = false;
                    } else if (currSymb == '\n')
                        currentState = EState.F;
                    else {
                        currentState = EState.E;
                        add = false;
                    }
                    break;
                case Ds:
                case Gs:
                case Cs:
                    if (currSymb == ' ')
                        currentState = EState.S;
                    else if (Character.isLetter(currSymb))
                        currentState = EState.Ai;
                    else if (Character.isDigit(currSymb))
                        currentState = EState.Ac;
                    else if (currSymb == '\n')
                        currentState = EState.F;
                    else {
                        currentState = EState.E;
                        add = false;
                    }
                    break;
                case Ms:
                    if (currSymb == ' ')
                        currentState = EState.S;
                    else if (Character.isLetter(currSymb))
                        currentState = EState.Ai;
                    else if (Character.isDigit(currSymb))
                        currentState = EState.Ac;
                    else {
                        currentState = EState.E;
                        add = false;
                    }
                    break;
            }

            if ((currSymb != ' ') && (currSymb != ';')) {
                currLexema += currSymb;
            }

            if (add) {
                if ((!currLexema.equals("")) && (!currLexema.equals(" ")))
                    results.add(new Lexema(currLexema, index));
                currLexema = "";
            }
            if (currSymb == ';')
            {
                results.add(new Lexema(";", index + 1));
            }
            index++;
            if (currLexema.equals("end")) {
                results.add(new Lexema(currLexema, index));
            }
        }
        if (currentState == EState.E)
            WriteFiles.writeFile("result.txt");
        else {
            WriteFiles.writeFile("result.txt", results);
            TreeHandler treeHandler = new TreeHandler(results);
            boolean result = treeHandler.workTheTree();
            if (result) {
                List<Expression> expressionsList = treeHandler.getExpressionList();

                int i = 0;
                Expression expr = expressionsList.get(expressionsList.size() - 1);
                if (expr != null) {
                    print(expr);
                }
                WriteFiles.writeFile1("tree.txt", toWrite);
            }
        }
    }

    public static void print(Expression expr) {
        if(!alreadyWrittenExpressions.contains(expr)) {
            toWrite.add(expr.toString());
            alreadyWrittenExpressions.add(expr);
            List<Expression> exprChildren = expr.getChild();
            int j = 0;
            if(exprChildren != null) {
                Expression exprChild = exprChildren.get(j);
                while (exprChild != null && j < exprChildren.size()) {
                    print(exprChild);
                    j++;
                    if (j < exprChildren.size()) {
                        exprChild = exprChildren.get(j);
                    }
                }
            }
        }
    }

    public static int compare(int a1, int a2) {
        if (a1 > a2)
            return 1;
        else
            return -1;
    }
}


