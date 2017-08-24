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

import java.util.LinkedList;
import java.util.List;

/***
 * Calculates the visiting order through all clusters. Therefore, a tour is calculated and Entry and Exit points
 * of every cluster are set, according to the calculated tour
 */
public class VisitingOrderCluster {

    /***
     * Calculates a tour through all clusters and iterates the tour, setting the Entry and Exit points for every cluster
     * @param clusterDistances Two-dimensional array of ClusterDistance objects
     * @param clusters List of clusters
     * @return A tour through all k clusters
     */
    public static Tour orderCluster (ClusterDistance [][] clusterDistances, List<Cluster> clusters){
        Tour orderCluster = new Tour(0);
        List<Point> clusterPoint = new LinkedList<Point>();
        double [][] distance = new double[clusterDistances.length][clusterDistances.length];
        int clusterNumber = -1;
        int following = -1;
        Point fromPoint;
        Point toPoint;

        for (int i=0; i < clusterDistances.length; i++){
            for (int j =0; j < clusterDistances.length; j++){
                distance[i][j] = clusterDistances[i][j].getClusterDistance();
            }
        }
        for (int i=0; i<clusters.size(); i++){
            Point temp = new Point(i+1, -1, -1);
            clusterPoint.add(temp);
        }
        orderCluster = ConstructionTourThroughClusters.NNHeuristic(distance, clusterPoint);

        LocalSearch twoOpt = new LocalSearch();
        twoOpt.twoOptCluster(orderCluster, distance);

        int tourLength = orderCluster.getSize();
        for (int i=0; i < tourLength; i++){
            clusterNumber = orderCluster.getPoint(i).getPointNumber()-1;
            following = orderCluster.getPoint((i+1)%tourLength).getPointNumber()-1;

            fromPoint = clusterDistances[clusterNumber][following].getfromNode();
            toPoint = clusterDistances[clusterNumber][following].gettoNode();

            clusters.get(clusterNumber).setOutPoint(fromPoint);
            clusters.get(following).setInPoint(toPoint);
        }
        return orderCluster;
    }
}
