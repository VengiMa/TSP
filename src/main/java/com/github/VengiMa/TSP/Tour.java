package com.github.VengiMa.TSP;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Admin on 17.03.2017.
 */
public class Tour {

    private ArrayList<Integer> tour;

    public Tour (ArrayList<Integer> t1){
        this.tour = t1;
    }

    public void setTour (ArrayList<Integer> t1){
        this.tour = t1;
    }

    public ArrayList getTour (){
        return tour;
    }
    //DistanceMAtrix saved in a final variable?? Not in the class of the tour itself?
    //public double distanceMatrix[][];

    public double getTourLength (double distanceMatrix [][]) {
        double s = 0;
        int a, b, start, end;
        //order of tour: addition of the costs among the tour t
        //weights are saved in matrix d
        for (int i = 0; i < tour.size()-1; i++){
            a = tour.get(i)-1;
            b = tour.get(i+1)-1;
            s = s + distanceMatrix[a][b];
        }
        //cost from the last city to the startin city
        end = tour.get(tour.size()-1)-1;
        start = tour.get(0)-1;
        s = s + distanceMatrix[end][start];
        return s;
    }
}

class TestTour {
    public static void main (String[] args) throws IOException {
        double matrix[][];
        double minimum = Double.MAX_VALUE;
        int rr [];

        try {
            File file = new File("C:\\Users\\Admin\\Desktop\\Hochschule\\Master\\Thesis - Richter\\Java\\Testdateien\\kn57.txt");
            //generate the distance matrix out of the txtfile
            matrix = InputToMatrix.FileToMatrix(file);
            for (int i = 0; i < 100; i++) {
                ArrayList<Integer> temp = NearestNeighbour.tspNN(matrix);
                Tour t1 = new Tour(temp);
                t1.setTour(TwoOptMove.TwoOpt(temp, matrix));
                if (t1.getTourLength(matrix) < minimum) {
                    minimum = t1.getTourLength(matrix);
                    System.out.println(i + " " +minimum);
                    ArrayList<Integer> temp2 = t1.getTour();
                    t1.setTour(ThreeOptMove.ThreeOpt(temp2, matrix));
                    System.out.println(t1.getTourLength(matrix));
                }

            }
        }
        catch (IOException e) {
            System.out.println("Fehler: IOException");
        }
        System.out.println("Die minimale berechnete Tourlänge beträgt: "+minimum);
    }
}

class EuclidTest {
    public static void main (String[] args)throws IOException{
        try {
            File file = new File("C:\\Users\\Admin\\Desktop\\Hochschule\\Master\\Thesis - Richter\\Java\\Testdateien\\grid04_xy.txt");
            double matrix[][] = InputToMatrix.FileToCoordinates(file);
            for (int i =0; i<matrix.length; i++){
                System.out.println(" ");
                for (int j=0; j<matrix[0].length; j++){
                    System.out.print(matrix[i][j]+ " ");
                }
            }
        }
        catch (IOException e) {
            System.out.println("Fehler: IOException");
        }
    }
}
