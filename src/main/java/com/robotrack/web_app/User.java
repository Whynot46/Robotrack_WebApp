package com.robotrack.web_app;


public class User {
    private int id;
    private String first_name;
    private String patronymic;
    private String last_name;
    private String birth_date;
    private String phone_number;
    private String password;
    private Role role;

    User(int id, String first_name, String patronymic, String last_name, String birth_date, String phone_number, String password, Role role){
        this.id = id;
        this.first_name = first_name;
        this.patronymic = patronymic;
        this.last_name = last_name;
        this.birth_date = birth_date;
        this.phone_number = phone_number;
        this.password = password;
        this.role = role;
        if (DataBase.is_old(phone_number)){
            DataBase.add_user(first_name, patronymic, last_name, birth_date, phone_number, password, role.get_id());
        }
            
    }

    public void set_first_name(String first_name){
        this.first_name = first_name;
    }

    public void set_patronymic(String patronymic){
        this.patronymic = patronymic;
    }

    public void set_last_name(String last_name){
        this.last_name = last_name;
    }

    public void set_birth_datee(String birth_date){
        this.birth_date = birth_date;
    }

    public void set_role(Role role){
        this.role = role;
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

    public Role get_role(){
        return role;
    }

    public String get_role_name(){
        return role.get_name();
    }

    public String get_phone_number(){
        return phone_number;
    }

    public String get_password(){
        return password;
    }

    public String toString(){
        return "id: " + id + ", first_name: " + first_name + ", patronymic: " + patronymic + "birth_date: " + birth_date + "phone_number:"+ phone_number + "password: "+ password + "role: " + role.get_name();
    }
}
