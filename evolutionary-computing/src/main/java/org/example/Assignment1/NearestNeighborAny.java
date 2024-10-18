package org.example.Assignment1;

import java.util.ArrayList;
import java.util.List;

public class NearestNeighborAny {
    private List<List<Integer>> distanceMatrix;
    private List<Integer> nodeCosts;

    public NearestNeighborAny(List<List<Integer>> distanceMatrix, List<Integer> nodeCosts) {
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

                    if(prevNode != -1 && !node.equals(visitedNodes.get(visitedNodes.size() - 1))) {
                        int distanceToPrev = distanceMatrix.get(prevNode).get(i);
                        int distanceInCycle = distanceMatrix.get(prevNode).get(node);
                        newObjective += distanceToPrev - distanceInCycle;
                    }

                    if (newObjective < minObjective) {
                        addingNode = i;
                        afterNode = prevNode;
                        minObjective = newObjective;

                        if(prevNode != -1 && node.equals(visitedNodes.get(visitedNodes.size() - 1))) {
                            afterNode = node;
                        }
                    }
                    prevNode = node;
                }

                if(visitedNodes.size() > 1) {
                    int newObjective = tempObjective + distanceMatrix.get(i).get(visitedNodes.get(visitedNodes.size()-1)) + nodeCosts.get(i) + distanceMatrix.get(i).get(visitedNodes.get(visitedNodes.size()-2)) - distanceMatrix.get(visitedNodes.get(visitedNodes.size()-2)).get(visitedNodes.get(visitedNodes.size()-1));
                    if(newObjective < minObjective) {
                        addingNode = i;
                        afterNode = visitedNodes.get(visitedNodes.size()-2);
                        minObjective = newObjective;
                    }
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
