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


package com.github.VengiMa.Pipeline;

import com.github.VengiMa.Algorithm.*;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.LinkedList;

/***
 * Represents the sequential equivalent to the distributed algorithm, consisting of TaskVent, TaskWork and TaskSink.
 * Calculates a tour with a construction heuristic that is set by environment variables
 * and is improved by a 2-opt move. The results are saved in a database.
 */
public class Sequential {
    /***
     * Following environment variables are needed:
     * The address of the database
     * The chosen heuristic, 1 = NN, 2 = FI, 3 =CI
     * The boolean value, if the problem instance is from the TSPLIB
     * The number of iterations, the algorithm will be executed
     * The path to the text file of the problem instance
     * @param args
     * @throws Exception Is thrown, if the environment variables are empty or wrong
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


        double coordinates[][] = InputData.FileToCoordinates(file, pointNamed);
        LinkedList<Point> init = InputData.createPointList(coordinates);
        double distanceMatrix[][] = InputData.distanceMatrix(coordinates);

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
