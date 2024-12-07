package com.robotrack.web_app;

public class Course {
    public int id;
    public String name;
    public String age_limit;

    Course(String name, String age_limit){
        this.name = name;
        this.age_limit = age_limit;
    }

    public String get_name(){
        return name;
    }

    public String get_age_limit(){
        return age_limit;
    }
}
