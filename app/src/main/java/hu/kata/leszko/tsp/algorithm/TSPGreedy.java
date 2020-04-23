package hu.kata.leszko.tsp.algorithm;


import java.util.ArrayList;
import java.util.Arrays;

import hu.kata.leszko.tsp.City;

public class TSPGreedy extends TSPBaseSolver{

    @Override
    public int[] solve(ArrayList<City> cities){
        //distance matrix
        double[][] distMtx = generateDistanceMatrix(cities);

        //route (+1 back to start)
        int[] order = new int[cities.size()+1];
        boolean visited[] = new boolean[cities.size()];
        Arrays.fill(visited, false);

        //starting at the first (idx: 0) city
        order[0] = 0;
        visited[0] = true;

        for(int i = 0; i < cities.size()-1; i++){
            int minidx = -1;
            double mind = Integer.MAX_VALUE;

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
