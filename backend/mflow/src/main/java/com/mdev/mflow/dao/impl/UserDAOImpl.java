//REVISON HISTORY:
//      DATE            NAME                COMMENTS
//  2025/06/15      DERRICK MANGARI      Created DAO functions that allows users to create, update,
//                                       delete users and login with their emails

package com.mdev.mflow.dao.impl;
import com.mdev.mflow.dao.UserDAO;
import com.mdev.mflow.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;
import java.sql.*;
import java.util.UUID;

public abstract class UserDAOImpl implements UserDAO {
    private final DataSource dataSource;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public boolean registerUser(String username, String email, String password) {
        String sql = "{CALL insert_user(?, ?, ?, ?)}";
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            String user_Id = UUID.randomUUID().toString();
            String hashedPwd = passwordEncoder.encode(password);
            stmt.setString(1, user_Id);
            stmt.setString(2, username);
            stmt.setString(3, email);
            stmt.setString(4, hashedPwd);

            stmt.execute();

            return true;

        } catch (SQLException e) {
            System.err.println("Failed to register user: " + e.getMessage());
            return false;
        }
    }
    @Override
    public User loginUser(String p_email, String p_password) {
        String sql = "{CALL login_user(?)}";
        try(Connection conn = dataSource.getConnection();
            CallableStatement stmt = conn.prepareCall(sql)){
            stmt.setString(1, p_email);
            boolean hasResult = stmt.execute();
            if(hasResult){
                try(ResultSet rs = stmt.getResultSet()){
                    if(rs.next()){
                        String storedHashedPwd = rs.getString("password");
                        if(passwordEncoder.matches(p_password, storedHashedPwd)){
                            String username  = rs.getString("username");
                            String email = rs.getString("email");
                            String hashedPwd = rs.getString("password");
                            String user_id = rs.getString("user_id");
                            return new User(user_id, username, email, hashedPwd);
                        }else{
                            return null;
                        }
                    }
                }
            }
        }catch (SQLException e) {
            System.err.println("Failed to login user: " + e.getMessage());
            return null;
        }
        return null;
    }

    @Override
    public boolean  saveUser(User user) {
        String sql = "{CALL update_user(?, ?, ?, ?)}";
        try(Connection conn = dataSource.getConnection();
            CallableStatement stmt = conn.prepareCall(sql)){
            stmt.setString(1, user.getUser_id());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPassword());
            stmt.execute();
            return true;
        } catch (SQLException e) {
            System.err.println("Failed to save user: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteUser(User user) {
        String sql = "{CALL delete_user(?)}";
        try(Connection conn = dataSource.getConnection();
            CallableStatement stmt = conn.prepareCall(sql)){
            stmt.setString(1, user.getUser_id());
            stmt.execute();
            return true;
        }catch(SQLException e){
            System.err.println("Failed to delete user: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean emailExists(String email) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM users WHERE email = ?")) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
            return false;

        } catch (SQLException e) {
            System.err.println("Error checking if email exists: " + e.getMessage());
            return false;
        }
    }
}
