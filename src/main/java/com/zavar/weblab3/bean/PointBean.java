package com.zavar.weblab3.bean;

import com.zavar.weblab3.hit.Point;
import com.zavar.weblab3.hit.Result;
import org.primefaces.PrimeFaces;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

@ManagedBean
@ApplicationScoped
public class PointBean implements Serializable {

    private static final long serialVersionUID = 2041275512219239990L;
    private ArrayList<Result> list = new ArrayList<Result>();

    private float x = 0.0F;
    private float y = 0.0F;
    private float r = 0.85F;

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getR() {
        return r;
    }

    public void setR(float r) {
        this.r = r;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void reset() {
        x = 0;
        y = 0;
        r = 0.85F;
    }

    public void onParameterChange() {
        PrimeFaces.current().executeScript("drawArea(" + r + ")");
    }

    public void check() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String,String> params = context.getExternalContext().getRequestParameterMap();
        String xP = params.get("x");
        String yP = params.get("y");
        if(yP == null || xP == null) {
            list.add(new Result(true, new Point(x, y, r)));
            PrimeFaces.current().executeScript("drawDotByClick('#37f863'," + x + ", " + y + ")");
        } else {
            list.add(new Result(true, new Point(Float.parseFloat(xP), Float.parseFloat(yP), r)));
            PrimeFaces.current().executeScript("drawDotByClick('#37f863'," + xP + ", " + yP + ")");
        }

    }

    public ArrayList<Result> getList() {
        return list;
    }
}
