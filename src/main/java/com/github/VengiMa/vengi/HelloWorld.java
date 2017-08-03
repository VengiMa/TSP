package com.github.VengiMa.vengi;

import com.github.VengiMa.Algorithm.InputCoordinates;

import java.io.File;
import java.util.ArrayList;
/**
 * Created by Admin on 27.02.2017.
 */
public class HelloWorld {
    public static void main (String[] args) throws Exception {
        String marco = "59";
        String stephan = "60";
        String filePath;
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
        String time = "500";
        long timer = Long.parseLong(time);
        System.out.println(timer);

        /*
        filePath = "src/main/ressources/mu1979.txt";
        File file = new File(filePath);
        double [][] coordinates = InputCoordinates.FileToCoordinates(file, true);
        for (int i=0; i<coordinates.length; i++){
            System.out.println( coordinates[i][0] + "  "+ coordinates[i][1]);
        }
        */


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
