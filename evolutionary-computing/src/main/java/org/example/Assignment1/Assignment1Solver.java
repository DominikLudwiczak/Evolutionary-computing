package org.example.Assignment1;

import org.example.ProblemDefinition.ProblemInitializer;
import org.example.ProblemDefinition.SolutionChecker;
import org.example.ProblemDefinition.SolutionSaver;

public class Assignment1Solver {
    public static void solveInstance(String problemInstance) {
        var Problem = new ProblemInitializer(problemInstance);
        var Solution = new SolutionChecker(Problem.getDistanceMatrix(), Problem.getNodeCosts());

        // random solution
        var Random = new RandomSolution(Problem.getDistanceMatrix());
        var randomSolution = Random.Solve(200);
        String problemInstanceName = problemInstance.substring(0, problemInstance.length() - 4);

        Solution.PerformNExperiments(randomSolution, 200,"Random", problemInstanceName);

        // nearest neighbor solution
        var NN = new NearestNeighbor(Problem.getDistanceMatrix(), Problem.getNodeCosts());
        var nearestNeighborSolution = NN.Solve(200);
        Solution.PerformNExperiments(nearestNeighborSolution, 200,"NearestNeighbor", problemInstanceName);

        // any nearest neighbor solution
        var NNA = new NearestNeighborAny(Problem.getDistanceMatrix(), Problem.getNodeCosts());
        var anyNearestNeighborSolution = NNA.Solve(200);
        Solution.PerformNExperiments(anyNearestNeighborSolution, 200, "AnyNearestNeighbor", problemInstanceName);

        // greedy cycle solution
        var GC = new GreedyCycle(Problem.getDistanceMatrix(), Problem.getNodeCosts());
        var greedyCycleSolution = GC.Solve(200);
        Solution.PerformNExperiments(greedyCycleSolution, 200, "GreedyCycle", problemInstanceName);
    }

    public static void solve() {
        SolutionSaver.RemoveFile("output.csv");
        solveInstance("TSPA.csv");
        solveInstance("TSPB.csv");
    }
}
