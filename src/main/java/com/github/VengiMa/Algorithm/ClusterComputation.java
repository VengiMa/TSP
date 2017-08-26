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

/***
 * Calculates a tour through all points of a given cluster and improves it using the 2-opt move.
 */
public class ClusterComputation {
    /***
     * Creates a tour, respectively a hamiltonian path through all points, starting from the
     * Entry point of the cluster and ending at the Exit point. First, the tour will be constructed
     * and then it gets improved
     * @param c The cluster that will be processed
     * @param distanceMatrix A 2-dimensional array. Contains the distances
     * @param choice The variable for choosing the construction heuristic, 1 = NN, 2 = FI, 3 = CI
     * @return A tour
     */
    public static Tour createTour(Cluster c, double [][] distanceMatrix, int choice){
        Tour tour;
        switch (choice){
            case 1: tour = TourConstruction.NNHeuristic(distanceMatrix, c);
            break;
            case 2: tour = TourConstruction.FarthestInsertion(distanceMatrix,c);
            break;
            case 3: tour = TourConstruction.CheapInsert(distanceMatrix,c);
            break;
            default: tour = TourConstruction.NNHeuristic(distanceMatrix, c);
            break;
        }
        System.out.println(tour.distanceTourLength(distanceMatrix));
        LocalSearch two = new LocalSearch();
        two.twoOpt(distanceMatrix,c,tour);
        System.out.println(tour.distanceTourLength(distanceMatrix));
        return tour;
    }
}
