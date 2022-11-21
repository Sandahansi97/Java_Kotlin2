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


public class Trade {
    public String tag = "";
    public LocalDateTime dateTime;
    public String direction = "";
    public String itemID = "";
    public String price;
    public Integer quantity;
    public String buyer = "";
    public String seller = "";
    public String comment;
    public String version = "";
    public String nestedTags;


    public List<List<String>> extractTrade = new ArrayList<>();

    {
        for (int i = 0; i < File_import.lines.size(); i++) {
            if (File_import.lines.get(i).startsWith("TRADE")) {
                tag = File_import.lines.get(i).substring(0, 5);
                version = "";

                String tmpDateTime = File_import.lines.get(i).substring(5, 22);
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
                    dateTime = LocalDateTime.parse(tmpDateTime, formatter);
                } catch(DateTimeParseException e) {
                    e.printStackTrace();
                }

                direction = File_import.lines.get(i).substring(22, 23);

                itemID = File_import.lines.get(i).substring(23, 35);

                if(File_import.lines.get(i).charAt(35) == '+'){
                    price = Integer.parseInt(File_import.lines.get(i).substring(36, 46)) +"."+File_import.lines.get(i).substring(46,50);

                }else{
                    price = File_import.lines.get(i).charAt(35)+String.valueOf(Integer.parseInt(File_import.lines.get(i).substring(36,46)))+"."+File_import.lines.get(i).substring(46,50);
                }

                quantity = Integer.parseInt(File_import.lines.get(i).substring(51, 61));

                buyer = File_import.lines.get(i).substring(61, 65);

                seller = File_import.lines.get(i).substring(65, 69);

                comment = File_import.lines.get(i).substring(69).trim();

                //nestedTags = "";

                List<String> tempTrade = new ArrayList<>();
                tempTrade.add(tag);
                tempTrade.add(version);
                tempTrade.add(dateTime.toString());
                tempTrade.add(direction);
                tempTrade.add(itemID);
                tempTrade.add(String.valueOf(price));
                tempTrade.add(String.valueOf(quantity));
                tempTrade.add(buyer);
                tempTrade.add(seller);
                tempTrade.add(comment);
                //tempTrade.add(nestedTags);

                extractTrade.add(tempTrade);
            }

            if (File_import.lines.get(i).startsWith("EXTRD")) {
                tag = File_import.lines.get(i).substring(0, 5);

                version = File_import.lines.get(i).substring(5, 9);

                String tmpDateTime = File_import.lines.get(i).substring(9, 26);
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
                    dateTime = LocalDateTime.parse(tmpDateTime, formatter);
                } catch(DateTimeParseException e) {
                    e.printStackTrace();
                }

                direction = File_import.lines.get(i).substring(26, 30);
                if(direction.equals("BUY_")){
                    direction = "BUY";
                }

                itemID = File_import.lines.get(i).substring(30, 42);

                if(File_import.lines.get(i).charAt(42) == '+'){
                    price = Integer.parseInt(File_import.lines.get(i).substring(43, 53)) +"."+File_import.lines.get(i).substring(53,57);

                }else{
                    price = File_import.lines.get(i).charAt(42)+String.valueOf(Integer.parseInt(File_import.lines.get(i).substring(43,53)))+"."+File_import.lines.get(i).substring(53,57);
                }

                quantity = Integer.parseInt(File_import.lines.get(i).substring(58, 68));

                buyer = File_import.lines.get(i).substring(68, 72);

                seller = File_import.lines.get(i).substring(72, 76);
                //comment = "";

                nestedTags = File_import.lines.get(i).substring(76);


                List<String> tempExtTrade = new ArrayList<>();
                tempExtTrade.add(tag);
                tempExtTrade.add(version);
                tempExtTrade.add(dateTime.toString());
                tempExtTrade.add(direction);
                tempExtTrade.add(itemID);
                tempExtTrade.add(String.valueOf(price));
                tempExtTrade.add(String.valueOf(quantity));
                tempExtTrade.add(buyer);
                tempExtTrade.add(seller);
                //tempExtTrade.add(comment);
                tempExtTrade.add(nestedTags);

                extractTrade.add(tempExtTrade);

            }

        }

    }
    public void Trade_Csv(){
        try {
          FileWriter out = new FileWriter("Trade.csv");
          CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT.builder().setHeader("Tag","Version","Trade Date and Time","Direction","ItemID","Price","Quantity","Buyer","Seller","Comment","Nested tags").build());
          for ( List<String> tr : extractTrade) {
              printer.printRecord(tr);
          }
          printer.flush();
          out.close();
      } catch (IOException e){
          System.out.println("A fatal error regarding on CSV!!");
          throw new RuntimeException(e);
      }
    }
}
