package com.zavar.weblab3.bean;

import com.zavar.weblab3.dao.ResultDAO;
import com.zavar.weblab3.hit.Result;
import com.zavar.weblab3.mbean.PointHitsPercentMXBean;
import org.primefaces.PrimeFaces;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.PersistenceException;
import java.util.ArrayList;

public class PointHitsPercent implements PointHitsPercentMXBean {

    private final ResultDAO resultDAO = new ResultDAO();

    public PointHitsPercent() {
        try {
            resultDAO.open();
        } catch (PersistenceException e) {
            resultDAO.setConnected(false);
            resultDAO.close();
            PrimeFaces.current().executeScript("alert('Проблемы с БД, попробуйте позже')");
        }
    }

    @PreDestroy
    public void close() {
        resultDAO.close();
    }

    @Override
    public double getHitsPercent() {
        return (1 - ((double)resultDAO.getAll().stream().filter(result -> result.getResult().equals("Нет")).count()/resultDAO.getAll().size())) * 100;
    }
}
