package com.zavar.weblab3.bean;

import com.zavar.weblab3.dao.ResultDAO;
import com.zavar.weblab3.hit.Point;
import com.zavar.weblab3.hit.Result;
import com.zavar.weblab3.mbean.PointBeanMXBean;
import org.primefaces.PrimeFaces;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.management.*;
import javax.persistence.PersistenceException;
import java.io.Serializable;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Map;

@ManagedBean
@ViewScoped
public class PointBean extends NotificationBroadcasterSupport implements Serializable, PointBeanMXBean {

    private static final long serialVersionUID = 2041275512219239990L;
    private ArrayList<Result> list;
    private final ResultDAO resultDAO = new ResultDAO();

    private float x = 0.0F;
    private float y = 0.0F;
    private float r = 0.85F;
    private long sequenceNumber = 1;

    public PointBean() {
        try {
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

    @Override
    public void checkWithoutContext(float x, float y, float r) {
        boolean temp = isIn(x, y, r);
        Result result = new Result(temp, new Point(x, y, r));
        resultDAO.send(result);
        list.add(result);

        if(Math.abs(x) >= 9 || Math.abs(y) >= 9)
            sendNotification(new Notification("Out of area", this.getClass().getName(), sequenceNumber++, "This point (" + x + "; " + y + ") is out of area"));
    }

    public void check() {
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

    @Override
    public long getTotalPointsCount() {
        return resultDAO.getAll().size();
    }

    @Override
    public long getFailedPointsCount() {
        return resultDAO.getAll().stream().filter(result -> result.getResult().equals("Нет")).count();
    }

    @Override
    public double getHitsPercent() {
        return (1 - (double)getFailedPointsCount()/getTotalPointsCount()) * 100;
    }

    @Override
    public MBeanNotificationInfo[] getNotificationInfo() {
        String[] types = new String[]{
                AttributeChangeNotification.ATTRIBUTE_CHANGE
        };

        String name = AttributeChangeNotification.class.getName();
        String description = "Point is out of area";
        MBeanNotificationInfo info =
                new MBeanNotificationInfo(types, name, description);
        return new MBeanNotificationInfo[]{info};
    }

}
