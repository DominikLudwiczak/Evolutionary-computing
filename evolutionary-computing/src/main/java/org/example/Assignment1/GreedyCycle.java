package org.example.Assignment1;

import java.util.ArrayList;
import java.util.List;

public class GreedyCycle {
    private List<List<Integer>> distanceMatrix;
    private List<Integer> nodeCosts;

    public GreedyCycle(List<List<Integer>> distanceMatrix, List<Integer> nodeCosts) {
        this.distanceMatrix = distanceMatrix;
        this.nodeCosts = nodeCosts;
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
        int[] objective = {nodeCosts.get(startingNode)};
        solution.add(startingNode);
        while (solution.size() < distanceMatrix.size() / 2) {
            solution = FindAnyNearestNeighbor(solution, objective);
        }

        return solution;
    }

    private List<Integer> FindAnyNearestNeighbor(List<Integer> visitedNodes, int[] objective) {
        int tempObjective = objective[0];
        int addingNode = -1;
        int afterNode = -1;
        int minObjective = Integer.MAX_VALUE;
        for (int i = 0; i < distanceMatrix.size(); i++) {
            if (!visitedNodes.contains(i)) {
                int prevNode = -1;

                for(Integer node : visitedNodes) {
                    int distanceToNext = distanceMatrix.get(i).get(node);
                    int newObjective = tempObjective + distanceToNext + nodeCosts.get(i);

                    if(prevNode != -1) {
                        int distanceToPrev = distanceMatrix.get(prevNode).get(i);
                        int distanceInCycle = 0;
                        if(!node.equals(visitedNodes.get(visitedNodes.size() - 1))) {
                            distanceInCycle = distanceMatrix.get(prevNode).get(node);
                        }
                        newObjective += distanceToPrev - distanceInCycle;
                    } else {
                        if(visitedNodes.size() > 1) {
                            int closingCycleDistance = distanceMatrix.get(i).get(visitedNodes.get(visitedNodes.size() - 1));
                            if(visitedNodes.size() > 2) {
                                closingCycleDistance -= distanceMatrix.get(visitedNodes.get(visitedNodes.size() - 1)).get(visitedNodes.get(0));
                            }
                            newObjective += closingCycleDistance;
                        }
                    }

                    if (newObjective < minObjective) {
                        addingNode = i;
                        afterNode = prevNode;
                        minObjective = newObjective;
                    }
                    prevNode = node;
                }
            }
        }
        objective[0] = minObjective;
        return AddNode(visitedNodes, addingNode, afterNode);
    }

    private List<Integer> AddNode(List<Integer> visitedNodes, int addingNode, int afterNode) {
        List<Integer> newVisitedNodes = new ArrayList<>();
        if(afterNode == -1) {
            newVisitedNodes.add(addingNode);
        }

        for(Integer node : visitedNodes) {
            newVisitedNodes.add(node);
            if(node.equals(afterNode)) {
                newVisitedNodes.add(addingNode);
            }
        }
        return newVisitedNodes;
    }
}
