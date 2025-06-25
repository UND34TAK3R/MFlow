//REVISON HISTORY:
//      DATE            NAME                COMMENTS
//  2025/06/15      DERRICK MANGARI      Created DAO functions that allows users to create, update, delete subtasks

package com.mdev.mflow.dao.impl;

import com.mdev.mflow.dao.SubtaskDAO;
import com.mdev.mflow.model.Subtask;
import com.mdev.mflow.model.Tag;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public abstract class SubtaskDAOImpl implements SubtaskDAO {
    private DataSource dataSource;

    public SubtaskDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    //ALLOWS TO CREATE A SUBTASK
    @Override
    public boolean createSubtask(Subtask subtask) {
        String sql = "{CALL insert_subtask(?, ?, ?, ?, ?, ?, ?, ?)}";
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            String subtaskId = UUID.randomUUID().toString();
            stmt.setString(1, subtaskId);
            stmt.setString(2, subtask.getTaskId());
            stmt.setString(3, subtask.getTitle());
            stmt.setString(4, subtask.getDescription());
            stmt.setBoolean(5, subtask.getIsDone());
            stmt.setDate(6, subtask.getDue_date() != null ? new java.sql.Date(subtask.getDue_date().getTime()) : null);
            stmt.setBoolean(7, subtask.getNotificationReminder());
            stmt.setDate(8, subtask.getNotificationDate() != null ? new java.sql.Date(subtask.getNotificationDate().getTime()) : null);
            stmt.execute();
            return true;

        } catch (SQLException e) {
            System.err.println("Failed to create subtask: " + e.getMessage());
            return false;
        }
    }

    //ALLOWS TO UPDATE AN EXISTING SUBTASK
    @Override
    public boolean updateSubtask(Subtask subtask) {
        String sql = "{CALL update_subtask(?, ?, ?, ?, ?, ?)}";
        try(Connection conn = dataSource.getConnection();
            CallableStatement stmt = conn.prepareCall(sql)){
            stmt.setString(1, subtask.getSubtaskId());
            stmt.setString(2, subtask.getTaskId());
            stmt.setString(3, subtask.getTitle());
            stmt.setString(4, subtask.getDescription());
            stmt.setBoolean(5, subtask.getIsDone());
            stmt.setDate(6, subtask.getDue_date() != null ? new java.sql.Date(subtask.getDue_date().getTime()) : null);
            stmt.setBoolean(7, subtask.getNotificationReminder());
            stmt.setDate(8, subtask.getNotificationDate() != null ? new java.sql.Date(subtask.getNotificationDate().getTime()) : null);
            stmt.execute();
            return true;
        } catch (SQLException e) {
            System.err.println("Failed to update subtask: " + e.getMessage());
            return false;
        }
    }

    // ALLOWS TO DELETE A SUBTASK
    @Override
    public boolean deleteSubtask(Subtask task) {
        String sql = "{CALL delete_subtask(?)}";
        try(Connection conn = dataSource.getConnection();
            CallableStatement stmt = conn.prepareCall(sql)){
            stmt.setString(1, task.getSubtaskId());
            stmt.execute();
            return true;
        }catch(SQLException e){
            System.err.println("Failed to delete subtask: " + e.getMessage());
            return false;
        }
    }

//ALLOWS TO LOAD ONE SUBTASK
    @Override
    public Subtask getSubtask(String p_subtask_id) {
        String sql = "{CALL select_one_subtask(?)}";
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, p_subtask_id);
            boolean hasResult = stmt.execute();

            if (hasResult) {
                try (ResultSet rs = stmt.getResultSet()) {
                    if (rs.next()) {
                        String subtask_id = rs.getString("subtask_id");
                        String task_id = rs.getString("task_id");
                        String title = rs.getString("title");
                        String description = rs.getString("description");
                        boolean isDone = rs.getBoolean("is_done");
                        Date date = rs.getDate("due_date");
                        boolean notification_reminder = rs.getBoolean("notification_reminder");
                        Date notification_date = rs.getDate("notification_date");
                        return new Subtask(subtask_id, title, description, isDone, task_id, date, notification_reminder, notification_date);}
                }
            }
        } catch (SQLException e) {
            System.err.println("Failed to get subtask: " + e.getMessage());
        }
        return null;
    }
//SAVES ALL SUBTASKS RELATED TO ONE TASK IN A LIST
    @Override
    public List<Subtask> getSubtasks() {
        List<Subtask> subtasks = new ArrayList<>();
        String sql = "{CALL select_all_subtasks()}";
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            boolean hasResult = stmt.execute();
            if (hasResult) {
                try (ResultSet rs = stmt.getResultSet()) {
                    while (rs.next()) {
                        String subtask_id = rs.getString("subtask_id");
                        String task_id = rs.getString("task_id");
                        String title = rs.getString("title");
                        String description = rs.getString("description");
                        boolean isDone = rs.getBoolean("is_done");
                        Date date =rs.getDate("due_date");
                        boolean notification_reminder = rs.getBoolean("notification_reminder");
                        Date notification_date = rs.getDate("notification_date");
                        subtasks.add(new Subtask(subtask_id, title, description, isDone, task_id, date, notification_reminder, notification_date));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Failed to get all subtasks: " + e.getMessage());
        }
        return subtasks;
    }
}