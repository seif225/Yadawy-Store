package com.example.ss.FindSellersPackage;

public class UserModel {
    private String name,profilePicture;
    private boolean follow_state;

    UserModel(){}
    UserModel(String name,String profilePicture,boolean follow_state){
        this.name=name;
        this.profilePicture=profilePicture;
        this.follow_state=follow_state;

    }

    public void setFollow_state(boolean follow_state) {
        this.follow_state = follow_state;
    }

    public boolean isFollow_state() {
        return follow_state;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getName() {
        return name;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

}
