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

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/***
 * Calculates a short tour through all clusters that is used to merge the partial tours of each cluster together to a final tour
 */
public class ConstructionTourThroughClusters {
    /***
     * The nearest neighbour heuristic is used to calculate a tour through all clusters
     * @param distancematrix A 2-dimensional array. Contains the distances
     * @param pointList The clusters transformed into a List of points
     * @return A short tour through all clusters
     */
    public static Tour NNHeuristic (double distancematrix [][], List<Point> pointList) {
        Stack<Integer> stack= new Stack<Integer>();
        Tour tour = new Tour(0);

        int numbernodes;
        numbernodes = pointList.size();
        int visited[] = new int[numbernodes];
        if (numbernodes == 2){
            tour.addPoint(pointList.get(0));
            tour.addPoint(pointList.get(1));
        }
        else if(numbernodes == 1){
            tour.addPoint(pointList.get(0));
        }
        else {
            int randomNum = ThreadLocalRandom.current().nextInt(1, numbernodes);
            int pointNumber = pointList.get(randomNum - 1).getPointNumber();
            boolean clusterFilled = false;
            for (int i = 0; i < pointList.size(); i++){
                if (distancematrix[pointNumber -1][i]  > 0){
                    clusterFilled = true;
                }
            }
            while (!clusterFilled){
                randomNum = ThreadLocalRandom.current().nextInt(1, numbernodes);
                pointNumber = pointList.get(randomNum - 1).getPointNumber();
                for (int i = 0; i < pointList.size(); i++){
                    if (distancematrix[pointNumber -1][i]  > 0){
                        clusterFilled = true;
                    }
                }
            }
            visited[pointNumber - 1] = 1;
            tour.addPoint(pointList.get(randomNum - 1));
            int element, dst = 0, i, pos = 0;
            stack.push(pointNumber);
            element = stack.peek() - 1;
            boolean minFlag = false;
            while (!stack.isEmpty()) {
                double min = Integer.MAX_VALUE;
                for (i = 0; i < pointList.size(); i++) {
                    int cityIndex = pointList.get(i).getPointNumber() - 1;
                    if (distancematrix[element][cityIndex] > 0 && visited[i] == 0) {
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
                    tour.addPoint(pointList.get(pos));
                    minFlag = false;
                    continue;
                }
                stack.pop();
            }
        }
        return tour;
    }
}
