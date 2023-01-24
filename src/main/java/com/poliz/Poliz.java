package com.poliz;

import com.interpretator.Interpretator;
import com.polizentry.PolizEntry;
import com.tree_handling.TreeHandler;
import com.ecmd.ECmd;
import com.entries.EntryType;
import com.readfiles.ReadFiles;
import com.writefiles.WriteFiles;

import java.util.*;

public class Poliz {

    public List<PolizEntry> allPolizEntries;

    public Poliz() {
        this.allPolizEntries = new ArrayList<PolizEntry>();
    }

    public void addNew(EntryType entryType, String lexema) {
        allPolizEntries.add(new PolizEntry(entryType, lexema));
    }

    public void addNew(PolizEntry polizEntry) {
        allPolizEntries.add(polizEntry);
    }

    public void addNew(int ind, EntryType entryType, String lexema) {
        allPolizEntries.add(ind, new PolizEntry(entryType, lexema));
    }

    public List<PolizEntry> getAllPolizEntries() {
        return allPolizEntries;
    }

    public void removeLast() {
        this.allPolizEntries.remove(this.allPolizEntries.size() - 1);
    }

    public void print() {
        WriteFiles.writeFile2(this.allPolizEntries);
        List<Double> varValues = ReadFiles.readFile1("varValues.txt");
        Interpretator interp = new Interpretator(this, varValues);
        interp.interpretatorWork();
        WriteFiles.writeFile1("interp.txt",interp.getToWrite());
    }

    public void setAllPolizEntries(List<PolizEntry> allPolizEntries) {
        this.allPolizEntries = allPolizEntries;
    }
}