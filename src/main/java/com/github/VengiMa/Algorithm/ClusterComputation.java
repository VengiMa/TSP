package com.github.VengiMa.Algorithm;

/**
 * Created by Admin on 18.05.2017.
 */
public class ClusterComputation {
    public static Tour createTour(Cluster c, double [][] distanceMatrix, int choice){
        Tour clusterTour;
        switch (choice){
            case 1: clusterTour = TourConstruction.NNHeuristic(distanceMatrix, c);
            break;
            case 2: clusterTour = TourConstruction.FarthestInsertion(distanceMatrix,c);
            break;
            case 3: clusterTour = TourConstruction.CheapInsert(distanceMatrix,c);
            break;
            default: clusterTour = TourConstruction.NNHeuristic(distanceMatrix, c);
            break;
        }
        System.out.println(clusterTour.distanceTourLength(distanceMatrix));
        ClusterLocalSearch two = new ClusterLocalSearch();
        two.twoOpt(distanceMatrix,c,clusterTour);
        System.out.println(clusterTour.distanceTourLength(distanceMatrix));
        return clusterTour;
    }
}
