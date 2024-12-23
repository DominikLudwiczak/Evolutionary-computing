package org.example.Assignment6;

import org.example.ProblemDefinition.ProblemInitializer;
import org.example.ProblemDefinition.SolutionChecker;
import org.example.ProblemDefinition.SolutionSaver;

public class Assignment6Solver {
    public static void solveInstance(String problemInstance) {
        var Problem = new ProblemInitializer(problemInstance);
        var Solution = new SolutionChecker(Problem.getDistanceMatrix(), Problem.getNodeCosts());
        String problemInstanceName = problemInstance.substring(0, problemInstance.length() - 4);

        var MSLS = new MSLS(Problem.getDistanceMatrix(), Problem.getNodeCosts());
        var ILS = new ILS(Problem.getDistanceMatrix(), Problem.getNodeCosts());

        var startTime = System.currentTimeMillis();
        var Msls = MSLS.Solve(20);
        var endTime = System.currentTimeMillis();
        Solution.PerformNExperiments(Msls.getKey(), 20, "MSLS", problemInstanceName, 6);
        System.out.println("Execution time: " + (endTime - startTime) + "ms");

        startTime = System.currentTimeMillis();
        var Ils = ILS.Solve(20, Msls.getValue());
        endTime = System.currentTimeMillis();
        Solution.PerformNExperiments(Ils, 20, "ILS", problemInstanceName, 6);
        System.out.println("Execution time: " + (endTime - startTime) + "ms");
    }

    public static void solve() {
        SolutionSaver.RemoveFile("output.csv", 6);
        solveInstance("TSPA.csv");
        solveInstance("TSPB.csv");
    }
}
