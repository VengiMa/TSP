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

public class TaskWork {
    public static void main (String[] args) throws Exception {
        String host_Sink = String.valueOf(System.getenv("HOST_SINK"));
        String host_Master = String.valueOf(System.getenv("HOST_MASTER"));

        ZMQ.Context context = ZMQ.context(1);

        //  Socket to receive messages on
        ZMQ.Socket receiver = context.socket(ZMQ.PULL);
        receiver.connect("tcp://"+ host_Master + ":5557");

        //  Socket to send messages to
        ZMQ.Socket sender = context.socket(ZMQ.PUSH);
        sender.connect("tcp://"+ host_Sink + ":5558");

        DataPackage data;
        double[][] distanceMatrix;

        while (!Thread.currentThread ().isInterrupted ()) {

            byte[] byteArray = receiver.recv();
            //receiving message from Vent

            //work with the received message/data
            data = (DataPackage) SerializationUtil.deserialize(byteArray);
            Cluster c = data.getClusterData();
            distanceMatrix = data.getDistanceMatrixData();

            Tour partialTour = ClusterComputation.createTour(c,distanceMatrix);

            data.setTourData(partialTour);
            System.out.flush();

            //  convert the tour into byteArray and send results to sink
            byteArray = SerializationUtil.serialize(data);
            sender.send(byteArray, 0);

            /*
            ZMQ.Socket controller = context.socket(ZMQ.SUB);
            controller.connect("tcp://localhost:5559");
            controller.subscribe("".getBytes());

            ZMQ.Poller items = new ZMQ.Poller(context,2);
            items.register(receiver, ZMQ.Poller.POLLIN);
            items.register(controller, ZMQ.Poller.POLLIN);

            //  Process tasks forever
            while (true) {
                items.poll();

                if (items.pollin(0)) {
                    //String string = new String(receiver.recv(0)).trim();
                    //long msec = Long.parseLong(string);
                    byte[] test = receiver.recv();


                    //  Simple progress indicator for the viewer
                    System.out.flush();
                    //System.out.print(string + '.');

                    System.out.print("[ ");
                    for (int j = 0; j < test.length; j++) {
                        System.out.print(test[j] + " ");
                    }
                    System.out.print("]\n");

                    //  Do the work
                    //Thread.sleep(msec);

                    //  Send results to sink
                    //sender.send("".getBytes(), 0);
                    //sender.send(string + '.', 0);
                    sender.send(test, 0);
                }
                if (items.pollin(1)) {
                    break; // Exit loop
                }
                */
        }
        sender.close();
        receiver.close();
        //controller.close();
        context.term();
    }
}
