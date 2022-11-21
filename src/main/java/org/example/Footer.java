package org.example;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Footer {
    public String footer;
    public Integer number_of_trade_and_extrd_structures, number_of_characters_in_trade_and_extrd_structures;
    public List<String> Footer_extract = new ArrayList<>();
    public int line;

    public Footer() {
        for (int i = 0; i < File_import.lines.size(); i++) {
            if (File_import.lines.get(i).startsWith("FOOTR")) {
                line = i;

                footer = File_import.lines.get(i).substring(0, 5);
                number_of_trade_and_extrd_structures = Integer.parseInt(File_import.lines.get(i).substring(5, 15));
                number_of_characters_in_trade_and_extrd_structures = Integer.parseInt(File_import.lines.get(i).substring(15, 25));

                Footer_extract.add(footer);
                Footer_extract.add(String.valueOf(number_of_trade_and_extrd_structures));
                Footer_extract.add(String.valueOf(number_of_characters_in_trade_and_extrd_structures));
            }
        }
    }

    //create footer csv
    public void Footer_Csv() {
        try {
            FileWriter out = new FileWriter("Footer.csv");
            CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT.builder().setHeader("Tag","Number of TRADE and EXTRD structures","Number of characters in TRADE and EXTRD structures").build());
            printer.printRecord(Footer_extract);
            printer.flush();
            out.close();
        } catch (IOException e){
            System.out.println("A fatal error regarding on CSV!!");
            throw new RuntimeException(e);
        }
    }
}