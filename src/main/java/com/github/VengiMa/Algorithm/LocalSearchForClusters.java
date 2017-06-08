package com.github.VengiMa.Algorithm;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

/**
 * Created by Admin on 05.04.2017.
 */
public class LocalSearchForClusters {
    /**
    *Methoden
    **/
    //2-opt move
    public void twoOpt (Tour tour, double[][] distance){
        //long startTime = System.currentTimeMillis();
        int tourSize = tour.getSize();
        boolean nearestNeighbours = false;
        int sizeNeighbours = 20;
        int counter = 0, temp2, temp4;

        int randomNumber1;
        int randomNumber2;
        double minimum = Double.MAX_VALUE;
        int a, b, c, d;
        int exchangeFrom = 0, exchangeTo = 0;

        if (tour.getSize() >= 4) {
            while (counter < 10000) {
                //if (System.currentTimeMillis() - startTime >=10000 || counter >= 15000) {
                //    return t;
                //}
                //choose two random numbers, which are used as the reference for the twoOpt
                Random randomNumberGenerator = new Random();
                randomNumber1 = randomNumberGenerator.nextInt(tourSize);

                if (nearestNeighbours){
                    for (int i = 0; i< sizeNeighbours-2; i++){
                        randomNumber2 = (randomNumber1 +(i+2))%tourSize;
                        temp2 = (randomNumber1 + 1) % tourSize;
                        temp4 = (randomNumber2 + 1) % tourSize;

                        if (temp2 != randomNumber2 && temp4 != randomNumber1) {
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
                                if (danach < minimum) {
                                    minimum = danach;
                                    exchangeFrom = temp2;
                                    exchangeTo = randomNumber2;
                                }
                            } else {
                                counter++;
                            }
                        }
                    }
                    LinkedList<Point> tempList = new LinkedList<Point>();
                    int l;
                    for (l = exchangeFrom; l <= exchangeTo; l++) {
                        tempList.add(tour.getPoint(l));
                    }
                    l = exchangeFrom;
                    for (int k = tempList.size() - 1; k >= 0; k--) {
                        tour.setPoint(l, tempList.get(k));
                        l++;
                    }
                }else {

                    randomNumber2 = randomNumberGenerator.nextInt(tourSize);

                    //get sure, that the two numbers are different
                    while (randomNumber1 == randomNumber2) {
                        randomNumber2 = randomNumberGenerator.nextInt(tourSize);
                    }

                    int[] edges = {randomNumber1, randomNumber2};
                    Arrays.sort(edges);
                    randomNumber1 = edges[0];
                    randomNumber2 = edges[1];
                    temp2 = randomNumber1 + 1;
                    temp4 = (randomNumber2 + 1) % tourSize;

                    //Two Opt can only be computed, if the tour has at minimum 4 points
                    if (temp2 != randomNumber2 && temp4 != randomNumber1) {
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
                        } else {
                            counter++;
                        }
                    }
                }
            }
        }
    }

    public void twoOpt (Tour tour,Cluster cluster, double[][] distance){
        //long startTime = System.currentTimeMillis();
        int tourSize = tour.getSize();
        boolean geschafft = false;
        boolean nearestNeighbours = false;
        int sizeNeighbours = 20;
        int counter = 0, temp2, temp4;
        int tourIn = tour.getPointIndex(cluster.getInPoint());
        int tourOut = tour.getPointIndex(cluster.getOutPoint());

        int randomNumber1;
        int randomNumber2;
        double minimum = Double.MAX_VALUE;
        int a, b, c, d;
        int exchangeFrom = 0, exchangeTo = 0;

        if (tour.getSize() >= 4) {
            while (counter < 10000) {
                //if (System.currentTimeMillis() - startTime >=10000 || counter >= 15000) {
                //    return t;
                //}
                //choose two random numbers, which are used as the reference for the twoOpt
                Random randomNumberGenerator = new Random();
                randomNumber1 = randomNumberGenerator.nextInt(tourSize);

                if (nearestNeighbours){
                    for (int i = 0; i< sizeNeighbours-2; i++){
                        randomNumber2 = (randomNumber1 +(i+2))%tourSize;
                        temp2 = (randomNumber1 + 1) % tourSize;
                        temp4 = (randomNumber2 + 1) % tourSize;

                        if ((temp2 != randomNumber2 && temp4 != randomNumber1)&&
                        (randomNumber2 != tourOut && temp4 != tourIn) &&
                        (randomNumber1 != tourOut && temp2 != tourIn) &&
                        (randomNumber1 != tourIn && temp2 != tourOut) &&
                        (randomNumber2 != tourIn && temp4 != tourOut)){
                            a = tour.getPoint(randomNumber1).getPointNumber() - 1;
                            b = tour.getPoint(temp2).getPointNumber() - 1;
                            c = tour.getPoint(randomNumber2).getPointNumber() - 1;
                            d = tour.getPoint(temp4).getPointNumber() - 1;

                            //compute the distances before and after the change of the edges
                            double vorher = distance[a][b] + distance[c][d];
                            double danach = distance[a][c] + distance[b][d];

                            //System.out.println("d1= "+vorher + "; d2= " +danach );
                            if (danach < vorher) {
                                if (danach < minimum) {
                                    minimum = danach;
                                    exchangeFrom = temp2;
                                    exchangeTo = randomNumber2;
                                }
                            } else {
                                counter++;
                            }
                        }
                    }
                    LinkedList<Point> tempList = new LinkedList<Point>();
                    int l;
                    for (l = exchangeFrom; l <= exchangeTo; l++) {
                        tempList.add(tour.getPoint(l));
                    }
                    l = exchangeFrom;
                    for (int k = tempList.size() - 1; k >= 0; k--) {
                        tour.setPoint(l, tempList.get(k));
                        l++;
                    }
                    //System.out.println(Arrays.toString(tem1));
                    //System.out.println(Arrays.toString(tem2));
                    geschafft = true;
                }else {

                    randomNumber2 = randomNumberGenerator.nextInt(tourSize);

                    //get sure, that the two numbers are different
                    while (randomNumber1 == randomNumber2) {
                        randomNumber2 = randomNumberGenerator.nextInt(tourSize);
                    }

                    int[] edges = {randomNumber1, randomNumber2};
                    Arrays.sort(edges);
                    randomNumber1 = edges[0];
                    randomNumber2 = edges[1];
                    temp2 = randomNumber1 + 1;
                    temp4 = (randomNumber2 + 1) % tourSize;

                    //Two Opt can only be computed, if the tour has at minimum 4 points
                    if (temp2 != randomNumber2 && temp4 != randomNumber1) {
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
                            //System.out.println(Arrays.toString(tem1));
                            //System.out.println(Arrays.toString(tem2));
                            geschafft = true;
                        } else {
                            counter++;
                        }
                    }
                }
            }
        }
        if (geschafft) {
            System.out.println("TwoOpt executed!");
        }
    }

    public void threeOpt (Tour tour, double[][] distance){
        //long startTime = System.currentTimeMillis();
        int groesseTour = tour.getSize();
        boolean geschafft = true;
        int counter = 0, temp2, temp4, temp6;
        while (counter < 10000) {
            //if (System.currentTimeMillis() - startTime >=10000 || counter >= 15000) {
            //    return t;
            //}

            LinkedList<Point> clone = new LinkedList<Point>();
            for (int i = 0; i < tour.getSize(); i++) {
                clone.add(tour.getPoint(i));
            }
            //zufall is chosen between 0 and clone.size -1; therefore it is in matrixindex
            //chooses an element out of clone, -1 --> matrix index again.
            double zufall = clone.size() * Math.random();
            int randomNumber1 = clone.get((int) zufall).getPointNumber()-1;
            clone.remove((int) zufall);

            zufall = clone.size() * Math.random();
            int randomNumber2 = clone.get((int) zufall).getPointNumber()-1;;
            clone.remove((int) zufall);

            zufall = clone.size() * Math.random();
            int randomNumber3 = clone.get((int) zufall).getPointNumber()-1;
            clone.remove((int) zufall);

            int[] edges = {randomNumber1, randomNumber2, randomNumber3};
            Arrays.sort(edges);

            randomNumber1 = edges[0]; randomNumber2 = edges[1]; randomNumber3 = edges[2];
            //System.out.println(" random " + randomNumber1 + " " + randomNumber2 + " " + randomNumber3 +" ");
            temp2 = randomNumber1 + 1; temp4 = randomNumber2 + 1; temp6 = (randomNumber3+1)%groesseTour;

            //Two Opt can only be computed, if the tour has at minimum 4 points
            if (tour.getSize() >= 6 && temp2 != randomNumber2 && temp4 != randomNumber3 && temp6 != randomNumber1){
                int a, b , c, d, e, f;
                a = tour.getPoint(randomNumber1).getPointNumber()-1;
                b = tour.getPoint(temp2).getPointNumber()-1;
                c = tour.getPoint(randomNumber2).getPointNumber()-1;
                d = tour.getPoint(temp4).getPointNumber()-1;
                e = tour.getPoint(randomNumber3).getPointNumber()-1;
                f = tour.getPoint(temp6).getPointNumber()-1;

                //compute the distances before and after the change of the edges
                double vorher = distance[a][b] + distance[c][d] + distance[e][f];
                double danach;
                //take the two Toursegments and store them in the two tempLists
                LinkedList<Point> tempList1 = new LinkedList<Point>();
                int l = temp2;
                for (l = temp2; l <= randomNumber2; l++){
                    tempList1.add(tour.getPoint(l));
                }
                LinkedList<Point> tempList2 = new LinkedList<Point>();
                int m = temp4;
                for (m = temp2; l <= randomNumber3; l++){
                    tempList2.add(tour.getPoint(m));
                }
                Random randomNumberGenerator = new Random();
                int which = randomNumberGenerator.nextInt(4);
                switch (which){
                    //identity: a - b - c - d - e - f
                    //changed to: a - c - b - e - d - f
                    case 0: danach = distance[a][c] + distance[b][e] + distance[d][f];
                            if (danach < vorher){
                                l = temp2;
                                for (int k = tempList1.size()-1; k >= 0; k--){
                                    tour.setPoint(l, tempList1.get(k));
                                    l++;
                                }
                                m = temp4;
                                for (int k = tempList2.size()-1; k >= 0; k--){
                                    tour.setPoint(m, tempList2.get(k));
                                    m++;
                                }
                            }
                            else {counter++;}
                            break;
                    //changed to: a - d - e - b - c - f
                    case 1: danach = distance[a][d] + distance[e][b] + distance[c][f];
                        if (danach < vorher){
                            m = temp2;
                            for (int k = 0; k < tempList2.size(); k++){
                                tour.setPoint(m, tempList2.get(k));
                                m++;
                            }
                            l = temp2 + tempList2.size();
                            for (int k = 0; k < tempList1.size(); k++){
                                tour.setPoint(l, tempList1.get(k));
                                l++;
                            }
                        }
                        else {counter++;}
                        break;
                    //changed to: a - d - e - c - b - f
                    case 2: danach = distance[a][c] + distance[b][e] + distance[d][f];
                        if (danach < vorher){
                            m = temp2;
                            for (int k = 0; k < tempList2.size(); k++){
                                tour.setPoint(m, tempList2.get(k));
                                m++;
                            }
                            l = temp2 + tempList2.size();
                            for (int k = tempList1.size()-1; k >= 0; k--){
                                tour.setPoint(l, tempList1.get(k));
                                l++;
                            }
                        }
                        else {counter++;}
                        break;
                    //changed to: a - e - d - b - c - f
                    case 3: danach = distance[a][c] + distance[b][e] + distance[d][f];
                        if (danach < vorher){
                            m = temp2;
                            for (int k = tempList2.size()-1; k >= 0; k--){
                                tour.setPoint(m, tempList2.get(k));
                                m++;
                            }
                            l = temp2 + tempList2.size();
                            for (int k = 0; k < tempList1.size(); k++){
                                tour.setPoint(l, tempList1.get(k));
                                l++;
                            }
                        }
                        else {counter++;}
                        break;
                }
            }
        }
        //System.out.println(tour.tour2String());
    }
}

