package org.kainos.ea.db;

import org.kainos.ea.cli.Task;
import org.kainos.ea.client.CannotGetEnvironmentVariableException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TaskDao {

  private DatabaseConnector databaseConnector = new DatabaseConnector();

  public List<Task> getAllTasks() {
    try (Connection connection = databaseConnector.getConnection()) {
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
    } catch (SQLException | CannotGetEnvironmentVariableException e) {
      System.err.println(e.getMessage());
    }
    return null;
  }

}
