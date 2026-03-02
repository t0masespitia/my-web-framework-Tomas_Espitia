package edu.escuela.web.core;

@FunctionalInterface
public interface RouteHandler {
    String handle(Request req, Response res);
}