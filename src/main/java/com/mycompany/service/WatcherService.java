package com.mycompany.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.*;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

@Component
public class WatcherService {
    @Value("${file.path}")
    private String filePath;
    private final Logger logger = LoggerFactory.getLogger(WatcherService.class);
    @Autowired
    StationProcessorService stationProcessorService;

    public void watchForDirectory() {
        try {
            logger.info("Watch Service registered for dir: " + filePath);
            Path fullPath = Paths.get(filePath);
            String file = fullPath.getFileName().toString();
            Path dir = fullPath.getParent();

            WatchService watcher = createWatcherService(dir);
            watchForFile(file, watcher);
        } catch (IOException ex) {
            logger.error("Problem with watching file", ex);
        }
    }

    private WatchService createWatcherService(Path dir) throws IOException {
        WatchService watcher = FileSystems.getDefault().newWatchService();
        dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
        return watcher;
    }

    private void watchForFile(String file, WatchService watcher) {
        while (true) {
            WatchKey key;
            try {
                key = watcher.take();
            } catch (InterruptedException ex) {
                return;
            }

            for (WatchEvent<?> event : key.pollEvents()) {
                WatchEvent.Kind<?> kind = event.kind();

                @SuppressWarnings("unchecked")
                WatchEvent<Path> ev = (WatchEvent<Path>) event;
                Path fileName = ev.context();

                System.out.println(kind.name() + ": " + fileName);

                if (kind == ENTRY_MODIFY &&
                        fileName.toString().equals(file)) {
                    logger.info("My source file has changed!!!");
                    stationProcessorService.initStationRoute();
                }
            }
            boolean valid = key.reset();
            if (!valid) {
                break;
            }
        }
    }
}
