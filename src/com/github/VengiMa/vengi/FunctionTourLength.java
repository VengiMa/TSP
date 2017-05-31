package com.github.VengiMa.vengi;

import java.util.ArrayList;

/**
 * Created by Admin on 03.03.2017.
 */
public class FunctionTourLength {

    public static void TourLength1 (ArrayList<Integer> t, double [][] d){
        double s = 0;
        int a, b, start, end;
        //order of tour: addition of the costs among the tour t
        //weights are saved in matrix d
        for (int i = 0; i < t.size()-1; i++){
            a = t.get(i)-1;
            b = t.get(i+1)-1;
            s = s + d[a][b];
        }
        //cost from the last city to the startin city
        end = t.get(t.size()-1)-1;
        start = t.get(0)-1;
        s = s + d[end][start];

        /*int j,k;
        for (j=0; j<d.length; j++){
            for(k=0; k<d.length; k++){
                System.out.print(d[j][k] + "  ");
            }
            System.out.println("");
        }*/

        //printing of the length of the tour
        System.out.println("");
        System.out.print("Die berechnete Tourlänge durch die Städte ");
        for (Integer number : t){
            System.out.print(number + " ");
        }
        System.out.print("beträgt: " +s);
    }

    //the input: tour and a distance matrix
    public static void main (String[] args){
        ArrayList<Integer> tour = new ArrayList<Integer> ();
        double [][] d = new double [5][5];
        int m = 1;
        tour.add(2);tour.add(4);tour.add(1);tour.add(5);tour.add(3);
        for (int i = 0; i < d.length; i++){
            for (int j = 0; j < d.length; j++){
                d[i][j] = m;
                m = m+1;
            }
        }
        TourLength1(tour,d);
    }
}
