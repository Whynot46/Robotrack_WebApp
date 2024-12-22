package com.robotrack.web_app;


public class Role {
    private int id;
    private String name;

    Role(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int get_id(){
        return id;
    }

    public String get_name(){
        return name;
    }
}
