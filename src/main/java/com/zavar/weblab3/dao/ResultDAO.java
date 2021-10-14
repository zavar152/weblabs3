package com.zavar.weblab3.dao;

import com.zavar.weblab3.hit.Result;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class ResultDAO {
    private EntityManager entityManager;
    private boolean connected = true;

    public void open() {
        entityManager = Persistence.createEntityManagerFactory( "hibernate" ).createEntityManager();
    }

    public void close() {
        if(entityManager != null)
            entityManager.close();
    }

    public void send(Result result) {
        entityManager.getTransaction().begin();
        entityManager.persist(result);
        entityManager.flush();
        entityManager.getTransaction().commit();
    }

    public List<Result> getAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Result> criteriaQuery = cb.createQuery(Result.class);
        Root<Result> root = criteriaQuery.from(Result.class);
        CriteriaQuery<Result> all = criteriaQuery.select(root);
        TypedQuery<Result>  allQuery = entityManager.createQuery(all);
        return allQuery.getResultList();
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }
}
