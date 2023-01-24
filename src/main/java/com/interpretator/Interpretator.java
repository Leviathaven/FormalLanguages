package com.interpretator;

import com.ecmd.ECmd;
import com.poliz.Poliz;
import com.polizentry.PolizEntry;
import com.writefiles.WriteFiles;

import java.util.*;
import java.lang.*;

public class Interpretator {

    private Poliz poliz;
    private List<Boolean> stackBoolean;
    private List<Double> stackDouble;
    private List<String> stackVars;
    private List<Double> varStartValues;
    private HashMap<String, Double> varCurrValues;
    private List<String> allUsedVars;
    private int cnt;
    private List<String> toWrite;

    public Interpretator(Poliz poliz, List<Double> varValues) {
        this.toWrite = new ArrayList<String>();
        this.poliz = poliz;
        this.varStartValues = varValues;
        this.allUsedVars = new ArrayList<String>();
        this.varCurrValues = new HashMap<String, Double>();
        this.stackBoolean = new ArrayList<Boolean>();
        this.stackDouble = new ArrayList<Double>();
        this.stackVars = new ArrayList<String>();
        this.cnt = 0;
    }

    public List<String> getToWrite() {
        return toWrite;
    }

    public void interpretatorWork() {
        int pos = 0;
        int tmp = 0;
        List<PolizEntry> polizEntryList = this.poliz.getAllPolizEntries();
        while (pos < polizEntryList.size()) {
            PolizEntry currEntry = polizEntryList.get(pos);
            String entryType = currEntry.getEntryType().toString();
            if(entryType.equals("var")) {
                String lexema = currEntry.getLexema();
                if(!allUsedVars.contains(lexema)) {
                    this.allUsedVars.add(lexema);
                    this.varCurrValues.put(lexema, varStartValues.get(cnt));
                    cnt++;
                }
                pushValDouble(this.varCurrValues.get(lexema));
                this.stackVars.add(lexema);
                pos++;
            }
            else if(entryType.equals("command")) {
                String ecmd = currEntry.getCmdType().toString();
                switch (ecmd) {
                    case "JMP":
                        pos = polizEntryList.size();
                        break;
                    case "JZ":
                        tmp = popValDouble().intValue();
                        if (popValBool())
                            pos++;
                        else
                            pos = tmp;
                        this.stackVars = new ArrayList<String>();
                        this.stackBoolean = new ArrayList<Boolean>();
                        this.stackDouble = new ArrayList<Double>();
                        break;
                    case "SET":
                        SetVarAndPop(this.stackVars.get(0));
                        pos++;
                        this.stackVars = new ArrayList<String>();
                        break;
                    case "ADD":
                        pushValDouble(popValDouble() + popValDouble());
                        pos++;
                        break;
                    case "SUB":
                        pushValDouble(- popValDouble() + popValDouble());
                        pos++;
                        break;
                    case "MUL":
                        System.out.println(ecmd);
                        pushValDouble(popValDouble() * popValDouble());
                        pos++;
                        break;
                    case "DIV":
                        pushValDouble(1 / popValDouble() * popValDouble());
                        pos++;
                        break;
                    case "NOT":
                        pushValBool(!popValBool());
                        pos++;
                        break;
                    case "AND":
                        pushValBool(popValBool() && popValBool());
                        pos++;
                        this.stackVars = new ArrayList<String>();
                        break;
                    case "OR":
                        pushValBool(popValBool() || popValBool());
                        pos++;
                        break;
                    case "CMPE":
                        pushValBool(popValDouble() == popValDouble());
                        pos++;
                        break;
                    case "CMPNE":
                        pushValBool(popValDouble() != popValDouble());
                        pos++;
                        break;
                    case "CMPL":
                        pushValBool(popValDouble() > popValDouble());
                        pos++;
                        break;
                    case "CMPG":
                        pushValBool(popValDouble() < popValDouble());
                        pos++;
                        break;
                    case "OUT":
                        Double valueWritten = popValDouble();
                        toWrite.add(String.valueOf(valueWritten));
                        pos++;
                        break;
                    default:
                        pos++;
                        break;
                }

            }
            else {
                pushValDouble(Double.valueOf(currEntry.getLexema()));
                pos++;
            }
        }
    }

    public void SetVarAndPop(String var) {
        this.varCurrValues.put(var, popValDouble());
    }

    public Double popValDouble() {
        Double popValue = this.stackDouble.get(0);
        this.stackDouble.remove(0);
        return popValue;
    }

    public Boolean popValBool() {
        Boolean popValue = this.stackBoolean.get(0);
        this.stackBoolean.remove(0);
        return popValue;
    }

    public void pushValBool(Boolean val) {
        this.stackBoolean.add(0, val);
    }

    public void pushValDouble(Double val) {
        this.stackDouble.add(0, val);
    }

    public Poliz getPoliz() {
        return poliz;
    }

    public void setPoliz(Poliz poliz) {
        this.poliz = poliz;
    }
}