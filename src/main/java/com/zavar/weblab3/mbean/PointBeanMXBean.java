package com.zavar.weblab3.mbean;


public interface PointBeanMXBean {
    long getTotalPointsCount();
    long getFailedPointsCount();
    double getHitsPercent();
    void checkWithoutContext(float x, float y, float r);
}
