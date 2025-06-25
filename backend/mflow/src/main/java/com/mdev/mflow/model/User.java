package com.mdev.mflow.model;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class User {
    //Fields
    private final String user_id;
    private String username;
    private String email;
    private String password;

    //Getters
    public String getUser_id() {
        return user_id;
    }
    public String getUsername() {
        return username;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }

    //Setters
    public void setUsername(String newUsername){
        this.username = newUsername;
    }

    public void setEmail(String newEmail){
        this.email = newEmail;
    }

    public void setPassword(String newPassword){
        this.password = newPassword;
    }
    //constructor
    public User(String user_id, String username, String email, String password) {
        this.user_id = user_id;
        setUsername(username);
        setEmail(email);
        setPassword(password);
    }
}
