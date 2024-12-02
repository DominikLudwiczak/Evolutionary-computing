package org.example.Assignment7;

import org.apache.commons.lang3.tuple.Pair;
import org.example.Assignment1.RandomSolution;
import org.example.Assignment3.LocalSearch;
import org.example.Assignment3.MoveType;
import org.example.Assignment3.TypeOfLocalSearch;
import java.util.ArrayList;
import java.util.List;

public class LNS {
    private List<List<Integer>> distanceMatrix;
    private List<Integer> nodeCosts;
    private LocalSearch LS;

    public LNS(List<List<Integer>> distanceMatrix, List<Integer> nodeCosts) {
        this.distanceMatrix = distanceMatrix;
        this.nodeCosts = nodeCosts;
        this.LS = new LocalSearch(distanceMatrix, nodeCosts);
    }

    public List<List<Integer>> Solve(int iterations, boolean withLS, Double avgTime) {
        List<List<Integer>> solutions = new ArrayList<>();
        for (int i = 0; i < iterations; i++) {
            List<Integer> solution =  new RandomSolution(this.distanceMatrix).Solve(1).get(0);
            var newSolution = GenerateSolution(solution, withLS, avgTime);
            solutions.add(newSolution);
        }
        return solutions;
    }

    public List<Integer> GenerateSolution(List<Integer> solution, boolean withLS, Double avgTime) {
        var startTime = System.currentTimeMillis();
        var LSsolution = LS.GenerateSolution(solution, TypeOfLocalSearch.Steepest, MoveType.EXCHANGE_EDGES);
        solution = LSsolution.getKey();

        while(System.currentTimeMillis() - startTime <= avgTime) {
            var destroyed = Destroy(solution);
            var repaired = Repair(destroyed.getKey());

            if(withLS) {
                var LSrepaired = LS.GenerateSolution(repaired.getKey(), TypeOfLocalSearch.Steepest, MoveType.EXCHANGE_EDGES);
                repaired = Pair.of(LSrepaired.getKey(), LSrepaired.getValue() + repaired.getValue());
            }

            if(repaired.getValue() + destroyed.getValue() < 0) {
                solution = repaired.getKey();
            }
        }
        return solution;
    }

    public Pair<List<Integer>, Integer> Destroy(List<Integer> solution) {
        var currSolution = new ArrayList<>(solution);
        var objectiveChange = 0;
        return Pair.of(currSolution, objectiveChange);
    }

    public Pair<List<Integer>, Integer> Repair(List<Integer> solution) {
        var currSolution = new ArrayList<>(solution);
        var objectiveChange = 0;
        return Pair.of(currSolution, objectiveChange);
    }
}
