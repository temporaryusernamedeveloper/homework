package com.mycompany;

import com.mycompany.dto.RouteStatus;
import com.mycompany.service.StationProcessorService;
import com.mycompany.service.WatcherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ModelService {
    @Value("${file.path}")
    private String filePath;
    private static final Logger logger = LoggerFactory.getLogger(ModelService.class);

    @Autowired
    StationProcessorService stationProcessorService;

    @RequestMapping("/api/direct")
    public RouteStatus isInRoute(@RequestParam(value = "dep_sid", required = true) Integer depSid, @RequestParam(value = "arr_sid", required = true) Integer arrSid) {
        return new RouteStatus(depSid, arrSid, stationProcessorService.isStationConnected(depSid, arrSid));
    }
}
