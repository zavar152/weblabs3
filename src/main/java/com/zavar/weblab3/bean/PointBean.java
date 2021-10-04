package com.zavar.weblab3.bean;

import com.zavar.weblab3.hit.Point;
import com.zavar.weblab3.hit.Result;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.util.ArrayList;

@ManagedBean
@ApplicationScoped
public class PointBean implements Serializable {

    private static final long serialVersionUID = 2041275512219239990L;
    private ArrayList<Result> list = new ArrayList<Result>();

    private int x;
    private float y;
    private float r;

    public int getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getR() {
        return r;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setR(float r) {
        this.r = r;
    }

    public void reset() {
        x = 0;
        y = 0;
        r = 1;
    }

    public void check() {
        list.add(new Result(true, new Point(x, y, r)));
    }

    public ArrayList<Result> getList() {
        return list;
    }
}
