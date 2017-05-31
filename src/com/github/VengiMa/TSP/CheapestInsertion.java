package com.github.VengiMa.TSP;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

/**
 * Created by Admin on 02.04.2017.
 */
public class CheapestInsertion {
    private static Stack<Integer> stack;

    public static ArrayList<Integer> CheapestInsert (double [][] distanceMatrix){
        stack = new Stack<Integer>();
        ArrayList<Integer> tour = new ArrayList();
        int numbernodes = distanceMatrix.length;
        int [] visited = new int [numbernodes];
        //two random starting points as a subset
        double temp1 = numbernodes * Math.random();
        int start = (int) temp1;
        double temp2 = numbernodes * Math.random();
        int ziel = (int) temp2;
        //two numbers cannot be the same
        while (start == ziel) {
            temp1 = numbernodes * Math.random();
            start = (int) temp1;
            temp2 = numbernodes * Math.random();
            ziel = (int) temp2;
        }
        //visited is true; because they are already part of the tour;
        visited[start] = 1;
        visited[ziel] = 1;
        System.out.println(start);
        System.out.println(ziel);
        //add those starting points to the tour
        tour.add(start+1);
        tour.add(ziel+1);
        //every vertex from 0 to probleminstance -1 will be parsed
        for (int i = 0; i < numbernodes; i++){
            //if the recent vertex is part of the tour already, do nothing
            if (visited[i] == 1){}
            else {

                //else try to insert the vertex in every possible position of the former tour
                //compare the distances for each insertion point to the minimum
                double distance;
                double minimum = Double.MAX_VALUE;
                int insertPoint = 0;
                for (int j = 1; j <= tour.size(); j++) {
                    int vor = tour.get((j - 1) % tour.size()) - 1;
                    int nach = tour.get((j) % tour.size()) - 1;
                    distance = distanceMatrix[vor][i] + distanceMatrix[i][nach];
                    if (distance < minimum) {
                        minimum = distance;
                        insertPoint = j % tour.size();
                    }
                }
                tour.add(insertPoint, i + 1);
                visited[i] = 1;
                System.out.println(Arrays.toString(visited));
            }
        }
        return tour;
    }

    public static void main (String[]args) throws IOException{
        double matrix[][];
        double minimum = Double.MAX_VALUE;
        int rr [];

        try {
            File file = new File("C:\\Users\\Admin\\Desktop\\Hochschule\\Master\\Thesis - Richter\\Java\\Testdateien\\lau15.txt");
            //generate the distance matrix out of the txtfile
            matrix = InputToMatrix.TextdataInput(file);
                ArrayList<Integer> temp = CheapestInsert(matrix);
                Tour t1 = new Tour(temp);
                    System.out.println(Arrays.toString(t1.getTour().toArray())+ " Länge " +t1.getTourLength(matrix));
        }
        catch (IOException e) {
            System.out.println("Fehler: IOException");
        }
        //System.out.println("Die minimale berechnete Tourlänge beträgt: "+minimum);
    }
}
