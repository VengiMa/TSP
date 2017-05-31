package com.github.VengiMa.vengi;

import java.util.Scanner;
//import java.util.Stack;
/**
 * Created by Admin on 04.03.2017.
 */
public class ScannerTest {

    public static void main (String[] args) {
        int nodes;
        Scanner scanner = null;

        System.out.println("Please enter the number of nodes:");
        scanner = new Scanner(System.in);
        nodes = scanner.nextInt();
        double distancematrix[][] = new double[nodes][nodes];
        System.out.println("Please enter the distancematrix:");
        for (int i=0; i < nodes; i++){
            System.out.println(" ");
            for (int j=0; j< nodes; j++){
                distancematrix[i][j] = scanner.nextDouble();
            }
        }
        //ArrayList<Integer> tour = new ArrayList<Integer>();
        //System.out.println("Please enter the tour: ");
        //for (int k=0; k <nodes; k++){
        //    tour.add(scanner.nextInt());

        NearestNeighbour tsp = new NearestNeighbour();
        FunctionTourLength.TourLength1(tsp.tspNN(distancematrix), distancematrix);
    }
}