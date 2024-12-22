package com.robotrack.web_app;

public class New {
    private int id;
    private int author_id;
    private String title;
    private String content;
    private String publish_datetime;

    New(int id, int author_id, String title, String content, String publish_datetime){
        this.id = id;
        this.author_id = author_id;
        this.title = title;
        this.content = content;
        this.publish_datetime = publish_datetime;
    }

    public int get_id(){
        return this.id;
    }

    public int get_author_id(){
        return this.author_id;
    }

    public String get_title(){
        return this.title;
    }

    public String get_content(){
        return this.content;
    }

    public String get_publish_datetime(){
        return this.publish_datetime;
    }

}
