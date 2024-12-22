package com.robotrack.web_app;

public class AdminProfile {
    private int user_id;

    AdminProfile(int user_id){
        this.user_id = user_id;
    }

    public int get_user_id(){
        return user_id;
    }
}
