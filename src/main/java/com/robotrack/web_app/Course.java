package com.robotrack.web_app;

import java.util.ArrayList;

public class Course {
    private int id;
    private String name;
    private String age_limit;
    private ArrayList<Integer> tasks_id;

    Course(int id, String name, String age_limit){
        this.id = id;
        this.name = name;
        this.age_limit = age_limit;
    }

    Course(int id, String name, String age_limit, ArrayList<Integer> tasks_id){
        this.id = id;
        this.name = name;
        this.age_limit = age_limit;
        this.tasks_id = tasks_id;
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

    public ArrayList<Integer> get_tasks_id(){
        return tasks_id;
    }
}
