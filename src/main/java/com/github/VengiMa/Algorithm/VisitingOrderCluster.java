package com.github.VengiMa.Algorithm;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Admin on 15.04.2017.
 */
public class VisitingOrderCluster {
    /**
     *Method for calcuating the visiting order of the clusters, based on Nearest Neighbour with LocalSearch Routines
     *on top, when a tour is calculated, assign the in and out vertices in each cluster, so those clusters can be assigned to the CPU's
     *Take the clusterMatrix with the distances --> compute a tour with NN and 2-opt --> assign the in and out vertices for each cluster
    **/
    public static Tour orderCluster (ClusterDistance [][] clusterDistances, List<Cluster> clusters){
        Tour orderCluster = new Tour(0);
        List<Point> clusterPoint = new LinkedList<Point>();
        double [][] distance = new double[clusterDistances.length][clusterDistances.length];
        int clusterNumber = -1;
        int following = -1;
        Point from;
        Point to;


        //generate the ClusterDistance out of the MAtrix
        for (int i=0; i < clusterDistances.length; i++){
            for (int j =0; j < clusterDistances.length; j++){
                distance[i][j] = clusterDistances[i][j].getClusterDistance();
            }
        }
        //generate the PointList for the NN Heuristic
        for (int i=0; i<clusters.size(); i++){
            Point temp = new Point(i+1, -1, -1);
            clusterPoint.add(temp);
        }
        orderCluster = ConstructionTourThroughClusters.NNHeuristic(distance, clusterPoint);

        LocalSearchForClusters twoOpt = new LocalSearchForClusters();
        twoOpt.twoOpt(orderCluster, distance);

        int tourLength = orderCluster.getSize();
        for (int i=0; i < tourLength; i++){
            //how to put the in and out points into each cluster, cluster 0 to i
            //iterate the tour, get the pointnumber (equal to the clusternumber and get the previous and following clusternumber
            //and with this information also the points, connecting the clusters
            clusterNumber = orderCluster.getPoint(i).getPointNumber()-1;
            following = orderCluster.getPoint((i+1)%tourLength).getPointNumber()-1;

            from = clusterDistances[clusterNumber][following].getfromNode();
            to = clusterDistances[clusterNumber][following].gettoNode();

            clusters.get(clusterNumber).setOutPoint(from);
            clusters.get(following).setInPoint(to);
        }
        System.out.println("Successful orderCluster");
        return orderCluster;
    }
}
