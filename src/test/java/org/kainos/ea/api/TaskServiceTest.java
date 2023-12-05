package org.kainos.ea.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.kainos.ea.cli.Task;
import org.kainos.ea.client.CannotGetEnvironmentVariableException;
import org.kainos.ea.db.DatabaseConnector;
import org.kainos.ea.db.TaskDao;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

  TaskDao taskDao = mock(TaskDao.class);
  DatabaseConnector databaseConnector = mock(DatabaseConnector.class);
  TaskService taskService = new TaskService(taskDao, databaseConnector);

  Connection connection;

  private  static final List<Task> records = Arrays.asList(
          new Task(1, "Task 1", false, new Timestamp(System.currentTimeMillis())),
          new Task(2, "Task 2", true, new Timestamp(System.currentTimeMillis()))
  );

  @Test
  public void getAllTasks_shouldReturnTasks() throws SQLException, CannotGetEnvironmentVariableException {
    Mockito.when(databaseConnector.getConnection()).thenReturn(connection);
    Mockito.when(taskDao.getAllTasks(connection)).thenReturn(records);
    List<Task> result = taskService.getAllTasks();
    assertEquals(result, records);
  }

  @Test
  public void getTasksById_shouldThrowSQLException_whenDaoThrowsSQLException() throws SQLException, CannotGetEnvironmentVariableException {
    // Mock database connection
    Mockito.when(databaseConnector.getConnection()).thenReturn(connection);

    // Create invalid id.
    int id = -1;

    // Tell stub to throw SQL exception when getting calling service method
    Mockito.when(taskDao.getTaskById(id, connection)).thenReturn(null);

    // assert that it was caught
    assertThrows(SQLException.class, () -> {
      taskService.getTaskById(id);
    });
  }

  @Test
  public void getTasksByID_shouldReturnTask() throws SQLException, CannotGetEnvironmentVariableException {
    // Mock database connection
    Mockito.when(databaseConnector.getConnection()).thenReturn(connection);
    // Create valid id that points to first element in records list field
    int id = records.get(0).getTaskId();
    // Tell stub to return record
    Mockito.when(taskDao.getTaskById(id, connection)).thenReturn(records.get(0));
    // assert result id is equal to id field of first element in records list
    assertEquals(id, taskService.getTaskById(id).getTaskId());
  }
}
