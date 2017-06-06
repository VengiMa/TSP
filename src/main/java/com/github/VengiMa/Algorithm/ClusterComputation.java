package com.github.VengiMa.Algorithm;

/**
 * Created by Admin on 18.05.2017.
 */
public class ClusterComputation {
    public static Tour createTour(Cluster c, double [][] distanceMatrix, int auswahl){
        Tour clusterTour;
        switch (auswahl){
            case 1: clusterTour = TourConstruction.NNHeuristic(distanceMatrix, c);
            break;
            case 2: clusterTour = TourConstruction.FarthestInsertion(distanceMatrix,c);
            break;
            case 3: clusterTour = TourConstruction.CheapInsert(distanceMatrix,c);
            break;
            default: clusterTour = TourConstruction.NNHeuristic(distanceMatrix, c);
        }
        ClusterLocalSearch two = new ClusterLocalSearch();
        two.twoOpt(distanceMatrix,c,clusterTour);
        System.out.println(clusterTour.tour2String());
        return clusterTour;
    }
}
