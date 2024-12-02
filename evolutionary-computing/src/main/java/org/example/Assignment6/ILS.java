package org.example.Assignment6;

import org.apache.commons.lang3.tuple.Pair;
import org.example.Assignment1.RandomSolution;
import org.example.Assignment3.LocalSearch;
import org.example.Assignment3.MoveType;
import org.example.Assignment3.TypeOfLocalSearch;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ILS {
    private List<List<Integer>> distanceMatrix;
    private List<Integer> nodeCosts;
    private LocalSearch LS;

    public ILS(List<List<Integer>> distanceMatrix, List<Integer> nodeCosts) {
        this.distanceMatrix = distanceMatrix;
        this.nodeCosts = nodeCosts;
        this.LS = new LocalSearch(distanceMatrix, nodeCosts);
    }

    public List<List<Integer>> Solve(int iterations, Double avgTime) {
        List<List<Integer>> solutions = new ArrayList<>();
        List<Integer> LSruns = new ArrayList<>();
        for (int i = 0; i < iterations; i++) {
            List<Integer> solution =  new RandomSolution(this.distanceMatrix).Solve(1).get(0);
            var genSol = GenerateSolution(solution, avgTime);
            solutions.add(genSol.getKey());
            LSruns.add(genSol.getValue());
        }
        System.out.println("Average LS runs: " + LSruns.stream().mapToInt(Integer::intValue).average().orElse(0));
        return solutions;
    }

    public Pair<List<Integer>, Integer> GenerateSolution(List<Integer> solution, Double avgTime) {
        var startTime = System.currentTimeMillis();
        int LS_runs = 1;
        var LSsolution = LS.GenerateSolution(solution, TypeOfLocalSearch.Steepest, MoveType.EXCHANGE_EDGES);
        solution = LSsolution.getKey();

        while(System.currentTimeMillis() - startTime <= avgTime) {
            var perturbation = this.Perturb(solution);
            var LSperurbatedSolution = LS.GenerateSolution(perturbation.getKey(), TypeOfLocalSearch.Steepest, MoveType.EXCHANGE_EDGES);
            LS_runs++;

            if(LSperurbatedSolution.getValue() + perturbation.getValue() < 0) {
                solution = LSperurbatedSolution.getKey();
            }
        }
        return Pair.of(solution, LS_runs);
    }

    public Pair<List<Integer>, Integer> Perturb(List<Integer> solution) {
        var currSolution = new ArrayList<>(solution);
        var objectiveChange = 0;
        for(int i=0; i < 3; i++) {
            var notUsedNodes = new ArrayList<>(IntStream.range(0, this.nodeCosts.size()).boxed().toList());
            notUsedNodes.removeAll(currSolution);

            var randomSolutionIndex = (int) (Math.random() * currSolution.size());

            int prevIndex = randomSolutionIndex - 1;
            if (prevIndex < 0) prevIndex = solution.size() - 1;
            int nextIndex = randomSolutionIndex + 1;
            if (nextIndex == currSolution.size()) nextIndex = 0;

            objectiveChange -= this.nodeCosts.get(currSolution.get(randomSolutionIndex));
            objectiveChange -= this.distanceMatrix.get(currSolution.get(prevIndex)).get(currSolution.get(randomSolutionIndex));
            objectiveChange -= this.distanceMatrix.get(currSolution.get(randomSolutionIndex)).get(currSolution.get(nextIndex));
            objectiveChange += this.distanceMatrix.get(currSolution.get(prevIndex)).get(currSolution.get(nextIndex));

            currSolution.remove(randomSolutionIndex);

            var randomIndex = (int) (Math.random() * currSolution.size());
            var randomNode = notUsedNodes.get((int) (Math.random() * notUsedNodes.size()));

            var prevIndex2 = randomIndex - 1;
            if (prevIndex2 < 0) prevIndex2 = currSolution.size() - 1;

            objectiveChange -= this.distanceMatrix.get(currSolution.get(prevIndex2)).get(currSolution.get(randomIndex));

            objectiveChange += this.nodeCosts.get(randomNode);
            objectiveChange += this.distanceMatrix.get(currSolution.get(prevIndex2)).get(randomNode);
            objectiveChange += this.distanceMatrix.get(randomNode).get(currSolution.get(randomIndex));

            currSolution.add(randomIndex, randomNode);
        }
        return Pair.of(currSolution, objectiveChange);
    }
}
