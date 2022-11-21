package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class File_import {
    String line = "";
    //get array list
    protected static List<String> lines = new ArrayList<>();
    Scanner myReader;

    //read the file
    public void file_Reader(){

        try {
            File myObj = new File("SAMPLE_v3.dat");
            myReader = new Scanner(myObj);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            throw new RuntimeException();
        }

        if(!myReader.hasNextLine()){
            System.out.println("File is empty");
            throw new RuntimeException();
        }

        while(myReader.hasNextLine()) {
            line = myReader.nextLine();
            lines.add(line);
        }

    }
}





