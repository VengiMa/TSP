package com.github.VengiMa.TSP;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Admin on 22.03.2017.
 */
public class ThreeOptMove {
    public static ArrayList<Integer> ThreeOpt (ArrayList<Integer> t, double [][] distance) {
        long startTime = System.currentTimeMillis();
        int vergleich = t.size();
        boolean geschafft = true;
        int counter = 0, temp2, temp4, temp6;
        while (true) {
            if (System.currentTimeMillis() - startTime >=10000 || counter >= 15000) {
                return t;
            }
            // Two Opt auf selbe weise!!!!
            ArrayList<Integer> clone = new ArrayList<Integer>();
            for (int i = 0; i < t.size(); i++) {
                clone.add(t.get(i));
            }
            //zufall is chosen between 0 and clone.size -1; therefore it is in matrixindex
            //chooses an element out of clone, -1 --> matrix index again.
            double zufall = clone.size() * Math.random();
            int temp5 = clone.get((int) zufall)-1;
            clone.remove((int) zufall);

            zufall = clone.size() * Math.random();
            int temp3 = clone.get((int) zufall)-1;
            clone.remove((int) zufall);


            zufall = clone.size() * Math.random();
            int temp1 = clone.get((int) zufall)-1;
            clone.remove((int) zufall);

            int[] edges = {temp1, temp3, temp5};
            Arrays.sort(edges);

            //in temp 1-6, those chosen numbers are saved. temp1 represents the posistion of the first edge, saved in a with t.get
            //t.get()-1, because the edge is used in the matrix
            temp1 = edges[0]; temp3 = edges[1]; temp5 = edges[2];
            temp2 = temp1 + 1; temp4 = temp3 + 1; temp6 = (temp5 + 1) % vergleich;

            if (temp2 != temp3 && temp4 != temp5 && temp6!=temp1){
                int a, b , c, d, e, f;
                //get the edges, which will be changed
                a = t.get(temp1)-1;
                b = t.get(temp2)-1;
                c = t.get(temp3)-1;
                d = t.get(temp4)-1;
                e = t.get(temp5)-1;
                f = t.get(temp6)-1;

                //System.out.println(a+ "  " +b+ "  " +c+ "  " +d+ "  " +e+ "  " +f);
                //compute the distances before and after the change of the edges
                double vorher = distance[a][b] + distance[c][d] + distance[e][f];
                double danach = distance[a][d] + distance[e][b] + distance[c][f];
                //System.out.println("d1= "+vorher + "; d2= " +danach );
                if (danach < vorher) {
                    int l = temp2;
                    //inserting the substrings of the computed changes
                    int tem1 [] = new int[temp3-temp2+1];
                    //System.out.println(temp1.length);
                    int tem2 [] = new int[temp5-temp4+1];
                    //System.out.println(temp2.length);
                    for (int i =0; i<tem1.length;i++){
                        tem1[i]= t.get(l);
                        l++;
                    }
                    int k = temp4;
                    l =temp2;
                    //System.out.println();
                    for (int i =0; i<tem2.length;i++){

                        tem2[i]= t.get(k);
                        t.set(l,t.get(k));
                        l++;
                        k++;
                    }
                    for(int i=0; i<tem1.length;i++){
                        t.set(temp2+tem2.length,tem1[i]);
                        temp2++;
                    }
                    //System.out.println(Arrays.toString(tem1));
                    //System.out.println(Arrays.toString(tem2));
                    geschafft = false;
                }
            }
            counter++;
        }
    }

    public static void main(String[] args) throws IOException{
        File file = new File("C:\\Users\\Admin\\Desktop\\Hochschule\\Master\\Thesis - Richter\\Java\\Testdateien\\ha30.txt");
        //generate the distance matrix out of the txtfile
        double matrix[][] = InputToMatrix.TextdataInput(file);
        ArrayList<Integer> temp = NearestNeighbour.tspNN(matrix);
        ThreeOpt(temp, matrix);
    }
}
