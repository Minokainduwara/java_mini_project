package com.example.app.Models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Notice {

    private final IntegerProperty id;
    private final StringProperty title;
    private final StringProperty content;

    // Constructor
    public Notice(int id, String title, String content) {
        this.id = new SimpleIntegerProperty(id);
        this.title = new SimpleStringProperty(title);
        this.content = new SimpleStringProperty(content);
    }

    // Getters as JavaFX properties
    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty titleProperty() {
        return title;
    }

    public StringProperty contentProperty() {
        return content;
    }

    // Getters (non-property methods)
    public int getId() {
        return id.get();
    }

    public String getTitle() {
        return title.get();
    }

    public String getContent() {
        return content.get();
    }

    // Setters
    public void setId(int id) {
        this.id.set(id);
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public void setContent(String content) {
        this.content.set(content);
    }
}
