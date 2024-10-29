package org.example.Assignment4;

import org.example.Assignment3.LocalSearch;
import org.example.Assignment3.MoveType;
import org.example.Assignment3.StartingSolution;
import org.example.Assignment3.TypeOfLocalSearch;
import org.example.ProblemDefinition.ProblemInitializer;
import org.example.ProblemDefinition.SolutionChecker;
import org.example.ProblemDefinition.SolutionSaver;

public class Assignment4Solver {
    public static void solveInstance(String problemInstance) {
        var Problem = new ProblemInitializer(problemInstance);
        var Solution = new SolutionChecker(Problem.getDistanceMatrix(), Problem.getNodeCosts());
        String problemInstanceName = problemInstance.substring(0, problemInstance.length() - 4);

        var LocalSearch = new LocalSearch(Problem.getDistanceMatrix(), Problem.getNodeCosts());

        var startTime = System.currentTimeMillis();
        var SteepestEdgesRandom = LocalSearch.Solve(200, TypeOfLocalSearch.Steepest, MoveType.EXCHANGE_EDGES, StartingSolution.Random);
        var endTime = System.currentTimeMillis();
        Solution.PerformNExperiments(SteepestEdgesRandom, 200, "SteepestEdgesRandom10NN", problemInstanceName, 4);
        System.out.println("Execution time: " + (endTime - startTime) + "ms");
    }

    public static void solve() {
        SolutionSaver.RemoveFile("output.csv", 4);
        solveInstance("TSPA.csv");
        solveInstance("TSPB.csv");
    }
}