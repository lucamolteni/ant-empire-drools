package com.antempire;

public class Worker implements Ant {

    @Override
    public String toString() {
        return "Worker";
    }

    @Override
    public boolean isQueen() {
        return false;
    }

    @Override
    public boolean isWorker() {
        return true;
    }
}
