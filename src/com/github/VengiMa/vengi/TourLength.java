package com.github.VengiMa.vengi;

import java.util.ArrayList;

/**
 * Created by Admin on 02.03.2017.
 */
public class TourLength {

    public static void main (String[] args) {
        int n = 5;
        int m = 1;
        ArrayList<Integer> tour = new ArrayList<Integer>();
        int[][] d ;
        d = new int [5][5];
        int s = 0, a, b;
        tour.add(2); tour.add(4); tour.add(1); tour.add(5); tour.add(3);
        for (Integer number : tour){
            System.out.print(number+ " ");
        }
        System.out.println("Die Instanzgröße beträgt n = " + tour.size());
        // initializing Matrix, values increasing with every cell
        for (int j=0; j<d.length; j++){
            for(int l=0; l<d.length; l++){
                d[j][l] = m;
                m = m+1;
            }
        }
        for (int i =0; i<tour.size()-1;i++ ){
            a = tour.get(i)-1;
            b = tour.get(i+1)-1;
            s = s + d[a][b];
        }
        int end = tour.get(tour.size()-1)-1;
        int start = tour.get(0)-1;
        s = s + d[end][start];
        System.out.print("Die berechnete Tourlänge durch die Städte ");
        for (Integer number : tour){
            System.out.print(number + " ");
        }
        System.out.print("beträgt: " +s);
    }

}
