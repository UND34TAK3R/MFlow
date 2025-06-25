package com.mdev.mflow.dao;

import com.mdev.mflow.model.Subtask;

import java.util.List;

public interface SubtaskDAO {
    boolean updateSubtask(Subtask subtask);
    boolean deleteSubtask(Subtask subtask);
    boolean createSubtask(Subtask subtask);
    Subtask getSubtask(String subtaskId);
    List<Subtask> getSubtasks();
}
