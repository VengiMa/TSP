package com.github.VengiMa.Algorithm;

import java.io.Serializable;

/**
 * Created by Admin on 05.04.2017.
 */
public class Point implements Serializable {

    /**
     * Variables
     **/
    private int pointNumber;
    private double xCoord;
    private double yCoord;
    private int cluster_number = 0;


    /**
     *Constructor
     **/
    public Point (int pointNumber, double xCoord, double yCoord){
        this.pointNumber = pointNumber;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.cluster_number = -1;
    }

    /**
     * Methods
     **/
    //getter
    public int getPointNumber(){return this.pointNumber;}
    public double getXCoord(){return this.xCoord;}
    public double getYCoord(){return this.yCoord;}
    public int getCluster(){return this.cluster_number;}

    //setter
    public void setPointNumber(int i){this.pointNumber = i;}
    public void setXCoord (double x){this.xCoord = x;}
    public void setYCoord (double y){this.yCoord = y;}
    public void setCluster (int n) {this.cluster_number = n;}

    //Print a point
    public void printPoint(){
        System.out.println("PointNumber: " + pointNumber + "; XCoord: " + xCoord + "; YCoord: " + yCoord +"; ClusterNumber: " + cluster_number);
    }

    //get the distance to a point z with matrix
    public double getDistance2Point (Point z, double [][] distanceMatrix){
        double distance = -1;
        distance = distanceMatrix[this.pointNumber-1][z.pointNumber-1];
        return distance;
    }

    //calcaulate the distance between two points using the coordinates
    public static double distance(Point p, Point centroid){
        return Math.sqrt(Math.pow((centroid.getYCoord() - p.getYCoord()), 2) + Math.pow((centroid.getXCoord() - p.getXCoord()), 2));
    }
}

