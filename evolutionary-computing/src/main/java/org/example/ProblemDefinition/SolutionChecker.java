package org.example.ProblemDefinition;

import java.util.ArrayList;
import java.util.List;

public class SolutionChecker {
    private List<List<Integer>> distanceMatrix;
    private List<Integer> nodeCosts;

    public SolutionChecker(List<List<Integer>> distanceMatrix, List<Integer> nodeCosts) {
        this.distanceMatrix = distanceMatrix;
        this.nodeCosts = nodeCosts;
    }

    public int CalculateDistance(List<Integer> solution)
    {
        int distance = nodeCosts.get(solution.get(0));
        for (int i = 0; i < solution.size() - 1; i++) {

            distance += distanceMatrix.get(solution.get(i)).get(solution.get(i + 1));
            distance += nodeCosts.get(solution.get(i + 1));
        }
        distance += distanceMatrix.get(solution.get(solution.size() - 1)).get(solution.get(0));
        return distance;
    }

    public void PerformNExperiments(List<List<Integer>> solutions, int N, String problemName, String problemInstance)
    {
        if(solutions.size() != N)
        {
            throw new IllegalArgumentException("The number of solutions must be equal to N");
        }
        List<Integer> distances = new ArrayList<>();
        int min_distance = Integer.MAX_VALUE;
        int max_distance = Integer.MIN_VALUE;
        List<Integer> BestSolution = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            var distance = CalculateDistance(solutions.get(i));
            distances.add(distance);
            if (distance < min_distance)
            {
                min_distance = distance;
                BestSolution = solutions.get(i);
            }
            if (distance > max_distance)
            {
                max_distance = distance;
            }
        }

        //save the best solution to txt file
        SolutionSaver.SaveSolution("output.csv", problemName, problemInstance, BestSolution.toString(), min_distance);

        System.out.println("-------------------" + problemName.toUpperCase() + "-------------------");
        System.out.println("Min distance: " + min_distance);
        System.out.println("Max distance: " + max_distance);
        System.out.println("Average distance: " + distances.stream().mapToInt(Integer::intValue).average().orElse(0));
    }
}
