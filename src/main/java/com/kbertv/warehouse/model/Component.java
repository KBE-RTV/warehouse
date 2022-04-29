package com.kbertv.warehouse.model;

public abstract class Component {
    private String id;
    private String name;
    private int amount;

    protected Component(String id, String name, int amount) {
        this.id = id;
        this.name = name;
        this.amount = amount;
    }
}
