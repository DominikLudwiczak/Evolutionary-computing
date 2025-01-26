package org.example.Assignemnt10;

import org.example.ProblemDefinition.ProblemInitializer;
import org.example.ProblemDefinition.SolutionChecker;
import org.example.ProblemDefinition.SolutionSaver;

public class Assignment10Solver {
    public static void solveInstance(String problemInstance, Double avgTime) {
        var Problem = new ProblemInitializer(problemInstance);
        var Solution = new SolutionChecker(Problem.getDistanceMatrix(), Problem.getNodeCosts());
        String problemInstanceName = problemInstance.substring(0, problemInstance.length() - 4);

        var Own = new OwnMethod(Problem.getDistanceMatrix(), Problem.getNodeCosts());

        var startTime = System.currentTimeMillis();
        var own = Own.Solve(20, avgTime);
        var endTime = System.currentTimeMillis();
        Solution.PerformNExperiments(own, 20, "Own Method", problemInstanceName, 10);
        System.out.println("Execution time: " + (endTime - startTime) + "ms");
    }

    public static void solve() {
        SolutionSaver.RemoveFile("output.csv", 10);
        solveInstance("TSPA.csv", 16500.5);
        solveInstance("TSPB.csv", 18915.8);
    }
}
