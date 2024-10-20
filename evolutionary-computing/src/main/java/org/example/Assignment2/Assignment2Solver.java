package org.example.Assignment2;

import org.example.Assignment1.GreedyCycle;
import org.example.Assignment1.NearestNeighbor;
import org.example.Assignment1.NearestNeighborAny;
import org.example.Assignment1.RandomSolution;
import org.example.ProblemDefinition.ProblemInitializer;
import org.example.ProblemDefinition.SolutionChecker;
import org.example.ProblemDefinition.SolutionSaver;

public class Assignment2Solver {
    public static void solveInstance(String problemInstance) {
        var Problem = new ProblemInitializer(problemInstance);
        var Solution = new SolutionChecker(Problem.getDistanceMatrix(), Problem.getNodeCosts());
        String problemInstanceName = problemInstance.substring(0, problemInstance.length() - 4);

        // random solution
        var Regret = new RegretMyLifeChoices(Problem.getDistanceMatrix(), Problem.getNodeCosts(), false);
        var regretSolution = Regret.Solve(200);
        Solution.PerformNExperiments(regretSolution, 200, "Regret2", problemInstanceName, 2);

        var WeightedRegret = new RegretMyLifeChoices(Problem.getDistanceMatrix(), Problem.getNodeCosts(), true);
        var weightedRegretSolution = WeightedRegret.Solve(200);
        Solution.PerformNExperiments(weightedRegretSolution, 200, "WeightedRegret2", problemInstanceName, 2);
    }

    public static void solve() {
        SolutionSaver.RemoveFile("output.csv", 2);
        solveInstance("TSPA.csv");
        solveInstance("TSPB.csv");
    }
}
