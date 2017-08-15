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

import java.util.List;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

/***
 * Provides the different construction heuristics to create a tour
 */
public class TourConstruction {

    /***
     * Calculates an initial tour inside a cluster using the Nearest Neighbour heuristic
     * @param distancematrix The distance matrix
     * @param cluster The cluster the tour is calculated in
     * @return A tour
     */
    public static Tour NNHeuristic (double distancematrix [][], Cluster cluster) {
        Stack<Integer> stack= new Stack<Integer>();
        Tour tour = new Tour(0);
        double length =0;

        if (cluster.getPoints().size() == 1){
            tour.addPoint(cluster.getPoints().get(0));
        } else {
            Point out = cluster.getOutPoint ();
            Point in = cluster.getInPoint();

            int numbernodes;
            //visited = List of elements, either value = 1 for visited or value = 0 for unvisited
            numbernodes = cluster.getPoints().size();
            int visited[] = new int[numbernodes];

            tour.addPoint(out);
            tour.addPoint(in);

            visited[cluster.getPointIndex(out)] = 1;
            visited[cluster.getPointIndex(in)] = 1;

            int element, dst = 0, i, pos = 0;
            stack.push(in.getPointNumber());
            //editing the stack value -1 because every city has its index -1 in the distancematrix
            element = stack.peek() - 1;
            boolean minFlag = false;
            while (!stack.isEmpty()) {
                double min = Integer.MAX_VALUE;
                //run the loop, increasing set index
                for (i = 0; i < numbernodes; i++) {
                    int cityIndex = cluster.getPoints().get(i).getPointNumber() - 1;
                    //compare, if city won't visit itself and that the city has not been visited yet
                    if (distancematrix[element][cityIndex] > 0 && visited[i] == 0) {
                        //if distance is smaller than the distances before: new minimum and next city
                        if (min > distancematrix[element][cityIndex]) {
                            min = distancematrix[element][cityIndex];
                            dst = cityIndex + 1;
                            pos = i;
                            minFlag = true;
                        }
                    }
                }
                if (minFlag) {
                    length = length + min;
                    visited[pos] = 1;
                    stack.push(dst);
                    element = stack.peek() - 1;
                    //System.out.print(dst + "\t");
                    tour.addPoint(cluster.getPoints().get(pos));
                    minFlag = false;
                    continue;
                }
                stack.pop();
            }
            //System.out.println(tour.tour2String());
            //length = length + distancematrix[tour.getLastPoint().getPointNumber()-1][out.getPointNumber()-1];
            tour.removePoint(out);
            tour.removePoint(in);
            tour.addPoint(out);
            tour.addPoint(in);
        }

        //tour.setDistance(length);
        return tour;
    }

    /***
     * Calculates an initial tour inside a cluster using the Cheapest Insert heuristic
     * @param distancematrix The distance matrix
     * @param cluster The cluster the tour is calculated in
     * @return A tour
     */
    public static Tour CheapInsert (double distancematrix [][], Cluster cluster) {
        Tour tour = new Tour(0);
        int start, ziel;

        if (cluster.getPoints().size() == 1){
            tour.addPoint(cluster.getPoints().get(0));
        } else {

            Point out = cluster.getOutPoint();
            Point in = cluster.getInPoint();

            int numbernodes;
            //visited = List of elements, either value = 1 for visited or value = 0 for unvisited
            numbernodes = cluster.getPoints().size();
            int visited[] = new int[numbernodes];
            start = in.getPointNumber() - 1;
            ziel = out.getPointNumber() - 1;

            //visited is true; because they are already part of the tour;
            visited[cluster.getPointIndex(out)] = 1;
            visited[cluster.getPointIndex(in)] = 1;
            System.out.println(start);
            System.out.println(ziel);
            //add those starting points to the tour
            tour.addPoint(out);
            tour.addPoint(in);
            //every vertex from 0 to probleminstance -1 will be parsed
            for (int i = 0; i < numbernodes; i++) {
                int cityIndex = cluster.getPoints().get(i).getPointNumber() - 1;
                int pos = -1;
                //if the recent vertex is part of the tour already, do nothing
                if (visited[i] == 1) {
                } else {
                    //else try to insert the vertex in every possible position of the former tour
                    //compare the distances for each insertion point to the minimum
                    double distance;
                    double minimum = Double.MAX_VALUE;
                    int insertPoint = 0;
                    for (int j = 1; j <= tour.getSize(); j++) {
                        if (tour.getPoint(j % tour.getSize()) == in) {
                            pos = i;
                        } else {
                            int vor = tour.getPoint((j - 1) % tour.getSize()).getPointNumber() - 1;
                            int nach = tour.getPoint((j) % tour.getSize()).getPointNumber() - 1;
                            distance = distancematrix[vor][cityIndex] + distancematrix[cityIndex][nach];
                            if (distance < minimum) {
                                minimum = distance;
                                insertPoint = j % tour.getSize();
                                pos = i;
                            }
                        }
                    }
                    tour.insertPoint(insertPoint, cluster.getPoints().get(i));
                    visited[pos] = 1;
                }
            }
        }
        return tour;
    }

