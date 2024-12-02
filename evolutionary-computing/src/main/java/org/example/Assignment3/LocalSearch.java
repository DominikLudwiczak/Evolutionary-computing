package org.example.Assignment3;

import org.apache.commons.lang3.tuple.Pair;
import org.example.Assignment1.RandomSolution;
import org.example.Assignment2.RegretMyLifeChoices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
            var newSolution = GenerateSolution(solution, typeOfLocalSearch, moveType).getKey();
            solutions.add(newSolution);
        }
        return solutions;
    }

    public Pair<List<Integer>, Integer> GenerateSolution(List<Integer> solution, TypeOfLocalSearch typeOfLocalSearch, MoveType moveType) {
        List<MoveType> moveTypes = new ArrayList<>(List.of(MoveType.CHANGE_WITH_NOT_USED, moveType));
        boolean foundBetterSolution = true;
        var AllObjectiveChange = 0;

        while(foundBetterSolution) {
            var minObjectiveChange = 0;
            Move bestMove = null;
            boolean breakLoop = false;

            var solutionIndexes = new ArrayList<>(IntStream.range(0, solution.size() - 1).boxed().toList());
            java.util.Collections.shuffle(solutionIndexes);
            for (int i : solutionIndexes) {
                java.util.Collections.shuffle(moveTypes);
                for (MoveType mType : moveTypes) {
                    var nodes = IntStream.range(i+1, solution.size()).boxed().collect(Collectors.toList());
                    if(mType == MoveType.CHANGE_WITH_NOT_USED) {
                        List<Integer> finalSolution = solution;
                        nodes = IntStream.range(0, this.nodeCosts.size()).filter(x -> !finalSolution.contains(x)).boxed().collect(Collectors.toList());
                    }
                    java.util.Collections.shuffle(nodes);
                    for (int j : nodes) {
                        Move move;
                        if (this.moves.containsKey(mType + " " + i + " " + j)) {
                            move = this.moves.get(mType + " " + i + " " + j);
                        } else {
                            move = new Move(mType, i, j, this.nodeCosts, this.distanceMatrix);
                            this.moves.put(mType + " " + i + " " + j, move);
                        }

                        var objectiveChange = move.SimulateMove(solution);
                        switch (typeOfLocalSearch) {
                            case Greedy -> {
                                if (objectiveChange < 0) {
                                    minObjectiveChange = objectiveChange;
                                    bestMove = move;
                                    breakLoop = true;
                                }
                            }
                            case Steepest -> {
                                if (objectiveChange < minObjectiveChange) {
                                    minObjectiveChange = objectiveChange;
                                    bestMove = move;
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

            if(bestMove == null) {
                foundBetterSolution = false;
            } else {
                solution = bestMove.MakeMove(solution);
                AllObjectiveChange += minObjectiveChange;
            }
        }
        return Pair.of(solution, AllObjectiveChange);
    }
}
