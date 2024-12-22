package com.robotrack.web_app;

public class ParentProfile {
    private int user_id;
    private int child_id;

    ParentProfile(int user_id, int child_id){
        this.user_id = user_id;
        this.child_id = child_id;
    }

    public int get_user_id(){
        return user_id;
    }

    public int get_child_id(){
        return child_id;
    }

}
