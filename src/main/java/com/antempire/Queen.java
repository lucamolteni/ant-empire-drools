package com.antempire;

public class Queen implements Ant {
    @Override
    public String toString() {
        return "Queen";
    }

    @Override
    public boolean isQueen() {
        return true;
    }

    @Override
    public boolean isWorker() {
        return false;
    }
}
