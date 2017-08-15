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

import java.io.File;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import com.github.VengiMa.Algorithm.*;
import org.zeromq.ZMQ;

/***
 * The ventilator reads in the text file, calculates the clusters and sends the tasks to the workers
 */
public class TaskVent {
    /***
     * Receives all essential data from environment variables, produces and sends tasks to the workers
     * @param args
     * @throws Exception Is thrown, if an environment variable is empty or wrong
     */
    public static void main (String[] args) throws Exception {
        int number;
        int iterations;
        String host_Sink;
        String filePath;
        boolean pointNamed = false;
        long sleeptime;

        try {
            number = Integer.parseInt(System.getenv("NUMBER_OF_CLUSTERS"));
        }
        catch(Exception e) {
            number = 4;
        }
        try {
            iterations = Integer.parseInt(System.getenv("ITERATIONS"));
        }
        catch(Exception e) {
            iterations = 10;
        }
        try {
            sleeptime = Long.parseLong(System.getenv("SLEEP"));
        }
        catch(Exception e) {
            sleeptime = 40000;
        }
        if(System.getenv("TSPLIB")!=null){
            pointNamed = true;
        }

        host_Sink = System.getenv("HOST_SINK");
        if (host_Sink == null)
            host_Sink = "localhost";

        filePath = System.getenv("FILE_PATH");
        if(filePath == null)
            filePath = "src/main/ressources/qa194.txt";

        System.out.println("Parameters:");
        System.out.println("NUMBER_OF_CLUSTERS: " + number);
        System.out.println("HOST_SINK: " + host_Sink);
        System.out.println("FILE_PATH: " + filePath);
        System.out.println("TSPLIB: " + pointNamed);

        File file = new File(filePath);

        DataPackage toSink = new DataPackage();
        toSink.setNumberClusters(number);
        toSink.setTyp(filePath);
        toSink.setIterations(iterations);

        byte[] dataToSink = SerializationUtil.serialize(toSink);

        ZMQ.Context context = ZMQ.context(1);

        //  Socket to send messages on
        ZMQ.Socket sender = context.socket(ZMQ.PUSH);
        sender.bind("tcp://*:5557");

        //  Socket to send messages on
        ZMQ.Socket sink = context.socket(ZMQ.PUSH);
        sink.connect("tcp://" + host_Sink + ":5558");
        sink.send(dataToSink, 0);

        System.out.println("Sending tasks to workers\n");

        double coordinates[][] = InputData.FileToCoordinates(file, pointNamed);
        LinkedList<Point> init = InputData.createPointList(coordinates);
        double distanceMatrix[][] = InputData.distanceMatrix(coordinates);
        List<Cluster> clusters = null;

        for(int i=0; i<iterations; i++) {
            System.out.println("Number:  " + i);
            Timestamp start = new Timestamp(System.currentTimeMillis());
            K_Means kmeans = new K_Means();
            kmeans.init(init, number);
            kmeans.calculate();
            clusters = kmeans.getClusters();

            ClusterDistance[][] distance = clusterMatrix.clusterMatrix(distanceMatrix, clusters);

            Tour test = VisitingOrderCluster.orderCluster(distance, clusters);

            //  Send number of tasks
            int task_nbr;
            for (task_nbr = 0; task_nbr < number; task_nbr++) {
                Cluster c = clusters.get(task_nbr);
                DataPackage data;
                if (task_nbr == 0) {
                    data = new DataPackage(c.getId(), c, distanceMatrix, test);
                    data.setStartTime(start);
                } else {
                    data = new DataPackage(c.getId(), c, distanceMatrix);
                    data.setStartTime(start);
                }
                byte[] byteArray = SerializationUtil.serialize(data);
                sender.send(byteArray, 0);
            }
            Thread.sleep(sleeptime);
            System.gc();
        }
        sink.close();
        sender.close();
        context.term();
    }
}
