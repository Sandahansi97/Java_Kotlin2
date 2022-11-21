package org.example;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        //Import File
        File_import import_file = new File_import();
        import_file.file_Reader();

        Header read_header = new Header();
        System.out.println(read_header.extractHeadr);
        read_header.Header_Csv();

        Trade tradeRead1 = new Trade();
        for (List<String> st : tradeRead1.extractTrade) {
            System.out.println(st);
        }
        tradeRead1.Trade_Csv();

        Footer footerRead1 = new Footer();
        System.out.println(footerRead1.Footer_extract);
        footerRead1.Footer_Csv();
    }
}
