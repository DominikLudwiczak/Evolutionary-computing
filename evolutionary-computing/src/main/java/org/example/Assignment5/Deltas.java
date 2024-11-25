package org.example.Assignment5;

import org.example.Assignment1.RandomSolution;
import org.example.Assignment3.Move;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Deltas {
    private List<List<Integer>> distanceMatrix;
    private List<Integer> nodeCosts;
    private HashMap<String, Move> moves = new HashMap<>();
    private HashMap<Integer, List<Integer>> TenNNs = new HashMap<>();

    public Deltas(List<List<Integer>> distanceMatrix, List<Integer> nodeCosts) {
        this.distanceMatrix = distanceMatrix;
        this.nodeCosts = nodeCosts;
    }

    public List<List<Integer>> Solve(int iterations) {
        List<List<Integer>> solutions = new ArrayList<>();
        for (int i = 0; i < iterations; i++) {
            List<Integer> solution =  new RandomSolution(this.distanceMatrix).Solve(1).get(0);
            var newSolution = GenerateSolution(solution);
            solutions.add(newSolution);
        }
        return solutions;
    }

    public List<Integer> GenerateSolution(List<Integer> solution) {
        return solution;
    }
}
