package com.github.VengiMa.Algorithm;

import java.util.LinkedList;

/**
 * Created by Admin on 19.04.2017.
 */
public class LocalSearch {
    public void twoOpt (double[][] distance, Cluster cluster, Tour tour) {
        //long startTime = System.currentTimeMillis();
        int groesseTour = tour.getSize();
        boolean geschafft = false;
        double before, after;
        int a,b,c,d;
        int steps = 0;
        if (tour.getSize() >= 4) {
            while (steps < ((groesseTour)/1.5)) {
                //if (System.currentTimeMillis() - startTime >=10000 || counter >= 15000) {
                //    return t;
                //}
                double gain = 0;

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
                        before = distance[a][b]+distance[c][d];
                        after = distance[a][c]+distance[b][d];

                        if(after < before){
                            steps = 0;
                            gain = before - after;

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

    public void twoOptSequentiel (Tour tour, double[][] distance) {
        //long startTime = System.currentTimeMillis();
        int groesseTour = tour.getSize();
        boolean geschafft = false;
        double before, after;
        int a, b, c, d;
        int steps = 0;
        if (tour.getSize() >= 4) {
            while (steps < ((groesseTour) / 1.5)) {
                //if (System.currentTimeMillis() - startTime >=10000 || counter >= 15000) {
                //    return t;
                //}
                double gain = 0;

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
                        before = distance[a][b] + distance[c][d];
                        after = distance[a][c] + distance[b][d];

                        if (after < before) {
                            steps = 0;
                            gain = before - after;

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
                            geschafft = true;
                        }
                    }
                }
                steps++;
            }
        }
    }

    /*
    public void twoOpt (double[][] distance, Cluster cluster, Tour tour){

        //long startTime = System.currentTimeMillis();
        int groesseTour = tour.getSize();
        boolean geschafft = false;
        int counter = 0, temp2, temp4;
        int in, out;
        in = cluster.getInPoint().getPointNumber();
        out = cluster.getOutPoint().getPointNumber();
        if (tour.getSize() >= 4) {
            while (counter < 10000) {
                //if (System.currentTimeMillis() - startTime >=10000 || counter >= 15000) {
                //    return t;
                //}
                //choose two random numbers, which are used as the reference for the twoOpt
                Random randomNumberGenerator = new Random();
                int randomNumber1 = randomNumberGenerator.nextInt(groesseTour);
                int randomNumber2 = randomNumberGenerator.nextInt(groesseTour);

                //get sure, that the two numbers are different
                while (randomNumber1 == randomNumber2) {
                    randomNumber2 = randomNumberGenerator.nextInt(groesseTour);
                }

                int[] edges = {randomNumber1, randomNumber2};
                Arrays.sort(edges);
                randomNumber1 = edges[0];
                randomNumber2 = edges[1];
                temp2 = randomNumber1 + 1;
                temp4 = (randomNumber2 + 1) % groesseTour;

                int tourIn = tour.getPointIndex(cluster.getInPoint());
                int tourOut = tour.getPointIndex(cluster.getOutPoint());
                //Two Opt can only be computed, if the tour has at minimum 4 points
                if  (temp2 != randomNumber2 && temp4 != randomNumber1 &&
                    (randomNumber2 != tourOut && temp4 != tourIn) &&
                    (randomNumber1 != tourOut && temp2 != tourIn) &&
                    (randomNumber1 != tourIn && temp2 != tourOut) &&
                    (randomNumber2 != tourIn && temp4 != tourOut)){
                    int a, b, c, d;
                    a = tour.getPoint(randomNumber1).getPointNumber() - 1;
                    b = tour.getPoint(temp2).getPointNumber() - 1;
                    c = tour.getPoint(randomNumber2).getPointNumber() - 1;
                    d = tour.getPoint(temp4).getPointNumber() - 1;

                    //System.out.println(a+ "  " +b+ "  " +c+ "  " +d);
                    //compute the distances before and after the change of the edges
                    double vorher = distance[a][b] + distance[c][d];
                    double danach = distance[a][c] + distance[b][d];

                    //System.out.println("d1= "+vorher + "; d2= " +danach );
                    if (danach < vorher) {
                        LinkedList<Point> tempList = new LinkedList<Point>();
                        int l = temp2;
                        for (l = temp2; l <= randomNumber2; l++) {
                            tempList.add(tour.getPoint(l));
                        }
                        l = temp2;
                        for (int k = tempList.size() - 1; k >= 0; k--) {
                            tour.setPoint(l, tempList.get(k));
                            l++;
                        }
                        geschafft = true;
                    } else {
                        counter++;
                    }
                }
                else {
                    counter++;
                }
            }
        }
        if (geschafft) {
            System.out.println("TwoOpt executed!");
        }
        tour.setStartingPoint(cluster.getInPoint());
    }
    */
}
