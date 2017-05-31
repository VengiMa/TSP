package com.github.VengiMa.Algorithm;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Admin on 10.04.2017.
 */
public class Cluster implements Serializable {
    public List<Point> points;
    public Point centroid;
    public int id;
    public Point in;
    public Point out;
    public LinkedList<Point> pointList;

    public Cluster(int id){
        this.id = id;
        this.points = new LinkedList<Point>();
        this.centroid = null;
        this.in = new Point(0,0,0);
        this.out = new Point(0,0,0);
    }

    /**
    *Methods
    **/

    //getter
    public List<Point> getPoints(){
        return points;
    }

    public Point getCentroid() {
        return centroid;
    }

    public Point getInPoint() {
        return in;
    }

    public Point getOutPoint() {
        return out;
    }

    public int getPointIndex(Point p){return points.indexOf(p);}

    public int getId() { return id; }

    //setter
    public void setPoints(List<Point> points){
        this.points = points;
    }

    public void setCentroid(Point centroid){
        this.centroid = centroid;
    }

    public void setInPoint(Point inPoint){
        this.in = inPoint;
    }

    public void setOutPoint(Point outPoint){
        this.out = outPoint;
    }


    //more
    public void addPoint(Point point){
        points.add(point);
    }

    public void clear(){
        points.clear();
    }

    public LinkedList<Point> toLinkedList() {
        for (Point p : points){
            pointList.add(p);
        }
        return pointList;
    }

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
