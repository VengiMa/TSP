package com.github.VengiMa.TSP;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.DecimalFormat;


/**
 * Created by Admin on 18.03.2017.
 */
public class InputToMatrix {
    public static double[][] FileToMatrix(File data) throws IOException {
        String line;
        BufferedReader in;
        int i = 0;
        int size = 0;
        String[] inputLines;
        // REading of the txt file; only distancematrix, no other information before
        // File should be opened by a dialogue, so the right one can be chosen
        try {
            // Method of creating the data matrix must be written!!!!!
            in = new BufferedReader(new FileReader(data));
            line = in.readLine();
            // get the size of the problem
            while (line != null) {
                line = in.readLine();
                i++;
            }
            size = i;
            in.close();
        }
        catch (IOException e) {
            System.out.println("Fehler: IOException");
        }
        in = new BufferedReader(new FileReader(data));
        line = in.readLine();
        //Definition of the size of the problem, generating an array of that size: inputLines
        inputLines = new String[size];
        i = 0;
        // saving the data in inputLines, read out of the file, line per line
        while (line != null) {
            inputLines[i] = line;
            line = in.readLine();
            i++;
        }
        //String[] data = inputLines[].split("\\s+");
        double matrix[][] = new double[inputLines.length][inputLines.length];
        for (int j = 0; j < inputLines.length; j++) {
            //first lines of the matrix cannot be parsed...only the matrix!!!
            //Also no empty lines should be read.
            //System.out.println("");
            Pattern p = Pattern.compile("[0-9]+");
            Matcher m = p.matcher(inputLines[j]);
            int k = 0;
            while (m.find()) {
                matrix[j][k] = Double.parseDouble(m.group());
                //System.out.print(" "+matrix[j][k]);
                k++;
            }

        }
        return matrix;
    }



    public static double[][] FileToCoordinates(File data) throws IOException{
        String line;
        BufferedReader in;
        int i = 0;
        int size = 0;
        String[] inputLines;
            // REading of the txt file; only distancematrix, no other information before
            // File should be opened by a dialogue, so the right one can be chosen

            // Method of creating the data matrix must be written!!!!!
        try {
            in = new BufferedReader(new FileReader(data));
            line = in.readLine();
            // get the size of the problem
            while (line != null) {
                    line = in.readLine();
                    i++;
            }
            size = i;
            in.close();
        }
        catch (IOException e) {
            System.out.println("Fehler: IOException");
        }
        in = new BufferedReader(new FileReader(data));
        line = in.readLine();
        //Definition of the size of the problem, generating an array of that size: inputLines
        inputLines = new String[size];
        i = 0;
        // saving the data in inputLines, read out of the file, line per line
        while (line != null) {
            inputLines[i] = line;
            line = in.readLine();
            i++;
        }
        in.close();
        double input[][] = new double[inputLines.length][2];
        for (int j = 0; j < inputLines.length; j++) {
            //first lines of the matrix cannot be parsed...only the matrix!!!
            //Also no empty lines should be read.
            //System.out.println("");
            Pattern p = Pattern.compile("[0-9]+.[0-9]+");
            Matcher m = p.matcher(inputLines[j]);
            int k = 0;
            while (m.find()) {
                input[j][k] = Double.parseDouble(m.group());
                //System.out.print(" "+matrix[j][k]);
                k++;
            }
        }
        /*double matrix[][] = new double[inputLines.length][inputLines.length];
        for (int j = 0; j < inputLines.length; j++) {
            for (int k = 0; k < inputLines.length; k++) {
                double temp = Math.sqrt(Math.pow((input[j][0] - input[k][0]), 2) + Math.pow((input[j][1] - input[k][1]), 2));
                matrix[j][k] = Math.round(temp * 100.00) / 100.00;
                //Math.round(temp*100.00)/100.00;
            }
        }
        */
        return input;
    }

    public static double[][] distanceMatrix (double[][] coordinates){
        double matrix[][] = new double[coordinates.length][coordinates.length];
        for (int j = 0; j < coordinates.length; j++) {
            for (int k = 0; k < coordinates.length; k++) {
                double temp = Math.sqrt(Math.pow((coordinates[j][0] - coordinates[k][0]), 2) + Math.pow((coordinates[j][1] - coordinates[k][1]), 2));
                matrix[j][k] = Math.round(temp * 100.00) / 100.00;
                //Math.round(temp*100.00)/100.00;
            }
        }
        return matrix;
    }

}
