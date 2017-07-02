package com.mycompany.dto;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RouteStatus {
    @JsonProperty("dep_sid")
    private Integer depSid;
    @JsonProperty("arr_sid")
    private Integer arrSid;
    @JsonProperty("direct_bus_route")
    private Boolean idDirectBusRoute;

    public RouteStatus(Integer depSid, Integer arrSid, Boolean idDirectBusRoute) {
        this.depSid = depSid;
        this.arrSid = arrSid;
        this.idDirectBusRoute = idDirectBusRoute;
    }

    public Integer getDepSid() {
        return depSid;
    }

    public void setDepSid(Integer depSid) {
        this.depSid = depSid;
    }

    public Integer getArrSid() {
        return arrSid;
    }

    public void setArrSid(Integer arrSid) {
        this.arrSid = arrSid;
    }

    public Boolean getIdDirectBusRoute() {
        return idDirectBusRoute;
    }

    public void setIdDirectBusRoute(Boolean idDirectBusRoute) {
        this.idDirectBusRoute = idDirectBusRoute;
    }
}
