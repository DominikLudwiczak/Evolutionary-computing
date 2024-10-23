package org.example;

import org.example.Assignment1.Assignment1Solver;
import org.example.Assignment2.Assignment2Solver;
import org.example.Assignment2.RegretMyLifeChoices;
import org.example.Assignment3.Move;
import org.example.Assignment3.MoveType;
import org.example.ProblemDefinition.ProblemInitializer;
import org.example.ProblemDefinition.SolutionChecker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
        public static void main(String[] args) {
//                var startTime = System.currentTimeMillis();
//                Assignment2Solver.solve();
//                var endTime = System.currentTimeMillis();
//                System.out.println("Execution time: " + (endTime - startTime) + "ms");
                var Problem = new ProblemInitializer("TSPA.csv");
                List<Integer> solution = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6));
                var move = new Move(
                        MoveType.EXCHANGE_EDGES,
                        2, 6,
                        Problem.getNodeCosts(),
                        Problem.getDistanceMatrix());
                var newSolution = move.MakeMove(solution);
                System.out.println(newSolution);
                System.out.println("Objective change: " + move.getObjectiveChange());
        }
}