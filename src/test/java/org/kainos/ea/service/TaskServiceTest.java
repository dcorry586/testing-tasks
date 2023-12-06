package org.kainos.ea.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kainos.ea.api.TaskService;
import org.kainos.ea.cli.Task;
import org.kainos.ea.cli.TaskRequest;
import org.kainos.ea.cli.TaskResponse;
import org.kainos.ea.client.CannotGetEnvironmentVariableException;
import org.kainos.ea.db.DatabaseConnector;
import org.kainos.ea.db.TaskDao;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

  TaskDao taskDao = mock(TaskDao.class);
  DatabaseConnector databaseConnector = mock(DatabaseConnector.class);
  TaskService taskService = new TaskService(taskDao, databaseConnector);

  Connection connection;

  private static final List<Task> records = Arrays.asList(
          new Task(1, "Task 1", false, new Timestamp(System.currentTimeMillis())),
          new Task(2, "Task 2", true, new Timestamp(System.currentTimeMillis()))
  );

  @Test
  public void getAllTasks_shouldReturnTasks() throws SQLException, CannotGetEnvironmentVariableException {
    List<TaskResponse> expectedResult = Arrays.asList(
            new TaskResponse(1, "Task 1", false, new Timestamp(System.currentTimeMillis())),
            new TaskResponse(2, "Task 2", true, new Timestamp(System.currentTimeMillis()))
    );
    Mockito.when(databaseConnector.getConnection()).thenReturn(connection);
    Mockito.when(taskDao.getAllTasks(connection)).thenReturn(expectedResult);
    List<TaskResponse> result = taskService.getAllTasks();
    assertEquals(result, expectedResult);
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

  @Test
  public void addTask_shouldReturnOne_whenDaoReturnsId() throws SQLException, CannotGetEnvironmentVariableException {
    // Mock database connection
    Mockito.when(databaseConnector.getConnection()).thenReturn(connection);

    // Create TaskRequest object to store in DB.
    TaskRequest taskRequest = new TaskRequest("go for run", false);

    int expectedResult = 1;

    // Mock record in Database
    Mockito.when(taskDao.addTask(taskRequest, connection)).thenReturn(expectedResult);


    // Tell service to add taskRequest
    int result = taskService.addTask(taskRequest);

    // assert
    assertEquals(expectedResult, result);
  }

  @Test
  public void addTask_shouldThrowSQLException_whenDaoThrowsSQLException() throws SQLException, CannotGetEnvironmentVariableException {
    // Mock database connection
    Mockito.when(databaseConnector.getConnection()).thenReturn(connection);
    // Tell Dao to throw SQLException

    TaskRequest taskRequest = new TaskRequest("go for run", false);
    Mockito.when(taskDao.addTask(taskRequest, connection))
            .thenThrow(SQLException.class);

    // Assert SQL exception throws from service class
    assertThrows(SQLException.class, () -> {
      taskService.addTask(taskRequest);
    });
  }
}
