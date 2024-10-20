package org.example;

import org.example.Assignment1.Assignment1Solver;
import org.example.Assignment2.Assignment2Solver;
import org.example.Assignment2.RegretMyLifeChoices;
import org.example.ProblemDefinition.ProblemInitializer;
import org.example.ProblemDefinition.SolutionChecker;

public class Main {
        public static void main(String[] args) {
                var startTime = System.currentTimeMillis();
                Assignment2Solver.solve();
                var endTime = System.currentTimeMillis();
                System.out.println("Execution time: " + (endTime - startTime) + "ms");
//                var Problem = new ProblemInitializer("TSPA.csv");
//                var regret = new RegretMyLifeChoices(Problem.getDistanceMatrix(), Problem.getNodeCosts(), true);
//                var solution = regret.GenerateSolution(0);
//                var checker = new SolutionChecker(Problem.getDistanceMatrix(), Problem.getNodeCosts());
//                var distance = checker.CalculateDistance(solution);
//                System.out.println("Distance: " + distance);
        }
}