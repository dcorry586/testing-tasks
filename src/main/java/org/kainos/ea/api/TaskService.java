package org.kainos.ea.api;

import org.kainos.ea.cli.Task;
import org.kainos.ea.db.TaskDao;

import java.util.List;

public class TaskService {
  private static final TaskDao taskDao = new TaskDao();

  public List<Task> getAllTasks() {
    return taskDao.getAllTasks();
  }
}
