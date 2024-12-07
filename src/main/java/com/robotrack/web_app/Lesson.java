package com.robotrack.web_app;

import java.util.ArrayList;

public class Lesson {
    private int id;
    public Course course;
    private String date;
    private String time;
    private ArrayList<User> students = new ArrayList<User>();

    Lesson(Course course, String date, String time){
        this.course = course;
        this.date = date;
        this.time = time;
    }

    public ArrayList<User> get_students(){
        return students;
    }

    public void add_student(User student){
        students.add(student);
    }

    public String get_date(){
        return date;
    }

    public String get_time(){
        return time;
    }

    public int get_id(){
        return id;
    }

}
