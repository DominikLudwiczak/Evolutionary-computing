package org.example.Assignment9;

import org.apache.commons.lang3.tuple.Pair;
import org.example.Assignment1.RandomSolution;
import org.example.Assignment3.LocalSearch;
import org.example.Assignment3.MoveType;
import org.example.Assignment3.TypeOfLocalSearch;
import org.example.Assignment7.LNS;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class HAE {
    private List<List<Integer>> distanceMatrix;
    private List<Integer> nodeCosts;
    private LocalSearch LS;
    private LNS Lns;

    public HAE(List<List<Integer>> distanceMatrix, List<Integer> nodeCosts) {
        this.distanceMatrix = distanceMatrix;
        this.nodeCosts = nodeCosts;
        this.LS = new LocalSearch(distanceMatrix, nodeCosts);
        this.Lns = new LNS(distanceMatrix, nodeCosts);
    }

    public List<List<Integer>> Solve(int iterations, boolean withLS, Double avgTime) {
        List<List<Integer>> solutions = new ArrayList<>();

        for (int i = 0; i < iterations; i++) {
            var population = new PriorityQueue<Pair<List<Integer>, Integer>>(20, (a, b) -> Integer.compare(b.getValue(), a.getValue()));
            for (int j = 0; j < 20; j++) {
                List<Integer> solution =  new RandomSolution(this.distanceMatrix).Solve(1).get(0);
                solution = LS.GenerateSolution(solution, TypeOfLocalSearch.Steepest, MoveType.EXCHANGE_EDGES).getKey();
                population.add(Pair.of(solution, CalculateDistance(solution)));
            }

            var newSolution = GenerateSolution(population, withLS, avgTime);
            solutions.add(newSolution);
        }
        return solutions;
    }

    private List<Integer> GenerateSolution(PriorityQueue<Pair<List<Integer>, Integer>> population, boolean withLS, Double avgTime) {
        var startTime = System.currentTimeMillis();

        while(System.currentTimeMillis() - startTime <= avgTime) {
            int parent1 = (int) (Math.random() * population.size());
            int parent2 = (int) (Math.random() * population.size());
            while (parent1 == parent2) {
                parent2 = (int) (Math.random() * population.size());
            }

            var parent1Solution = population.stream().skip(parent1).findFirst().get().getKey();
            var parent2Solution = population.stream().skip(parent2).findFirst().get().getKey();

            var child = Recombination(parent1Solution, parent2Solution);
            if(withLS) {
                child = LS.GenerateSolution(child, TypeOfLocalSearch.Steepest, MoveType.EXCHANGE_EDGES).getKey();
            }
            var childObj = CalculateDistance(child);

            if(childObj < population.peek().getValue() && !population.contains(Pair.of(child, childObj))) {
                population.poll();
                population.add(Pair.of(child, CalculateDistance(child)));
            }
        }

        List<Integer> bestSolution = new ArrayList<>();
        while (!population.isEmpty()) {
            bestSolution = population.poll().getKey();
        }
        return bestSolution;
    }

    private List<Integer> Recombination(List<Integer> parent1, List<Integer> parent2) {
        List<Integer> child = new ArrayList<>();
        for (int i = 0; i < parent1.size(); i++) {
            var index = parent2.indexOf(parent1.get(i));
            var prevIndex = index - 1;
            var nextIndex = index + 1;
            if (prevIndex == -1) prevIndex = parent2.size() - 1;
            if (nextIndex == parent2.size()) nextIndex = 0;

            int prevI = i - 1;
            int nextI = i + 1;
            if (prevI == -1) prevI = parent1.size() - 1;
            if (nextI == parent1.size()) nextI = 0;

            if(index != -1 && (
                parent1.get(prevI).equals(parent2.get(prevIndex)) ||
                parent1.get(nextI).equals(parent2.get(nextIndex)) ||
                parent1.get(prevI).equals(parent2.get(nextIndex)) ||
                parent1.get(nextI).equals(parent2.get(prevIndex))
            )) {
               child.add(parent1.get(i));
            }
        }
        child = Lns.Repair(child).getKey();
        return child;
    }

    private int CalculateDistance(List<Integer> solution)
    {
        int distance = nodeCosts.get(solution.get(0));
        for (int i = 0; i < solution.size() - 1; i++) {

            distance += distanceMatrix.get(solution.get(i)).get(solution.get(i + 1));
            distance += nodeCosts.get(solution.get(i + 1));
        }
        distance += distanceMatrix.get(solution.get(solution.size() - 1)).get(solution.get(0));
        return distance;
    }
}
