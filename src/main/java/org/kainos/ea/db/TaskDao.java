package org.kainos.ea.db;

import org.kainos.ea.cli.Task;
import org.kainos.ea.client.CannotGetEnvironmentVariableException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDao {

  public TaskDao() {
  }

  public List<Task> getAllTasks(Connection connection) throws SQLException {
    Statement statement = connection.createStatement();
    ResultSet rs = statement.executeQuery("SELECT task_id, task, completed, created_at FROM tasks;");

    List<Task> tasks = new ArrayList<>();

    while (rs.next()) {
      tasks.add(new Task(
              rs.getInt("task_id"),
              rs.getString("task"),
              rs.getBoolean("completed"),
              rs.getTimestamp("created_at")
      ));
    }
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
}
