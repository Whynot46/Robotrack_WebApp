package com.robotrack.web_app;

import java.util.ArrayList;

public class Lesson {
    private int id;
    private int course_id;
    private String date;
    private String time;
    private ArrayList<Integer> students_id = new ArrayList<Integer>();

    Lesson(int id, int course_id, String date, String time, ArrayList<Integer> students_id){
        this.id = id;
        this.course_id = course_id;
        this.date = date;
        this.time = time;
        this.students_id = students_id;
    }

    public ArrayList<Integer> get_students_id(){
        return students_id;
    }

    public void add_student(int student_id){
        students_id.add(student_id);
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

    public int get_course_id(){
        return course_id;
    }


}
