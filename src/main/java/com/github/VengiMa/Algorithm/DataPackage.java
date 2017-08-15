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

    public int getiDData(){return iD;}

    public Cluster getClusterData(){return cluster;}

    public double[][] getDistanceMatrixData(){return distanceMatrix;}

    public Tour getClusterTourData() {return clusterTour;}

    public Tour getTourData() {return tour;}

    public String getTyp() {return typ;}

    public String getHeuristic() {return heuristic;}

    public int getNumberClusters(){return numberClusters;}

    public Timestamp getStartTime() {return startTime;}

    public int getIterations(){return iterations;}



    public void setiDData (int iD) {this.iD = iD;}

    public void setClusterData (Cluster c){this.cluster = c;}

    public void setDistanceMatrixData (double[][] distance){this.distanceMatrix = distance;}

    public void setClusterTourData (Tour t){this.clusterTour = t;}

    public void setTourData (Tour tour){this.tour = tour;}

    public void setTyp (String type){this.typ = type;}

    public void setHeuristic (String heur){this.heuristic = heur;}

    public void setNumberClusters (int number){this.numberClusters = number;}

    public void setStartTime (Timestamp start){this.startTime = start;}

    public void setIterations (int iter){this.iterations = iter;}
}
