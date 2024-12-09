package org.example;

import org.example.Assignment1.Assignment1Solver;
import org.example.Assignment2.Assignment2Solver;
import org.example.Assignment3.Assignment3Solver;
import org.example.Assignment3.Move;
import org.example.Assignment3.MoveType;
import org.example.Assignment4.Assignment4Solver;
import org.example.ProblemDefinition.ProblemInitializer;
import org.example.ProblemDefinition.SolutionChecker;
import org.example.assignment5.Assignment5Solver;
import org.example.assignment5.Edge;
import org.example.assignment5.ListOfMoves;
import org.example.assignment5.PreviousMove;

import java.util.List;
import java.util.Arrays;


public class Main {
        public static void main(String[] args) {
                var startTime = System.currentTimeMillis();
                Assignment4Solver.solve();
                var endTime = System.currentTimeMillis();
                System.out.println("Execution time: " + (endTime - startTime) + "ms");

//                var problem = new ProblemInitializer("TSPA.csv");
//                var nc = problem.getNodeCosts();
//                var dm = problem.getDistanceMatrix();
//
//                var checker = new SolutionChecker(dm, nc);

//                var lom = new ListOfMoves(dm, nc);
//                var test = lom.Solve(1);
//                var solution = test.get(0);

//                var solution = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
//                System.out.println(checker.CalculateDistance(solution));
//
//                Move move = new Move(MoveType.CHANGE_WITH_NOT_USED, 1, 10, nc, dm);
//                System.out.println(move.SimulateMove(solution));
//
//                var edge1 = new Edge(0, 1, dm.get(0).get(1), nc.get(0), nc.get(1));
//                var edge2 = new Edge(1, 2, dm.get(1).get(2), nc.get(1), nc.get(2));
//
//                var newEdge1 = new Edge(0, 10, dm.get(0).get(10), nc.get(0), nc.get(10));
//                var newEdge2 = new Edge(10, 2, dm.get(10).get(2), nc.get(10), nc.get(2));
//
//                var pm = new PreviousMove(edge1, edge2, newEdge1, newEdge2, false);
//
//                System.out.println(pm.getObjectiveChange());
//
//                solution = pm.applyMove(solution);
//
//                System.out.println(checker.CalculateDistance(solution));
        }
}