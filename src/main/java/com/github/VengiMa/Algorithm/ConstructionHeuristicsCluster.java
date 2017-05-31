package com.github.VengiMa.Algorithm;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Admin on 06.04.2017.
 */
public class ConstructionHeuristicsCluster {
    public static Tour NNHeuristic (double distancematrix [][], List<Point> pointList) {
        Stack<Integer> stack= new Stack<Integer>();
        Tour tour = new Tour(0);

        int numbernodes;
        //visited = List of elements, either value = 1 for visited or value = 0 for unvisited
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
            //add the Point to the tour:
            tour.addPoint(pointList.get(randomNum - 1));
            int element, dst = 0, i, pos = 0;
            stack.push(pointNumber);
            //editiing the stack value -1 because every city has its index -1 in the distancematrix
            element = stack.peek() - 1;
            boolean minFlag = false;
            //System.out.print("1" + "\t");
            //System.out.println(randomNum + "   "+ element);
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
        }
        //System.out.println(tour.tour2String());
        return tour;
    }


    /*
    public static Tour CheapInsert (double distancematrix [][], LinkedList<Point> set, List<Integer> filledClusters) {
        Tour tour = new Tour();
        int start, ziel;

        int filled = filledClusters.get(0);

        int numbernodes;
        //visited = List of elements, either value = 1 for visited or value = 0 for unvisited
        numbernodes = set.size();
        int visited[] = new int[numbernodes];
        if (filled == 2){

            tour.addPoint(set.get(filledClusters.get(1)));
            tour.addPoint(set.get(filledClusters.get(2)));
        }
        else if(filled == 1){
            tour.addPoint(set.get(filledClusters.get(1)));
        }
        else {
            //two random starting points as a subset
            double temp1 = numbernodes * Math.random();
            int st = (int) temp1;
            double temp2 = numbernodes * Math.random();
            int zi = (int) temp2;
            //two numbers cannot be the same
            while (st == zi) {
                temp1 = numbernodes * Math.random();
                st = (int) temp1;
                temp2 = numbernodes * Math.random();
                zi = (int) temp2;
            }
            start = set.get(st).getPointNumber() - 1;
            ziel = set.get(zi).getPointNumber() - 1;

            //visited is true; because they are already part of the tour;
            visited[start] = 1;
            visited[ziel] = 1;
            System.out.println(start);
            System.out.println(ziel);
            //add those starting points to the tour
            tour.addPoint(set.get(st));
            tour.addPoint(set.get(zi));
            //every vertex from 0 to probleminstance -1 will be parsed
            for (int i = 0; i < set.size(); i++) {
                int cityIndex = set.get(i).getPointNumber() - 1;
                //if the recent vertex is part of the tour already, do nothing
                if (visited[cityIndex] == 1) {
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
                        }
                    }
                    tour.insertPoint(insertPoint, set.get(i));
                    visited[cityIndex] = 1;
                }
            }
        }
        return tour;
    }
    */
}
