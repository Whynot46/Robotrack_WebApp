package com.robotrack.web_app;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class UserController{
    private ArrayList<User> users = new ArrayList<User>();

    UserController(){
        users.add(new User("Алексей", "Дмитриевич", "Пахалев", "28.11.2004", new Role("Администратор")));
        users.add(new User("Никита", "Денисович", "Мальцев", "03.08.2004", new Role("Ученик")));
    }

    @GetMapping("get_users")
    public String get_users(){
        String users_str = "{";
        for (User user : users) {
            users_str += user.toString() + "\n";
        }
        users_str += "}";
        return users_str;
    }

    @GetMapping("get_user/{id}")
    public String get_user(@PathVariable int id){
        String user_str = "{";
        for (User user : users) {
            if (user.get_id()==id){
                user_str += user.toString() + "\n";
            };
        }
        user_str += "}";
        return user_str;
    }

    @GetMapping("get_users_model")
    public String get_users(Model model){
        model.addAttribute("users_model", users);
        return "users_model";
    }

}
