package com.github.VengiMa.Algorithm;

import java.io.Serializable;

/**
 * Created by Admin on 25.05.2017.
 */
public class DataPackage implements Serializable {
    public Cluster cluster;
    public double[][] distanceMatrix;
    public Tour clusterTour;
    public Tour tour;
    public int iD;

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

    /**
     * setter
     */

    public void setiDData (int iD) {this.iD = iD;}

    public void setClusterData (Cluster c){this.cluster = c;}

    public void setDistanceMatrixData (double[][] distance){this.distanceMatrix = distance;}

    public void setClusterTourData (Tour t){this.clusterTour = t;}

    public void setTourData (Tour tour){this.tour = tour;}
}
