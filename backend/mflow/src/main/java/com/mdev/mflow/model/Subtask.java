package com.mdev.mflow.model;

import java.util.Date;

public class Subtask extends Task {
    //Fields
    private final String subtaskId;
    private boolean isDone;
    private String taskId;

    //getters
    public String getSubtaskId(){
        return this.subtaskId;
    }
    public boolean getIsDone(){
       return this.isDone;
    }
    public String getTaskId(){
        return this.taskId;
    }

    //setters
    public void setIsDone(boolean isDone){
        this.isDone = isDone;
    }
    public void setTaskId(String taskId){
        this.taskId = taskId;
    }

    //constructor
    public Subtask(String subtaskId, String title, String description, boolean isDone, String taskId, Date dueDate, boolean notification_reminder, Date notification_date) {
        super(title,description, dueDate, notification_reminder, notification_date);
        this.subtaskId = subtaskId;
        setIsDone(isDone);
        setTaskId(taskId);
    }
}
