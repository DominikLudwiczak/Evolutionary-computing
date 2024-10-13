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
        return distance;
    }

    public void PerformNExperiments(List<List<Integer>> solutions, int N)
    {
        if(solutions.size() != N)
        {
            throw new IllegalArgumentException("The number of solutions must be equal to N");
        }
        List<Integer> distances = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            distances.add(CalculateDistance(solutions.get(i)));
        }

        System.out.println("The maximum distance is: " + distances.stream().max(Integer::compareTo).get());
        System.out.println("The minimum distance is: " + distances.stream().min(Integer::compareTo).get());
        System.out.println("The average distance is: " + distances.stream().mapToInt(Integer::intValue).average().getAsDouble());
    }
}
