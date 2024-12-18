package com.robotrack.web_app;

public class Task {
    private int id;
    private String name;
    private String description;
    private Course course;

    Task(int id, String name, String description, Course course){
        this.id = id;
        this.name = name;
        this.description = description;
        this.course = course;
    }

    public void set_name(String name){
        this.name = name;
    }

    public void set_description(String description){
        this.description = description;
    }

    public void set_course(Course course){
        this.course = course;
    }

    public Integer get_id(){
        return id;
    }

    public String get_name(){
        return name;
    }

    public String get_description(){
        return description;
    }

    public Course get_course(){
        return course;
    }
}
