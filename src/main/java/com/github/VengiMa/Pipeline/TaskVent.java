package com.github.VengiMa.Pipeline;

/**
 * Created by Admin on 21.05.2017.
 */
import java.io.File;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.github.VengiMa.Algorithm.*;
import org.zeromq.ZMQ;
//
//  Task ventilator in Java
//  Binds PUSH socket to tcp://localhost:5557
//  Sends batch of tasks to workers via that socket
//
public class TaskVent {
    public static void main (String[] args) throws Exception {


        int number;
        int iterations;
        String host_Sink;
        String filePath;
        boolean pointNamed = true;

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

        // Socket to publish to the sink
        //ZMQ.Socket pub = context.socket(ZMQ.PUB);
        //pub.connect("tcp://*:5559");

        //  Socket to send messages on
        ZMQ.Socket sink = context.socket(ZMQ.PUSH);
        sink.connect("tcp://" + host_Sink + ":5558");
        System.out.println("Sending tasks to workers\n");

        //  The first message is "0" and signals start of batch
        sink.send(dataToSink, 0);

        //  Initialize random number generator
        Random srandom = new Random(System.currentTimeMillis());

        /**
         * Test-File
         */
        double coordinates[][] = InputCoordinates.FileToCoordinates(file, pointNamed);
        LinkedList<Point> init = InputCoordinates.createPointList(coordinates);
        double distanceMatrix[][] = InputCoordinates.distanceMatrix(coordinates);
        List<Cluster> clusters = null;

        for(int i=0; i<iterations; i++) {
            System.out.println("Number:  " + i);
            Timestamp start = new Timestamp(System.currentTimeMillis());

            K_Means kmeans = new K_Means();
            kmeans.init(init, number);
            kmeans.calculate();
            clusters = kmeans.getClusters();

            //declare the distancematrix for each cluster

            ClusterDistance[][] distance = clusterMatrix.clusterMatrix(distanceMatrix, clusters);

        //todo:try and test if it works
            Tour test = VisitingOrderCluster.orderCluster(distance, distanceMatrix, clusters);

        /*
        int counter = 0;
        while(counter<2) {
            System.out.println("Publishing");
            byte [] info = SerializationUtil.serialize(test);
            pub.send(info,0);
            counter++;
        }
        */

            //  Send number of tasks
            int task_nbr;
            for (task_nbr = 0; task_nbr < number; task_nbr++) {
                int workload;
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
            Thread.sleep(1500);
            System.gc();
        }

        Thread.sleep(1000);              //  Give 0MQ time to deliver
        sink.close();
        sender.close();
        context.term();
    }
}
