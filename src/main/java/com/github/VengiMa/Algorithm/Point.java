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

//todo: Java doc über tools

package com.github.VengiMa.Algorithm;

import java.io.Serializable;

/***
 * Contains x- and y-coordinate, the number of the point and the number of the cluster it belongs to.
 * Represents the smallest instance for every other class
 */
public class Point implements Serializable {
    private int pointNumber;
    private double xCoord;
    private double yCoord;
    private int cluster_number = 0;

    /***
     *
     * @param pointNumber The point number according to the text-file
     * @param xCoord The x-coordinate as declared in the text-file
     * @param yCoord The y-coordinate as declared in the text-file
     */
    public Point (int pointNumber, double xCoord, double yCoord){
        this.pointNumber = pointNumber;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.cluster_number = -1;
    }

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

    /***
     *
     * @param z The point the distance should be calculated to.
     * @param distanceMatrix The distance matrix, where the distances are stored in.
     * @return The distance between the current point and the point z, using a distance matrix
     */
    public double getDistance2Point (Point z, double [][] distanceMatrix){
        double distance = -1;
        distance = distanceMatrix[this.pointNumber-1][z.pointNumber-1];
        return distance;
    }

    /***
     *
     * @param p The first point, the distance will be calculated from
     * @param q The second point, the distance will be calculated to
     * @return The distance between the two points p and q, using the coordinates
     */
    public static double distance(Point p, Point q){
        return Math.sqrt(Math.pow((q.getYCoord() - p.getYCoord()), 2) + Math.pow((q.getXCoord() - p.getXCoord()), 2));
    }
}

