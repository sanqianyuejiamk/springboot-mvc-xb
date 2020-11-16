package com.mengka.springboot.DP_ChainOfResponsibility_01;

import com.mengka.springboot.DP_ChainOfResponsibility_01.handler.MunicipalityRoadRoadIncidentHandler;
import com.mengka.springboot.DP_ChainOfResponsibility_01.handler.StateRoadRoadIncidentHandler;
import com.mengka.springboot.DP_ChainOfResponsibility_01.handler.TownRoadRoadIncidentHandler;

/**
 *  责任链模式（Chain of Responsibility Pattern）
 * https://www.runoob.com/design-pattern/chain-of-responsibility-pattern.html
 *
 *  责任链模式(Chain of Responsibility Pattern), 是行为型设计模式之一。
 *
 *   这种模型结构有点类似现实生活中铁链，由一个个铁环首尾相接构成一条链，如果这种结构用在编程领域，则每个节点可以看做一个对象，
 *  每个对象有不同的处理逻辑，将一个请求从链的首端发出，沿着链的路径依次传递每个节点对象，直到有对象处理这个请求为止，
 *  我们将这样一种模式称为责任链模式。
 *
 */
public class Taa {

    public static void main(String[] args) {
        StateRoadRoadIncidentHandler state = new StateRoadRoadIncidentHandler();
        MunicipalityRoadRoadIncidentHandler municipality = new MunicipalityRoadRoadIncidentHandler(state);
        TownRoadRoadIncidentHandler firstTown = new TownRoadRoadIncidentHandler(municipality);
        TownRoadRoadIncidentHandler secondTown = new TownRoadRoadIncidentHandler(state);

        RoadIncident roadIncident = new RoadIncident(0d,0d,"Something happened");
        firstTown.handle(roadIncident);
        secondTown.handle(roadIncident);
    }

}
