package org.example;


import org.example.ProblemDefinition.ProblemInitializer;
import org.example.ProblemDefinition.SolutionChecker;

import java.util.List;

public class Main {
    public static void main(String[] args)
    {
        var Problem = new ProblemInitializer();
        Problem.PrintMatrix();
        var Solution = new SolutionChecker(Problem.getDistanceMatrix(), Problem.getNodeCosts());
    }
}