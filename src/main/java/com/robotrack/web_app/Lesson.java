package com.robotrack.web_app;

import java.util.ArrayList;

public class Lesson {
    private int id;
    private Course course;
    private String date;
    private String time;
    private ArrayList<User> students = new ArrayList<User>();

    Lesson(int id, Course course, String date, String time, ArrayList<User> students){
        this.id = id;
        this.course = course;
        this.date = date;
        this.time = time;
        this.students = students;
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

    public int get_course_id(){
        return course.get_id();
    }

    public String get_str_students_id() {
        StringBuilder students_id = new StringBuilder();
        for (int i = 0; i < students.size(); i++) {
            students_id.append(students.get(i).get_id());
            if (i < students.size() - 1) {
                students_id.append(","); // Добавляем запятую между идентификаторами
            }
        }
        return students_id.toString(); // Возвращаем строку
    }

}
