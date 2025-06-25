package com.mdev.mflow.model;

import java.util.Date;

public class MainTask extends Task {
    private final String taskId;
    private Status status;
    private Priority priority;
    private String tagId;
    private String userId;
    private boolean recurringTask;
    private ReminderTime reminderTime;

    //getters
    public String getTask_id() {
        return taskId;
    }
    public Status getStatus() {
        return status;
    }
    public Priority getPriority() {
        return priority;
    }
    public String getTag_id() {
        return tagId;
    }
    public String getUser_id() {
        return userId;
    }
    public boolean getRecurringTask() { return recurringTask; }
    public ReminderTime getReminderTime() { return reminderTime; }

    //setters
    public void setStatus(Status status) {
        this.status = status;
    }
    public void setPriority(Priority priority) {
        this.priority = priority;
    }
    public void setTag_id(String tag_id) {
        this.tagId = tag_id;
    }
    public void setUser_id(String user_id) {
        this.userId = user_id;
    }
    public void setRecurringTask(boolean recurringTask) { this.recurringTask = recurringTask; }
    public void setReminderTime(ReminderTime reminderTime) { this.reminderTime = reminderTime; }

    //constructor
    public MainTask(String task_id, String title, String description, Status status, Date due_date, Priority priority, String tag_id, String user_id, boolean recurringTask, boolean notification_reminder, Date notification_date, ReminderTime reminderTime) {
        super(title, description, due_date, notification_reminder, notification_date);
        this.taskId = task_id;
        setStatus(status);
        setPriority(priority);
        setTag_id(tag_id);
        setUser_id(user_id);
        setRecurringTask(recurringTask);
        setReminderTime(reminderTime);
    }
}
