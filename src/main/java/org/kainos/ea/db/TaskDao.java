package org.kainos.ea.db;

import org.kainos.ea.cli.Task;
import org.kainos.ea.cli.TaskRequest;
import org.kainos.ea.cli.TaskResponse;
import org.kainos.ea.client.CannotGetEnvironmentVariableException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDao {

  public TaskDao() {
  }

  public List<TaskResponse> getAllTasks(Connection connection) throws SQLException {
    Statement statement = connection.createStatement();
    ResultSet rs = statement.executeQuery("SELECT task_id, task, completed, created_at FROM tasks;");

    List<TaskResponse> tasks = new ArrayList<>();

    while (rs.next()) {
      tasks.add(new TaskResponse(
              rs.getInt("task_id"),
              rs.getString("task"),
              rs.getBoolean("completed"),
              rs.getTimestamp("created_at")
      ));
    }

    System.out.println("logging TIMESTAMP CREATED_AT FIELD: " + tasks.get(0).getCreatedAt());
    return tasks;
  }

  public Task getTaskById(int id, Connection connection) throws SQLException {
    String sql = "SELECT task_id, task, completed, created_at FROM tasks WHERE task_id = ?;";
    PreparedStatement statement = connection.prepareStatement(sql);
    statement.setInt(1, id);

    ResultSet rs = statement.executeQuery();

    if (rs.next()) {
      return new Task(
              rs.getInt("task_id"),
              rs.getString("task"),
              rs.getBoolean("completed"),
              rs.getTimestamp("created_at")
      );
    }
    return null;
  }

  public int addTask(TaskRequest taskRequest, Connection connection) throws SQLException {
    String sql = "INSERT INTO tasks (task, completed, created_at) " +
            "VALUES (?, ?, CURRENT_TIMESTAMP());";

    PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    statement.setString(1, taskRequest.getTask());
    statement.setBoolean(2, taskRequest.isCompleted());

    int id = statement.executeUpdate();

    if (id == 0) {
      throw new SQLException("Failed to create task");
    }
    return id;
  }
}
