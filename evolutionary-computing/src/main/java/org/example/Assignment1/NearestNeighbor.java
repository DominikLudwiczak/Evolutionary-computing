package org.example.Assignment1;

import java.util.ArrayList;
import java.util.List;

public class NearestNeighbor {
    private List<List<Integer>> distanceMatrix;
    private List<Integer> nodeCosts;

    public NearestNeighbor(List<List<Integer>> distanceMatrix, List<Integer> nodeCosts) {
        this.distanceMatrix = distanceMatrix;
        this.nodeCosts = nodeCosts;
    }

    public List<Integer> Solve(int startingNode) {
        List<Integer> solution = new ArrayList<>();
        solution.add(startingNode);
        int currentNode = startingNode;
        while (solution.size() < distanceMatrix.size()) {
            int nextNode = FindNearestNeighbor(currentNode, solution);
            solution.add(nextNode);
            currentNode = nextNode;
        }
        return solution;
    }

    private int FindNearestNeighbor(int currentNode, List<Integer> visitedNodes) {
        int nearestNode = -1;
        int minDistance = Integer.MAX_VALUE;
        for (int i = 0; i < distanceMatrix.size(); i++) {
            if (!visitedNodes.contains(i) && distanceMatrix.get(currentNode).get(i) < minDistance) {
                nearestNode = i;
                minDistance = distanceMatrix.get(currentNode).get(i);
            }
        }
        return nearestNode;
    }
}
