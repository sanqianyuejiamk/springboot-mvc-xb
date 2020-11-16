package com.mengka.springboot.DP_ChainOfResponsibility_01;

/**
 *  某个城市（经度、纬度）
 *
 */
public class RoadIncident {

    private final double latitude;//纬度
    private final double longtitude;//经度
    private final String report;

    public RoadIncident(double latitude, double longtitude, String report) {
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.report = report;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public String getReport() {
        return report;
    }
}
