package org.example.Assignment4;

import org.apache.commons.lang3.tuple.Pair;
import org.example.Assignment1.RandomSolution;
import org.example.Assignment3.Move;
import org.example.Assignment3.MoveType;
import org.example.Main;

import java.util.*;
import java.util.stream.IntStream;

import static org.example.Assignment3.MoveType.CANDIDATE_EDGE;
import static org.example.Assignment3.MoveType.EXCHANGE_EDGES;

public class LocalSearchNN {
    private List<List<Integer>> distanceMatrix;
    private List<Integer> nodeCosts;
    private HashMap<String, Move> moves = new HashMap<>();
    private HashMap<Integer, List<Integer>> TenNNs = new HashMap<>();

    public LocalSearchNN(List<List<Integer>> distanceMatrix, List<Integer> nodeCosts) {
        this.distanceMatrix = distanceMatrix;
        this.nodeCosts = nodeCosts;
    }

    public List<List<Integer>> Solve(int iterations) {
        List<List<Integer>> solutions = new ArrayList<>();
        for (int i = 0; i < iterations; i++) {
            List<Integer> solution =  new RandomSolution(this.distanceMatrix).Solve(1).get(0);
            var newSolution = GenerateSolution(solution);
            solutions.add(newSolution);
        }
        return solutions;
    }

    public List<Integer> GenerateSolution(List<Integer> solution) {
        boolean foundBetterSolution = true;

        while(foundBetterSolution) {
            var minObjectiveChange = 0;
            Move bestMove = null;

            for (int i = 0; i < solution.size() - 1; i++) {
                List<Integer> nodes;
                int currentNode = solution.get(i);
                if (this.TenNNs.containsKey(currentNode)) {
                    nodes = this.TenNNs.get(currentNode);
                } else {
                    nodes = Find10NN(currentNode);
                    this.TenNNs.put(currentNode, nodes);
                }
                for (int j : nodes) {
                    MoveType mType = CANDIDATE_EDGE;
                    Move move;

                    if (solution.contains(j)) {
                        mType = EXCHANGE_EDGES;
                        var targetNode = solution.indexOf(j);
                        if (Math.abs(i - targetNode) == 1 || Math.abs(i - targetNode) == solution.size() - 1) {
                            continue;
                        }
                        Move move2;
                        if (this.moves.containsKey(mType + " " + i + " " + targetNode)) {
                            move = this.moves.get(mType + " " + i + " " + targetNode);
                        } else {
                            move = new Move(mType, i, targetNode, this.nodeCosts, this.distanceMatrix);
                            this.moves.put(mType + " " + i + " " + targetNode, move);
                        }

                        var objectiveChange = move.SimulateMove(solution);
                        int prevI = i - 1 >= 0 ? i - 1 : solution.size() - 1;
                        int prevTargetNode = targetNode - 1 >= 0 ? targetNode - 1 : solution.size() - 1;
                        if (this.moves.containsKey(mType + " " + prevI + " " + prevTargetNode)) {
                            move2 = this.moves.get(mType + " " + prevI + " " + prevTargetNode);
                        } else {
                            move2 = new Move(mType, prevI, prevTargetNode, this.nodeCosts, this.distanceMatrix);
                            this.moves.put(mType + " " + prevI + " " + prevTargetNode, move);
                        }

                        var objectiveChange2 = move2.SimulateMove(solution);

                        if (objectiveChange > objectiveChange2) {
                            move = move2;
                            objectiveChange = objectiveChange2;
                        }

                        if (objectiveChange < minObjectiveChange) {
                            minObjectiveChange = objectiveChange;
                            bestMove = move;
                        }
                    }
                    else{
                        if (this.moves.containsKey(mType + " " + i + " " + j)) {
                            move = this.moves.get(mType + " " + i + " " + j);
                        } else {
                            move = new Move(mType, i, j, this.nodeCosts, this.distanceMatrix);
                            this.moves.put(mType + " " + i + " " + j, move);
                        }

                        var objectiveChange = move.SimulateMove(solution);

                        if (objectiveChange < minObjectiveChange) {
                            minObjectiveChange = objectiveChange;
                            bestMove = move;
                        }
                    }
                }
            }

            if(bestMove == null) {
                foundBetterSolution = false;
            } else {
                solution = bestMove.MakeMove(solution);
            }
        }
        return solution;
    }

    public List<Integer> Find10NN(int currentNode) {
        List<Pair<Integer, Integer>> distances = new ArrayList<>();
        var nodes = IntStream.range(0, this.nodeCosts.size()).boxed().toList();

        for (int i : nodes) {
            if (i == currentNode) {
                continue;
            }
            distances.add(Pair.of(i, distanceMatrix.get(currentNode).get(i) + nodeCosts.get(i)));
        }

        distances.sort(Comparator.comparingInt(Pair::getRight));
        return distances.subList(0, 10).stream().map(Pair::getLeft).toList();
    }
}
