package org.example.Assignment2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class RegretMyLifeChoices {
    private List<List<Integer>> distanceMatrix;
    private List<Integer> nodeCosts;

    public HashSet<Integer> getTakenNodes() {
        return takenNodes;
    }

    public void setTakenNodes(HashSet<Integer> takenNodes) {
        this.takenNodes = takenNodes;
    }

    private HashSet<Integer> takenNodes;
    private boolean weighted = false;

    public RegretMyLifeChoices(List<List<Integer>> distanceMatrix, List<Integer> nodeCosts, boolean weighted) {
        this.distanceMatrix = distanceMatrix;
        this.nodeCosts = nodeCosts;
        this.weighted = weighted;
    }

    public List<List<Integer>> Solve(int iterations) {
        List<List<Integer>> solutions = new ArrayList<>();
        for (int i = 0; i < iterations; i++) {
            List<Integer> solution = GenerateSolution(i);
            solutions.add(solution);
        }
        return solutions;
    }

    public List<Integer> GenerateSolution(int startingNode) {
        List<Integer> solution = new ArrayList<>();
        int[] objective = {0};
        solution.add(startingNode);
        objective[0] += nodeCosts.get(startingNode);
        takenNodes = new HashSet<>(solution);
        while (solution.size() < distanceMatrix.size() / 2) {
            if (solution.size() <= 2){
                solution = findFirst2(solution, objective);
            } else {
                solution = FindRegret2(solution, objective);
            }
        }
        return solution;
    }

    public List<Integer> FindRegret2(List<Integer> solution,
                                      int[] objective) {
        int addingNode = -1;
        var bestRegret = new RegretModel();
        for (int i = 0; i < distanceMatrix.size(); i++) {
            if (takenNodes.contains(i)) {
                continue;
            }
            int bestCostforNode = Integer.MAX_VALUE;
            int secondBestCostforNode = Integer.MAX_VALUE;
            int bestPosition = -1;
            for (int j = 0; j < solution.size(); j++) {
                int next = j < solution.size() - 1 ? j + 1 : 0;
                int cost = nodeCosts.get(i) + distanceMatrix.get(i).get(solution.get(j))
                        + distanceMatrix.get(solution.get(next)).get(i)
                        - distanceMatrix.get(solution.get(j)).get(solution.get(next));
                if (cost < bestCostforNode) {
                    secondBestCostforNode = bestCostforNode;
                    bestCostforNode = cost;
                    bestPosition = j;
                } else if (cost < secondBestCostforNode) {
                    secondBestCostforNode = cost;
                }
            }
            var regretModel = new RegretModel();
            regretModel.setBestCost(bestCostforNode);
            regretModel.setSecondBestCost(secondBestCostforNode);
            regretModel.setBestNodePosition(bestPosition);
            if (weighted) {
                if (regretModel.getRegretCostRatio() > bestRegret.getRegretCostRatio()) {
                    bestRegret = regretModel;
                    addingNode = i;
                }
            }
            else {
                if (regretModel.getRegret() > bestRegret.getRegret()) {
                    bestRegret = regretModel;
                    addingNode = i;
                }
            }

        }
        solution.add(bestRegret.getBestNodePosition() + 1, addingNode);
        objective[0] += bestRegret.getBestCost();
        takenNodes.add(addingNode);
        return solution;
    }

    public List<Integer> findFirst2(List<Integer> solution, int[] objective) {
        int minCost = Integer.MAX_VALUE;
        int addingNode = -1;
        for (int i = 0; i < nodeCosts.size(); i++) {
            if (takenNodes.contains(i)) {
                continue;
            }
            int cost = solution.size() == 1
                    ? nodeCosts.get(i) + distanceMatrix.get(solution.get(0)).get(i)
                    : nodeCosts.get(i) + distanceMatrix.get(solution.get(0)).get(i)
                            + distanceMatrix.get(i).get(solution.get(1));
            if (cost < minCost) {
                minCost = cost;
                addingNode = i;
            }
        }
        solution.add(addingNode);
        objective[0] += minCost;
        takenNodes.add(addingNode);
        return solution;
    }
}
