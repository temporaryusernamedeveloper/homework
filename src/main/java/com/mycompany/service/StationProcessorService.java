package com.mycompany.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;

@Service
public class StationProcessorService {
    @Autowired
    FileReaderService fileReaderService;
    private Map<Integer, Set<Integer>> stationRouteMap = new HashMap<>();

    @PostConstruct
    public void initStationRoute() {
        stationRouteMap = fileReaderService.readStationRouteMapFromFile();
    }

    public boolean isStationConnected(Integer depSid, Integer arrSid) {
        Set<Integer> setOfRoutesForDep = stationRouteMap.get(depSid);
        Set<Integer> setOfRoutesForArr = stationRouteMap.get(arrSid);

        return !(setOfRoutesForDep == null || setOfRoutesForArr == null)
                && setOfRoutesForDep.stream().anyMatch(setOfRoutesForArr::contains);
    }
}
