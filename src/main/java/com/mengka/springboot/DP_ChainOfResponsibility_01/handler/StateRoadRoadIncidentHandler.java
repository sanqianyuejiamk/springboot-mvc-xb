package com.mengka.springboot.DP_ChainOfResponsibility_01.handler;

import com.mengka.springboot.DP_ChainOfResponsibility_01.RoadIncident;
import lombok.extern.slf4j.Slf4j;

/**
 *  省
 *
 */
@Slf4j
public class StateRoadRoadIncidentHandler implements RoadIncidentHandler {

    @Override
    public void handle(RoadIncident roadIncident) {
        //经纬度不在该省范围内
        if(!withinBounds(roadIncident.getLatitude(),roadIncident.getLatitude())) {
            throw new IllegalArgumentException("Incident cannot be handled by state. No successor available");
        }

        /**
         * Handle the incident
         */
        log.info("sheng...");
    }

    @Override
    public boolean withinBounds(double lat, double lng) {

        /**
         * Reverse geolocation, look up by name, radius based distance etc.
         */

        return true;
    }
}
