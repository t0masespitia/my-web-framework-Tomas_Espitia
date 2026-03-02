package edu.escuela.web.core;

import java.util.LinkedHashMap;
import java.util.Map;

public class Response {

    private int status = 200;
    private final Map<String, String> headers = new LinkedHashMap<>();

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void header(String key, String value) {
        headers.put(key, value);
    }
}