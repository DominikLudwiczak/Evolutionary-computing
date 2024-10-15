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
        int beforeNode = -1;
        int minObjective = Integer.MAX_VALUE;
        for (int i = 0; i < distanceMatrix.size(); i++) {
            if (!visitedNodes.contains(i)) {
                int prevNode = -1;
                for(Integer node : visitedNodes) {
                    int distanceToNext = distanceMatrix.get(node).get(i);
                    if(prevNode == -1) {
                        int closingCycleDistance = distanceMatrix.get(i).get(visitedNodes.get(visitedNodes.size()-1));
                        closingCycleDistance -= distanceMatrix.get(visitedNodes.get(visitedNodes.size()-1)).get(visitedNodes.get(0));
                        int newObjective = tempObjective + distanceToNext + nodeCosts.get(i) + closingCycleDistance;
                        if (newObjective < minObjective) {
                            addingNode = i;
                            minObjective = newObjective;
                        }
                    } else {
                        int distanceToPrev = distanceMatrix.get(prevNode).get(i);
                        int distaneInCycle = distanceMatrix.get(prevNode).get(node);
                        int newObjective = tempObjective + distanceToPrev + distanceToNext + nodeCosts.get(i) - distaneInCycle;
                        if (newObjective < minObjective) {
                            addingNode = i;
                            beforeNode = prevNode;
                            minObjective = newObjective;
                        }
                    }
                    prevNode = node;
                }
            }
        }
        objective[0] = minObjective;
        return AddNode(visitedNodes, addingNode, beforeNode);
    }

    private List<Integer> AddNode(List<Integer> visitedNodes, int addingNode, int beforeNode) {
        List<Integer> newVisitedNodes = new ArrayList<>();
        if(beforeNode == -1) {
            newVisitedNodes.add(addingNode);
        }

        for(Integer node : visitedNodes) {
            newVisitedNodes.add(node);
            if(node == beforeNode) {
                newVisitedNodes.add(addingNode);
            }
        }
        return newVisitedNodes;
    }
}
