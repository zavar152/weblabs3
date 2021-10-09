package com.zavar.weblab3.hit;

public class Result {
    private String result;
    private Point point;
    private boolean resultBoolean;

    public Result(boolean result, Point point) {
        resultBoolean = result;
        this.result = result ? "Да" : "Нет";
        this.point = point;
    }

    public String getResult() {
        return result;
    }

    public boolean getResultAsBoolean() {
        return resultBoolean;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public String getStyle() {
        return result.equals("Да") ? "yes" : "no";
    }
}
