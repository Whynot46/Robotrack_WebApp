package com.robotrack.web_app;

import java.util.ArrayList;

public class StudentProfile {
    private int user_id;
    private String school_shift;
    private int last_task_id;
    private ArrayList<Integer> courses_id;
    private ArrayList<Integer> lessons_id;

    StudentProfile(int user_id, String school_shift, int last_task_id, ArrayList<Integer> courses_id, ArrayList<Integer> lessons_id){
        this.user_id = user_id;
        this.school_shift = school_shift;
        this.last_task_id = last_task_id;
        this.courses_id = courses_id;
        this.lessons_id = lessons_id;
    }

    public int get_user_id(){
        return user_id;
    }

    public String get_school_shift(){
        return school_shift;
    }

    public int get_last_task_id(){
        return last_task_id;
    }

    public ArrayList<Integer> get_courses_id(){
        return courses_id;
    }

    public ArrayList<Integer> get_lessons_id(){
        return lessons_id;
    }
    
}
