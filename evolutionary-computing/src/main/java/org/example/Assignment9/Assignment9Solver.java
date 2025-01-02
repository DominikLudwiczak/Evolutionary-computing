package org.example.Assignment9;

import org.example.ProblemDefinition.ProblemInitializer;
import org.example.ProblemDefinition.SolutionChecker;
import org.example.ProblemDefinition.SolutionSaver;

public class Assignment9Solver {
    public static void solveInstance(String problemInstance, Double avgTime) {
        var Problem = new ProblemInitializer(problemInstance);
        var Solution = new SolutionChecker(Problem.getDistanceMatrix(), Problem.getNodeCosts());
        String problemInstanceName = problemInstance.substring(0, problemInstance.length() - 4);

        var HAE = new HAE(Problem.getDistanceMatrix(), Problem.getNodeCosts());

        var startTime = System.currentTimeMillis();
        var HaeLS = HAE.Solve(20, true, avgTime);
        var endTime = System.currentTimeMillis();
        Solution.PerformNExperiments(HaeLS, 20, "HAE with LS", problemInstanceName, 9);
        System.out.println("Execution time: " + (endTime - startTime) + "ms");

        startTime = System.currentTimeMillis();
        var Hae = HAE.Solve(20, true, avgTime);
        endTime = System.currentTimeMillis();
        Solution.PerformNExperiments(Hae, 20, "HAE without LS", problemInstanceName, 9);
        System.out.println("Execution time: " + (endTime - startTime) + "ms");
    }

    public static void solve() {
        SolutionSaver.RemoveFile("output.csv", 9);
        solveInstance("TSPA.csv", 16500.5);
        solveInstance("TSPB.csv", 18915.8);
    }
}
