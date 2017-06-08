package com.github.VengiMa.Pipeline;

/**
 * Created by Admin on 21.05.2017.
 */
import com.github.VengiMa.Algorithm.DataPackage;
import com.github.VengiMa.Algorithm.Tour;
import org.zeromq.ZMQ;
import com.github.VengiMa.Algorithm.*;

import java.util.ArrayList;

public class TaskSink {
    public static void main (String[] args) throws Exception {

        //  Prepare our context and socket
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket receiver = context.socket(ZMQ.PULL);

        System.out.println("Bind Sink to " + "tcp://*:5558 \n");
        receiver.bind("tcp://*:5558");

        // Socket for subscribe
        //ZMQ.Socket sub = context.socket(ZMQ.SUB);
        //sub.bind("tcp://*:5559");

        //  Wait for start of batch
        byte[] maxTaskByte = receiver.recv();
        int maxTask_nbr = (int) SerializationUtil.deserialize(maxTaskByte);

        for (int ii=0; ii<10; ii++) {

            double[][] distanceMatrix = new double[0][0];
            Tour clusterTour = new Tour();
            Tour finalTour = new Tour();
            ArrayList<DataPackage> dataSet = new ArrayList<>();
            DataPackage data;
            long tstart =0;

            //  Process the confirmations
            int task_nbr;
            for (task_nbr = 0; task_nbr < maxTask_nbr; task_nbr++) {
                byte[] byteArray = receiver.recv();
                data = (DataPackage) SerializationUtil.deserialize(byteArray);

                if (task_nbr == 0) {
                    tstart = System.currentTimeMillis();
                    distanceMatrix = data.getDistanceMatrixData();
                }
                if (data.getClusterTourData() != null) {
                    clusterTour = data.getClusterTourData();
                }
                dataSet.add(data);
            /*
            if ((task_nbr / 10) * 10 == task_nbr) {
                System.out.print(":");
            } else {
                System.out.print(".");
            }
            */
                System.out.flush();
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
            double distance = finalTour.distanceTourLength(distanceMatrix);
            System.out.println(String.format("%.2f", distance));
            //  Calculate and report duration of batch
            long tend = System.currentTimeMillis();
            System.out.println((tend - tstart));
        }

        //  Send the kill signal to the workers
        //controller.send("KILL", 0);

        //controller.close();
        receiver.close();
        context.term();
    }
}