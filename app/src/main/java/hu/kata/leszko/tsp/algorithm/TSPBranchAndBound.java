package hu.kata.leszko.tsp.algorithm;

import java.util.ArrayList;
import java.util.Arrays;

import hu.kata.leszko.tsp.City;

public class TSPBranchAndBound extends TSPBaseSolver {
    private int N;

    private int final_path[];
    private boolean visited[];
    private double final_res;


    private void copyToFinal(int curr_path[])
    {
        for (int i = 0; i < N; i++)
            final_path[i] = curr_path[i];
        final_path[N] = curr_path[0];
    }

    private double firstMin(double distMtx[][], int i)
    {
        double min = Double.MAX_VALUE;
        for (int k = 0; k < N; k++)
            if (distMtx[i][k] < min && i != k)
                min = distMtx[i][k];
        return min;
    }

    private double secondMin(double distMtx[][], int i)
    {
        double first = Double.MAX_VALUE, second = Double.MAX_VALUE;
        for (int j=0; j<N; j++)
        {
            if (i == j)
                continue;

            if (distMtx[i][j] <= first)
            {
                second = first;
                first = distMtx[i][j];
            }
            else if (distMtx[i][j] <= second &&
                    distMtx[i][j] != first)
                second = distMtx[i][j];
        }
        return second;
    }

    private void TSPRec(double distMtx[][], int curr_bound, double curr_weight,
                int level, int curr_path[])
    {
        if (level == N)
        {
            if (distMtx[curr_path[level - 1]][curr_path[0]] != 0)
            {
                double curr_res = curr_weight +
                        distMtx[curr_path[level-1]][curr_path[0]];

                if (curr_res < final_res)
                {
                    copyToFinal(curr_path);
                    final_res = curr_res;
                }
            }
            return;
        }


        for (int i = 0; i < N; i++)
        {

            if (distMtx[curr_path[level-1]][i] != 0 &&
                    visited[i] == false)
            {
                int temp = curr_bound;
                curr_weight += distMtx[curr_path[level - 1]][i];

                if (level==1)
                    curr_bound -= ((firstMin(distMtx, curr_path[level - 1]) +
                            firstMin(distMtx, i))/2);
                else
                    curr_bound -= ((secondMin(distMtx, curr_path[level - 1]) +
                            firstMin(distMtx, i))/2);


                if (curr_bound + curr_weight < final_res)
                {
                    curr_path[level] = i;
                    visited[i] = true;

                    TSPRec(distMtx, curr_bound, curr_weight, level + 1,
                            curr_path);
                }


                curr_weight -= distMtx[curr_path[level-1]][i];
                curr_bound = temp;

                Arrays.fill(visited,false);
                for (int j = 0; j <= level - 1; j++)
                    visited[curr_path[j]] = true;
            }
        }
    }

    public int[] solve(ArrayList<City> cities)
    {
        N = cities.size();
        final_path = new int[cities.size() + 1 ];
        visited = new boolean[cities.size()];
        final_res = Integer.MAX_VALUE;

        double[][] adj = generateDistanceMatrix(cities);
        int curr_path[] = new int[N + 1];
        int curr_bound = 0;
        Arrays.fill(curr_path, -1);
        Arrays.fill(visited, false);

        for (int i = 0; i < N; i++)
            curr_bound += (firstMin(adj, i) +
                    secondMin(adj, i));

        curr_bound = (curr_bound==1)? curr_bound/2 + 1 :
                curr_bound/2;

        visited[0] = true;
        curr_path[0] = 0;

        TSPRec(adj, curr_bound, 0, 1, curr_path);
        return final_path;
    }
}


