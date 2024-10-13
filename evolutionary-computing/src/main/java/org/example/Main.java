package org.example;

import org.example.Assignment1.RandomSolution;
import org.example.ProblemDefinition.ProblemInitializer;
import org.example.ProblemDefinition.SolutionChecker;

public class Main {
    public static void main(String[] args) {
        var Problem = new ProblemInitializer();
        Problem.PrintMatrix();
        var Solution = new SolutionChecker(Problem.getDistanceMatrix(), Problem.getNodeCosts());
        var randomSolution = new RandomSolution(Problem.getDistanceMatrix());
        Solution.PerformNExperiments(randomSolution.Solve(200), 200, "RANDOM");
    }
}