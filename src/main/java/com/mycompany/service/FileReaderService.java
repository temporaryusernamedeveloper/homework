package com.mycompany.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FileReaderService {
    @Value("${file.path}")
    private String filePath;
    private final Logger logger = LoggerFactory.getLogger(FileReaderService.class);

    public Map<Integer, List<Integer>> readStationRouteMapFromFile() {
        Map<Integer, List<Integer>> stationRouteMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)))) {
            stationRouteMap = buildStationRouteMap(reader);
        } catch (Exception e) {
            throw new RuntimeException("Cannot find file:" + filePath);
        }
        return stationRouteMap;
    }

    private Map<Integer, List<Integer>> buildStationRouteMap(BufferedReader reader) {
        Map<Integer, List<Integer>> stationRouteMap = new HashMap<>();
        reader.lines().skip(1).forEach(line -> processLine(stationRouteMap, line));
        return stationRouteMap;
    }

    private void processLine(Map<Integer, List<Integer>> stationRouteMap, String line) {
        List<Integer> splittedLine = Arrays.stream(line.split("\\s+"))
                .map(Integer::valueOf)
                .collect(Collectors.toList());

        splittedLine.stream().skip(1).forEach(station -> {
            if (!stationRouteMap.containsKey(station)) {
                stationRouteMap.put(station, new ArrayList<>());
            }
            stationRouteMap.get(station).add(splittedLine.get(0));
        });
    }
}
