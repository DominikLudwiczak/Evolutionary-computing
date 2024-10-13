package org.example;

import org.example.ProblemDefinition.ProblemInitializer;
import org.example.ProblemDefinition.SolutionChecker;
import org.example.Assignment1.RandomSolution;

public class Main {
    public static void main(String[] args)
    {
        var Problem = new ProblemInitializer();
        Problem.PrintMatrix();
        var Solution = new SolutionChecker(Problem.getDistanceMatrix(), Problem.getNodeCosts());
//        Problem.PrintMatrix();
        var Random = new RandomSolution(Problem.getDistanceMatrix());
        var solution = Random.Solve(200);
        System.out.println(solution);
    }
}