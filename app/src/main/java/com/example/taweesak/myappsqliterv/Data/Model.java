package com.example.taweesak.myappsqliterv.Data;

public class Model {
    private int id;
    private String Title;
    private String Content;

    public Model(String title, String content) {
        Title = title;
        Content = content;
    }

    public Model(int id, String title, String content) {
        this.id = id;
        Title = title;
        Content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
