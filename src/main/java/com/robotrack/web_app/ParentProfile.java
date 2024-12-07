package com.robotrack.web_app;

public class ParentProfile {
    public User user;
    public User child;

    ParentProfile(User user, User child){
        this.user = user;
        this.child = child;
    }
}
