package hu.kata.leszko.tsp.algorithm;


import java.util.ArrayList;

import hu.kata.leszko.tsp.City;

public class TSPBruteForce extends TSPBaseSolver {

    @Override
    public int[] solve(ArrayList<City> cities){
        double distMtx[][] = generateDistanceMatrix(cities);
        int order[] = new int[cities.size()+1];
        double minDist = Double.MAX_VALUE;

        int currentOrder[] = new int[cities.size()];
        for(int i=0; i<cities.size(); i++){
            currentOrder[i] = i;
        }

        do{
            if(currentOrder[0]==0){
                double currentDist = getRouteDist(distMtx, currentOrder);
                if(currentDist < minDist){
                    minDist = currentDist;
                    for(int i=0; i<cities.size(); i++){
                        order[i] = currentOrder[i];
                    }
                }
            }
        }while (nextPermutation(currentOrder));

        order[cities.size()] = 0;
        return order;
    }

    private boolean nextPermutation(int[] array) {
        // Find longest non-increasing suffix
        int i = array.length - 1;
        while (i > 0 && array[i - 1] >= array[i])
            i--;
        // Now i is the head index of the suffix

        // Are we at the last permutation already?
        if (i <= 0)
            return false;

        // Let array[i - 1] be the pivot
        // Find rightmost element that exceeds the pivot
        int j = array.length - 1;
        while (array[j] <= array[i - 1])
            j--;
        // Now the value array[j] will become the new pivot
        // Assertion: j >= i

        // Swap the pivot with j
        int temp = array[i - 1];
        array[i - 1] = array[j];
        array[j] = temp;

        // Reverse the suffix
        j = array.length - 1;
        while (i < j) {
            temp = array[i];
            array[i] = array[j];
            array[j] = temp;
            i++;
            j--;
        }

        // Successfully computed the next permutation
        return true;
    }

}
