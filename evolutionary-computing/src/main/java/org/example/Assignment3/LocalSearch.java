package org.example.Assignment3;

import org.example.Assignment1.RandomSolution;
import org.example.Assignment2.RegretMyLifeChoices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LocalSearch {
    private List<List<Integer>> distanceMatrix;
    private List<Integer> nodeCosts;
    private HashMap<String, Move> moves = new HashMap<>();

    public LocalSearch(List<List<Integer>> distanceMatrix, List<Integer> nodeCosts) {
        this.distanceMatrix = distanceMatrix;
        this.nodeCosts = nodeCosts;
    }

    public List<List<Integer>> Solve(int iterations, TypeOfLocalSearch typeOfLocalSearch, MoveType moveType, StartingSolution startingSolution) {
        List<List<Integer>> solutions = new ArrayList<>();
        for (int i = 0; i < iterations; i++) {
            List<Integer> solution = new ArrayList<>();
            switch (startingSolution) {
                case Random -> solution = new RandomSolution(this.distanceMatrix).Solve(1).get(0);
                case RegretWeighted -> {
                    int randomNode = (int) (Math.random() * this.distanceMatrix.size());
                    solution = new RegretMyLifeChoices(this.distanceMatrix, this.nodeCosts, true).GenerateSolution(randomNode);
                }
            }
            System.out.println("Initial solution: " + this.CalculateDistance(solution));
            var newSolution = GenerateSolution(solution, typeOfLocalSearch, moveType);
            solutions.add(newSolution);
        }
        return solutions;
    }

    public List<Integer> GenerateSolution(List<Integer> solution, TypeOfLocalSearch typeOfLocalSearch, MoveType moveType) {
        List<MoveType> moveTypes = List.of(MoveType.CHANGE_WITH_NOT_USED, moveType);
        boolean foundBetterSolution = true;

        while(foundBetterSolution) {
            var minObjectiveChange = 0;
            List<Integer> bestSolution = null;
            boolean breakLoop = false;

            System.out.println(minObjectiveChange);
            for (int i = 0; i < solution.size(); i++) {
                for (int j = i + 1; j < solution.size(); j++) {
                    for (MoveType mType : moveTypes) {
                        if (solution.contains(j) && mType.equals(MoveType.CHANGE_WITH_NOT_USED)) {
                            continue;
                        }
                        Move move;
                        if (this.moves.containsKey(mType + " " + i + " " + j)) {
                            move = this.moves.get(mType + " " + i + " " + j);
                        } else {
                            move = new Move(mType, i, j, this.nodeCosts, this.distanceMatrix);
                            this.moves.put(mType + " " + i + " " + j, move);
                        }
                        var newSolution = move.MakeMove(solution);
                        switch (typeOfLocalSearch) {
                            case Greedy -> {
                                if (move.getObjectiveChange() < 0) {
                                    minObjectiveChange = move.getObjectiveChange();
                                    bestSolution = newSolution;
                                    breakLoop = true;
                                }
                            }
                            case Steepest -> {
                                if (move.getObjectiveChange() < minObjectiveChange) {
                                    minObjectiveChange = move.getObjectiveChange();
                                    bestSolution = newSolution;
                                }
                            }
                        }
                        if(breakLoop) {
                            break;
                        }
                    }
                    if(breakLoop) {
                        break;
                    }
                }
                if(breakLoop) {
                    break;
                }
            }

            System.out.println("Objective change: " + minObjectiveChange);
            System.out.println(solution.equals(bestSolution));
            if(bestSolution == null) {
                foundBetterSolution = false;
            } else {
                solution = bestSolution;
            }
        }
        return solution;
    }

    public int CalculateDistance(List<Integer> solution)
    {
        int distance = nodeCosts.get(solution.get(0));
        for (int i = 0; i < solution.size() - 1; i++) {

            distance += distanceMatrix.get(solution.get(i)).get(solution.get(i + 1));
            distance += nodeCosts.get(solution.get(i + 1));
        }
        distance += distanceMatrix.get(solution.get(solution.size() - 1)).get(solution.get(0));
        return distance;
    }
}

enum TypeOfLocalSearch {
    Greedy,
    Steepest
}

enum StartingSolution {
    Random,
    RegretWeighted
}
