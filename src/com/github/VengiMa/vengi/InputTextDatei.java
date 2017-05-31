package com.github.VengiMa.vengi;

import java.lang.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Admin on 06.03.2017.
 */
public class InputTextDatei {
    public static void main (String[] args) throws IOException{
        File inputdata = new File("C:\\Users\\Admin\\Desktop\\Hochschule\\Master\\Thesis - Richter\\Java\\Testdateien\\ha30_dist.txt");
        TextdataInput(inputdata);
        NearestNeighbour tsp = new NearestNeighbour();
        double matrix [][] = TextdataInput(inputdata);
        FunctionTourLength.TourLength1(tsp.tspNN(matrix), matrix);

    }

    public static double [][] TextdataInput (File data) throws IOException {
        String line;
        BufferedReader in;
        // REading of the txt file; only distancematrix, no other information before
        // File should be opened by a dialogue, so the right one can be chosen

        // Method of creating the data matrix must be written!!!!!
        in = new BufferedReader(new FileReader(data));
        line = in.readLine();
        int i = 0;
        // get the size of the problem
        while(line != null)
        {
            line = in.readLine();
            i++;
        }
        int größe = i;
        in.close();
        in = new BufferedReader(new FileReader(data));
        line = in.readLine();
        //Definition of the size of the problem, generating an array of that size: inputLines
        String[] inputLines = new String[größe];
        i = 0;
        // saving the data in inputLines, read out of the file, line per line
        while(line != null)
        {
            inputLines[i] = line;
            line = in.readLine();
            i++;
        }

        //String[] data = inputLines[].split("\\s+");
        double matrix [][] = new double [inputLines.length][inputLines.length];
        for (int j=0; j<inputLines.length;j++){
            //first lines of the matrix cannot be parsed...only the matrix!!!
            //Also no empty lines should be read.
            //System.out.println("");
            Pattern p = Pattern.compile("[0-9]+");
            Matcher m = p.matcher(inputLines[j]);
            int k=0;
            while(m.find()){
                matrix[j][k]= Double.parseDouble(m.group());
                //System.out.print(" "+matrix[j][k]);
                k++;
            }

        }
        return matrix;
    }

}

/*
String[] reihe = inputLines[j].split("\\s+");
            for (int k = 0; k <reihe.length; k++){
                reihe[k].replaceAll("\\s+",null);
                matrix[j][k]= Double.parseDouble(reihe[k]);
            }
 */
