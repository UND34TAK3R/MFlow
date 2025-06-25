//REVISON HISTORY:
//      DATE            NAME                COMMENTS
//  2025/06/15      DERRICK MANGARI      Created DAO functions that allows users to create, update, delete Tasks

package com.mdev.mflow.dao.impl;
import com.mdev.mflow.dao.TaskDAO;
import com.mdev.mflow.model.MainTask;
import com.mdev.mflow.model.Priority;
import com.mdev.mflow.model.ReminderTime;
import com.mdev.mflow.model.Status;


import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
public abstract class TaskDAOImpl implements TaskDAO {
    private final DataSource dataSource;

    public TaskDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

//ALLOWS TO CREATE A MAIN TASK
    @Override
    public boolean createTask(MainTask task) {
        String sql = "{CALL insert_task(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            String taskId = UUID.randomUUID().toString();
            stmt.setString(1, taskId);
            stmt.setString(2, task.getTitle());
            stmt.setString(3, task.getDescription());
            stmt.setString(4, task.getStatus().name());
            stmt.setDate(5, task.getDue_date() != null ? new java.sql.Date(task.getDue_date().getTime()) : null);
            stmt.setString(6, task.getPriority().name());
            stmt.setString(7, task.getTag_id());
            stmt.setString(8, task.getUser_id());
            stmt.setBoolean(9, task.getRecurringTask());
            stmt.setBoolean(10, task.getNotificationReminder());
            stmt.setDate(11, task.getNotificationDate() != null ? new java.sql.Date(task.getNotificationDate().getTime()) : null);
            stmt.setString(12, task.getReminderTime().name());
            stmt.execute();
            return true;

        } catch (SQLException e) {
            System.err.println("Failed to create task: " + e.getMessage());
            return false;
        }
    }

//ALLOWS TO UPDATE AN EXISTING TAG
    @Override
    public boolean  updateTask(MainTask task) {
        String sql = "{CALL update_task(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        try(Connection conn = dataSource.getConnection();
            CallableStatement stmt = conn.prepareCall(sql)){
            stmt.setString(1, task.getTask_id());
            stmt.setString(2, task.getTitle());
            stmt.setString(3, task.getDescription());
            stmt.setString(4, task.getStatus().name());
            stmt.setDate(5, task.getDue_date() != null ? new java.sql.Date(task.getDue_date().getTime()) : null);
            stmt.setString(6, task.getPriority().name());
            stmt.setString(7, task.getTag_id());
            stmt.setString(8, task.getUser_id());
            stmt.setBoolean(9, task.getRecurringTask());
            stmt.setBoolean(10, task.getNotificationReminder());
            stmt.setDate(11, task.getNotificationDate() != null ? new java.sql.Date(task.getNotificationDate().getTime()) : null);
            stmt.setString(12, task.getReminderTime().name());
            stmt.execute();
            return true;
        } catch (SQLException e) {
            System.err.println("Failed to update task: " + e.getMessage());
            return false;
        }
    }

//ALLOWS TO DELETE AN EXISTING TAG
    @Override
    public boolean deleteTask(MainTask task) {
        String sql = "{CALL delete_task(?)}";
        try(Connection conn = dataSource.getConnection();
            CallableStatement stmt = conn.prepareCall(sql)){
            stmt.setString(1, task.getTask_id());
            stmt.execute();
            return true;
        }catch(SQLException e){
            System.err.println("Failed to delete task: " + e.getMessage());
            return false;
        }
    }

// ALLOWS TO LOAD A TASK
    @Override
    public MainTask getTask(String p_task_id) {
        String sql = "{CALL select_one_task(?)}";
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, p_task_id);
            boolean hasResult = stmt.execute();

            if (hasResult) {
                try (ResultSet rs = stmt.getResultSet()) {
                    if (rs.next()) {
                        String task_id = rs.getString("task_id");
                        String title = rs.getString("title");
                        String description = rs.getString("description");
                        Status status = Status.valueOf(rs.getString("status").toUpperCase());
                        Date due_date = rs.getDate("due_date");
                        Priority priority = Priority.valueOf(rs.getString("priority").toUpperCase());
                        String user_id = rs.getString("user_id");
                        String tag_id = rs.getString("tag_id");
                        Boolean recurringTask = rs.getBoolean("recurringTask");
                        Boolean notificationReminder = rs.getBoolean("notificationReminder");
                        Date notificationDate = rs.getDate("notificationDate");
                        ReminderTime reminderTime = ReminderTime.valueOf(rs.getString("reminderTime").toUpperCase());

                        return new MainTask(task_id, title, description, status, due_date, priority, tag_id, user_id, recurringTask, notificationReminder, notificationDate, reminderTime);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Failed to get task: " + e.getMessage());
        }
        return null;
    }

//ALLOWS TO SAVE ALL TASKS INSIDE A LIST
    @Override
    public List<MainTask> getTasks() {
        List<MainTask> mainTasks = new ArrayList<>();
        String sql = "{CALL select_all_tasks()}";
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            boolean hasResult = stmt.execute();
            if (hasResult) {
                try (ResultSet rs = stmt.getResultSet()) {
                    while (rs.next()) {
                        String task_id = rs.getString("task_id");
                        String title = rs.getString("title");
                        String description = rs.getString("description");
                        Status status = Status.valueOf(rs.getString("status").toUpperCase());
                        Date due_date = rs.getDate("due_date");
                        Priority priority = Priority.valueOf(rs.getString("priority").toUpperCase());
                        String user_id = rs.getString("user_id");
                        String tag_id = rs.getString("tag_id");
                        boolean recurringTask = rs.getBoolean("recurringTask");
                        boolean notificationReminder = rs.getBoolean("notificationReminder");
                        Date notificationDate = rs.getDate("notificationDate");
                        ReminderTime reminderTime = ReminderTime.valueOf(rs.getString("reminderTime").toUpperCase());
                        mainTasks.add(new MainTask(task_id, title, description, status, due_date, priority, tag_id, user_id, recurringTask, notificationReminder, notificationDate, reminderTime));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Failed to get all tasks: " + e.getMessage());
        }
        return mainTasks;
    }
}
