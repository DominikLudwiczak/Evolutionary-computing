package org.example;

import org.example.Assignment1.Assignment1Solver;
import org.example.Assignment2.Assignment2Solver;
import org.example.Assignment3.Assignment3Solver;
import org.example.Assignment4.Assignment4Solver;

public class Main {
        public static void main(String[] args) {
                var startTime = System.currentTimeMillis();
                Assignment4Solver.solve();
                var endTime = System.currentTimeMillis();
                System.out.println("Execution time: " + (endTime - startTime) + "ms");
        }
}