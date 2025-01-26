package org.example.Assignemnt10;

import org.apache.commons.lang3.tuple.Pair;
import org.example.Assignment1.GreedyCycle;
import org.example.Assignment3.LocalSearch;
import org.example.Assignment3.MoveType;
import org.example.Assignment3.TypeOfLocalSearch;
import org.example.Assignment6.ILS;
import org.example.Assignment7.LNS;

import java.util.ArrayList;
import java.util.List;

public class OwnMethod {
    private List<List<Integer>> distanceMatrix;
    private List<Integer> nodeCosts;
    private GreedyCycle greedyCycle;
    private LNS Lns;
    private LocalSearch LS;
    private ILS Ils;

    public OwnMethod(List<List<Integer>> distanceMatrix, List<Integer> nodeCosts) {
        this.distanceMatrix = distanceMatrix;
        this.nodeCosts = nodeCosts;
        this.greedyCycle = new GreedyCycle(distanceMatrix, nodeCosts);
        this.Lns = new LNS(distanceMatrix, nodeCosts);
        this.LS = new LocalSearch(distanceMatrix, nodeCosts);
        this.Ils = new ILS(distanceMatrix, nodeCosts);
    }

    public List<List<Integer>> Solve(int iterations, Double avgTime) {
        List<List<Integer>> solutions = new ArrayList<>();

        for (int i = 0; i < iterations; i++) {
            var randomNode = (int) (Math.random() * distanceMatrix.size());
            var solution = greedyCycle.GenerateSolution(randomNode);
            solution = GenerateSolution(solution, avgTime);
            solutions.add(solution);
        }
        return solutions;
    }

    public List<Integer> GenerateSolution(List<Integer> solution, Double avgTime) {
        var startTime = System.currentTimeMillis();

        while(System.currentTimeMillis() - startTime <= avgTime) {
            var random = Math.random();
            if(random < 0.5) {
                var destroyed = Lns.Destroy(solution);
                var repaired = Lns.Repair(destroyed.getKey());

                var LSrepaired = LS.GenerateSolution(repaired.getKey(), TypeOfLocalSearch.Steepest, MoveType.EXCHANGE_EDGES);
                repaired = Pair.of(LSrepaired.getKey(), LSrepaired.getValue() + repaired.getValue());

                if(repaired.getValue() + destroyed.getValue() < 0) {
                    solution = repaired.getKey();
                }
            } else {
                var perturbation = Ils.Perturb(solution);
                var LSperurbatedSolution = LS.GenerateSolution(perturbation.getKey(), TypeOfLocalSearch.Steepest, MoveType.EXCHANGE_EDGES);

                if(LSperurbatedSolution.getValue() + perturbation.getValue() < 0) {
                    solution = LSperurbatedSolution.getKey();
                }
            }

        }
        return solution;
    }
}
