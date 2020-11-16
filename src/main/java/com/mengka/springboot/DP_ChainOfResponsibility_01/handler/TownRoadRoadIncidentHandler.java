package com.mengka.springboot.DP_ChainOfResponsibility_01.handler;

import com.mengka.springboot.DP_ChainOfResponsibility_01.RoadIncident;
import lombok.extern.slf4j.Slf4j;

/**
 *  镇
 *
 */
@Slf4j
public class TownRoadRoadIncidentHandler implements RoadIncidentHandler {

    private final RoadIncidentHandler successor;

    public TownRoadRoadIncidentHandler(final RoadIncidentHandler successor) {
        this.successor = successor;
    }

    @Override
    public void handle(RoadIncident roadIncident) {
        //经纬度不在该镇范围内
        if(!withinBounds(roadIncident.getLatitude(),roadIncident.getLongtitude())) {
            successor.handle(roadIncident);
        } else {
            /**
             * Handle the incident
             */
            log.info("zhen...");
        }
    }

    @Override
    public boolean withinBounds(double lat, double lng) {
        /**
         * Reverse geolocation, look up by name, radius based distance etc.
         */
        return false;
    }
}
