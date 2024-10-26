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

        var GreedyEdgesRandom = LocalSearch.Solve(200, TypeOfLocalSearch.Greedy, MoveType.EXCHANGE_EDGES, StartingSolution.Random);
        Solution.PerformNExperiments(GreedyEdgesRandom, 200, "GreedyIntraRandom", problemInstanceName, 3);

        var GreedyEdgesRegret = LocalSearch.Solve(200, TypeOfLocalSearch.Greedy, MoveType.EXCHANGE_EDGES, StartingSolution.Random);
        Solution.PerformNExperiments(GreedyEdgesRegret, 200, "GreedyIntraRegret", problemInstanceName, 3);

        var GreedyNodesRandom = LocalSearch.Solve(200, TypeOfLocalSearch.Greedy, MoveType.SWAP_WITHIN, StartingSolution.Random);
        Solution.PerformNExperiments(GreedyNodesRandom, 200, "GreedyInterRandom", problemInstanceName, 3);

        var GreedyNodesRegret = LocalSearch.Solve(200, TypeOfLocalSearch.Greedy, MoveType.SWAP_WITHIN, StartingSolution.Random);
        Solution.PerformNExperiments(GreedyNodesRegret, 200, "GreedyInterRegret", problemInstanceName, 3);

        var SteepestEdgesRandom = LocalSearch.Solve(200, TypeOfLocalSearch.Greedy, MoveType.EXCHANGE_EDGES, StartingSolution.Random);
        Solution.PerformNExperiments(SteepestEdgesRandom, 200, "SteepestIntraRandom", problemInstanceName, 3);

        var SteepestEdgesRegret = LocalSearch.Solve(200, TypeOfLocalSearch.Greedy, MoveType.EXCHANGE_EDGES, StartingSolution.Random);
        Solution.PerformNExperiments(SteepestEdgesRegret, 200, "SteepestIntraRegret", problemInstanceName, 3);

        var SteepestNodesRandom = LocalSearch.Solve(200, TypeOfLocalSearch.Greedy, MoveType.SWAP_WITHIN, StartingSolution.Random);
        Solution.PerformNExperiments(SteepestNodesRandom, 200, "SteepestInterRandom", problemInstanceName, 3);

        var SteepestNodesRegret = LocalSearch.Solve(200, TypeOfLocalSearch.Greedy, MoveType.SWAP_WITHIN, StartingSolution.Random);
        Solution.PerformNExperiments(SteepestNodesRegret, 200, "SteepestInterRegret", problemInstanceName, 3);
    }

    public static void solve() {
        SolutionSaver.RemoveFile("output.csv", 3);
        solveInstance("TSPA.csv");
        solveInstance("TSPB.csv");
    }
}
