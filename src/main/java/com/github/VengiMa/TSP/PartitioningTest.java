package com.github.VengiMa.TSP;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by Admin on 30.03.2017.
 */
public class PartitioningTest {
    public static void main(String[] args){
        try {
            File file = new File("C:\\Users\\Admin\\Desktop\\Hochschule\\Master\\Thesis - Richter\\Java\\Testdateien\\kn57_xy.txt");
            double coordinates[][] = InputToMatrix.FileToCoordinates(file);
            List<ArrayList<Integer>> mengen = mengen(coordinates);
            double distanceMatrix [][] = InputToMatrix.distanceMatrix(coordinates);
            double [][] clusterMatrix =clusterMatrix(distanceMatrix, mengen);
            for(int i=0; i < clusterMatrix.length; i++ ){
                System.out.print("\n");
                for(int j=0; j< clusterMatrix.length; j++){
                    System.out.print(clusterMatrix[i][j] + " ");
                }
            }
            ArrayList<Integer> temp = NearestNeighbour.tspNN(clusterMatrix);
            Tour t1 = new Tour(temp);
            System.out.print("\n"+Arrays.toString(t1.getTour().toArray()) + " Länge: "+ +t1.getTourLength(clusterMatrix));;
        }
        catch (IOException e) {
            System.out.println("Fehler: IOException");
        }
    }



    public static double [][] clusterMatrix (double [][] distanceMatrix, List<ArrayList<Integer>> mengen){
        double clusterMatrix[][] = new double [mengen.size()][mengen.size()];
        for(int i = 0; i < mengen.size(); i++){
            int visited [] = new int [mengen.get(i).size()];
            for(int j =0; j< mengen.size();j++){
                if(i==j){
                    clusterMatrix[i][i] = 0.00;
                }
                else {
                    double minimum = Double.MAX_VALUE;
                    int von =0;
                    int zu = 0;
                    for(int k =0; k<mengen.get(i).size();k++){
                        int vergleich = mengen.get(i).get(k) -1;
                        for(int l =0; l<mengen.get(j).size();l++){
                            if(distanceMatrix[vergleich][mengen.get(j).get(l)-1] < minimum && visited[k] != 1){
                                minimum = distanceMatrix[vergleich][mengen.get(j).get(l)-1];
                                //visited[k] = 1;
                                von = vergleich +1;
                                zu = mengen.get(j).get(l);
                            }
                        }
                    }
                    //System.out.println("von: "+ von + "  zu: " +zu);
                    clusterMatrix[i][j] = minimum;
                }

            }
        }
        return clusterMatrix;
    }

    public static List<ArrayList<Integer>> mengen (double[][] coordinates){
        System.out.println(coordinates.length);

        double x [] = new double [coordinates.length];
        double y [] = new double [coordinates.length];
        for (int i = 0; i<coordinates.length; i++){
            x[i] = coordinates[i][0];
            y[i] = coordinates[i][1];
        }
        Arrays.sort(x);
        double xMedian;
        if (x.length % 2 == 0) {
            xMedian = ((double) x[x.length / 2] + (double) x[x.length / 2 - 1]) / 2;
        }else {
            xMedian = (double) x[x.length / 2];
        }

        Arrays.sort(y);
        double yMedian;
        if (x.length % 2 == 0) {
            yMedian = ((double) y[y.length / 2] + (double) y[y.length / 2 - 1]) / 2;
        }else {
            yMedian = (double) y[y.length / 2];
        }
        System.out.println("x-Median: "+xMedian);
        System.out.println("y-Median: "+yMedian);
        for(int i=0; i < 4; i++){
            ArrayList<Integer> d = new ArrayList<Integer>();
        }
        ArrayList<Integer> eins = new ArrayList<Integer>();
        ArrayList<Integer> zwei = new ArrayList<Integer>();
        ArrayList<Integer> drei = new ArrayList<Integer>();
        ArrayList<Integer> vier = new ArrayList<Integer>();

        for (int i =0; i<coordinates.length;i++){
            double xt = coordinates[i][0];
            double yt = coordinates[i][1];
            if (xt <= xMedian && yt > yMedian){eins.add(i+1);}
            else if (xt > xMedian && yt > yMedian) {zwei.add(i+1);}
            else if (xt <= xMedian && yt <= yMedian) {vier.add(i+1);}
            else if (xt > xMedian && yt <= yMedian) {drei.add(i+1);}
        }

        List<ArrayList<Integer>> mengen = new ArrayList<ArrayList<Integer>>();
        mengen.add(eins);
        mengen.add(zwei);
        mengen.add(drei);
        mengen.add(vier);

        return mengen;
    }
}
