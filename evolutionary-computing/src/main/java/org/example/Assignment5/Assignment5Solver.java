package org.example.Assignment5;

import org.example.Assignment4.LocalSearchNN;
import org.example.ProblemDefinition.ProblemInitializer;
import org.example.ProblemDefinition.SolutionChecker;
import org.example.ProblemDefinition.SolutionSaver;

public class Assignment5Solver {
    public static void solveInstance(String problemInstance) {
        var Problem = new ProblemInitializer(problemInstance);
        var Solution = new SolutionChecker(Problem.getDistanceMatrix(), Problem.getNodeCosts());
        String problemInstanceName = problemInstance.substring(0, problemInstance.length() - 4);

        var Deltas = new Deltas(Problem.getDistanceMatrix(), Problem.getNodeCosts());

        var startTime = System.currentTimeMillis();
        var SteepestEdgesRandomCandidate = Deltas.Solve(200);
        var endTime = System.currentTimeMillis();
        Solution.PerformNExperiments(SteepestEdgesRandomCandidate, 200, "SteepestEdgesRandomCandidate", problemInstanceName, 4);
        System.out.println("Execution time: " + (endTime - startTime) + "ms");
    }

    public static void solve() {
        SolutionSaver.RemoveFile("output.csv", 4);
        solveInstance("TSPA.csv");
        solveInstance("TSPB.csv");
    }
}
