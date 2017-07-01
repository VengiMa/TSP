package com.github.VengiMa.Algorithm;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.time.Duration;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by Admin on 10.04.2017.
 */
public class K_Means{
    private int maxPointsperCluster = Integer.MAX_VALUE;
    private int minPointsperCluster = 0;

    private LinkedList<Point> points;
    private static List<Cluster> clusters;

    public K_Means(){
        this.points = new LinkedList<Point>();
        this.clusters = new ArrayList<Cluster>();
    }

    public void init(LinkedList<Point> init, int number){
        points = init;
        double minX = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE;
        double minY = Double.MAX_VALUE;
        double maxY = Double.MIN_VALUE;

        maxPointsperCluster = (int)Math.ceil((points.size()/number) + 0.1*points.size());
        minPointsperCluster = (int)Math.ceil((points.size()/number) - 0.1*points.size());

        for (Point p: init) {
            if (p.getXCoord() < minX) {
                minX = p.getXCoord();
            } else if (p.getXCoord() > maxX) {
                maxX = p.getXCoord();
            }

            if (p.getYCoord() < minY) {
                minY = p.getYCoord();
            } else if (p.getYCoord() > maxY) {
                maxY = p.getYCoord();
            }
        }
        List<Integer> chosenPoints = new ArrayList<Integer>();
        for(int i =0; i < number; i++){
            Cluster cluster = new Cluster(i);
            Random r = new Random();
            //double x = minX + (maxX - minX) * r.nextDouble();
            //double y = minY + (maxY - minY) * r.nextDouble();
            int pointNumber = r.nextInt(points.size());
            while (chosenPoints.contains(pointNumber)){
                pointNumber = r.nextInt(points.size());
            }
            chosenPoints.add(pointNumber);
            double x = points.get(pointNumber).getXCoord();
            double y = points.get(pointNumber).getYCoord();
            Point centroid = new Point(-i, x, y);
            cluster.setCentroid(centroid);
            clusters.add(cluster);
        }
        //plotClusters();
    }

    private void plotClusters(){
        for (int i = 0; i < clusters.size(); i++){
            Cluster c = clusters.get(i);
            c.plotCluster();
        }
    }

    public void calculate(){
        boolean finish = false;
        int iteration = 0;

        while(!finish){
            clearClusters();
            List<Point> lastCentroids = getCentroids();
            Double [][] last = new Double [lastCentroids.size()][2];
            for (int i =0; i< lastCentroids.size(); i++){
                last[i][0] = lastCentroids.get(i).getXCoord();
                last[i][1] = lastCentroids.get(i).getYCoord();
            }

            assignClusters();

            calculateCentroids();

            iteration++;

            List<Point> currentCentroids = getCentroids();

            double distance = 0.0;
            double sum = 0;
            for (int i =0; i< lastCentroids.size(); i++){

                Point q = currentCentroids.get(i);
                distance = Math.sqrt(Math.pow((currentCentroids.get(i).getYCoord() - last[i][1]), 2) + Math.pow((currentCentroids.get(i).getXCoord() - last[i][0]), 2));
                sum = sum + distance;
            }
            //plotClusters();

            if(sum==0) {
                finish = true;
                System.out.println("Iteration: " + iteration);
                System.out.println("######");
                //System.out.println("Centroid distances: " + distance);
            }
        }
    }

    private void clearClusters(){
        for(Cluster cluster: clusters){
            cluster.clear();
        }
    }

    private List<Point> getCentroids() {
        List<Point> centroids = new LinkedList<Point>();
        for(Cluster cluster : clusters) {
            Point aux = cluster.getCentroid();
            centroids.add(aux);
        }
        return centroids;
    }

    private void assignClusters(){
        double min;
        int cluster = 0;
        double distance = 0.0;

        for (Point point: points){
            min = Double.MAX_VALUE;
            for (int i =0; i < clusters.size(); i++){
                Cluster c = clusters.get(i);
                //no more Points in one Cluster then the defined size
                if (c.getPoints().size() < maxPointsperCluster) {
                    distance = Point.distance(point, c.getCentroid());
                    if (distance < min) {
                        min = distance;
                        cluster = i;
                    }
                }
            }
            point.setCluster(cluster);
            clusters.get(cluster).addPoint(point);
        }
    }

    private void calculateCentroids(){
        for (Cluster cluster: clusters){
            double sumX = 0;
            double sumY = 0;
            List<Point> list = cluster.getPoints();
            int n_points = list.size();

            for(Point point: list){
                sumX = sumX + point.getXCoord();
                sumY = sumY + point.getYCoord();
            }

            Point centroid = cluster.getCentroid();
            if(n_points > 0){
                double newX = sumX / n_points;
                double newY = sumY / n_points;
                centroid.setXCoord(newX);
                centroid.setYCoord(newY);
            }
        }
    }

    public static List<Cluster> getClusters() {
        return clusters;
    }

