package hu.kata.leszko.tsp.algorithm;


import java.util.ArrayList;
import java.util.Arrays;

import hu.kata.leszko.tsp.City;

public class TSPGreedy {

    public int[] solve(ArrayList<City> cities){
        //distance matrix
        int[][] distMtx = new int[cities.size()][cities.size()];

        for (int i = 0; i < cities.size(); i++) {
            for (int j = 0; j < cities.size(); j++) {
                if (i == j) {
                    distMtx[i][j] = 0;
                } else {
                    distMtx[i][j] = (int) Math.round(Math.sqrt(
                            Math.pow(Math.abs((cities.get(j).getY() - cities.get(i).getY())), 2) +
                                    Math.pow(Math.abs((cities.get(j).getX() - cities.get(i).getX())), 2)));
                }
            }
        }

        //route (+1 back to start)
        int[] order = new int[cities.size()+1];
        boolean visited[] = new boolean[cities.size()];
        Arrays.fill(visited, false);

        //starting at the first (idx: 0) city
        order[0] = 0;
        visited[0] = true;

        for(int i = 0; i < cities.size()-1; i++){
            int minidx = -1;
            int mind = Integer.MAX_VALUE;

            //find the nearest city
            for(int j = 0; j<cities.size(); j++){
                if(!visited[j]){
                    if(distMtx[order[i]][j] < mind){
                        mind = distMtx[order[i]][j];
                        minidx = j;
                    }
                }
            }
            visited[minidx] = true;
            order[i+1] = minidx;
        }

        //back to the start
        order[cities.size()] = 0;
        return order;
    }

}
