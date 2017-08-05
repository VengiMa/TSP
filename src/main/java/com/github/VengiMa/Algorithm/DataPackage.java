package com.github.VengiMa.Algorithm;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Admin on 25.05.2017.
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


    public DataPackage(){}

    public DataPackage(int iD, Cluster c, double[][] matrix){
        this.iD = iD;
        this.cluster = c;
        this.distanceMatrix = matrix;
    }

    public DataPackage(int iD, Cluster c, double[][] matrix, Tour clusterTour) {
        this.iD = iD;
        this.cluster = c;
        this.distanceMatrix = matrix;
        this.clusterTour = clusterTour;
    }

    /**
     * getter
     */

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
    /**
     * setter
     */

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