    /***
     * Calculates an initial tour inside a cluster using the Farthest Insertion heuristic
     * @param distancematrix The distance matrix
     * @param cluster The cluster the tour is calculated in
     * @return A tour
     */
    public static Tour FarthestInsertion (double distancematrix [][], Cluster cluster) {
        Tour tour = new Tour(0);
        int numbernodes;
        //visited = List of elements, either value = 1 for visited or value = 0 for unvisited
        numbernodes = cluster.getPoints().size();
        int visited[] = new int[numbernodes];
        //int unvisitedRest;
        double max;
        double min;
        int chosenPoint = 0;
        int pos = -1;

        Point out = cluster.getOutPoint();
        Point in = cluster.getInPoint();

        if (cluster.getPoints().size() == 1){
            tour.addPoint(cluster.getPoints().get(0));
        } else {
            //visited is true; because they are already part of the tour;
            visited[cluster.getPointIndex(out)] = 1;
            visited[cluster.getPointIndex(in)] = 1;
            //add those starting points to the tour
            tour.addPoint(in);
            tour.addPoint(out);
            //every vertex from 0 to probleminstance -1 will be parsed
            while (tour.getSize() != numbernodes) {
                int point = 1;
                int insertPoint = 1;
                max =0;
                for (int i = 0; i < numbernodes; i++) {
                    double minimum = Double.MAX_VALUE;

                    int cityIndex = cluster.getPoints().get(i).getPointNumber() - 1;
                    //if the recent vertex is part of the tour already, do nothing
                    if (visited[i] == 1) {
                    } else {
                        //else try to insert the vertex in every possible position of the former tour
                        //compare the distances for each insertion point to the minimum
                        double distance;

                        for (int j = 1; j <= tour.getSize() - 1; j++) {
                            int vor = tour.getPoint((j - 1)).getPointNumber() - 1;
                            distance = distancematrix[vor][cityIndex];
                            if (distance < minimum) {
                                minimum = distance;
                                point = j;
                                pos = i;
                            }
                        }
                        if (minimum > max){
                            max = minimum;
                            chosenPoint = pos;
                            insertPoint = point;
                        }
                    }
                }
                tour.insertPoint(insertPoint, cluster.getPoints().get(chosenPoint));
                visited[chosenPoint] = 1;
            }
        }
        tour.removePoint(in);
        tour.addPoint(in);
        return tour;
    }

    /***
     * Calculates an initial tour for the sequential algorithm using the Nearest Neighbour heuristic
     * @param distancematrix The distance matrix
     * @param pointList A list of all points from the TSP
     * @return A tour
     */
    public static Tour NN (double distancematrix [][], List<Point> pointList) {
        Stack<Integer> stack= new Stack<Integer>();
        Tour tour = new Tour(0);

        int numbernodes;
        //visited = List of elements, either value = 1 for visited or value = 0 for unvisited
        numbernodes = pointList.size();
        int visited[] = new int[numbernodes];
        int randomNum = ThreadLocalRandom.current().nextInt(1, numbernodes);
        int pointNumber = pointList.get(randomNum - 1).getPointNumber();

        visited[pointNumber - 1] = 1;
        //add the Point to the tour:
        tour.addPoint(pointList.get(randomNum - 1));
        int element, dst = 0, i, pos = 0;
        stack.push(pointNumber);
        //editiing the stack value -1 because every city has its index -1 in the distancematrix
        element = stack.peek() - 1;
        boolean minFlag = false;
        while (!stack.isEmpty()) {
            double min = Integer.MAX_VALUE;
            //run the loop, increasing set index
            for (i = 0; i < pointList.size(); i++) {
                int cityIndex = pointList.get(i).getPointNumber() - 1;
                //compare, if city won't visit itself and that the city has not been visited yet
                if (distancematrix[element][cityIndex] > 0 && visited[i] == 0) {
                    //if distance is smaller than the distances before: new minimum and next city
                    if (min > distancematrix[element][cityIndex]) {
                        min = distancematrix[element][cityIndex];
                        dst = cityIndex + 1;
                        pos = i;
                        minFlag = true;
                    }
                }
            }
            if (minFlag) {
                visited[dst - 1] = 1;
                stack.push(dst);
                element = stack.peek() - 1;
                //System.out.print(dst + "\t");
                tour.addPoint(pointList.get(pos));
                minFlag = false;
                continue;
            }
            stack.pop();
        }
        return tour;
    }

