package org.example.Assignment6;

import org.apache.commons.lang3.tuple.Pair;
import org.example.Assignment1.RandomSolution;
import org.example.Assignment3.LocalSearch;
import org.example.Assignment3.MoveType;
import org.example.Assignment3.TypeOfLocalSearch;

import java.util.ArrayList;
import java.util.List;

public class MSLS {
    private List<List<Integer>> distanceMatrix;
    private LocalSearch LS;

    public MSLS(List<List<Integer>> distanceMatrix, List<Integer> nodeCosts) {
        this.distanceMatrix = distanceMatrix;
        this.LS = new LocalSearch(distanceMatrix, nodeCosts);
    }

    public Pair<List<List<Integer>>, Double> Solve(int iterations) {
        List<List<Integer>> solutions = new ArrayList<>();
        List<Long> times = new ArrayList<>();
        for (int i = 0; i < iterations; i++) {
            var startTime = System.currentTimeMillis();
            List<Integer> solution =  new RandomSolution(this.distanceMatrix).Solve(1).get(0);
            var newSolution = GenerateSolution(solution);
            var endTime = System.currentTimeMillis();
            times.add(endTime - startTime);
            solutions.add(newSolution);
        }

        var avgTime = times.stream().mapToLong(Long::longValue).average().orElse(0);
        System.out.println("Average time: " + avgTime + "ms");
        return Pair.of(solutions, avgTime);
    }

    public List<Integer> GenerateSolution(List<Integer> solution) {
        var bestObjective = 0;
        List<Integer> bestSolution = solution;
        for(int i=0; i < 200; i++) {
            var LS_result = LS.GenerateSolution(solution, TypeOfLocalSearch.Steepest, MoveType.EXCHANGE_EDGES);
            if(LS_result.getValue() < bestObjective) {
                bestObjective = LS_result.getValue();
                bestSolution = LS_result.getKey();
            }
        }
        return bestSolution;
    }
}
