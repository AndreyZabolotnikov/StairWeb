package ru.duxa.stairweb.stair;

import java.util.HashMap;
import java.util.Map;

public class Stair{
    private int upperFloor;
    private int downFloor;
    private int widthStair;
    private Map<Integer, Integer> stepHeights = new HashMap<>();
    private Map<Integer, Integer> stepLengths = new HashMap<>();

    public int getUpperFloor() {
        return upperFloor;
    }

    public void setUpperFloor(int upperFloor) {
        this.upperFloor = upperFloor;
    }

    public int getDownFloor() {
        return downFloor;
    }

    public void setDownFloor(int downFloor) {
        this.downFloor = downFloor;
    }

    public int getWidthStair() {
        return widthStair;
    }

    public void setWidthStair(int widthStair) {
        this.widthStair = widthStair;
    }

    public void setStepHeight (int numberStep, int heightStep) {
        stepHeights.put(numberStep, heightStep);
    }

    public void setStepLength (int numberStep, int lengthStep) {
        stepLengths.put(numberStep, lengthStep);
    }

    public int getStepHeight(int numberStep) {
        return stepHeights.get(numberStep);
    }

    public int getStepLength(int numberStep) {
        return stepLengths.get(numberStep);
    }

}
