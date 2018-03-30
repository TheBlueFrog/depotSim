package com.mike.sim;

import java.util.List;

public class RouteErrors {
    private List<RouteError> errors;

    public boolean hasErrors() {
        return false;
    }

    public void add(RouteError error) {
        errors.add(error);
    }
}