    public static void main2(String[] args) throws IOException{
        long elapsed;
        long start = System.nanoTime();
        try {
            int number =4;
            double distanceMatrix [][];
            String fileName = "lau15_xy";
            String path = "C:\\Users\\Admin\\Desktop\\Hochschule\\Master\\Thesis - Richter\\Java\\Testdateien\\" + fileName + ".txt";
            File file = new File(path);

                    //"C:\\Users\\Admin\\Desktop\\Hochschule\\Master\\Thesis - Richter\\Java\\Testdateien\\sgb_128.txt");

            double coordinates[][] = InputCoordinates.FileToCoordinates(file, false);

            distanceMatrix = InputCoordinates.distanceMatrix(coordinates);

            LinkedList<Point> init = InputCoordinates.createPointList(coordinates);
            System.out.println(init.size());
            List<Cluster> clusters = null;


            K_Means kmeans = new K_Means();
            kmeans.init(init,number);
            kmeans.calculate();
            clusters = kmeans.getClusters();

            ClusterDistance[][] distance = clusterMatrix.clusterMatrix(distanceMatrix, clusters);

            Tour test = VisitingOrderCluster.orderCluster(distance, distanceMatrix, clusters);

            /*
            for (int i =0; i< clusters.size(); i++){
                Cluster c = clusters.get(i);
                c.setInPoint(c.getPoints().get(4));
                c.setOutPoint(c.getPoints().get(6));

                Tour tour = TourConstruction.NNHeuristic(distanceMatrix, c);
                LocalSearch twoOpt = new LocalSearch();
                twoOpt.twoOpt(distanceMatrix,c,tour);
                System.out.println(tour.distanceTourLength(distanceMatrix));
                System.out.println(tour.isFeasible(init) +" ; "+ tour.tour2String());
            }
            */



            //ClusterDistance [][] distance = clusterMatrix.clusterMatrix(distanceMatrix,clusters);

           // List<Integer> filledClusters = clusterMatrix.getFilledCluster();

            //System.out.println(distance.length);
           // System.out.println(filledClusters.toString());


            /*

            for (int i = 0; i < distance.length; i++) {
                System.out.println("\n");
                for (int j = 0; j < distance.length; j++) {
                    System.out.print(distance[i][j].getClusterDistance() + ", " + distance[i][j].getfromNode().getPointNumber() + ", "
                            + distance[i][j].gettoNode().getPointNumber() + "  ");
                }
            }
            System.out.println("\n");
            Tour test = VisitingOrderCluster.orderCluster(distance, clusters);
            System.out.println(test.tour2String());

            for (Cluster c : clusters){
                c.plotCluster();
            }
            Tour tour = new Tour(0);
            for (int i =0; i< test.getSize(); i++){
                int j = test.getPoint(i).getPointNumber()-1;
                Cluster c = clusters.get(j);

                start = System.nanoTime();
                Tour clusterTour = ClusterComputation.createTour(c,distanceMatrix);
                System.out.println(clusterTour.getDistance());
                System.out.println(clusterTour.distanceTourLength(distanceMatrix));
                tour.addTour(ClusterComputation.createTour(c,distanceMatrix));

                elapsed = (System.nanoTime() - start) / 1_000_000L;
                System.out.println("completed in " + elapsed + "ms");

                */

                /*
                Tour clusterTour = TourConstruction.NNHeuristic(distanceMatrix, clusters.get(j));
                System.out.println(clusterTour.tour2String());
                LocalSearch two = new LocalSearch();
                two.twoOpt(distanceMatrix,clusters.get(j),clusterTour);
                System.out.println(clusterTour.tour2String());
                tour.addTour(clusterTour);
                */
            // }
            /*
            System.out.println(tour.tour2String());
            System.out.println("Tour is feasible: " + tour.isFeasible(init));
            System.out.println(tour.distanceTourLength(distanceMatrix));

            LocalSearchForClusters after = new LocalSearchForClusters();
            after.twoOpt(tour, distanceMatrix);
            System.out.println(tour.tour2String());
            System.out.println(tour.distanceTourLength(distanceMatrix));
            */
        }
        catch (IOException e) {
            System.out.println("Fehler: IOException");
        }
        //elapsed = (System.nanoTime() - start) / 1_000_000L;
        //System.out.println("completed in " + elapsed + "ms");
    }

    public static void main (String[] args) {
        try
        {
            // Step 1: "Load" the JDBC driver
            Class.forName("org.postgresql.Driver");

            // Step 2: Establish the connection to the database
            String url = "jdbc:postgresql://10.95.61.77:5433/postgres";
            Connection conn = DriverManager.getConnection(url,"postgres","postgres");

            Timestamp start = new Timestamp(System.currentTimeMillis());
            Timestamp stop = new Timestamp(System.currentTimeMillis()+20);
            long dur = stop.getNanos() - start.getNanos();
            System.out.println(dur/1000);
            double length = 154624.213;
            String map = "qa194";
            String heur = "NN";
            int number = 4;
            String sql = "INSERT INTO test_results " +
                    "(begin, ending, duration, tourlength, typ, heuristic, clusters)"+
                    "VALUES(?,?,?,?,?,?,?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setTimestamp(1,start);
            pst.setTimestamp(2,stop);
            pst.setLong(3, dur);
            pst.setDouble(4,length);
            pst.setString(5,map);
            pst.setString(6,heur);
            pst.setInt(7,number);
            pst.executeUpdate();

            System.out.println("Inserting successful!");
        }
        catch (Exception e)
        {
            System.err.println("D'oh! Got an exception!");
            System.err.println(e.getMessage());
        }
    }

}
