package com.robotrack.web_app;

public class Course {
    private int id;
    private String name;
    private String age_limit;

    Course(int id, String name, String age_limit){
        this.id = id;
        this.name = name;
        this.age_limit = age_limit;
    }

    public int get_id(){
        return id;
    }

    public String get_name(){
        return name;
    }

    public String get_age_limit(){
        return age_limit;
    }
}
