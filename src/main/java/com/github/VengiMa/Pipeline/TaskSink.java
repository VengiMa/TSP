package com.github.VengiMa.Pipeline;

/**
 * Created by Admin on 21.05.2017.
 */
import com.github.VengiMa.Algorithm.DataPackage;
import com.github.VengiMa.Algorithm.Tour;
import org.zeromq.ZMQ;
import com.github.VengiMa.Algorithm.*;

import java.sql.*;
import java.util.ArrayList;


/***
 *
 */
public class TaskSink {
    /***
     *
     * @param args
     * @throws Exception
     */
    public static void main (String[] args) throws Exception {

        //  Prepare our context and socket
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket receiver = context.socket(ZMQ.PULL);

        System.out.println("Bind Sink to " + "tcp://*:5558 \n");
        receiver.bind("tcp://*:5558");

        String database;
        database = System.getenv("DATABASE");
        if (database == null)
            database = "10.95.61.77:5433/postgres";
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://" + database;
        Connection conn = DriverManager.getConnection(url,"postgres","postgres");

        //  Wait for start of batch
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
            long dur;
            String heur = "NN";

            //  Process the confirmations
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
            //  Calculate and report duration of batch
            Timestamp tend = new Timestamp(System.currentTimeMillis());
            dur = -1;
            try {
                dur = (tend.getTime() - tstart.getTime());
            }
            catch (Exception e){
                e.printStackTrace();
            }
            //todo:
            //10 nearest neighbours, storage
            //clusterung, schnittebenen
            //java-doc file erzeugen, mit dokumentation

            double distance = ((double) Math.round(finalTour.distanceTourLength(distanceMatrix)*100))/100;

            System.out.println(String.format("%.2f", distance));


            String sql = "INSERT INTO test_results " +
                    "(begin, ending, duration, tourlength, typ, heuristic, clusters)"+
                    "VALUES(?,?,?,?,?,?,?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setTimestamp(1,tstart);
            pst.setTimestamp(2,tend);
            pst.setLong(3, dur);
            pst.setDouble(4,distance);
            pst.setString(5,typemap);
            pst.setString(6,heur);
            pst.setInt(7,maxTask_nbr);
            pst.executeUpdate();

            System.out.println("Inserting successful...!");
            System.gc();
        }

        System.out.println("Calculation finished!");

        //  Send the kill signal to the workers
        //controller.send("KILL", 0);

        //controller.close();
        receiver.close();
        context.term();
    }
}