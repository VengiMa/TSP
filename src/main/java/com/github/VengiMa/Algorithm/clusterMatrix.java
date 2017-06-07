package com.github.VengiMa.Algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 13.04.2017.
 */
public class clusterMatrix {


    public static ClusterDistance [][] clusterMatrix (double [][] distanceMatrix, List<Cluster> clusters) {

        ClusterDistance[][] clusterMatrix = new ClusterDistance[clusters.size()][clusters.size()];
        Point from = new Point(-1, -1, -1);
        Point to = new Point(-1, -1, -1);
        int[] visited = new int[distanceMatrix.length];

        //todo: find a solution for calcualting the distance between the clusters, no direct calculation of both points
        //probably only one? and the other one when searching for the closest vertex?
        //calculate the closest edge, no matter if the vertex is already used or not, (visited)
        if (clusters.size() > 2) {
            for (int i = 0; i < clusters.size(); i++) {
                for (int j = i; j < clusters.size(); j++) {
                    if (i == j) {
                        Point useless = new Point(-1, -1, -1);
                        ClusterDistance cd = new ClusterDistance(0.00, clusters.get(i), clusters.get(j), useless, useless);
                        clusterMatrix[i][j] = cd;
                    } else {
                        double minimum = Double.MAX_VALUE;
                        int tempFrom = -1;
                        int tempTo = -1;
                        int quantityFromCluster = clusters.get(i).getPoints().size();
                        int quantityToCluster = clusters.get(j).getPoints().size();

                        for (int k = 0; k < quantityFromCluster; k++) {
                            int fromPoint = clusters.get(i).getPoints().get(k).getPointNumber() - 1;
                            if (visited[fromPoint] != 1) {
                                for (int l = 0; l < quantityToCluster; l++) {
                                    int toPoint = clusters.get(j).getPoints().get(l).getPointNumber() - 1;
                                    if (distanceMatrix[fromPoint][toPoint] < minimum && visited[toPoint] != 1) {
                                        minimum = distanceMatrix[fromPoint][toPoint];
                                        from = clusters.get(i).getPoints().get(k);
                                        to = clusters.get(j).getPoints().get(l);

                                        tempFrom = clusters.get(i).getPoints().get(k).getPointNumber();
                                        tempTo = clusters.get(j).getPoints().get(l).getPointNumber();
                                    }
                                }
                            }
                        }

                        if (tempFrom != -1 && tempTo != -1) {
                            if (quantityFromCluster == 1 && quantityToCluster == 1) {
                            } else if (quantityFromCluster <= 2) {
                                visited[tempTo - 1] = 1;
                            } else if (quantityToCluster <= 2) {
                                visited[tempFrom - 1] = 1;
                            } else if (quantityFromCluster < clusters.size()) {
                                visited[tempTo - 1] = 1;
                            } else if (quantityToCluster < clusters.size()) {
                                visited[tempFrom - 1] = 1;
                            } else {
                                visited[tempFrom - 1] = 1;
                                visited[tempTo - 1] = 1;
                            }
                        }

                        ClusterDistance zu = new ClusterDistance(minimum, clusters.get(i), clusters.get(j), from, to);
                        clusterMatrix[i][j] = zu;
                        ClusterDistance back = new ClusterDistance(minimum, clusters.get(i), clusters.get(j), to, from);
                        clusterMatrix[j][i] = back;
                    }
                }
            }
        }
        else if (clusters.size() ==2){
            for (int i = 0; i < clusters.size(); i++) {
                for (int j = 0; j < clusters.size(); j++) {
                    if (clusters.get(i).getPoints().size() == 0) {
                        Point useless = new Point(-1, -1, -1);
                        clusterMatrix[i][j] = new ClusterDistance(0.00, clusters.get(i), clusters.get(j), useless, useless);
                    } else if (clusters.get(j).getPoints().size() == 0) {
                        Point useless = new Point(-1, -1, -1);
                        clusterMatrix[i][j] = new ClusterDistance(0.00, clusters.get(i), clusters.get(j), useless, useless);
                    } else {
                        if (i == j) {
                            Point useless = new Point(-1, -1, -1);
                            clusterMatrix[i][j] = new ClusterDistance(0.00, clusters.get(i), clusters.get(j), useless, useless);
                        } else {
                            double minimum = Double.MAX_VALUE;
                            int tempFrom = -1;
                            int tempTo = -1;
                            int sizeClusteri = clusters.get(i).getPoints().size();
                            int sizeClusterj = clusters.get(j).getPoints().size();

                            for (int k = 0; k < clusters.get(i).getPoints().size(); k++) {
                                int fromPoint = clusters.get(i).getPoints().get(k).getPointNumber() - 1;
                                if (visited[fromPoint] != 1) {
                                    for (int l = 0; l < clusters.get(j).getPoints().size(); l++) {
                                        int toPoint = clusters.get(j).getPoints().get(l).getPointNumber() - 1;
                                        if (distanceMatrix[fromPoint][toPoint] < minimum && visited[toPoint] != 1) {
                                            minimum = distanceMatrix[fromPoint][clusters.get(j).getPoints().get(l).getPointNumber() - 1];
                                            from = clusters.get(i).getPoints().get(k);
                                            to = clusters.get(j).getPoints().get(l);

                                            tempFrom = clusters.get(i).getPoints().get(k).getPointNumber();
                                            tempTo = clusters.get(j).getPoints().get(l).getPointNumber();
                                        }
                                    }
                                }
                            }
                            if (tempFrom != -1 && tempTo != -1) {
                                if (sizeClusteri <= 2 && sizeClusterj <= 2) {
                                } else if (sizeClusteri <= 2) {
                                    visited[tempTo - 1] = 1;
                                } else if (sizeClusterj <= 2) {
                                    visited[tempFrom - 1] = 1;
                                } else {
                                    visited[tempFrom - 1] = 1;
                                    visited[tempTo - 1] = 1;
                                }
                            }
                            ClusterDistance zu = new ClusterDistance(minimum, clusters.get(i), clusters.get(j), from, to);
                            clusterMatrix[i][j] = zu;
                        }
                    }
                }
            }
        }
        return clusterMatrix;
    }


