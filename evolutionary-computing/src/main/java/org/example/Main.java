package org.example;

import org.example.Assignment1.GreedyCycle;
import org.example.Assignment1.NearestNeighbor;
import org.example.Assignment1.RandomSolution;
import org.example.Assignment1.NearestNeighborAny;
import org.example.ProblemDefinition.ProblemInitializer;
import org.example.ProblemDefinition.SolutionChecker;

public class Main {
        public static void main(String[] args) {
                var Problem = new ProblemInitializer();
                var Solution = new SolutionChecker(Problem.getDistanceMatrix(), Problem.getNodeCosts());

                // random solution
                var Random = new RandomSolution(Problem.getDistanceMatrix());
                var randomSolution = Random.Solve(200);
                Solution.PerformNExperiments(randomSolution, 200,"Random");

                // nearest neighbor solution
                var NN = new NearestNeighbor(Problem.getDistanceMatrix(), Problem.getNodeCosts());
                var nearestNeighborSolution = NN.Solve(200);

                // any nearest neighbor solution
                var NNA = new NearestNeighborAny(Problem.getDistanceMatrix(), Problem.getNodeCosts());
                var anyNearestNeighborSolution = NNA.Solve(200);

                // greedy cycle solution
                var GC = new GreedyCycle(Problem.getDistanceMatrix(), Problem.getNodeCosts());
                var greedyCycleSolution = GC.Solve(200);
                Solution.PerformNExperiments(nearestNeighborSolution, 200,"NearestNeighbor");
        }
}