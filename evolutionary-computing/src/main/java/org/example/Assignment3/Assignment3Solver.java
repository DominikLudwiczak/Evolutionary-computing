package org.example.Assignment3;

import org.example.ProblemDefinition.SolutionSaver;

public class Assignment3Solver {
    public static void solveInstance(String problemInstance) {
    }

    public static void solve() {
        SolutionSaver.RemoveFile("output.csv", 3);
        solveInstance("TSPA.csv");
        solveInstance("TSPB.csv");
    }
}
