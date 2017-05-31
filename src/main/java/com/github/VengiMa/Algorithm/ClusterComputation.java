package com.github.VengiMa.Algorithm;

/**
 * Created by Admin on 18.05.2017.
 */
public class ClusterComputation {
    public static Tour createTour(Cluster c, double [][] distanceMatrix){
        //todo: Auswahl bereitstellen zur Auswahl der Heurisitk
        Tour clusterTour = TourConstruction.NNHeuristic(distanceMatrix, c);
        ClusterLocalSearch two = new ClusterLocalSearch();
        two.twoOpt(distanceMatrix,c,clusterTour);
        System.out.println(clusterTour.tour2String());
        return clusterTour;
    }
}
