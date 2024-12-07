package com.robotrack.web_app;

import java.util.ArrayList;

public class TeacherProfile {
    public User user;
    private ArrayList<Course> courses = new ArrayList<Course>();

    TeacherProfile(User user){
        this.user = user;
    }

    public ArrayList<Course> get_courses(){
        return courses;
    }

    public void add_course(Course course){
        courses.add(course);
    }

    public void remove_course(Course course){
        courses.remove(course);
    }
}
