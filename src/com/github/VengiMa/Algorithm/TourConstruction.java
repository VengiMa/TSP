package com.github.VengiMa.Algorithm;

import java.util.Stack;

/**
 * Created by Admin on 19.04.2017.
 */
public class TourConstruction {
    public static Tour NNHeuristic (double distancematrix [][], Cluster cluster) {
        Stack<Integer> stack= new Stack<Integer>();
        Tour tour = new Tour(0);
        double length =0;

        if (cluster.getPoints().size() == 0) {
        } else if (cluster.getPoints().size() == 1){
            tour.addPoint(cluster.getPoints().get(0));
        } else {

            Point out = cluster.getOutPoint();
            Point in = cluster.getInPoint();

            int numbernodes;
            //visited = List of elements, either value = 1 for visited or value = 0 for unvisited
            numbernodes = cluster.getPoints().size();
            int visited[] = new int[numbernodes];

            tour.addPoint(out);
            tour.addPoint(in);
            //length = length + distancematrix[out.getPointNumber()-1][in.getPointNumber()-1];

            visited[cluster.getPointIndex(out)] = 1;
            visited[cluster.getPointIndex(in)] = 1;

            int element, dst = 0, i, pos = 0;
            stack.push(in.getPointNumber());
            //editing the stack value -1 because every city has its index -1 in the distancematrix
            element = stack.peek() - 1;
            boolean minFlag = false;
            //System.out.print("1" + "\t");
            //System.out.println(randomNum + "   "+ element);
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

    public static Tour CheapInsert (double distancematrix [][], Cluster cluster) {
        Tour tour = new Tour(0);
        int start, ziel;

        if (cluster.getPoints().size() == 0) {
        } else if (cluster.getPoints().size() == 1){
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

    //todo: Farthest Insertion
}
