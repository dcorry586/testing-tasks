package org.kainos.ea.api;

import org.kainos.ea.cli.Task;
import org.kainos.ea.db.TaskDao;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

public class TaskService {
  private static final TaskDao taskDao = new TaskDao();

  public List<Task> getAllTasks() {
//    return Arrays.asList(
//            new Task(1, "walk dog", false, new Timestamp(System.currentTimeMillis())),
//            new Task(2, "take out bins", false, new Timestamp(System.currentTimeMillis()))
//    );
    return taskDao.getAllTasks();
  }
}
