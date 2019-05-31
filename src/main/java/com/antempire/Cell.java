package com.antempire;

public class Cell {

    private boolean isProducing = false;
    private final int x;
    private final int y;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isProducing() {
        return isProducing;
    }

    public void setProducing(boolean producing) {
        isProducing = producing;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
