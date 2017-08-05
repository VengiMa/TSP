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
    /***
     *
     * @param args
     * @throws Exception
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

        // Socket to publish to the sink
        //ZMQ.Socket pub = context.socket(ZMQ.PUB);
        //pub.connect("tcp://*:5559");

        //  Socket to send messages on
        ZMQ.Socket sink = context.socket(ZMQ.PUSH);
        sink.connect("tcp://" + host_Sink + ":5558");
        sink.send(dataToSink, 0);

        System.out.println("Sending tasks to workers\n");
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
            //todo:look for error in clustering, algorithm stops after several iterations
            K_Means kmeans = new K_Means();
            kmeans.init(init, number);
            kmeans.calculate();
            clusters = kmeans.getClusters();

            //declare the distancematrix for each cluster

            ClusterDistance[][] distance = clusterMatrix.clusterMatrix(distanceMatrix, clusters);

            Tour test = VisitingOrderCluster.orderCluster(distance, distanceMatrix, clusters);

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
            Thread.sleep(sleeptime);
            System.gc();
        }
        sink.close();
        sender.close();
        context.term();
    }
}
