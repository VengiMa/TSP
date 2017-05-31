package com.github.VengiMa.Algorithm;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by Admin on 05.04.2017.
 */
public class Tour implements Serializable {

    /**
    *Variables
    **/
    public LinkedList<Point> pointList = new LinkedList<Point>();
    public double distance;
    public int iD;

    public Tour(){}
    public Tour(int iD){
        this.iD = iD;
    }

    /**
    *Mehtods
    **/
    //add a new Point at end of the Tour
    public void addPoint(Point p){
        pointList.add(p);
    }
    //add a new Point at index i
    public void insertPoint(int i, Point p){
        pointList.add(i, p);
    }
    //return the size of the tour
    public int getSize(){return pointList.size();}
    //duplicate the current tour
    public LinkedList<Point> copy(){
        LinkedList<Point> clone = new LinkedList<Point>();
        for (int i = 0; i < pointList.size(); i++) {
            clone.add(pointList.get(i));
        }
        return clone;
    }
    //add another tour at the end of the current tour
    public void addTour(Tour t){
        if (t.checkEmpty()){}
        for(int i =0; i < t.getSize(); i++){
            pointList.add(t.getPoint(i));
        }
    }
    public void addTour(Tour t, int i){
        if (t.checkEmpty()){}
        for(int j =0; j < t.getSize(); j++){
            pointList.add(i, t.getPoint(j));
            i++;
        }
    }
    //get the Distance of the Tour
    public double getDistance () {return this.distance;}
    //set the DIstance of the Tour
    public void setDistance (double length) {this.distance = length;}
    //returns Point in tour at index i
    public Point getPoint(int i){
        return pointList.get(i);
    }
    //returns the first Point in the Tour
    public Point getFirstPoint(){
        return pointList.getFirst();
    }
    //returns the last Point in the Tour
    public Point getLastPoint(){
        return pointList.getLast();
    }
    //checks if the tour is empty
    public boolean checkEmpty(){
        return pointList.isEmpty();
    }
    //returns the index of specified Point in the Tour
    public int getPointIndex(Point p){
        return pointList.indexOf(p);
    }
    //sets the point at index i with the new point p
    public void setPoint (int i, Point p){
        pointList.set(i, p);
    }
    //remove the point p from the tour
    public void removePoint (Point p){
        pointList.remove(p);
    }
    //remove point at index i
    public void removePointAtIndex(int i){pointList.remove(i); }
    //check if tour contains point p
    public boolean containsPoint (Point p){
        for (int i =0; i<pointList.size(); i++){
            if(this.getPoint(i).equals(p)){
                return(true);
            }
        }
        return false;
    }

    public int getiD(){return iD;}
    public void setiD(int iD){this.iD = iD;}

    /**
     *change the permutation of the tour, so that point p is at the beginning
     */
    public void setStartingPoint(Point p){
        boolean reachedStartingPoint = false;
        LinkedList<Point> temp = new LinkedList<>();
        for(int i = this.pointList.size() -1 ; i >= 0; i --){
            if(!reachedStartingPoint) {
                Point point = this.pointList.get(this.pointList.size()-1);
                this.pointList.remove(point);
                temp.add(point);
                if(point == p){
                    reachedStartingPoint = true;
                }
            }
        }
        for (Point c : temp){
            this.pointList.add(0,c);
        }
    }

    //put out the current Tour
    public String tour2String(){
        Point p;
        Iterator<Point> tourIterator = pointList.iterator();
        String tourString = "[";
        while(tourIterator.hasNext()){
            p = tourIterator.next();
            tourString = tourString + p.getPointNumber()+ " ";
        }
        tourString = tourString.substring(0, tourString.length()-1) + "]";
        return tourString;
    }

    public double distanceTourLength(double[][] distanceMatrix){
        double distance = 0;
        for (int j =0; j<pointList.size(); j++){
            distance = distance + this.getPoint(j).getDistance2Point(this.pointList.get((j+1)%pointList.size()), distanceMatrix);
        }
        return distance;
    }

    public boolean isFeasible (LinkedList<Point> set){
        boolean isFeasible = false;

        for (int i =0; i < set.size(); i++){
            isFeasible = this.containsPoint(set.get(i));
            if (!isFeasible){
                System.out.println("The computed tour is no feasible solution!");
                break;
            }
        }
        return isFeasible;
    }
}
