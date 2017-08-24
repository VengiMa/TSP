/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Marco Venghaus
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */


package com.github.VengiMa.Algorithm;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***
 * Contains all methods to read in the text file and convert it into the needed object
 */
public class InputData implements Serializable{
    /***
     * Reads in the text file and transforms them into a two-dimensional array of the coordinates
     * @param data The text file with the coordinates from the problem instance
     * @param pointNamed Boolean that determines if the file has point numbers included or not
     * @return A two-dimensional array containing the x- and y-coordinate of every point
     * @throws IOException Is thrown ,when the chosen text file is empty or has the wrong format
     */
    public static double[][] FileToCoordinates(File data, boolean pointNamed) throws IOException {
        String line;
        BufferedReader in;
        int i = 0;
        int size = 0;
        String[] inputLines;

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

        inputLines = new String[size];
        i = 0;

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
                Pattern p = Pattern.compile("[+\\-]?(?:0|[1-9]\\d*)(?:(\\,|\\.)\\d*)?(?:[eE][+\\-]?\\d+)?");
                Matcher m = p.matcher(inputLines[j]);
                int k = -1;
                while (m.find()) {
                    if (k ==-1){
                    }else{
                        input[j][k] = Double.parseDouble(m.group().replace(",","."));
                    }
                    k++;
                }
            }
        }else{
            for (int j = 0; j < inputLines.length; j++) {
                Pattern p = Pattern.compile("[+\\-]?(?:0|[1-9]\\d*)(?:(\\,|\\.)\\d*)?(?:[eE][+\\-]?\\d+)?");
                Matcher m = p.matcher(inputLines[j]);
                int k = 0;
                while (m.find()) {
                    input[j][k] = Double.parseDouble(m.group());
                    k++;
                }
            }
        }
        return input;
    }

    /***
     * Calculates the distance matrix to the according coordinates, using the euclidian distance, creating a n x n distance matrix
     * @param coordinates The coordinates of the points
     * @return A two-dimensional array of double values containing the distances
     */
    public static double[][] distanceMatrix (double[][] coordinates){
        double matrix[][] = new double[coordinates.length][coordinates.length];
        for (int j = 0; j < coordinates.length; j++) {
            for (int k = 0; k < coordinates.length; k++) {
                double temp = Math.sqrt(Math.pow((coordinates[j][0] - coordinates[k][0]), 2) + Math.pow((coordinates[j][1] - coordinates[k][1]), 2));
                matrix[j][k] = Math.round(temp * 100.00) / 100.00;
            }
        }
        return matrix;
    }

    /***
     * Creates a List of points that is needed for construction heuristics and k-means
     * @param coordinates The coordinates of the points
     * @return
     */
    public static LinkedList<Point> createPointList (double[][] coordinates){
        LinkedList<Point> pointList = new LinkedList<Point>();
        for (int i=0; i< coordinates.length; i++){
            Point p = new Point(i+1, coordinates[i][0], coordinates[i][1]);
            pointList.add(p);
        }
        return pointList;
    }
}
