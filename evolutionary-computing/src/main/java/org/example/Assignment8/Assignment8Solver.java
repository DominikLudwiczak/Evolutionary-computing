package org.example.Assignment8;

import org.example.ProblemDefinition.ProblemInitializer;
import org.example.ProblemDefinition.SolutionSaver;

public class Assignment8Solver {
    public static void solveInstance(String problemInstance) {
        var Problem = new ProblemInitializer(problemInstance);
        var Similarity = new Similarity(Problem.getDistanceMatrix(), Problem.getNodeCosts());

        var startTime = System.currentTimeMillis();
        Similarity.Solve(problemInstance.split("\\.")[0]);
        var endTime = System.currentTimeMillis();
        System.out.println("Execution time: " + (endTime - startTime) + "ms");
    }

    public static void solve() {
        SolutionSaver.RemoveFile("output.csv", 8);
        solveInstance("TSPA.csv");
        solveInstance("TSPB.csv");
    }
}
