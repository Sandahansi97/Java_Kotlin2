package org.example;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;


public class Header {

    public String tag_header;
    public String file_version = "";
    public LocalDateTime file_creation_date_Time;
    public String file_comment;
    public List<String> extractHeadr = new ArrayList<>();

    public int line;

    public Header() {

        for (int i = 0; i < File_import.lines.size(); i++) {
            if (File_import.lines.get(i).startsWith("HEADR")) {

                line = i;
                tag_header = File_import.lines.get(line).substring(0, 5);

                if ((File_import.lines.get(line).startsWith("0004", 5)) | File_import.lines.get(line).startsWith("0005", 5)) {
                    file_version = File_import.lines.get(line).substring(5, 9);
                }

                String tmpDateTime = File_import.lines.get(line).substring(9, 26);
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
                    file_creation_date_Time = LocalDateTime.parse(tmpDateTime, formatter);
                } catch (DateTimeParseException e) {
                    e.printStackTrace();
                }

                file_comment = File_import.lines.get(line).substring(26);
                extractHeadr.add(tag_header);
                extractHeadr.add(file_version);
                extractHeadr.add(file_creation_date_Time.toString());
                extractHeadr.add(file_comment);
            }
        }
    }
    public void Header_Csv() {
        try {
            FileWriter out = new FileWriter("Header.csv");
            CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT.builder().setHeader("Tag", "File Version", "File created date and time", "File comment").build());
            printer.printRecord(extractHeadr);
            printer.flush();
            out.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}

