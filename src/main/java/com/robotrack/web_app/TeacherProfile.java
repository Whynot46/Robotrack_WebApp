package com.robotrack.web_app;

import java.util.ArrayList;

public class TeacherProfile {
    private int user_id;
    private ArrayList<Integer> courses_id;

    TeacherProfile(int user_id, ArrayList<Integer> courses_id){
        this.user_id = user_id;
        this.courses_id = courses_id;
    }

    public int get_user_id(){
        return this.user_id;
    }

    public ArrayList<Integer> get_courses_id(){
        return this.courses_id;
    }

}
