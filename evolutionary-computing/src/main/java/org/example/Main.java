package org.example;

import org.example.Assignment1.NearestNeighbor;
import org.example.ProblemDefinition.ProblemInitializer;
import org.example.ProblemDefinition.SolutionChecker;

public class Main {
        public static void main(String[] args) {
                var Problem = new ProblemInitializer();
                var Solution = new SolutionChecker(Problem.getDistanceMatrix(), Problem.getNodeCosts());

                // random solution
                var Random = new RandomSolution(Problem.getDistanceMatrix());
                var randomSolution = Random.Solve(200);

                // nearest neighbor solution
                var NN = new NearestNeighbor(Problem.getDistanceMatrix(), Problem.getNodeCosts());
                var nearestNeighborSolution = NN.Solve(200);
        }
}