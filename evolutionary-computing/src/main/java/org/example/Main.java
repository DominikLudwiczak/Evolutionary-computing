package org.example;

import org.example.Assignemnt10.Assignment10Solver;
import org.example.Assignment1.Assignment1Solver;
import org.example.Assignment2.Assignment2Solver;
import org.example.Assignment3.Assignment3Solver;
import org.example.Assignment3.Move;
import org.example.Assignment3.MoveType;
import org.example.Assignment4.Assignment4Solver;
import org.example.Assignment5.Assignment5Solver;
import org.example.Assignment6.Assignment6Solver;
import org.example.Assignment7.Assignment7Solver;
import org.example.Assignment8.Assignment8Solver;
import org.example.Assignment9.Assignment9Solver;

public class Main {
        public static void main(String[] args) {
                var startTime = System.currentTimeMillis();
                Assignment10Solver.solve();
                var endTime = System.currentTimeMillis();
                System.out.println("Execution time: " + (endTime - startTime) + "ms");
        }
}