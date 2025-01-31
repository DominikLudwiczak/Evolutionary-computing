package org.example.Assignment7;

import org.apache.commons.lang3.tuple.Pair;
import org.example.Assignment1.RandomSolution;
import org.example.Assignment2.RegretMyLifeChoices;
import org.example.Assignment3.LocalSearch;
import org.example.Assignment3.MoveType;
import org.example.Assignment3.TypeOfLocalSearch;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

public class LNS {
    private List<List<Integer>> distanceMatrix;
    private List<Integer> nodeCosts;
    private LocalSearch LS;
    private HashSet<Integer> takenNodes;
    private int cnt = 0;

    public LNS(List<List<Integer>> distanceMatrix, List<Integer> nodeCosts) {
        this.distanceMatrix = distanceMatrix;
        this.nodeCosts = nodeCosts;
        this.LS = new LocalSearch(distanceMatrix, nodeCosts);
        this.cnt = 0;
    }

    public List<List<Integer>> Solve(int iterations, boolean withLS, Double avgTime) {
        List<List<Integer>> solutions = new ArrayList<>();

        var counters = new ArrayList<Integer>();
        for (int i = 0; i < iterations; i++) {
            List<Integer> solution =  new RandomSolution(this.distanceMatrix).Solve(1).get(0);
            var newSolution = GenerateSolution(solution, withLS, avgTime);
            solutions.add(newSolution);
            counters.add(cnt);
        }
        System.out.println("Average number of iterations: " + counters.stream().mapToInt(Integer::intValue).average().orElse(0));
        return solutions;
    }

    public List<Integer> GenerateSolution(List<Integer> solution, boolean withLS, Double avgTime) {
        var startTime = System.currentTimeMillis();
        var LSsolution = LS.GenerateSolution(solution, TypeOfLocalSearch.Steepest, MoveType.EXCHANGE_EDGES);
        solution = LSsolution.getKey();

        while(System.currentTimeMillis() - startTime <= avgTime) {
            cnt++;
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

        for (int i = 0; i < 3; i++) {
            var nodes = new PriorityQueue<Pair<Integer, Integer>>(30, (a, b) -> Integer.compare(b.getValue(), a.getValue()));
            var usedNodes = new ArrayList<Integer>();
            for (int j = 0; j < 30; j++) {
                var index = (int) (Math.random() * currSolution.size());
                var node = currSolution.get(index);
                while (usedNodes.contains(node)) {
                    index = (int) (Math.random() * currSolution.size());
                    node = currSolution.get(index);
                }
                nodes.add(Pair.of(node, nodeCosts.get(index)));
                usedNodes.add(node);
            }

            for (int j = 0; j < 10; j++) {
                var node = nodes.poll().getKey();
                var solutionIndex = currSolution.indexOf(node);
                int prevIndex = solutionIndex - 1;
                if (prevIndex < 0) prevIndex = solution.size() - 1;
                int nextIndex = solutionIndex + 1;
                if (nextIndex == currSolution.size()) nextIndex = 0;

                objectiveChange -= this.nodeCosts.get(currSolution.get(solutionIndex));
                objectiveChange -= this.distanceMatrix.get(currSolution.get(prevIndex)).get(currSolution.get(solutionIndex));
                objectiveChange -= this.distanceMatrix.get(currSolution.get(solutionIndex)).get(currSolution.get(nextIndex));
                objectiveChange += this.distanceMatrix.get(currSolution.get(prevIndex)).get(currSolution.get(nextIndex));

                currSolution.remove(solutionIndex);
            }
        }

        return Pair.of(currSolution, objectiveChange);
    }

    public Pair<List<Integer>, Integer> Repair(List<Integer> solution) {
        takenNodes = new HashSet<>(solution);
        var heuristic = new RegretMyLifeChoices(this.distanceMatrix, this.nodeCosts, true);
        heuristic.setTakenNodes(takenNodes);
        int[] objectiveChange = {0};

        while (solution.size() < distanceMatrix.size() / 2) {
            if (solution.size() <= 2){
                solution = heuristic.findFirst2(solution, objectiveChange);
            } else {
                solution = heuristic.FindRegret2(solution, objectiveChange);
            }
        }
        return Pair.of(solution, objectiveChange[0]);
    }
}
