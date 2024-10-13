package org.example.Assignment1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RandomSolution {
    private List<List<Integer>> distanceMatrix;

    public RandomSolution(List<List<Integer>> distanceMatrix) {
        this.distanceMatrix = distanceMatrix;
    }

    public List<List<Integer>> Solve(int iterations) {
        List<List<Integer>> solutions = new ArrayList<>();
        for (int i = 0; i < iterations; i++) {
            List<Integer> permutation = GenerateRandomPermutation(i);
            solutions.add(permutation);
        }
        return solutions;
    }

    private List<Integer> GenerateRandomPermutation(int startingNode) {
        List<Integer> permutation = new ArrayList<>();
        for (int i = 0; i < distanceMatrix.size(); i++) {
            permutation.add(i);
        }
        Collections.shuffle(permutation);
        permutation.remove((Integer) startingNode);
        permutation.add(0, startingNode);
        return permutation;
    }
}
