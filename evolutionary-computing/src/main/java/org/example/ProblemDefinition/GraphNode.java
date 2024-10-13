package org.example.ProblemDefinition;

public class GraphNode {
    private int x;
    private int y;
    private int cost;

    public int getY() {
        return y;
    }

    public GraphNode(int x, int y, int cost) {
        this.x = x;
        this.y = y;
        this.cost = cost;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
