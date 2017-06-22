package com.github.VengiMa.vengi;

import java.util.ArrayList;
/**
 * Created by Admin on 27.02.2017.
 */
public class HelloWorld {
    public static void main (String[] args) {
        String marco = "59";
        String stephan = "60";
        ArrayList<Integer> tour = new ArrayList<Integer>();
        tour.add(3);
        tour.add(1);
        tour.add(4);
        tour.add(5);
        tour.add(2);
        double test =30e02;
        //for (Integer number : tour) {
        //    System.out.println(number);
        //}
        //int sum = marco + stephan;
        //System.out.print(addieren(marco, stephan));
        //int d [][] = new int [4][3];
        //System.out.println(d.length);
        //System.out.println(d[0].length);
        //System.out.println(d[1].length);
        //System.out.println(d[2].length);
        System.out.println(test);

    }
    public static int add(int num1, int num2) {
        int c = num1 + num2;
        return c +2;
    }
    public static String addieren (String eins, String zwei){
        String k = eins + zwei;
        return k;
    }
}
