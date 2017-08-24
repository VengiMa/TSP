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


package com.github.VengiMa.Algorithm;

import java.util.ArrayList;
import java.util.List;

/***
 * Calculates the distances between the clusters, using the pair of points with the shortest distance
 */
public class clusterMatrix {
    /***
     * Calculates the distance between the clusters by comparing all pair of points of the two involved clusters
     * @param distanceMatrix A 2-dimensional array. Contains the distances
     * @param clusters The list of all clusters
     * @return a two-dimensional array of objects from ClusterDistance, primary containing the distance with the involved points
     */
    public static ClusterDistance[][] clusterMatrix(double[][] distanceMatrix, List<Cluster> clusters) {

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
        } else if (clusters.size() == 2) {
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
}