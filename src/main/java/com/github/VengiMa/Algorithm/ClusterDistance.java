package com.github.VengiMa.Algorithm;

/**
 * Created by Admin on 14.04.2017.
 */
public class ClusterDistance {
    private double distance;
    private Point fromNode;
    private Point toNode;
    private Cluster from;
    private Cluster to;

    /**
     * Constructor
    **/
    public ClusterDistance(double distance,Cluster from, Cluster to, Point fromNode, Point toNode){
        this.distance = distance;
        this.from = from;
        this.to = to;
        this.fromNode = fromNode;
        this.toNode = toNode;
    }

    /**
     *MEthods
    **/

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
