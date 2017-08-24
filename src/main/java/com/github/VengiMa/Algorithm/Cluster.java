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
import java.util.LinkedList;
import java.util.List;

/***
 * A cluster is an object that contains a List of points, a centroid that represents its centre, an ID
 * and an Entry and Exit point. The set of points is partitioned into a fixed number of clusters
 */
public class Cluster implements Serializable {
    private List<Point> points;
    private Point centroid;
    public int id;
    private Point in;
    private Point out;

    /**
     *
     * @param id An identification number for every cluster
     */
    public Cluster(int id){
        this.id = id;
        this.points = new LinkedList<Point>();
        this.centroid = null;
        this.in = new Point(0,0,0);
        this.out = new Point(0,0,0);
    }


    /**
     *
     * @return A list of all points contained by the cluster
     */
    public List<Point> getPoints(){
        return points;
    }

    /**
     *
     * @return The centroid of the cluster
     */
    public Point getCentroid() {
        return centroid;
    }

    /**
     *
     * @return The entry point of the cluster
     */
    public Point getInPoint() {
        return in;
    }

    /**
     *
     * @return The exit point of the cluster
     */
    public Point getOutPoint() {
        return out;
    }

    /**
     *
     * @param p A point inside the cluster
     * @return The index of the point inside the cluster
     */
    public int getPointIndex(Point p){return points.indexOf(p);}

    /**
     *
     * @return The ID of the cluster
     */
    public int getId() { return id; }



    /**
     *
     * @param points A list of points that will be added to the cluster
     */
    public void setPoints(List<Point> points){
        this.points = points;
    }

    /**
     *
     * @param centroid The point that represents the centroid of the cluster
     */
    public void setCentroid(Point centroid){
        this.centroid = centroid;
    }

    /**
     *
     * @param inPoint The entry point of the cluster
     */
    public void setInPoint(Point inPoint){
        this.in = inPoint;
    }

    /**
     *
     * @param outPoint The exit point of the cluster
     */
    public void setOutPoint(Point outPoint){
        this.out = outPoint;
    }


    /**
     *
     * @param point Adds a point to the cluster at the end of the List
     */
    public void addPoint(Point point){
        points.add(point);
    }

    /**
     * clears the cluster
     */
    public void clear(){
        points.clear();
    }

    /**
     * plots all information of the cluster, e.g. ID, Centroid, Entry and Exit Point, every point it contains
     */
    public void plotCluster() {
        System.out.println("[Cluster: " + id+"]");
        System.out.print("[Centroid: " ); centroid.printPoint();
        System.out.print("[Point in: " ); in.printPoint();
        System.out.print("[Point out: " ); out.printPoint();
        System.out.print("[Points: \n");
        for(Point p : points) {
            p.printPoint();
        }
        System.out.println("]");
    }

}