    /***
     * Calculates an initial tour for the sequential algorithm using the Farthest Insertion heuristic
     * @param distancematrix The distance matrix
     * @param pointList A list of all points from the TSP
     * @return A tour
     */
    public static Tour Farthest (double distancematrix [][], List<Point> pointList) {
        Tour tour = new Tour(0);
        int numbernodes;
        //visited = List of elements, either value = 1 for visited or value = 0 for unvisited
        numbernodes = pointList.size();
        int visited[] = new int[numbernodes];
        int randomNum = ThreadLocalRandom.current().nextInt(1, numbernodes);
        int pointNumber = pointList.get(randomNum - 1).getPointNumber();
        double max;
        int chosenPoint = 0;
        int pos = -1;


        if (pointList.size() == 1){
            tour.addPoint(pointList.get(0));
        } else {
            //visited is true; because they are already part of the tour;
            visited[pointNumber - 1] = 1;
            //add the Point to the tour:
            tour.addPoint(pointList.get(randomNum - 1));
            //every vertex from 0 to probleminstance -1 will be parsed
            while (tour.getSize() != numbernodes) {
                int point = 1;
                int insertPoint = 1;
                max =0;
                for (int i = 0; i < numbernodes; i++) {
                    double minimum = Double.MAX_VALUE;

                    int cityIndex = pointList.get(i).getPointNumber() - 1;
                    //if the recent vertex is part of the tour already, do nothing
                    if (visited[i] == 1) {
                    } else {
                        //else try to insert the vertex in every possible position of the former tour
                        //compare the distances for each insertion point to the minimum
                        double distance;

                        for (int j = 1; j <= tour.getSize(); j++) {
                            int vor = tour.getPoint((j - 1)).getPointNumber() - 1;
                            distance = distancematrix[vor][cityIndex];
                            if (distance < minimum) {
                                minimum = distance;
                                point = j;
                                pos = i;
                            }
                        }
                        if (minimum > max){
                            max = minimum;
                            chosenPoint = pos;
                            insertPoint = point;
                        }
                    }
                }
                tour.insertPoint(insertPoint, pointList.get(chosenPoint));
                visited[chosenPoint] = 1;
            }
        }
        return tour;
    }

    /***
     * Calculates an initial tour for the sequential algorithm using the Cheapest Insertion heuristic
     * @param distancematrix The distance matrix
     * @param pointList A list of all points from the TSP
     * @return A tour
     */
    public static Tour Cheapest (double distancematrix [][],  List<Point> pointList) {
        Tour tour = new Tour(0);

        if (pointList.size() == 1){
            tour.addPoint(pointList.get(0));
        } else {

            int numbernodes;
            //visited = List of elements, either value = 1 for visited or value = 0 for unvisited
            numbernodes = pointList.size();
            int visited[] = new int[numbernodes];
            int randomNum = ThreadLocalRandom.current().nextInt(1, numbernodes);
            int pointNumber = pointList.get(randomNum - 1).getPointNumber();;

            //visited is true; because they are already part of the tour;
            visited[pointNumber - 1] = 1;
            //add the Point to the tour:
            tour.addPoint(pointList.get(randomNum - 1));

            //every vertex from 0 to probleminstance -1 will be parsed
            for (int i = 0; i < numbernodes; i++) {
                int cityIndex = pointList.get(i).getPointNumber() - 1;
                int pos = -1;
                //if the recent vertex is part of the tour already, do nothing
                if (visited[i] == 1) {
                } else {
                    //else try to insert the vertex in every possible position of the former tour
                    //compare the distances for each insertion point to the minimum
                    double distance;
                    double minimum = Double.MAX_VALUE;
                    int insertPoint = 0;
                    for (int j = 1; j <= tour.getSize(); j++) {
                        int vor = tour.getPoint((j - 1) % tour.getSize()).getPointNumber() - 1;
                        int nach = tour.getPoint((j) % tour.getSize()).getPointNumber() - 1;
                        distance = distancematrix[vor][cityIndex] + distancematrix[cityIndex][nach];
                        if (distance < minimum) {
                            minimum = distance;
                            insertPoint = j % tour.getSize();
                            pos = i;
                        }
                    }
                    tour.insertPoint(insertPoint, pointList.get(i));
                    visited[pos] = 1;
                }
            }
        }
        return tour;
    }
}


