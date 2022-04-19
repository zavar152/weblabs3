package com.zavar.weblab3.bean;

import com.zavar.weblab3.dao.ResultDAO;
import com.zavar.weblab3.hit.Point;
import com.zavar.weblab3.hit.Result;
import org.primefaces.PrimeFaces;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.persistence.PersistenceException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

@ManagedBean
@ApplicationScoped
public class PointBean implements Serializable {

    private static final long serialVersionUID = 2041275512219239990L;
    private ArrayList<Result> list;
    private final ResultDAO resultDAO = new ResultDAO();

    private float x = 0.0F;
    private float y = 0.0F;
    private float r = 0.85F;
sdfsdfsdfsdf
    @PostConstruct
    public void init() {
        try {sdafdsafasdfasfd
            resultDAO.open();
            list = (ArrayList<Result>) resultDAO.getAll();
        } catch (PersistenceException e) {
            resultDAO.setConnected(false);
            resultDAO.close();
            PrimeFaces.current().executeScript("alert('Проблемы с БД, попробуйте позже')");
            list = new ArrayList<Result>();
        }
    }

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

    public void check(){
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String,String> params = context.getExternalContext().getRequestParameterMap();
        String xP = params.get("x");
        String yP = params.get("y");
        float tX;
        float tY;
        boolean temp;
        if(yP == null || xP == null) {
            tX = x;
            tY = y;
        } else {
            tX = Float.parseFloat(xP);
            tY = Float.parseFloat(yP);
        }
        temp = isIn(tX, tY, r);
        Result result = new Result(temp, new Point(tX, tY, r));
        try {
            if(!resultDAO.isConnected()) {
                resultDAO.open();
                resultDAO.setConnected(true);
                PrimeFaces.current().executeScript("clean()");
                PrimeFaces.current().executeScript("draw(" + r + ")");
                list = (ArrayList<Result>) resultDAO.getAll();
                for (Result res: list) {
                    PrimeFaces.current().executeScript("drawDotByClick('" + (res.getResult().equals("Да") ? "#37f863" : "crimson") + "'," + res.getPoint().getX() + ", " + res.getPoint().getY() + ")");
                }
            }
            resultDAO.send(result);
            list.add(result);
            PrimeFaces.current().executeScript("drawDotByClick('" + (temp ? "#37f863" : "crimson") + "'," + tX + ", " + tY + ")");
        } catch (PersistenceException e) {
            resultDAO.setConnected(false);
            resultDAO.close();
            PrimeFaces.current().executeScript("alert('Проблемы с БД, попробуйте позже')");
        }
    }


    private boolean isIn(float x, float y, float r) {
        return Math.pow(x/r/7, 2) * Math.sqrt((Math.abs(Math.abs(x/r)-3))/(Math.abs(x/r)-3)) + Math.pow(y/r/3, 2)*Math.sqrt((Math.abs(y/r + 3* Math.sqrt(33) / 7))/(y/r + 3* Math.sqrt(33) / 7)) - 1 <= 0 |
                (Math.abs(x/r/2) - ((3*Math.sqrt(33)-7)*x/r*x/r)/112 - 3 + Math.sqrt(1 - Math.pow(Math.abs(Math.abs(x/r) - 2) - 1, 2)) - y/r <= 0 & y/r <= 0 & -3 <= y/r & -4 <= x/r & x/r <= 4) |
                (-Math.abs(x/r) / 2 - 3 * Math.sqrt(10) / 7 * Math.sqrt(4 - Math.pow(Math.abs(x/r) - 1, 2)) - y/r + 6 * Math.sqrt(10) / 7 + 1.5 >= 0 & y/r > 0)
                | (1.5 + 3*Math.abs(x/r) + 0.75 - y/r >= 0 & y/r <= 4 & y/r > 0) & (9 - 8*Math.abs(x/r) - y/r >= 0 & y/r <= 4 & y/r > 0);
    }

    public ArrayList<Result> getList() {
        return list;
    }
}
