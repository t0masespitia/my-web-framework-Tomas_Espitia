package edu.escuela.web.core;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class HttpServer {

    private final Router router = new Router();
    private final StaticFileService staticFiles = new StaticFileService();

    public void staticfiles(String folder) {
        staticFiles.setStaticFolder(folder);
    }

    public void setBasePrefix(String prefix) {
        router.setBasePrefix(prefix);
    }

    public void get(String path, RouteHandler handler) {
        router.get(path, handler);
    }

    public void start(int port) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server running on http://localhost:" + port);

            while (true) {
                try (Socket client = serverSocket.accept()) {
                    handle(client);
                } catch (Exception e) {
                    System.err.println("Error: " + e.getMessage());
                }
            }
        }
    }

    private void handle(Socket client) throws IOException, URISyntaxException {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(client.getInputStream(), StandardCharsets.UTF_8)
        );
        OutputStream out = client.getOutputStream();

        String requestLine = in.readLine();
        if (requestLine == null || requestLine.isBlank()) return;

        String[] parts = requestLine.split(" ");
        String method = parts.length > 0 ? parts[0] : "";
        String target = parts.length > 1 ? parts[1] : "/";

        // Leer headers hasta línea vacía
        String line;
        while ((line = in.readLine()) != null) {
            if (line.isEmpty()) break;
        }

        URI uri = new URI(target);
        String path = (uri.getPath() == null) ? "/" : uri.getPath();
        Map<String, List<String>> queryParams = parseQuery(uri.getQuery());

        Request req = new Request(path, queryParams);
        Response res = new Response();

        if (!"GET".equalsIgnoreCase(method)) {
            sendText(out, 405, "text/plain; charset=utf-8", "Method Not Allowed");
            return;
        }

        // 1) REST route
        RouteHandler handler = router.matchGet(path);
        if (handler != null) {
            String body = handler.handle(req, res);
            sendText(out, res.getStatus(), "text/plain; charset=utf-8", body == null ? "" : body);
            return;
        }

        // 2) Static file
        byte[] fileBytes = staticFiles.load(path);
        if (fileBytes != null) {
            sendBytes(out, 200, staticFiles.contentType(path), fileBytes);
            return;
        }

        // 3) 404
        sendText(out, 404, "text/plain; charset=utf-8", "Not Found");
    }

    private Map<String, List<String>> parseQuery(String query) throws UnsupportedEncodingException {
        Map<String, List<String>> map = new HashMap<>();
        if (query == null || query.isBlank()) return map;

        for (String pair : query.split("&")) {
            String[] kv = pair.split("=", 2);
            String key = URLDecoder.decode(kv[0], StandardCharsets.UTF_8);
            String val = (kv.length > 1) ? URLDecoder.decode(kv[1], StandardCharsets.UTF_8) : "";
            map.computeIfAbsent(key, k -> new ArrayList<>()).add(val);
        }
        return map;
    }

    private void sendText(OutputStream out, int status, String contentType, String body) throws IOException {
        sendBytes(out, status, contentType, body.getBytes(StandardCharsets.UTF_8));
    }

    private void sendBytes(OutputStream out, int status, String contentType, byte[] body) throws IOException {
        String statusText = switch (status) {
            case 200 -> "OK";
            case 404 -> "Not Found";
            case 405 -> "Method Not Allowed";
            default -> "OK";
        };

        String headers =
                "HTTP/1.1 " + status + " " + statusText + "\r\n" +
                "Content-Type: " + contentType + "\r\n" +
                "Content-Length: " + body.length + "\r\n" +
                "Connection: close\r\n" +
                "\r\n";

        out.write(headers.getBytes(StandardCharsets.UTF_8));
        out.write(body);
        out.flush();
    }
}