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

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.*;

/***
 * Implements the k-means algorithm to partition a set of points into k clusters
 */
public class K_Means{
    private int maxPointsperCluster = Integer.MAX_VALUE;
    private int minPointsperCluster = 0;

    private LinkedList<Point> points;
    private static List<Cluster> clusters;

    /***
     * Initializes the K_Means object with an empty List of points and clusters
     */
    public K_Means(){
        this.points = new LinkedList<Point>();
        this.clusters = new ArrayList<Cluster>();
    }

    /***
     * Initializes k empty clusters with k centroids representing the centre of the cluster and adds them to the List clusters
     * @param init A List that contains all points from the TSP
     * @param number The number of clusters, therefore k
     */
    public void init(LinkedList<Point> init, int number){
        points = init;
        maxPointsperCluster = (int)Math.ceil((points.size()/number) + 0.05*points.size());
        minPointsperCluster = (int)Math.ceil((points.size()/number) - 0.05*points.size());

        List<Integer> chosenPoints = new ArrayList<Integer>();
        for(int i =0; i < number; i++){
            Cluster cluster = new Cluster(i);
            Random r = new Random();
            int pointNumber = r.nextInt(points.size());
            while (chosenPoints.contains(pointNumber)){
                pointNumber = r.nextInt(points.size());
            }
            chosenPoints.add(pointNumber);
            double x = points.get(pointNumber).getXCoord();
            double y = points.get(pointNumber).getYCoord();
            Point centroid = new Point(-i, x, y);
            cluster.setCentroid(centroid);
            clusters.add(cluster);
        }
    }

    /***
     * prints out every cluster with its information like contained points, the centroid, ID etc.
     */
    private void plotClusters(){
        for (int i = 0; i < clusters.size(); i++){
            Cluster c = clusters.get(i);
            c.plotCluster();
        }
    }

    /***
     * Calculates the clusters. That means assigning each point to the cluster with the shortest distance to its centroid
     * This method stops when the centroids have not changed, so the clusters stayed the same.
     * It counts the amount of needed iterations
     */
    public void calculate(){
        boolean finish = false;
        int iteration = 0;

        while(!finish){
            clearClusters();
            List<Point> lastCentroids = getCentroids();
            Double [][] last = new Double [lastCentroids.size()][2];
            for (int i =0; i< lastCentroids.size(); i++){
                last[i][0] = lastCentroids.get(i).getXCoord();
                last[i][1] = lastCentroids.get(i).getYCoord();
            }

            assignClusters();

            calculateCentroids();

            iteration++;

            List<Point> currentCentroids = getCentroids();

            double distance = 0.0;
            double sum = 0;
            for (int i =0; i< lastCentroids.size(); i++){

                Point q = currentCentroids.get(i);
                distance = Math.sqrt(Math.pow((currentCentroids.get(i).getYCoord() - last[i][1]), 2) + Math.pow((currentCentroids.get(i).getXCoord() - last[i][0]), 2));
                sum = sum + distance;
            }

            if(sum==0) {
                finish = true;
                System.out.println("Iteration: " + iteration);
                for (Cluster cl : clusters){
                    System.out.println("Cluster " + cl.id + ": " + cl.getPoints().size());
                }
                System.out.println("######");
            }
        }
    }

    /***
     * Clears all clusters, removing all points from every cluster
     */
    private void clearClusters(){
        for(Cluster cluster: clusters){
            cluster.clear();
        }
    }

    /***
     * Creates a List of points that contain the centroid of every cluster
     * @return
     */
    private List<Point> getCentroids() {
        List<Point> centroids = new LinkedList<Point>();
        for(Cluster cluster : clusters) {
            Point aux = cluster.getCentroid();
            centroids.add(aux);
        }
        return centroids;
    }

    /***
     * Assigns all points to the cluster with the shortest distance to its centroid
     */
    private void assignClusters(){
        double min;
        int cluster = 0;
        double distance = 0.0;

        for (Point point: points){
            min = Double.MAX_VALUE;
            for (int i =0; i < clusters.size(); i++){
                Cluster c = clusters.get(i);
                //no more Points in one Cluster then the defined size
                if (c.getPoints().size() < maxPointsperCluster) {
                    distance = Point.distance(point, c.getCentroid());
                    if (distance < min) {
                        min = distance;
                        cluster = i;
                    }
                }
            }
            point.setCluster(cluster);
            clusters.get(cluster).addPoint(point);
        }
    }

    /***
     * Recalculates the new centroids for every cluster after each iteration
     */
    private void calculateCentroids(){
        for (Cluster cluster: clusters){
            double sumX = 0;
            double sumY = 0;
            List<Point> list = cluster.getPoints();
            int n_points = list.size();

            for(Point point: list){
                sumX = sumX + point.getXCoord();
                sumY = sumY + point.getYCoord();
            }

            Point centroid = cluster.getCentroid();
            if(n_points > 0){
                double newX = sumX / n_points;
                double newY = sumY / n_points;
                centroid.setXCoord(newX);
                centroid.setYCoord(newY);
            }
        }
    }

    /***
     * Returns the List clusters as an object from List
     * @return List of clusters
     */
    public static List<Cluster> getClusters() {
        return clusters;
    }
}
