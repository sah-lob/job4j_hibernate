package ru.job4j.todo.models;

import java.util.Date;

public class Item {

    private int id;
    private String description;
    private Date created;
    private boolean done;


    public Item(String description, Date created, boolean done) {
        this.description = description;
        this.created = created;
        this.done = done;
    }

    public Item() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
