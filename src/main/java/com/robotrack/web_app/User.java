package com.robotrack.web_app;

import java.util.ArrayList;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class User implements UserDetails {
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

    public String get_fullname() {
        return first_name + " " + patronymic + " " + last_name;
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

    public String get_role_name(){
        return (DataBase.get_role(role_id)).get_name();
    }

    public String get_last_task_name() {
        int last_task_id = DataBase.get_student_last_task_id(id);
        if (last_task_id == -1) return "Нет данных";
        
        Task last_task = DataBase.get_task(last_task_id);
        return last_task != null ? last_task.get_name() : "Нет данных";
    }

    public int get_child_id(){
        return DataBase.get_child_id(id);
    }

    public String toString(){
        return "id: " + id + ", first_name: " + first_name + ", patronymic: " + patronymic + ", last_name: " + last_name + "birth_date: " + birth_date + "phone_number:"+ phone_number + "password_hash: "+ password_hash + "role_id: " + role_id;
    }

        // Реализация методов UserDetails
    @Override
    public String getUsername() {
        return get_fullname(); // Возвращаем полное имя как имя пользователя
    }

    @Override
    public String getPassword() {
        return password_hash; // Возвращаем хэш пароля
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        if (role_id == 1) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else if (role_id == 2) {
            authorities.add(new SimpleGrantedAuthority("ROLE_TEACHER"));
        } else if (role_id == 3) {
            authorities.add(new SimpleGrantedAuthority("ROLE_STUDENT"));
        } else if (role_id == 4) {
            authorities.add(new SimpleGrantedAuthority("ROLE_PARENT"));
        }
        return authorities;
    }

    public String getFullName() {
        return last_name + " " + first_name + " " + patronymic; // Возвращает полное имя
    }

}
