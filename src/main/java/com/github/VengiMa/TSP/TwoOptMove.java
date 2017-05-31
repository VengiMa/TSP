package com.github.VengiMa.TSP;

import com.github.VengiMa.Algorithm.*;
import java.util.ArrayList;

/**
 * Created by Admin on 18.03.2017.
 */
public class TwoOptMove {
    public static ArrayList<Integer> TwoOpt (ArrayList<Integer> t, double [][] distance) {
        long startTime = System.currentTimeMillis();
        int vergleich = t.size();
        int counter = 0;
        while (true) {
            //first edge, i is initialized and traversed
            for (int i = 0; i < t.size(); i++) {
                //second edge, j is initialized and traversed
                //maybe there is a more efficient way of searching for a 2-opt move that decreases the tour length??
                for (int j = 0; j < t.size(); j++) {
                    //controlling, that same edges will not be changed, is wrong, also, no direct neighbours can be changed
                    if ((i != j) && (j != (i + 1)) && (i != (j + 1))) {
                        //as a time limit, otherwise, the algorithm would proceed till the loops are completed
                        if (System.currentTimeMillis() - startTime >=10000 || counter >= 15000) {
                            return t;
                        }
                        //variables a to d take the values in the current tour, but must be decreased by 1
                        //beacause city i is represented in the matrix by i-1
                        int a = t.get(i) - 1;
                        int b = t.get((i + 1) % vergleich) - 1;
                        int c = t.get(j) - 1;
                        int d = t.get((j + 1) % vergleich) - 1;
                        double distanceAB = distance[a][b];
                        double distanceCD = distance[c][d];

                        double distanceAC = distance[a][c];
                        double distanceBD = distance[b][d];
                        //System.out.println("\n" + "a="+a+"; b="+b+"; c="+c+"; d="+d);
                        //System.out.println("distanceAB="+distanceAB+"; distanceCD="+distanceCD);
                        double change = (distanceAB + distanceCD) - (distanceAC + distanceBD);
                        //if there is no gain in the 2-opt move, it will be forgotten
                        //otherwise,
                        if (change > 0) {
                            //increase the variables by 1 again, so they represent the cityIndex and not the matrix index
                            a = a + 1;
                            b = b + 1;
                            c = c + 1;
                            d = d + 1;
                            t.set(j, b);
                            t.set((i + 1) % vergleich, c);
                            //due to the 2-opt move, tour between i+1 and j must be reversed, because the order gets changed,
                            //therefore different cases must be considered:
                            //1. edges are direct neighbours --> no change in the order between the changed edges
                            //2. j is smaller than i+2, meaning: the whole tour, including the last city and restarting at the beginning, must be reversed
                            //3. i+2 is smaller than j, edges in between and therefore the order must reversed
                            //Reversing of the order is made by filling an array temp[] with the tour segment between i+2 and j
                            //Afterwards, the toursegment t from i+2 to j is filled backwards with the tour saved in temp[]
                            //Case 1:
                            if (j - (i + 2) == 0) {
                            }
                            //Case 3:
                            if (j < (i + 2)) {
                                int temp[] = new int[((t.size()) - (i + 2) % vergleich) + j];
                                int k;
                                int l = i;

                                for (k = temp.length - 1; k >= 0; k--) {
                                    temp[k] = t.get((l + 2) % vergleich);
                                    l++;
                                }
                                int m = i;
                                for (k = 0; k < temp.length; k++) {
                                    t.set((m + 2) % vergleich, temp[k]);
                                    m++;
                                }
                                //Case 3:
                            } else {
                                int temp[] = new int[j - (i + 2)];
                                int k;
                                int l = i;
                                //System.out.print("\n");
                                for (k = temp.length - 1; k >= 0; k--) {
                                    temp[k] = t.get((l + 2) % vergleich);
                                    //System.out.print(temp[k]+",");
                                    l++;
                                }
                                //System.out.print("    ");
                                int m = i;
                                for (k = 0; k < temp.length; k++) {
                                    t.set((m + 2) % vergleich, temp[k]);
                                    //System.out.print(t.get(m + 2)+",");
                                    m++;
                                }
                            }
                            //System.out.print("\n");
                            //System.out.println("\ni: " + i + "; j: " + j + "; counter: "+counter);
                            //System.out.print(change + "  " + a + "-" + b + " und " + c + "-" + d + " wird zu: " + a + "-" + c + " und " + b + "-" + d + "\n");
                            //for (Integer number : t) {
                                //System.out.print(number + " ");
                            //}
                        }
                        else{counter = counter +1;}

                    }

                }
            }
        }
    }
}
