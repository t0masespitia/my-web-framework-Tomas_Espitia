package edu.escuela.web.core;

import java.util.List;
import java.util.Map;

public class Request {

    private final String path;
    private final Map<String, List<String>> queryParams;

    public Request(String path, Map<String, List<String>> queryParams) {
        this.path = path;
        this.queryParams = queryParams;
    }

    public String getPath() {
        return path;
    }

    public String getValues(String key) {
        List<String> values = queryParams.get(key);
        if (values == null || values.isEmpty()) return "";
        return String.join(",", values);
    }
}