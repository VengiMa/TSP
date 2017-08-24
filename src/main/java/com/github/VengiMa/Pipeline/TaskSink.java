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

import com.github.VengiMa.Algorithm.DataPackage;
import com.github.VengiMa.Algorithm.Tour;
import org.zeromq.ZMQ;
import com.github.VengiMa.Algorithm.*;

import java.sql.*;
import java.util.ArrayList;

/***
 * The sink is the last instance. It receives information from the workers and the ventilator. Its task is to
 * merge the hamiltonian paths together to a final tour for the TSP and is finished or improves the final tour
 * for a fixed amount of time. The results are stored in a database.
 */
public class TaskSink {
    /***
     * Receives the hamiltonian paths from the workers and merges them to one final tour for the TSP.
     * All essential information are set by the following environment variables:
     * The address of the database
     * Optional: Total time the algorithm should be executed
     * @param args
     * @throws Exception
     */
    public static void main (String[] args) throws Exception {

        //  Prepare our context and socket
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket receiver = context.socket(ZMQ.PULL);

        System.out.println("Bind Sink to " + "tcp://*:5558 \n");
        receiver.bind("tcp://*:5558");
        long time = 0;
        boolean improvementAfter = false;
        String timestring = System.getenv("TIME");
        if(timestring.length() != 0) {
            time = Long.parseLong(timestring);
            improvementAfter = true;
        }

        String database;
        database = System.getenv("DATABASE");
        if (database == null)
            database = "10.95.61.77:5433/postgres";
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://" + database;
        Connection conn = DriverManager.getConnection(url,"postgres","postgres");

        byte[] maxTaskByte = receiver.recv();
        DataPackage fromVent = (DataPackage) SerializationUtil.deserialize(maxTaskByte);
        int maxTask_nbr = fromVent.getNumberClusters();
        int iterations = fromVent.getIterations();
        String typemap = fromVent.getTyp();

        for (int ii=0; ii<iterations; ii++) {

            double[][] distanceMatrix = new double[0][0];
            Tour clusterTour = new Tour();
            Tour finalTour = new Tour();
            ArrayList<DataPackage> dataSet = new ArrayList<>();
            DataPackage data;
            Timestamp tstart = null ;
            Timestamp tend = null;
            long dur;
            String heur = "NN";

            int task_nbr;
            int index =1;
            for (task_nbr = 0; task_nbr < maxTask_nbr; task_nbr++) {
                byte[] byteArray = receiver.recv();
                data = (DataPackage) SerializationUtil.deserialize(byteArray);
                System.out.println(index + "/" + maxTask_nbr);

                if (task_nbr == 0) {
                    distanceMatrix = data.getDistanceMatrixData();
                    heur = data.getHeuristic();
                }
                if (data.getClusterTourData() != null) {
                    clusterTour = data.getClusterTourData();
                }
                if (tstart == null){
                    tstart = data.getStartTime();
                }
                dataSet.add(data);
                System.out.flush();
                index++;
            }

            for (int i = 0; i < clusterTour.getSize(); i++) {
                int point = clusterTour.getPoint(i).getPointNumber() - 1;
                for (int j = 0; j < dataSet.size(); j++) {
                    if (point == dataSet.get(j).getiDData()) {
                        finalTour.addTour(dataSet.get(j).getTourData());
                        dataSet.remove(j);
                    }
                }
            }
            if (timestring.length() == 0) {
                tend = new Timestamp(System.currentTimeMillis());
                dur = -1;
                try {
                    dur = (tend.getTime() - tstart.getTime());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                tend = new Timestamp(System.currentTimeMillis());
                dur = (tend.getTime()-tstart.getTime());
                long duration = time - dur;
                LocalSearch improveAfter = new LocalSearch();
                improveAfter.twoOptAfter(finalTour, distanceMatrix, duration);
                tend = new Timestamp(System.currentTimeMillis());
                dur = (tend.getTime()-tstart.getTime());
                System.out.println("Improvement after merging together executed");
            }

            double distance = ((double) Math.round(finalTour.distanceTourLength(distanceMatrix)*100))/100;

            System.out.println(String.format("%.2f", distance));


            String sql = "INSERT INTO test_results " +
                    "(begin, ending, duration, tourlength, typ, heuristic, clusters, improveafter)"+
                    "VALUES(?,?,?,?,?,?,?,?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setTimestamp(1,tstart);
            pst.setTimestamp(2,tend);
            pst.setLong(3, dur);
            pst.setDouble(4,distance);
            pst.setString(5,typemap);
            pst.setString(6,heur);
            pst.setInt(7,maxTask_nbr);
            pst.setBoolean(8,improvementAfter);
            pst.executeUpdate();

            System.out.println("Inserting successful...!");
            System.gc();
        }

        System.out.println("Calculation finished!");

        receiver.close();
        context.term();
    }
}