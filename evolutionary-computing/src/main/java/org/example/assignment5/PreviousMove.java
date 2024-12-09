package org.example.assignment5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PreviousMove implements Comparable<PreviousMove> {
    private Edge edge1;
    private Edge edge2;
    private Edge newEdge1;
    private Edge newEdge2;
    private int objectiveChange;
    private boolean isEdge = true;

    public PreviousMove(Edge edge1, Edge edge2, Edge newEdge1, Edge newEdge2, boolean isEdge) {
        this.edge1 = edge1;
        this.edge2 = edge2;
        this.newEdge1 = newEdge1;
        this.newEdge2 = newEdge2;
        this.isEdge = isEdge;
        this.objectiveChange = calculateObjective();
    }

    private int calculateObjective()
    {
        if (isEdge){
            return newEdge1.getEdgeCost() + newEdge2.getEdgeCost() - edge1.getEdgeCost() - edge2.getEdgeCost();
        }
        else {
            return newEdge1.getEdgeCost() + newEdge2.getEdgeCost() + newEdge1.getEndNodeCost() - edge1.getEdgeCost() - edge2.getEdgeCost() - edge1.getEndNodeCost();
        }
    }
    public int getObjectiveChange() {
        return objectiveChange;
    }
    public void setObjectiveChange(int objectiveChange) {
        this.objectiveChange = objectiveChange;
    }
    @Override
    public int compareTo(PreviousMove o) {
        return Integer.compare(this.objectiveChange, o.objectiveChange);
    }
    public List<Integer> applyMove(List<Integer> solution)
    {
        if (isEdge){
            return applyEdgeMove(solution);
        }
        else{
            return applyNodeMove(solution);
        }

    }

    public List<Integer> applyNodeMove(List<Integer> solution){
        solution.set(solution.indexOf(edge1.getEnd()), newEdge1.getEnd());
        return solution;
    }

    public List<Integer> applyEdgeMove(List<Integer> solution)
    {
        int smallerIdx = solution.indexOf(edge1.getStart());
        int largerIdx = solution.indexOf(edge2.getStart());

        if (solution.indexOf(edge1.getStart()) > solution.indexOf(edge2.getStart())){
            smallerIdx = solution.indexOf(edge2.getStart());
            largerIdx = solution.indexOf(edge1.getStart());
        }

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

    public Edge getEdge1() {
        return edge1;
    }

    public Edge getEdge2() {
        return edge2;
    }

    public Edge getNewEdge1() {
        return newEdge1;
    }

    public Edge getNewEdge2() {
        return newEdge2;
    }
}
