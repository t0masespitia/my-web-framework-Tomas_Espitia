package edu.escuela.web.core;


import java.util.HashMap;
import java.util.Map;

public class Router {
    private final Map<String, RouteHandler> getRoutes = new HashMap<>();
    private String basePrefix = "";

    public void setBasePrefix(String basePrefix) {
        this.basePrefix = (basePrefix == null) ? "" : basePrefix;
    }

    public void get(String path, RouteHandler handler) {
        getRoutes.put(basePrefix + path, handler);
    }

    public RouteHandler matchGet(String fullPath) {
        return getRoutes.get(fullPath);
    }
}