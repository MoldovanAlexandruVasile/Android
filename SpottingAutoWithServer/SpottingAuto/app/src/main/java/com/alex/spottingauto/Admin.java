package com.alex.spottingauto;

public class Admin {

    private Integer ID;
    private String email;

    public Admin(String email) {
        this.email = email;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "ID=" + ID +
                ", email='" + email + '\'' +
                '}';
    }
}
