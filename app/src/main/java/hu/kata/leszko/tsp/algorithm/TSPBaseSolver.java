package hu.kata.leszko.tsp.algorithm;

import java.util.ArrayList;
import hu.kata.leszko.tsp.City;

public class TSPBaseSolver implements TSPSolver {
    @Override
    public int[] solve(ArrayList<City> cities) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Override the solve method!");
    }

    public double getRouteDist(double[][] distMtx, int order[]){
        double dist = 0;

        //sum the travel distance based on the city order
        for(int i=0; i<order.length-1; i++){
            dist += distMtx[order[i]][order[i+1]];
        }

        //travel distance from the last city to the first city
        dist += distMtx[order[order.length-1]][order[0]];

        return dist;
    }

    public double[][] generateDistanceMatrix(ArrayList<City> cities) {
        double[][] distMtx = new double[cities.size()][cities.size()];

        for(int i=0; i<cities.size(); i++){
            for(int j=0; j<cities.size(); j++){
                if(i==j){
                    distMtx[i][j] = 0;
                }else{
                    distMtx[i][j] = getDist(cities.get(i), cities.get(j));
                }
            }
        }
        return distMtx;
    }

    public double getDist(City a, City b){
        // sqrt(|x1-x2|^2 + |y1-y2|^2)
        return Math.hypot(a.getX()-b.getX(), a.getY()-b.getY());
    }
}
