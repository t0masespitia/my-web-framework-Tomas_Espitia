package edu.escuela.web.core;

import java.io.IOException;
import java.io.InputStream;

public class StaticFileService {

    private String staticFolder = "/webroot";

    public void setStaticFolder(String folder) {
        if (folder == null || folder.isBlank()) staticFolder = "/webroot";
        else staticFolder = folder.startsWith("/") ? folder : "/" + folder;
    }

    public byte[] load(String requestPath) throws IOException {
        if (requestPath.equals("/")) requestPath = "/index.html";
        String resourcePath = staticFolder + requestPath;

        try (InputStream in = getClass().getResourceAsStream(resourcePath)) {
            if (in == null) return null;
            return in.readAllBytes();
        }
    }

    public String contentType(String path) {
        String p = path.toLowerCase();
        if (p.endsWith(".html")) return "text/html; charset=utf-8";
        if (p.endsWith(".css")) return "text/css; charset=utf-8";
        if (p.endsWith(".js")) return "application/javascript; charset=utf-8";
        if (p.endsWith(".png")) return "image/png";
        if (p.endsWith(".jpg") || p.endsWith(".jpeg")) return "image/jpeg";
        if (p.endsWith(".gif")) return "image/gif";
        return "application/octet-stream";
    }
}