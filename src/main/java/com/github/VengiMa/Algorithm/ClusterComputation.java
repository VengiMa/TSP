package com.github.VengiMa.Algorithm;

/**
 * Created by Admin on 18.05.2017.
 */
public class ClusterComputation {
    /***
     *
     * @param c
     * @param distanceMatrix
     * @param choice
     * @return
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
