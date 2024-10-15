package org.example;

import org.example.Assignment1.Assignment1Solver;

public class Main {
        public static void main(String[] args) {
                var startTime = System.currentTimeMillis();
                Assignment1Solver.solve();
                var endTime = System.currentTimeMillis();
                System.out.println("Execution time: " + (endTime - startTime) + "ms");
        }
}