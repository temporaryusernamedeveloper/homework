package com.mycompany.util;

import java.util.ArrayList;
import java.util.List;

public class CachedRouteList {
    private List<Integer> cacheRoutesList = new ArrayList<>();

    public Integer get(Integer currentRoute) {
        if (cacheRoutesList.contains(currentRoute)) {
            return cacheRoutesList.get(currentRoute);
        } else {
           return add(currentRoute);
        }
    }

    private Integer add(Integer route) {
        cacheRoutesList.add(route);
        return route;
    }
}
