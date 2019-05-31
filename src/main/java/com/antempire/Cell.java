package com.antempire;

import java.util.Optional;

public class Cell {

    private final int x;
    private final int y;


    private boolean isProducing = false;
    private Ant ant;

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

    public Ant getAnt() {
        return ant;
    }

    public void setAnt(Ant ant) {
        this.ant = ant;
    }
}
