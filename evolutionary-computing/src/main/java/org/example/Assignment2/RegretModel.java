package org.example.Assignment2;

public class RegretModel {
    private int bestCost = Integer.MAX_VALUE;
    private int secondBestCost = Integer.MAX_VALUE;
    private int bestNodePosition = -1;

    public int getRegret(){
        if (bestCost == Integer.MAX_VALUE || secondBestCost == Integer.MAX_VALUE){
            return -1;
        }
        return secondBestCost - bestCost;
    }

    public float getRegretCostRatio(){
        if (bestCost == Integer.MAX_VALUE || secondBestCost == Integer.MAX_VALUE){
            return - bestCost * 0.5f;
        }
        return (secondBestCost - bestCost) * 0.5f - bestCost * 0.5f;
    }
    public int getBestCost() {
        return bestCost;
    }

    public void setBestCost(int bestCost) {
        this.bestCost = bestCost;
    }

    public int getSecondBestCost() {
        return secondBestCost;
    }

    public void setSecondBestCost(int secondBestCost) {
        this.secondBestCost = secondBestCost;
    }

    public int getBestNodePosition() {
        return bestNodePosition;
    }

    public void setBestNodePosition(int bestNodePosition) {
        this.bestNodePosition = bestNodePosition;
    }
}