    /*
    //todo: try to only take the shortest distance, computing the entry and exit point after computing the tour.
    public static ClusterDistance [][] clusterMatrix (double [][] distanceMatrix, List<Cluster> clusters) {

        ClusterDistance[][] clusterMatrix = new ClusterDistance[clusters.size()][clusters.size()];
        Point from = new Point(-1, -1, -1);
        Point to = new Point(-1, -1, -1);
        int[] visited = new int[distanceMatrix.length];

        if (clusters.size() > 2) {
            for (int i = 0; i < clusters.size(); i++) {
                for (int j = i; j < clusters.size(); j++) {
                    if (i == j) {
                        Point useless = new Point(-1, -1, -1);
                        ClusterDistance cd = new ClusterDistance(0.00, clusters.get(i), clusters.get(j), useless, useless);
                        clusterMatrix[i][j] = cd;
                    } else {
                        double minimum = Double.MAX_VALUE;
                        int quantityFromCluster = clusters.get(i).getPoints().size();
                        int quantityToCluster = clusters.get(j).getPoints().size();

                        for (int k = 0; k < quantityFromCluster; k++) {
                            int fromPoint = clusters.get(i).getPoints().get(k).getPointNumber() - 1;
                            for (int l = 0; l < quantityToCluster; l++) {
                                int toPoint = clusters.get(j).getPoints().get(l).getPointNumber() - 1;
                                if (distanceMatrix[fromPoint][toPoint] < minimum && visited[toPoint] != 1) {
                                    minimum = distanceMatrix[fromPoint][toPoint];
                                    from = clusters.get(i).getPoints().get(k);
                                    to = clusters.get(j).getPoints().get(l);
                                }
                            }
                        }
                        ClusterDistance zu = new ClusterDistance(minimum, clusters.get(i), clusters.get(j), from, to);
                        clusterMatrix[i][j] = zu;
                        ClusterDistance back = new ClusterDistance(minimum, clusters.get(i), clusters.get(j), to, from);
                        clusterMatrix[j][i] = back;
                    }
                }
            }
        }
        else if (clusters.size() ==2){
            for (int i = 0; i < clusters.size(); i++) {
                for (int j = 0; j < clusters.size(); j++) {
                    if (i == j) {
                        Point useless = new Point(-1, -1, -1);
                        clusterMatrix[i][j] = new ClusterDistance(0.00, clusters.get(i), clusters.get(j), useless, useless);
                    } else {
                        double minimum = Double.MAX_VALUE;
                        int sizeClusteri = clusters.get(i).getPoints().size();
                        int sizeClusterj = clusters.get(j).getPoints().size();

                        for (int k = 0; k < sizeClusteri; k++) {
                            int fromPoint = clusters.get(i).getPoints().get(k).getPointNumber() - 1;
                            for (int l = 0; l < sizeClusterj; l++) {
                                int toPoint = clusters.get(j).getPoints().get(l).getPointNumber() - 1;
                                if (distanceMatrix[fromPoint][toPoint] < minimum && visited[toPoint] != 1) {
                                    minimum = distanceMatrix[fromPoint][clusters.get(j).getPoints().get(l).getPointNumber() - 1];
                                    from = clusters.get(i).getPoints().get(k);
                                    to = clusters.get(j).getPoints().get(l);
                                }
                            }
                        }
                        ClusterDistance zu = new ClusterDistance(minimum, clusters.get(i), clusters.get(j), from, to);
                        clusterMatrix[i][j] = zu;
                    }
                }
            }
        }
        return clusterMatrix;
    }
    */
}
