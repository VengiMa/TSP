package com.github.VengiMa.Algorithm;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Admin on 05.04.2017.
 */
public class InputCoordinates implements Serializable{
    public static double[][] FileToCoordinates(File data, boolean pointNamed) throws IOException {
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
        double input[][];
        input = new double[inputLines.length][2];
        if (pointNamed){
            for (int j = 0; j < inputLines.length; j++) {
                //first lines of the matrix cannot be parsed...only the matrix!!!
                //Also no empty lines should be read.
                //System.out.println("");
                //old pattern "[-]?[0-9]+[.|,0-9]*"
                Pattern p = Pattern.compile("[+\\-]?(?:0|[1-9]\\d*)(?:(\\,|\\.)\\d*)?(?:[eE][+\\-]?\\d+)?");

                Matcher m = p.matcher(inputLines[j]);
                int k = -1;
                while (m.find()) {
                    if (k ==-1){
                    }else{
                        input[j][k] = Double.parseDouble(m.group());
                    }
                    k++;
                }
            }
        }else{
            for (int j = 0; j < inputLines.length; j++) {
                //first lines of the matrix cannot be parsed...only the matrix!!!
                //Also no empty lines should be read.
                //System.out.println("");
                Pattern p = Pattern.compile("[-]?[0-9]+.[0-9]+");
                Matcher m = p.matcher(inputLines[j]);
                int k = 0;
                while (m.find()) {
                    input[j][k] = Double.parseDouble(m.group());
                    //System.out.print(" "+matrix[j][k]);
                    k++;
                }
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

    public static LinkedList<Point> createPointList (double[][] coordinates){
        LinkedList<Point> pointList = new LinkedList<Point>();
        for (int i=0; i< coordinates.length; i++){
            Point p = new Point(i+1, coordinates[i][0], coordinates[i][1]);
            pointList.add(p);
        }
        return pointList;
    }

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
        in.close();
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

    public static void main(String[] args){
        try {
            File file = new File("C:\\Users\\Admin\\Desktop\\Hochschule\\Master\\Thesis - Richter\\Java\\Testdateien\\qa194.txt");
            double coordinates[][] = FileToCoordinates(file, true);
            double distanceMatrix [][] = distanceMatrix(coordinates);
            boolean showDistance = false;
            List<Integer> filled = new ArrayList<Integer>();
            filled.add(3);
            if (showDistance) {
                for (int i = 0; i < distanceMatrix.length; i++) {
                    System.out.print("\n");
                    for (int j = 0; j < distanceMatrix.length; j++) {
                        System.out.print(distanceMatrix[i][j] + " ");
                    }
                }
            }
            System.out.println();
            LinkedList<Point> init = createPointList(coordinates);
            System.out.println(init.size());
            Tour tour = new Tour(0);
            tour = ConstructionTourThroughClusters.NNHeuristic(distanceMatrix, init);
            tour.isFeasible(init);
            System.out.println(tour.tour2String() + " Länge: " + tour.distanceTourLength(distanceMatrix));
            LocalSearchForClusters two = new LocalSearchForClusters();
            two.twoOpt(tour, distanceMatrix);
            System.out.println(tour.tour2String() + "\n" + " Länge: " + tour.distanceTourLength(distanceMatrix));
            //two.threeOpt(tour, distanceMatrix);
            //System.out.println(tour.tour2String() + " Länge: " + tour.distanceTourLength(distanceMatrix));
        }
        catch (IOException e) {
            System.out.println("Fehler: IOException");
        }
    }
}
