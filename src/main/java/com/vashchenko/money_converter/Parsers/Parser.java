package com.vashchenko.money_converter.Parsers;

public class Parser {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Parser(String name) {
        this.name = name;
    }

    public Parser() {
    }
}
