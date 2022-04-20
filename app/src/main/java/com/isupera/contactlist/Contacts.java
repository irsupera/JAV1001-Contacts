//JAV-1001
//Iricher B Supera
//A00237146
package com.isupera.contactlist;

public class Contacts {

    //  attributes
    private String name;
    private String mobile;
    private String email;

    // constructor
    public Contacts(String name, String mobile, String email) {
        this.name = name;
        this.mobile = mobile;
        this.email = email;
    }
    // get and set methods
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
