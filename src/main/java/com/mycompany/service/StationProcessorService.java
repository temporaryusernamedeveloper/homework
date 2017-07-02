package com.mycompany.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StationProcessorService {
    @Autowired
    FileReaderService fileReaderService;
    private Map<Integer, List<Integer>> stationRouteMap = new HashMap<>();

    @PostConstruct
    public void initStationRoute() {
        stationRouteMap = fileReaderService.readStationRouteMapFromFile();
    }

    public boolean isStationConnected(Integer depSid, Integer arrSid) {
        List<Integer> listOfRoutesForDep = stationRouteMap.get(depSid);
        List<Integer> listOfRoutesForArr = stationRouteMap.get(arrSid);

        return !(listOfRoutesForDep == null || listOfRoutesForArr == null)
                && !Collections.disjoint(listOfRoutesForDep, listOfRoutesForArr);
    }
}
