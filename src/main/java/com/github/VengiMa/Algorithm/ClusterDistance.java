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

/***
 * Object that is used in the ClusterDistance class to contain the distance and the entry and exit point
 * of the two involved clusters.
 */
public class ClusterDistance {
    private double distance;
    private Point fromNode;
    private Point toNode;
    private Cluster from;
    private Cluster to;

    /***
     * Object that is initialized in the ClusterDistance class.
     * @param distance The distance between two clusters
     * @param from The cluster the distance is calculated from
     * @param to The cluster the distance is calculated to
     * @param fromNode The Exit point of the cluster the distance is calculated from
     * @param toNode The Entry point of the cluster the distance is calculated to
     */
    public ClusterDistance(double distance,Cluster from, Cluster to, Point fromNode, Point toNode){
        this.distance = distance;
        this.from = from;
        this.to = to;
        this.fromNode = fromNode;
        this.toNode = toNode;
    }

    //getter
    public double getClusterDistance() {return this.distance;}
    public Cluster getFrom() {return this.from;}
    public Cluster getTo() {return this.to;}
    public Point getfromNode() {return this.fromNode;}
    public Point gettoNode() {return this.toNode;}

    //Setter
    public void setClusterDistance(double distance) {this.distance = distance;}
    public void setFrom(Cluster k) {this.from = k;}
    public void setTo(Cluster l) {this.to = l;}
    public void setfromNode(Point i) {this.fromNode = i;}
    public void settoNode(Point j) {this.toNode = j;}
}
