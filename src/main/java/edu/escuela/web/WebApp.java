package edu.escuela.web;

import edu.escuela.web.core.HttpServer;

public class WebApp {
    public static void main(String[] args) throws Exception {
        HttpServer server = new HttpServer();

        server.staticfiles("/webroot");   // resources/webroot
        server.setBasePrefix("/App");     // prefijo sugerido

        server.get("/hello", (req, resp) -> "Hello " + req.getValues("name"));
        server.get("/pi", (req, resp) -> String.valueOf(Math.PI));

        server.start(8080);
    }
}