package com.writefiles;

import com.lexema.Lexema;
import com.polizentry.PolizEntry;
import java.io.*;
import java.util.*;

public class WriteFiles {

    public static void writeFile(String path, List<Lexema> toWrite) {

        try(FileWriter writer = new FileWriter(path, false))
        {
            List<Lexema> identificators = new ArrayList<Lexema>();
            List<Lexema> constants = new ArrayList<Lexema>();
            writer.write("ALL LEXEMAS LIST:" + "\n");
            for (Lexema lex: toWrite) {
                writer.write(lex + "\n");
                if (lex.getLexType().equals("var")) {
                    if (lex.getLexema().matches("[a-zA-Z]+")) {
                        identificators.add(lex);
                    }
                }
                else if (lex.getLexType().equals("const"))
                    constants.add(lex);
            }
            writer.write("\n");
            writer.write("ALL IDENTIFICATORS LIST:" + "\n");
            for (Lexema lex: identificators) {
                writer.write(lex + "\n");
            }
            writer.write("\n");
            writer.write("ALL CONSTANTS LIST:" + "\n");
            for (Lexema lex: constants) {
                writer.write(lex + "\n");
            }
            writer.close();
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }

    }

    public static void writeFile(String path) {

        try(FileWriter writer = new FileWriter(path, false))
        {
            writer.write("There is a mistake in the structure");
            writer.close();
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }

    }

    public static void writeFile(String path, String toWrite) {

        try(FileWriter writer = new FileWriter(path, false))
        {
            writer.write(toWrite);
            writer.close();
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }

    }

    public static void writeFile1(String path, List<String> toWrite) {

        try(FileWriter writer = new FileWriter(path, false))
        {
            for(String line: toWrite) {
                writer.write(line + "\n");
            }
            writer.close();
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }

    }

    public static void writeFile2(List<PolizEntry> polizEntryList) {
        try(FileWriter writer = new FileWriter("poliz.txt", false))
        {
            for(PolizEntry polizEntry: polizEntryList) {
                writer.write(polizEntry.toString() + " ");
            }
            writer.close();
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
    }


}