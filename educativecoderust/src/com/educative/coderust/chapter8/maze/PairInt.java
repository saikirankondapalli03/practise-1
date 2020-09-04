package com.educative.coderust.chapter8.maze;

public class PairInt {
    
    
    private int x, y;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public PairInt(int x, int y) {
        super();
        this.x = x;
        this.y = y;
    }

    public PairInt copy() {
        return new PairInt(x, y);
    }

    @Override
    public String toString() {
        return "[" + x + "," + y + "]";
    }

}