package com.example.claritus.claritus.auth.registration;

public class RegistrationData {
    private String email;
    private String password;
    private String phone;
    private String firstName;
    private String lastName;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public RegistrationData(String email, String password, String phone, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
