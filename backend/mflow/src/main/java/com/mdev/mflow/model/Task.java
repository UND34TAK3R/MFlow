package com.mdev.mflow.model;

import java.util.Date;

public class Task {
    //Fields
    private String title;
    private String description;
    private Date dueDate;
    private boolean notificationReminder;
    private Date notificationDate;

    //Setters
    public void setTitle(String title) {
        this.title = title;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setDue_date(Date due_date) { this.dueDate = due_date; }
    public void setNotificationReminder(boolean notificationReminder) { this.notificationReminder = notificationReminder; }
    public void setNotificationDate(Date notificationDate) { this.notificationDate = notificationDate; }
    //getters
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public Date getDue_date() {
        return dueDate;
    }
    public boolean getNotificationReminder() { return notificationReminder; }
    public Date getNotificationDate() { return notificationDate; }

    //constructor
    public Task(String title, String description, Date due_date, boolean notificationReminder, Date notificationDate) {
        setTitle(title);
        setDescription(description);
        setDue_date(due_date);
        setNotificationReminder(notificationReminder);
        setNotificationDate(notificationDate);

    }
}
