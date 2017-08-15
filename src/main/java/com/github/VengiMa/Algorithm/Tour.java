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
import java.util.Iterator;
import java.util.LinkedList;

/***
 * Is a tour through n points. Represented by a List of points
 */
public class Tour implements Serializable {
    private LinkedList<Point> pointList = new LinkedList<Point>();
    private double distance;
    private int iD;

    /***
     * Creates an new empty object of tour
     */
    public Tour(){}

    /***
     *
     * @param iD Creates a new tour with an ID
     */
    public Tour(int iD){
        this.iD = iD;
    }

    /***
     *
     * @param p Adds a point to the end of the tour
     */
    public void addPoint(Point p){
        pointList.add(p);
    }

    /***
     *
     * @param i The position for the insertion
     * @param p The point that will be inserted
     */
    public void insertPoint(int i, Point p){
        pointList.add(i, p);
    }

    /***
     *
     * @return Returns the size of the tour, here the number of points in the tour
     */
    public int getSize(){return pointList.size();}

    /***
     * Creates a copy of the Tour. Needed to modify it for calculations
     * @return A Linked List of the tour
     */
    public LinkedList<Point> copy(){
        LinkedList<Point> clone = new LinkedList<Point>();
        for (int i = 0; i < pointList.size(); i++) {
            clone.add(pointList.get(i));
        }
        return clone;
    }

    /***
     * Adds the tour t at the end of the current tour
     * @param t A tour t
     */
    public void addTour(Tour t){
        if (t.checkEmpty()){}
        for(int i =0; i < t.getSize(); i++){
            pointList.add(t.getPoint(i));
        }
    }

    /***
     * Inserts the tour at the index i
     * @param t A tour that will be inserted
     * @param i The index for inserting the tour
     */
    public void addTour(Tour t, int i){
        if (t.checkEmpty()){}
        for(int j =0; j < t.getSize(); j++){
            pointList.add(i, t.getPoint(j));
            i++;
        }
    }

    /***
     *
     * @return The distance saved within the tour object
     */
    public double getDistance () {return this.distance;}

    /***
     * Setting the distance of the tour with the value of length
     * @param length Distance of the tour
     */
    public void setDistance (double length) {this.distance = length;}

    /***
     * returns Point in tour at index i
     * @param i Index i, the point is placed
     * @return The point of the tour at i-th position
     */
    public Point getPoint(int i){
        return pointList.get(i);
    }

    /***
     *
     * @return First point of the tour
     */
    public Point getFirstPoint(){
        return pointList.getFirst();
    }

    /***
     *
     * @return The last point of the tour
     */
    public Point getLastPoint(){
        return pointList.getLast();
    }

    /***
     * Checks if the tour is empty
     * @return Boolean value, if tour is empty
     */
    public boolean checkEmpty(){
        return pointList.isEmpty();
    }

    /***
     * Returns the index of specified Point in the Tour
     * @param p Searched point
     * @return The position of the searched point in the tour
     */
    public int getPointIndex(Point p){
        return pointList.indexOf(p);
    }

    /***
     * Sets the point at index i with the new point p
     * @param i Index of the point that is modified
     * @param p The point that will be added
     */
    public void setPoint (int i, Point p){
        pointList.set(i, p);
    }

    /***
     * Removes the point p from the tour
     * @param p Point that is removed
     */
    public void removePoint (Point p){
        pointList.remove(p);
    }

    /***
     * Removes the point at i-th position
     * @param i The position of the tour that will be removed
     */
    public void removePointAtIndex(int i){pointList.remove(i); }

    /***
     * Looks if the point p is contained in the tour
     * @param p Point that is searched in the tour
     * @return A boolean if the point is part of the tour
     */
    public boolean containsPoint (Point p){
        for (int i =0; i<pointList.size(); i++){
            if(this.getPoint(i).equals(p)){
                return(true);
            }
        }
        return false;
    }

    /***
     * Changes the permutation of the tour, so that Point p is at the beginning
     * @param p The point the tour should start with
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

    /***
     * Provides a String of the current tour that contains all point numbers
     * @return A String of the tour
     */
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

    /***
     * Calculates the length of the current tour
     * @param distanceMatrix The distance matrix that is used for calculation
     * @return The total tour length of the tour
     */
    public double distanceTourLength(double[][] distanceMatrix){
        double distance = 0;
        for (int j =0; j<pointList.size(); j++){
            distance = distance + this.getPoint(j).getDistance2Point(this.pointList.get((j+1)%pointList.size()), distanceMatrix);
        }
        return distance;
    }

    /***
     * Checks if the tour contains every point of the problem
     * @param set The set of points as a List of Points
     * @return A boolean, if the tour is a feasible solution
     */
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
