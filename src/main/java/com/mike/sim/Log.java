package com.mike.sim;

public class Log {

    static public void d(String tag, String body) {
        System.out.println(String.format("%10.10s %s", tag, body));
    }
}
