package com.mycompany.service;

import com.mycompany.util.CachedRouteList;
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
        BufferedReader reader = readFile();
        return buildStationRouteMap(reader);
    }

    private Map<Integer, List<Integer>> buildStationRouteMap(BufferedReader reader) {
        CachedRouteList cachedRouteList = new CachedRouteList();
        Map<Integer, List<Integer>> stationRouteMap = new HashMap<>();

        Scanner scanner = new Scanner(reader);
        Integer numberOfBusRoutes = Integer.valueOf(scanner.nextLine());
        try {
            while (scanner.hasNextLine()) {
                processLine(cachedRouteList, stationRouteMap, scanner.nextLine());
            }
        } catch (Exception e) {
            logger.error("Can not process string line", e);
        }
        scanner.close();
        return stationRouteMap;
    }

    private void processLine(CachedRouteList cachedRouteList, Map<Integer, List<Integer>> stationRouteMap, String line) {
        List<Integer> lineList = Arrays.stream(line.split("\\s+")).map(Integer::valueOf).collect(Collectors.toList());
        ListIterator<Integer> iterator = lineList.listIterator();

        // add caching for saving memory
        Integer currentRoute = cachedRouteList.get(iterator.next());
        while (iterator.hasNext()) {
            Integer stationName = iterator.next();
            List<Integer> listOfRoutes = stationRouteMap.get(stationName);
            if (listOfRoutes == null) {
                listOfRoutes = new ArrayList<>();
            }
            listOfRoutes.add(currentRoute);
            stationRouteMap.put(stationName, listOfRoutes);
        }
    }


    private BufferedReader readFile() {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Can not find file");
        }
        return new BufferedReader(new InputStreamReader(inputStream));
    }
}
