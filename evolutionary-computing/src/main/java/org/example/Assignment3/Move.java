package org.example.Assignment3;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Move {
    private MoveType moveType;
    private int node1Idx;
    private int node2Idx;
    private int objectiveChange = 0;
    private List<Integer> nodeCosts;
    private List<List<Integer>> distanceMatrix;

    public Move (MoveType moveType,
                    int node1Idx,
                    int node2Idx,
                    List<Integer> nodeCosts,
                    List<List<Integer>> distanceMatrix){
        this.moveType = moveType;
        this.node1Idx = node1Idx;
        this.node2Idx = node2Idx;
        this.nodeCosts = nodeCosts;
        this.distanceMatrix = distanceMatrix;
    }

    public List<Integer> MakeMove(List<Integer> solution){
        objectiveChange = 0;
        switch (moveType){
            case SWAP_WITHIN:
                return SwapWithin(solution);
            case EXCHANGE_EDGES:
                return ExchangeEdges(solution);
            case CHANGE_WITH_NOT_USED:
                return ChangeWithNotUsed(solution);
            default:
                return solution;
        }
    }

    public int SimulateMove(List<Integer> solution){
        objectiveChange = 0;
        switch (moveType){
            case SWAP_WITHIN:
                return SimulateSwapWithin(solution);
            case EXCHANGE_EDGES:
                return SimulateExchangeEdges(solution);
            case CHANGE_WITH_NOT_USED:
                return SimulateChangeWithNotUsed(solution);
            default:
                return 0;
        }
    }

    private int SimulateSwapWithin(List<Integer> solution){
        int prevNode1Idx = node1Idx == 0 ? solution.size() - 1 : node1Idx - 1;
        int nextNode1Idx = node1Idx == solution.size() - 1 ? 0 : node1Idx + 1;

        int prevNode2Idx = node2Idx == 0 ? solution.size() - 1 : node2Idx - 1;
        int nextNode2Idx = node2Idx == solution.size() - 1 ? 0 : node2Idx + 1;

        objectiveChange -= distanceMatrix.get(solution.get(prevNode1Idx)).get(solution.get(node1Idx))
                - distanceMatrix.get(solution.get(node1Idx)).get(solution.get(nextNode1Idx))
                - distanceMatrix.get(solution.get(prevNode2Idx)).get(solution.get(node2Idx))
                - distanceMatrix.get(solution.get(node2Idx)).get(solution.get(nextNode2Idx))
                + distanceMatrix.get(solution.get(prevNode1Idx)).get(solution.get(node2Idx))
                + distanceMatrix.get(solution.get(node2Idx)).get(solution.get(nextNode1Idx))
                + distanceMatrix.get(solution.get(prevNode2Idx)).get(solution.get(node1Idx))
                + distanceMatrix.get(solution.get(node1Idx)).get(solution.get(nextNode2Idx));

        return objectiveChange;
    }

    private List<Integer> SwapWithin(List<Integer> solution){
        int prevNode1Idx = node1Idx == 0 ? solution.size() - 1 : node1Idx - 1;
        int nextNode1Idx = node1Idx == solution.size() - 1 ? 0 : node1Idx + 1;

        int prevNode2Idx = node2Idx == 0 ? solution.size() - 1 : node2Idx - 1;
        int nextNode2Idx = node2Idx == solution.size() - 1 ? 0 : node2Idx + 1;

        objectiveChange -= distanceMatrix.get(solution.get(prevNode1Idx)).get(solution.get(node1Idx))
                        - distanceMatrix.get(solution.get(node1Idx)).get(solution.get(nextNode1Idx))
                        - distanceMatrix.get(solution.get(prevNode2Idx)).get(solution.get(node2Idx))
                        - distanceMatrix.get(solution.get(node2Idx)).get(solution.get(nextNode2Idx))
                        + distanceMatrix.get(solution.get(prevNode1Idx)).get(solution.get(node2Idx))
                        + distanceMatrix.get(solution.get(node2Idx)).get(solution.get(nextNode1Idx))
                        + distanceMatrix.get(solution.get(prevNode2Idx)).get(solution.get(node1Idx))
                        + distanceMatrix.get(solution.get(node1Idx)).get(solution.get(nextNode2Idx));

        int temp = solution.get(node1Idx);
        solution.set(node1Idx, solution.get(node2Idx));
        solution.set(node2Idx, temp);
        return solution;
    }

    private int SimulateExchangeEdges (List<Integer> solution){
        int nextNode1Idx = node1Idx == solution.size() - 1 ? 0 : node1Idx + 1;
        int nextNode2Idx = node2Idx == solution.size() - 1 ? 0 : node2Idx + 1;

        objectiveChange -= distanceMatrix.get(solution.get(node1Idx)).get(solution.get(nextNode1Idx))
                - distanceMatrix.get(solution.get(node2Idx)).get(solution.get(nextNode2Idx))
                + distanceMatrix.get(solution.get(node1Idx)).get(solution.get(node2Idx))
                + distanceMatrix.get(solution.get(nextNode1Idx)).get(solution.get(nextNode2Idx));

        return objectiveChange;
    }

    private List<Integer> ExchangeEdges (List<Integer> solution){
        int nextNode1Idx = node1Idx == solution.size() - 1 ? 0 : node1Idx + 1;
        int nextNode2Idx = node2Idx == solution.size() - 1 ? 0 : node2Idx + 1;

        objectiveChange -= distanceMatrix.get(solution.get(node1Idx)).get(solution.get(nextNode1Idx))
                        - distanceMatrix.get(solution.get(node2Idx)).get(solution.get(nextNode2Idx))
                        + distanceMatrix.get(solution.get(node1Idx)).get(solution.get(node2Idx))
                        + distanceMatrix.get(solution.get(nextNode1Idx)).get(solution.get(nextNode2Idx));

        int smallerIdx = node1Idx < node2Idx ? node1Idx : node2Idx;
        int largerIdx = node1Idx < node2Idx ? node2Idx : node1Idx;

        var firstPart = solution.subList(0, smallerIdx + 1);
        var secondPart = solution.subList(smallerIdx + 1, largerIdx + 1);
        var thirdPart = solution.subList(largerIdx + 1, solution.size());

        List<Integer> tempsolution = new ArrayList<>();
        tempsolution.addAll(firstPart);
        Collections.reverse(secondPart);
        tempsolution.addAll(secondPart);
        tempsolution.addAll(thirdPart);

        return tempsolution;
    }

    private int SimulateChangeWithNotUsed(List<Integer> solution){
        int prevNode1Idx = node1Idx == 0 ? solution.size() - 1 : node1Idx - 1;
        int nextNode1Idx = node1Idx == solution.size() - 1 ? 0 : node1Idx + 1;

        objectiveChange -= distanceMatrix.get(solution.get(prevNode1Idx)).get(solution.get(node1Idx))
                - distanceMatrix.get(solution.get(node1Idx)).get(solution.get(nextNode1Idx))
                - nodeCosts.get(solution.get(node1Idx))
                + distanceMatrix.get(solution.get(prevNode1Idx)).get(node2Idx)
                + distanceMatrix.get(node2Idx).get(solution.get(nextNode1Idx))
                + nodeCosts.get(node2Idx);

        return objectiveChange;
    }
    private List<Integer> ChangeWithNotUsed(List<Integer> solution){
        int prevNode1Idx = node1Idx == 0 ? solution.size() - 1 : node1Idx - 1;
        int nextNode1Idx = node1Idx == solution.size() - 1 ? 0 : node1Idx + 1;

        objectiveChange -= distanceMatrix.get(solution.get(prevNode1Idx)).get(solution.get(node1Idx))
                        - distanceMatrix.get(solution.get(node1Idx)).get(solution.get(nextNode1Idx))
                        - nodeCosts.get(solution.get(node1Idx))
                        + distanceMatrix.get(solution.get(prevNode1Idx)).get(node2Idx)
                        + distanceMatrix.get(node2Idx).get(solution.get(nextNode1Idx))
                        + nodeCosts.get(node2Idx);

        solution.set(node1Idx, node2Idx);
        return solution;
    }

    public int getObjectiveChange(){
        return objectiveChange;
    }
}
