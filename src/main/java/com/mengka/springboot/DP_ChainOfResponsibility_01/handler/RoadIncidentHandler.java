package com.mengka.springboot.DP_ChainOfResponsibility_01.handler;

import com.mengka.springboot.DP_ChainOfResponsibility_01.RoadIncident;

public interface RoadIncidentHandler {

    void handle(RoadIncident roadIncident);

    boolean withinBounds(double lat, double lng);

}
