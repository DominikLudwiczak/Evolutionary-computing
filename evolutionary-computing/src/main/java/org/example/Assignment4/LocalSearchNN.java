package org.example.Assignment4;

import org.apache.commons.lang3.tuple.Pair;
import org.example.Assignment1.RandomSolution;
import org.example.Assignment3.Move;
import org.example.Assignment3.MoveType;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.example.Assignment3.MoveType.CANDIDATE_EDGE;

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
                if(this.TenNNs.containsKey(i)) {
                    nodes= this.TenNNs.get(i);
                } else {
                    nodes = Find10NN(i, solution);
                    this.TenNNs.put(i, nodes);
                }
                for (int j : nodes) {
                    Move move;
                    if (this.moves.containsKey(i + " " + j)) {
                        move = this.moves.get(i + " " + j);
                    } else {
                        move = new Move(CANDIDATE_EDGE, i, j, this.nodeCosts, this.distanceMatrix);
                        this.moves.put(i + " " + j, move);
                    }

                    var objectiveChange = move.SimulateMove(solution);

                    if (objectiveChange < minObjectiveChange) {
                        minObjectiveChange = objectiveChange;
                        bestMove = move;
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

    public List<Integer> Find10NN(int currentNode, List<Integer> solution) {
        List<Pair<Integer, Integer>> distances = new ArrayList<>();
        var nodes = IntStream.range(0, this.nodeCosts.size()).filter(x -> !solution.contains(x)).boxed().collect(Collectors.toList());
        for (int i : nodes) {
            distances.add(Pair.of(i, distanceMatrix.get(currentNode).get(i)));
        }

        distances.sort(Comparator.comparingInt(Pair::getRight));
        return distances.subList(1, 11).stream().map(Pair::getLeft).toList();
    }
}
