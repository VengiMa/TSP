package com.github.VengiMa.vengi;


import java.util.Stack;
import java.util.ArrayList;
import java.io.*;
import java.util.concurrent.ThreadLocalRandom;
/**
 * Created by Admin on 05.03.2017.
 */
public class NearestNeighbour {

    private static Stack<Integer> stack;

    ArrayList<Integer> tour = new ArrayList();

    public NearestNeighbour() {
        stack = new Stack<Integer>();
    }

    public static ArrayList<Integer> tspNN ( double distancematrix [][]) {
        ArrayList<Integer> tour = new ArrayList();
        int numbernodes;
        //visited = List of elements, either value = 1 for visited or value = 0 for unvisited
        numbernodes = distancematrix.length;
        int visited[] = new int[numbernodes];
        int randomNum = ThreadLocalRandom.current().nextInt(0, numbernodes + 1);
        visited[randomNum] = 1;
        int element, dst = 0, i;
        stack.push(randomNum);
        //editiing the stack value -1 because every city has its index -1 in the distancematrix
        element = stack.peek()-1;
        boolean minFlag = false;
        //System.out.print("1" + "\t");
        tour.add(randomNum);
        while (!stack.isEmpty()) {
            i = 0;
            double min = Integer.MAX_VALUE;
            //run the loop, increasing cityindexes
            while (i < numbernodes){
                //compare, if city won't visit itself and that the city has not been visited yet
                if (distancematrix[element][i] > 0 && visited[i] == 0) {
                    //if distance is smaller than the distances before: new minimum and next city
                    if (min > distancematrix[element][i]) {
                        min = distancematrix[element][i];
                        dst = i+1;
                        minFlag = true;
                    }
                }
                i++;
            }
            if (minFlag){
                visited[dst-1]=1;
                stack.push(dst);
                element = stack.peek()-1;
                //System.out.print(dst + "\t");
                tour.add(dst);
                minFlag = false;
                continue;
            }
            stack.pop();
        }
        return tour;
    }


    public static void main (String[] args) throws IOException{
        File file = new File("C:\\Users\\Admin\\Desktop\\Hochschule\\Master\\Thesis - Richter\\Java\\Testdateien\\ha30_dist.txt");
        //generate the distance matrix out of the txtfile
        NearestNeighbour tsp = new NearestNeighbour();
        double matrix[][] = InputTextDatei.TextdataInput(file);
        //1. generate the distance matrix out of the txtfile --> InputdataTextdatei
        //2. initiliaze an NN-Tour with tsp.tspNN
        //3. compute the tour length
        FunctionTourLength.TourLength1(tsp.tspNN(matrix), matrix);
        // 4. Modify the tour built by NN
        ImproveTwoOptMove.TwoOptMove(tsp.tspNN(matrix),matrix);
        // 5. Compute the tour length of modified tour
        FunctionTourLength.TourLength1(ImproveTwoOptMove.TwoOptMove(tsp.tspNN(matrix),matrix), matrix);
    }

}
