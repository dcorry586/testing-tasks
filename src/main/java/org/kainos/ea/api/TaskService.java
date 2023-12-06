package org.kainos.ea.api;

import org.kainos.ea.cli.Task;
import org.kainos.ea.cli.TaskRequest;
import org.kainos.ea.cli.TaskResponse;
import org.kainos.ea.cli.TaskUpdateRequest;
import org.kainos.ea.client.CannotGetEnvironmentVariableException;
import org.kainos.ea.db.DatabaseConnector;
import org.kainos.ea.db.TaskDao;

import java.sql.SQLException;
import java.util.List;

public class TaskService {
  private final TaskDao taskDao;
  private final DatabaseConnector databaseConnector;

  public TaskService(TaskDao taskDao, DatabaseConnector databaseConnector) {
    this.taskDao = taskDao;
    this.databaseConnector = databaseConnector;
  }

  public List<TaskResponse> getAllTasks() throws SQLException, CannotGetEnvironmentVariableException {
    return taskDao.getAllTasks(databaseConnector.getConnection());
  }

  public TaskResponse getTaskById(int id) throws SQLException, CannotGetEnvironmentVariableException {
    TaskResponse task = taskDao.getTaskById(id, databaseConnector.getConnection());

    if (task == null) {
      throw new SQLException();
    }
    return task;
  }

  public int addTask(TaskRequest taskRequest) throws SQLException, CannotGetEnvironmentVariableException {
    return taskDao.addTask(taskRequest, databaseConnector.getConnection());
  }

  public String deleteTask(int id) throws SQLException, CannotGetEnvironmentVariableException {
    return taskDao.deleteTask(id, databaseConnector.getConnection());
  }

  public int updateTask(int id, TaskUpdateRequest taskUpdateRequest) throws SQLException,
          CannotGetEnvironmentVariableException {
    return taskDao.updateTask(id, databaseConnector.getConnection(), taskUpdateRequest);
  }
}
