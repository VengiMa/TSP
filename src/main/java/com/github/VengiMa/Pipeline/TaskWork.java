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

import com.github.VengiMa.Algorithm.Cluster;
import com.github.VengiMa.Algorithm.ClusterComputation;
import com.github.VengiMa.Algorithm.DataPackage;
import com.github.VengiMa.Algorithm.Tour;
import org.zeromq.ZMQ;
import com.github.VengiMa.Algorithm.*;

import java.sql.Timestamp;

/***
 * The worker receives the task from the ventilator. It calculates a tour through the cluster
 * from its entry to its exit point. The final tour, respectively the hamiltonian path, is send to the sink.
 */
public class TaskWork {
    /***
     * Calculates a tour through the cluster using the specified heuristic,
     * set in an environment variable and sends it to the sink
     * Environment variables:
     * The chosen heuristic, 1 = NN, 2 = FI, 3 =CI
     * The IP of the port of the sink
     * The IP of the port of the ventilator
     * @param args
     * @throws Exception Is thrown, if the environment variable is empty or wrong
     */
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

            //work with the received message/data
            data = (DataPackage) SerializationUtil.deserialize(byteArray);
            Cluster c = data.getClusterData();
            distanceMatrix = data.getDistanceMatrixData();

            Tour partialTour = ClusterComputation.createTour(c,distanceMatrix, choice);
            System.out.println();

            System.out.println("Calculation finished");
            data.setTourData(partialTour);
            data.setHeuristic(typ);
            System.out.flush();

            //  convert the tour into byteArray and send results to sink
            byteArray = SerializationUtil.serialize(data);
            sender.send(byteArray, 0);
            System.out.println("and sendt!");
            System.gc();

        }
        sender.close();
        receiver.close();
        context.term();
    }
}
