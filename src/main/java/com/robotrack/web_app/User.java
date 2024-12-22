package com.robotrack.web_app;


public class User {
    private int id;
    private String first_name;
    private String patronymic;
    private String last_name;
    private String birth_date;
    private String phone_number;
    private String password_hash;
    private int role_id;

    User(int id, String first_name, String patronymic, String last_name, String birth_date, String phone_number, String password_hash, int role_id){
        this.id = id;
        this.first_name = first_name;
        this.patronymic = patronymic;
        this.last_name = last_name;
        this.birth_date = birth_date;
        this.phone_number = phone_number;
        this.password_hash = password_hash;
        this.role_id = role_id;    
    }

    public Integer get_id(){
        return id;
    }

    public String get_first_name(){
        return first_name;
    }

    public String get_patronymic(){
        return patronymic;
    }

    public String get_last_name(){
        return last_name;
    }

    public String get_birth_date(){
        return birth_date;
    }

    public int get_role_id(){
        return role_id;
    }

    public String get_phone_number(){
        return phone_number;
    }

    public String get_password_hash(){
        return password_hash;
    }

    public String toString(){
        return "id: " + id + ", first_name: " + first_name + ", patronymic: " + patronymic + "birth_date: " + birth_date + "phone_number:"+ phone_number + "password_hash: "+ password_hash + "role_id: " + role_id;
    }
}
