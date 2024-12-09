package org.example.assignment5;

public class Edge {
    private int start;
    private int end;
    private int edgeCost;
    private int startNodeCost;
    private int endNodeCost;

    public Edge(int start, int end, int edgeCost, int startNodeCost, int endNodeCost) {
        this.start = start;
        this.end = end;
        this.edgeCost = edgeCost;
        this.startNodeCost = startNodeCost;
        this.endNodeCost = endNodeCost;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getEdgeCost() {
        return edgeCost;
    }

    public void setEdgeCost(int edgeCost) {
        this.edgeCost = edgeCost;
    }

    public int getStartNodeCost() {
        return startNodeCost;
    }

    public void setStartNodeCost(int startNodeCost) {
        this.startNodeCost = startNodeCost;
    }

    public int getEndNodeCost() {
        return endNodeCost;
    }

    public void setEndNodeCost(int endNodeCost) {
        this.endNodeCost = endNodeCost;
    }
}
