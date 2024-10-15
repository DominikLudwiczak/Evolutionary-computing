package org.example.Assignment2;

import java.util.ArrayList;
import java.util.List;

public class RegretMyLifeChoices {
    private List<List<Integer>> distanceMatrix;
    private List<Integer> nodeCosts;

    public RegretMyLifeChoices(List<List<Integer>> distanceMatrix, List<Integer> nodeCosts) {
        this.distanceMatrix = distanceMatrix;
        this.nodeCosts = nodeCosts;
    }

    public List<List<Integer>> Solve(int iterations) {
        List<List<Integer>> solutions = new ArrayList<>();
        for (int i = 0; i < iterations; i++) {
            List<Integer> solution = GenerateSolution(i);
            solutions.add(solution);
        }
        return solutions;
    }

    private List<Integer> GenerateSolution(int startingNode) {
        List<Integer> solution = new ArrayList<>();
        int[] objective = {0};
        solution.add(startingNode);
        objective[0] += nodeCosts.get(startingNode);
        while (solution.size() < distanceMatrix.size() / 2) {
            solution = FindRegret2(solution, objective);
        }
        return solution;
    }

    private List<Integer> FindRegret2(List<Integer> solution, int[] objective) {
        int tempObjective = objective[0];
        return solution;
    }
}
