package com.robotrack.web_app;

import java.util.ArrayList;

public class StudentProfile {
    public User user;
    private String school_shift;
    public Task last_task;
    private ArrayList<Course> courses = new ArrayList<Course>();
    private ArrayList<Lesson> lessons = new ArrayList<Lesson>();

    StudentProfile(User user, String school_shift, Task last_task){
        this.user = user;
        this.school_shift = school_shift;
        this.last_task = last_task;
    }

    public void set_school_shift(String school_shift){
        this.school_shift = school_shift;
    }

    public void set_last_task(Task last_task){
        this.last_task = last_task;
    }

    public void add_course(Course course){
        this.courses.add(course);
    }

    public void add_lesson(Lesson lesson){
        this.lessons.add(lesson);
    }

    public void change_last_task(Task last_task){
        this.last_task = last_task;
    }

    public ArrayList<Course> get_course(){
        return courses;
    }

    public ArrayList<Lesson> get_lessons(){
        return lessons;
    }

    public String get_school_shift(){
        return school_shift;
    }
    
}
