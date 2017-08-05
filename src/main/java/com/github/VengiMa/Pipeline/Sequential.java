package com.github.VengiMa.Pipeline;

import com.github.VengiMa.Algorithm.*;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Admin on 12.06.2017.
 */
public class Sequential {
    /***
     *
     * @param args
     * @throws Exception
     */
    public static void main (String[] args) throws Exception {
        String filePath;
        boolean pointNamed = false;
        Tour tour;
        long dur;
        double distance;
        String typ;
        int cluster = 0;
        int choice;
        int iterations;

        String database;
        database = System.getenv("DATABASE");
        if (database == null)
            database = "10.95.61.77:5433/postgres";
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://" + database;
        Connection conn = DriverManager.getConnection(url,"postgres","postgres");

        try {
            choice = Integer.parseInt(System.getenv("HEURISTIC"));
        }
        catch(Exception e) {
            choice = 1;
        }

        if(System.getenv("TSPLIB")!=null){
            pointNamed = true;
        }

        filePath = System.getenv("FILE_PATH");
        if (filePath == null) {
            filePath = "src/main/ressources/qa194.txt";
        }
        File file = new File(filePath);

        try {
            iterations = Integer.parseInt(System.getenv("ITERATIONS"));
        }
        catch(Exception e) {
            iterations = 10;
        }

        System.out.println("Parameters:");
        System.out.println("Iterations " + iterations);
        System.out.println("Heuristic " + choice);
        System.out.println("FILE_PATH: " + filePath);
        System.out.println("TSPLIB: " + pointNamed);


        double coordinates[][] = InputCoordinates.FileToCoordinates(file, pointNamed);
        LinkedList<Point> init = InputCoordinates.createPointList(coordinates);
        double distanceMatrix[][] = InputCoordinates.distanceMatrix(coordinates);

        for(int i = 0; i < iterations; i++) {
            Timestamp tstart = new Timestamp(System.currentTimeMillis());

            switch (choice) {
                case 1:
                    tour = TourConstruction.NN(distanceMatrix, init);
                    typ = "NN";
                    break;
                case 2:
                    tour = TourConstruction.Farthest(distanceMatrix, init);
                    typ = "Far";
                    break;
                case 3:
                    tour = TourConstruction.Cheapest(distanceMatrix, init);
                    typ = "Cheap";
                    break;
                default:
                    tour = TourConstruction.NN(distanceMatrix, init);
                    typ = "NN";
                    break;
            }
            LocalSearch improve = new LocalSearch();
            improve.twoOptSequentiel(tour, distanceMatrix);

            Timestamp tend = new Timestamp(System.currentTimeMillis());
            dur = (tend.getTime() - tstart.getTime());
            distance = ((double) Math.round(tour.distanceTourLength(distanceMatrix)*100))/100;
            System.out.println(distance + " ; time: " + dur + "msec ; feasible: " + tour.isFeasible(init));


            String sql = "INSERT INTO test_results " +
                    "(begin, ending, duration, tourlength, typ, heuristic, clusters)"+
                    "VALUES(?,?,?,?,?,?,?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setTimestamp(1,tstart);
            pst.setTimestamp(2,tend);
            pst.setLong(3, dur);
            pst.setDouble(4,distance);
            pst.setString(5,filePath);
            pst.setString(6,typ);
            pst.setInt(7,cluster);
            pst.executeUpdate();

            System.out.println("Inserting successful!");
        }
        System.out.println("Calculation executed!");
    }
}