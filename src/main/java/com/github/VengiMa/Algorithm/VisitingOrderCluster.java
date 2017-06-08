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
    public static Tour orderCluster (ClusterDistance [][] clusterDistances, double [][] distanceMatrix, List<Cluster> clusters){
        Tour orderCluster = new Tour(0);
        List<Point> clusterPoint = new LinkedList<Point>();
        double [][] distance = new double[clusterDistances.length][clusterDistances.length];
        int clusterNumber = -1;
        int following = -1;
        Point fromPoint;
        Point toPoint;
        int fromPointNumber;
        int toPointNumber;

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


        //todo: this is the working method!
        int tourLength = orderCluster.getSize();
        for (int i=0; i < tourLength; i++){
            //how to put the in and out points into each cluster, cluster 0 to i
            //iterate the tour, get the pointnumber (equal to the clusternumber and get the previous and following clusternumber
            //and with this information also the points, connecting the clusters
            clusterNumber = orderCluster.getPoint(i).getPointNumber()-1;
            following = orderCluster.getPoint((i+1)%tourLength).getPointNumber()-1;

            fromPoint = clusterDistances[clusterNumber][following].getfromNode();
            toPoint = clusterDistances[clusterNumber][following].gettoNode();

            clusters.get(clusterNumber).setOutPoint(fromPoint);
            clusters.get(following).setInPoint(toPoint);
        }
        //System.out.println("Successful orderCluster");
        return orderCluster;



        /*
        int tourLength = orderCluster.getSize();
        int [] visited = new int[distanceMatrix.length];
        for (int i=0; i < tourLength; i++){
            //how to put the in and out points into each cluster, cluster 0 to i
            //iterate the tour, get the pointnumber (equal to the clusternumber and get the previous and following clusternumber
            //and with this information also the points, connecting the clusters
            clusterNumber = orderCluster.getPoint(i).getPointNumber()-1;
            following = orderCluster.getPoint((i+1)%tourLength).getPointNumber()-1;
            fromPoint = clusterDistances[clusterNumber][following].getfromNode();
            toPoint = clusterDistances[clusterNumber][following].gettoNode();
            fromPointNumber = fromPoint.getPointNumber()-1;
            toPointNumber = toPoint.getPointNumber()-1;
            int quantityFromCluster = clusters.get(clusterNumber).getPoints().size();
            int quantityToCluster = clusters.get(following).getPoints().size();
            double minimum = Double.MAX_VALUE;

            //if both points weren't used before, mark them visited
            //and declare the in and outpoint of the clusters
            if (visited[fromPointNumber] !=1  && visited[toPointNumber] !=1){
                visited[fromPointNumber] = 1;
                visited[toPointNumber] = 1;

                clusters.get(clusterNumber).setOutPoint(fromPoint);
                clusters.get(following).setInPoint(toPoint);

            //if both points have been visited, search for the next shortest distance between the two clusters
            }else if(visited[fromPointNumber] ==1 && visited[toPointNumber] ==1){
                for (int k = 0; k < quantityFromCluster; k++) {
                    int fromNumber = clusters.get(clusterNumber).getPoints().get(k).getPointNumber() - 1;
                    if(visited[fromNumber] !=1) {
                        for (int l = 0; l < quantityToCluster; l++) {
                            int toNumber = clusters.get(following).getPoints().get(l).getPointNumber() - 1;
                            if (distanceMatrix[fromNumber][toNumber] < minimum && visited[toNumber] != 1) {
                                minimum = distanceMatrix[fromNumber][toNumber];
                                fromPoint = clusters.get(clusterNumber).getPoints().get(k);
                                toPoint = clusters.get(following).getPoints().get(l);
                            }
                        }
                    }
                }
                visited[fromPointNumber] = 1;
                visited[toPointNumber] = 1;

                clusters.get(clusterNumber).setOutPoint(fromPoint);
                clusters.get(following).setInPoint(toPoint);
            }else if(visited[fromPointNumber] ==1 && visited[toPointNumber] !=1){
                for (int k= 0; k < quantityFromCluster; k++){
                    int fromNumber = clusters.get(clusterNumber).getPoints().get(k).getPointNumber() - 1;
                    if(visited[fromNumber] !=1) {
                        if (distanceMatrix[fromNumber][toPointNumber] < minimum) {
                            minimum = distanceMatrix[fromNumber][toPointNumber];
                            fromPoint = clusters.get(clusterNumber).getPoints().get(k);
                        }
                    }
                }
                visited[fromPointNumber] = 1;
                visited[toPointNumber] = 1;

                clusters.get(clusterNumber).setOutPoint(fromPoint);
                clusters.get(following).setInPoint(toPoint);
            }else if(visited[fromPointNumber] !=1 && visited[toPointNumber] ==1){
                for (int l= 0; l < quantityToCluster; l++){
                    int toNumber = clusters.get(following).getPoints().get(l).getPointNumber() - 1;
                    if(visited[toNumber] !=1) {
                        if (distanceMatrix[fromPointNumber][toNumber] < minimum) {
                            minimum = distanceMatrix[fromPointNumber][toNumber];
                            toPoint = clusters.get(following).getPoints().get(l);
                        }
                    }
                }
                visited[fromPointNumber] = 1;
                visited[toPointNumber] = 1;

                clusters.get(clusterNumber).setOutPoint(fromPoint);
                clusters.get(following).setInPoint(toPoint);
            }
        }
        return orderCluster;
        */
    }
}
