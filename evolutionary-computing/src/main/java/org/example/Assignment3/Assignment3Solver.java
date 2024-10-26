package org.example.Assignment3;

import org.example.ProblemDefinition.ProblemInitializer;
import org.example.ProblemDefinition.SolutionChecker;
import org.example.ProblemDefinition.SolutionSaver;

public class Assignment3Solver {
    public static void solveInstance(String problemInstance) {
        var Problem = new ProblemInitializer(problemInstance);
        var Solution = new SolutionChecker(Problem.getDistanceMatrix(), Problem.getNodeCosts());
        String problemInstanceName = problemInstance.substring(0, problemInstance.length() - 4);

        var LocalSearch = new LocalSearch(Problem.getDistanceMatrix(), Problem.getNodeCosts());

        var startTime = System.currentTimeMillis();
        var GreedyEdgesRandom = LocalSearch.Solve(200, TypeOfLocalSearch.Greedy, MoveType.EXCHANGE_EDGES, StartingSolution.Random);
        var endTime = System.currentTimeMillis();
        Solution.PerformNExperiments(GreedyEdgesRandom, 200, "GreedyEdgesRandom", problemInstanceName, 3);
        System.out.println("Execution time: " + (endTime - startTime) + "ms");

        startTime = System.currentTimeMillis();
        var GreedyEdgesRegret = LocalSearch.Solve(200, TypeOfLocalSearch.Greedy, MoveType.EXCHANGE_EDGES, StartingSolution.RegretWeighted);
        endTime = System.currentTimeMillis();
        Solution.PerformNExperiments(GreedyEdgesRegret, 200, "GreedyEdgesRegret", problemInstanceName, 3);
        System.out.println("Execution time: " + (endTime - startTime) + "ms");

        startTime = System.currentTimeMillis();
        var GreedyNodesRandom = LocalSearch.Solve(200, TypeOfLocalSearch.Greedy, MoveType.SWAP_WITHIN, StartingSolution.Random);
        endTime = System.currentTimeMillis();
        Solution.PerformNExperiments(GreedyNodesRandom, 200, "GreedyNodesRandom", problemInstanceName, 3);
        System.out.println("Execution time: " + (endTime - startTime) + "ms");

        startTime = System.currentTimeMillis();
        var GreedyNodesRegret = LocalSearch.Solve(200, TypeOfLocalSearch.Greedy, MoveType.SWAP_WITHIN, StartingSolution.RegretWeighted);
        endTime = System.currentTimeMillis();
        Solution.PerformNExperiments(GreedyNodesRegret, 200, "GreedyNodesRegret", problemInstanceName, 3);
        System.out.println("Execution time: " + (endTime - startTime) + "ms");

        startTime = System.currentTimeMillis();
        var SteepestEdgesRandom = LocalSearch.Solve(200, TypeOfLocalSearch.Steepest, MoveType.EXCHANGE_EDGES, StartingSolution.Random);
        endTime = System.currentTimeMillis();
        Solution.PerformNExperiments(SteepestEdgesRandom, 200, "SteepestEdgesRandom", problemInstanceName, 3);
        System.out.println("Execution time: " + (endTime - startTime) + "ms");

        startTime = System.currentTimeMillis();
        var SteepestEdgesRegret = LocalSearch.Solve(200, TypeOfLocalSearch.Steepest, MoveType.EXCHANGE_EDGES, StartingSolution.RegretWeighted);
        endTime = System.currentTimeMillis();
        Solution.PerformNExperiments(SteepestEdgesRegret, 200, "SteepestEdgesRegret", problemInstanceName, 3);
        System.out.println("Execution time: " + (endTime - startTime) + "ms");

        startTime = System.currentTimeMillis();
        var SteepestNodesRandom = LocalSearch.Solve(200, TypeOfLocalSearch.Steepest, MoveType.SWAP_WITHIN, StartingSolution.Random);
        endTime = System.currentTimeMillis();
        Solution.PerformNExperiments(SteepestNodesRandom, 200, "SteepestNodesRandom", problemInstanceName, 3);
        System.out.println("Execution time: " + (endTime - startTime) + "ms");

        startTime = System.currentTimeMillis();
        var SteepestNodesRegret = LocalSearch.Solve(200, TypeOfLocalSearch.Steepest, MoveType.SWAP_WITHIN, StartingSolution.RegretWeighted);
        endTime = System.currentTimeMillis();
        Solution.PerformNExperiments(SteepestNodesRegret, 200, "SteepestNodesRegret", problemInstanceName, 3);
        System.out.println("Execution time: " + (endTime - startTime) + "ms");
    }

    public static void solve() {
        SolutionSaver.RemoveFile("output.csv", 3);
        solveInstance("TSPA.csv");
        solveInstance("TSPB.csv");
    }
}
