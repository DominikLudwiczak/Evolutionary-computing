package org.example.Assignment7;

import org.example.ProblemDefinition.ProblemInitializer;
import org.example.ProblemDefinition.SolutionChecker;
import org.example.ProblemDefinition.SolutionSaver;

public class Assignment7Solver {
    public static void solveInstance(String problemInstance, Double avgTime) {
        var Problem = new ProblemInitializer(problemInstance);
        var Solution = new SolutionChecker(Problem.getDistanceMatrix(), Problem.getNodeCosts());
        String problemInstanceName = problemInstance.substring(0, problemInstance.length() - 4);

        var LNS = new LNS(Problem.getDistanceMatrix(), Problem.getNodeCosts());

        var startTime = System.currentTimeMillis();
        var Lns = LNS.Solve(20, false, avgTime);
        var endTime = System.currentTimeMillis();
        Solution.PerformNExperiments(Lns, 20, "LNS without LS", problemInstanceName, 7);
        System.out.println("Execution time: " + (endTime - startTime) + "ms");

        startTime = System.currentTimeMillis();
        var LnsLS = LNS.Solve(20, true, avgTime);
        endTime = System.currentTimeMillis();
        Solution.PerformNExperiments(LnsLS, 20, "LNS with LS", problemInstanceName, 7);
        System.out.println("Execution time: " + (endTime - startTime) + "ms");
    }

    public static void solve() {
        SolutionSaver.RemoveFile("output.csv", 7);
        solveInstance("TSPA.csv", 16500.5);
        solveInstance("TSPB.csv", 18915.8);
    }
}
