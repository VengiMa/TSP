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

import java.io.Serializable;
import java.sql.Timestamp;

/***
 * Contains all essential information that need to be distributed among the system instances
 */
public class DataPackage implements Serializable {
    private Cluster cluster;
    private double[][] distanceMatrix;
    private Tour clusterTour;
    private Tour tour;
    private int iD;
    private String typ;
    private String heuristic;
    private int numberClusters;
    private Timestamp startTime;
    private int iterations;

    /***
     * Initializes an empty object
     */
    public DataPackage(){}

    /***
     * Initializes an object with an ID, a cluster and a distance matrix
     * @param iD The ID of the Datapackage
     * @param c The cluster send with the package
     * @param matrix The distance matrix
     */
    public DataPackage(int iD, Cluster c, double[][] matrix){
        this.iD = iD;
        this.cluster = c;
        this.distanceMatrix = matrix;
    }

    /***
     * Initializes an object with an ID, a cluster, a distance matrix and the tour through all clusters
     * @param iD The ID of the Datapackage
     * @param c The cluster send with the package
     * @param matrix The distance matrix
     * @param clusterTour The tour through all clusters
     */
    public DataPackage(int iD, Cluster c, double[][] matrix, Tour clusterTour) {
        this.iD = iD;
        this.cluster = c;
        this.distanceMatrix = matrix;
        this.clusterTour = clusterTour;
    }

    /***
     *
     * @return The ID of the datapackage
     */
    public int getiDData(){return iD;}

    /***
     *
     * @return The cluster within the datapackage
     */
    public Cluster getClusterData(){return cluster;}

    /***
     *
     * @return The distance matrix within the datapackage
     */
    public double[][] getDistanceMatrixData(){return distanceMatrix;}

    /***
     *
     * @return The tour through all clusters
     */
    public Tour getClusterTourData() {return clusterTour;}

    /***
     *
     * @return The tour within a cluster
     */
    public Tour getTourData() {return tour;}

    /***
     *
     * @return The name of the problem instance
     */
    public String getTyp() {return typ;}

    /***
     *
     * @return The used heuristic to construct a tour
     */
    public String getHeuristic() {return heuristic;}

    /***
     *
     * @return The number of clusters the problem instance is partitioned into
     */
    public int getNumberClusters(){return numberClusters;}

    /***
     *
     * @return The starting time of the algorithm
     */
    public Timestamp getStartTime() {return startTime;}

    /***
     *
     * @return The number of iterations the problem will be processed
     */
    public int getIterations(){return iterations;}



    /***
     * Sets the ID of the datapackage
     * @param iD The ID
     */
    public void setiDData (int iD) {this.iD = iD;}

    /***
     * Sets the cluster of the datapackage
     * @param c The cluster
     */
    public void setClusterData (Cluster c){this.cluster = c;}

    /***
     * Sets the distance matrix of the datapackage
     * @param distance The distance matrix
     */
    public void setDistanceMatrixData (double[][] distance){this.distanceMatrix = distance;}

    /***
     * Sets the tour through all clusters
     * @param t The tour
     */
    public void setClusterTourData (Tour t){this.clusterTour = t;}

    /***
     * Sets the tour within a cluster
     * @param tour A tour
     */
    public void setTourData (Tour tour){this.tour = tour;}

    /***
     * Sets the processed problem instance
     * @param type Problem instance
     */
    public void setTyp (String type){this.typ = type;}

    /***
     * Sets the used heuristic to construct a tour. 1 = NN, 2 = FI, 3 = CI
     * @param heur The heuristic
     */
    public void setHeuristic (String heur){this.heuristic = heur;}

    /***
     * Sets the number of clusters the problem instance is partitioned into
     * @param number The number of clusters
     */
    public void setNumberClusters (int number){this.numberClusters = number;}

    /***
     * Sets the starting time of the algorithm within the class ventilator
     * @param start The starting time
     */
    public void setStartTime (Timestamp start){this.startTime = start;}

    /***
     * Sets the number of iterations the problem instance is processed
     * @param iter The number of iterations
     */
    public void setIterations (int iter){this.iterations = iter;}
}
