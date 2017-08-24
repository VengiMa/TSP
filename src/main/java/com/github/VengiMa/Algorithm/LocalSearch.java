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

import java.util.LinkedList;

/***
 * Provides the Local Search methods for improving an existing tour
 */
public class LocalSearch {
    /***
     * Improves a tour inside a cluster using the 2-opt move. Sets the starting point of the tour to the Entry point of the cluster
     * @param distanceMatrix A 2-dimensional array. Contains the distances
     * @param cluster The cluster the tour is calculated in
     * @param tour The calculated tour
     */
    public void twoOpt (double[][] distanceMatrix, Cluster cluster, Tour tour) {
        int groesseTour = tour.getSize();
        boolean geschafft = false;
        double before, after;
        int a,b,c,d;
        int steps = 0;
        if (tour.getSize() >= 4) {
            while (steps < ((groesseTour)*(groesseTour/2))) {

                for (int i =0; i<groesseTour - 3;i++){

                    for (int k =i+1; k<groesseTour-2; k++){

                        if(i == 0) {
                            a = tour.getPoint(groesseTour-1).getPointNumber()-1;
                        }else {
                            a = tour.getPoint(i-1).getPointNumber()-1;
                        }

                        b = tour.getPoint(i).getPointNumber()-1;
                        c = tour.getPoint(k).getPointNumber()-1;
                        d = tour.getPoint(k+1).getPointNumber()-1;
                        before = distanceMatrix[a][b]+distanceMatrix[c][d];
                        after = distanceMatrix[a][c]+distanceMatrix[b][d];

                        if(after < before){
                            steps = 0;

                            LinkedList<Point> tempList = new LinkedList<Point>();
                            int l;
                            for (l = i; l <= k; l++) {
                                tempList.add(tour.getPoint(l));
                            }
                            l = i;
                            for (int j = tempList.size() - 1; j >= 0; j--) {
                                tour.setPoint(l, tempList.get(j));
                                l++;
                            }
                            geschafft = true;
                        }else{
                            steps++;
                        }
                    }
                }
            }
        }
        if (geschafft == true){
            System.out.println("2-Opt executed!");
        }
        tour.setStartingPoint(cluster.getInPoint());
    }

    /***
     * Improves a given tour for the sequential algorithm
     * @param tour Calculated tour through all points
     * @param distanceMatrix A 2-dimensional array. Contains the distances
     */
    public void twoOptSequentiel (Tour tour, double[][] distanceMatrix) {
        int groesseTour = tour.getSize();
        double before, after;
        int a, b, c, d;
        int steps = 0;
        if (tour.getSize() >= 4) {
            while (steps < ((groesseTour)*(groesseTour/2))) {

                for (int i = 0; i < groesseTour - 3; i++) {

                    for (int k = i + 1; k < groesseTour - 2; k++) {

                        if (i == 0) {
                            a = tour.getPoint(groesseTour - 1).getPointNumber() - 1;
                        } else {
                            a = tour.getPoint(i - 1).getPointNumber() - 1;
                        }

                        b = tour.getPoint(i).getPointNumber() - 1;
                        c = tour.getPoint(k).getPointNumber() - 1;
                        d = tour.getPoint(k + 1).getPointNumber() - 1;
                        before = distanceMatrix[a][b] + distanceMatrix[c][d];
                        after = distanceMatrix[a][c] + distanceMatrix[b][d];

                        if (after < before) {
                            steps = 0;
                            LinkedList<Point> tempList = new LinkedList<Point>();
                            int l = i;
                            for (l = i; l <= k; l++) {
                                tempList.add(tour.getPoint(l));
                            }
                            l = i;
                            for (int j = tempList.size() - 1; j >= 0; j--) {
                                tour.setPoint(l, tempList.get(j));
                                l++;
                            }
                        }
                        else{
                            steps++;
                        }
                    }
                }
            }
        }
    }

    /***
     * The improvement method that is executed for a given time after merging the hamiltonian paths from every cluster back together
     * @param tour The calculated tour
     * @param distanceMatrix A 2-dimensional array. Contains the distances
     * @param duration The duration the whole algorithm will be executed
     */
    public void twoOptAfter (Tour tour, double[][] distanceMatrix, long duration) {
        int groesseTour = tour.getSize();
        long startTime = System.currentTimeMillis();
        double before, after;
        int a, b, c, d;
        if (tour.getSize() >= 4) {
            while (System.currentTimeMillis()-startTime <= duration) {

                for (int i = 0; i < groesseTour - 3; i++) {

                    for (int k = i + 1; k < groesseTour - 2; k++) {

                        if (i == 0) {
                            a = tour.getPoint(groesseTour - 1).getPointNumber() - 1;
                        } else {
                            a = tour.getPoint(i - 1).getPointNumber() - 1;
                        }

                        b = tour.getPoint(i).getPointNumber() - 1;
                        c = tour.getPoint(k).getPointNumber() - 1;
                        d = tour.getPoint(k + 1).getPointNumber() - 1;
                        before = distanceMatrix[a][b] + distanceMatrix[c][d];
                        after = distanceMatrix[a][c] + distanceMatrix[b][d];

                        if (after < before) {
                            LinkedList<Point> tempList = new LinkedList<Point>();
                            int l = i;
                            for (l = i; l <= k; l++) {
                                tempList.add(tour.getPoint(l));
                            }
                            l = i;
                            for (int j = tempList.size() - 1; j >= 0; j--) {
                                tour.setPoint(l, tempList.get(j));
                                l++;
                            }
                        }
                    }
                }
            }
        }
        System.out.println("TwoOptAfter executed");
    }

    /***
     * Improves a given tour using the 2-opt move
     * @param tour The calculated tour
     * @param distancematrix A 2-dimensional array. Contains the distances
     */
    public void twoOptCluster (Tour tour, double[][] distancematrix){
        int tourSize = tour.getSize();
        int a, b, c, d;

        int steps = 0;
        double before, after;
        if (tour.getSize() >= 4) {
            while (steps < ((tourSize)*(tourSize/1.5))) {

                for (int i =0; i<tourSize - 3;i++){

                    for (int k =i+1; k<tourSize-2; k++){

                        if(i == 0) {
                            a = tour.getPoint(tourSize-1).getPointNumber()-1;
                        }else {
                            a = tour.getPoint(i-1).getPointNumber()-1;
                        }

                        b = tour.getPoint(i).getPointNumber()-1;
                        c = tour.getPoint(k).getPointNumber()-1;
                        d = tour.getPoint(k+1).getPointNumber()-1;
                        before = distancematrix[a][b]+distancematrix[c][d];
                        after = distancematrix[a][c]+distancematrix[b][d];

                        if(after < before){
                            steps = 0;
                            LinkedList<Point> tempList = new LinkedList<Point>();
                            int l = i;
                            for (l = i; l <= k; l++) {
                                tempList.add(tour.getPoint(l));
                            }
                            l = i;
                            for (int j = tempList.size() - 1; j >= 0; j--) {
                                tour.setPoint(l, tempList.get(j));
                                l++;
                            }
                        }else{
                            steps++;
                        }
                    }
                }
            }
        }
    }
}
