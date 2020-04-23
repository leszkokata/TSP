package hu.kata.leszko.tsp.algorithm;

import java.util.ArrayList;

import hu.kata.leszko.tsp.City;

public interface TSPSolver {
    public int[] solve(ArrayList<City> cities) throws Exception;
}
