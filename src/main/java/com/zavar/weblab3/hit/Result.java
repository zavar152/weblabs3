package com.zavar.weblab3.hit;

import javax.persistence.*;

@Entity
@Table(name = "POINTS")
public class Result {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "RES")
    private String result;

    @Embedded
    private Point point;

    public Result() {

    }

    public Result(boolean result, Point point) {
        this.result = result ? "Да" : "Нет";
        this.point = point;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResult() {
        return result;
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
