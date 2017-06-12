package com.github.VengiMa.Pipeline;

/**
 * Created by Admin on 21.05.2017.
 */
import com.github.VengiMa.Algorithm.Cluster;
import com.github.VengiMa.Algorithm.ClusterComputation;
import com.github.VengiMa.Algorithm.DataPackage;
import com.github.VengiMa.Algorithm.Tour;
import org.zeromq.ZMQ;
import com.github.VengiMa.Algorithm.*;

import java.sql.Timestamp;

public class TaskWork {
    public static void main (String[] args) throws Exception {
        String host_Sink = String.valueOf(System.getenv("HOST_SINK"));
        String host_Master = String.valueOf(System.getenv("HOST_MASTER"));
        int choice;
        try {
            choice = Integer.parseInt(System.getenv("HEURISTIC"));
        }
        catch(Exception e) {
            choice = 2;
        }

        ZMQ.Context context = ZMQ.context(1);

        //  Socket to receive messages on
        ZMQ.Socket receiver = context.socket(ZMQ.PULL);
        receiver.connect("tcp://"+ host_Master + ":5557");
        System.out.println("connect to receiver: " + "tcp://"+ host_Master + ":5557");

        //  Socket to send messages to
        ZMQ.Socket sender = context.socket(ZMQ.PUSH);
        sender.connect("tcp://"+ host_Sink + ":5558");
        System.out.println("connect to sender: " + "tcp://"+ host_Sink + ":5558");

        DataPackage data;
        double[][] distanceMatrix;

        //choice of the construction heuristic: 1 = NN, 2 = Farthest Insertion, 3 = Cheapest Insertion
        String typ;
        switch (choice){
            case 1: typ = "NN";
                break;
            case 2: typ = "Far";
                break;
            case 3: typ = "Cheap";
                break;
            default: typ = "NN";
                break;
        }

        while (!Thread.currentThread ().isInterrupted ()) {

            byte[] byteArray = receiver.recv();
            //receiving message from Vent

            //work with the received message/data
            data = (DataPackage) SerializationUtil.deserialize(byteArray);
            Cluster c = data.getClusterData();
            distanceMatrix = data.getDistanceMatrixData();

            Tour partialTour = ClusterComputation.createTour(c,distanceMatrix, choice);
            System.out.println();

            data.setTourData(partialTour);
            data.setHeuristic(typ);
            System.out.flush();

            //  convert the tour into byteArray and send results to sink
            byteArray = SerializationUtil.serialize(data);
            sender.send(byteArray, 0);
        }
        sender.close();
        receiver.close();
        //controller.close();
        context.term();
    }
}
