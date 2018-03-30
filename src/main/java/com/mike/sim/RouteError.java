package com.mike.sim;

public class RouteError {
    private final Consumer consumer;
    private final Type type;

    public RouteError(Type type, Consumer consumer) {
        this.type = type;
        this.consumer = consumer;
    }

    public enum Type {DidNotDropTo, DidNotPickFor}
}
