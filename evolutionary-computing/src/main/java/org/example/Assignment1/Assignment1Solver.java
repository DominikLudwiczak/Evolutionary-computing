package org.example.Assignment1;

import org.example.ProblemDefinition.ProblemInitializer;
import org.example.ProblemDefinition.SolutionChecker;
import org.example.ProblemDefinition.SolutionSaver;

public class Assignment1Solver {
    public static void solveInstance(String problemInstance) {
        var Problem = new ProblemInitializer(problemInstance);
        var Solution = new SolutionChecker(Problem.getDistanceMatrix(), Problem.getNodeCosts());

        SolutionSaver.RemoveFile("output.csv");

        // random solution
        var Random = new RandomSolution(Problem.getDistanceMatrix());
        var randomSolution = Random.Solve(200);
        Solution.PerformNExperiments(randomSolution, 200,"Random");

        // nearest neighbor solution
        var NN = new NearestNeighbor(Problem.getDistanceMatrix(), Problem.getNodeCosts());
        var nearestNeighborSolution = NN.Solve(200);
        Solution.PerformNExperiments(nearestNeighborSolution, 200,"NearestNeighbor");
    }

    public static void solve() {
        solveInstance("TSPA.csv");
        solveInstance("TSPB.csv");
    }
}
