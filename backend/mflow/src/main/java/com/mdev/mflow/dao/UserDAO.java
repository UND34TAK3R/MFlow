package com.mdev.mflow.dao;

import com.mdev.mflow.model.User;

public interface UserDAO {

    /**
     * Register a user to the website
     * @param username the Username
     * @param password the Password
     * @param email the User email
     * @return true if successful
     */
    boolean registerUser(String username, String password, String email);

    /**
     * Login a user to the website
     * @param email the username
     * @param password the password (non-hashed)
     * @return the User
     */
    User loginUser(String email, String password);

    /**
     * Saves the user's profile changes
     * @param user the User
     * @return true if successful
     */
    boolean saveUser(User user);

    /**
     * Deletes a user from the website
     * @param user the User
     * @return true if successful
     */
    boolean deleteUser(User user);


    /**
     * Checks if the email exists (helps for registration)
     * @param email the user's email
     * @return true if exists
     */
    boolean emailExists(String email);
}
