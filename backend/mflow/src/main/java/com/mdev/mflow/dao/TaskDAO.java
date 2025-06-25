package com.mdev.mflow.dao;

import com.mdev.mflow.model.MainTask;
import com.mdev.mflow.model.Task;

import java.util.List;

public interface TaskDAO {

    boolean updateTask(MainTask task);

    boolean deleteTask(MainTask task);

    boolean createTask(MainTask task);

    MainTask getTask(String taskId);
    List<MainTask> getTasks();
}
