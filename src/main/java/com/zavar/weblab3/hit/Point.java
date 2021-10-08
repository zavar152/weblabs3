package com.zavar.weblab3.hit;

public class Point {
    private float x;
    private float y;
    private float r;

    public Point(float x, float y, float r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }

    public float getR() {
        return r;
    }

    public void setR(float r) {
        this.r = r;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
