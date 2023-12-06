package org.kainos.ea.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.kainos.ea.api.TaskService;
import org.kainos.ea.cli.TaskResponse;
import org.kainos.ea.client.CannotGetEnvironmentVariableException;
import org.kainos.ea.db.DatabaseConnector;
import org.kainos.ea.db.TaskDao;
import org.kainos.ea.resources.TaskController;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TaskControllerTest {
  private static TaskService taskService;
  private static TaskController taskController;
  private static TaskDao taskDao;
  private static DatabaseConnector databaseConnector;

  @BeforeAll
  public static void setUp() {
    taskDao = mock(TaskDao.class);
    databaseConnector = mock(DatabaseConnector.class);
    taskService = new TaskService(taskDao, databaseConnector);
    taskController = new TaskController(taskService);
  }

  @Test
  public void testGetAllTasksSuccess() throws SQLException, CannotGetEnvironmentVariableException {
    List<TaskResponse> tasks = Arrays.asList(
            new TaskResponse(1, "task", false, new Timestamp(System.currentTimeMillis())),
            new TaskResponse(2, "another one", false, new Timestamp(System.currentTimeMillis()))
    );
    when(taskService.getAllTasks()).thenReturn(tasks);

    Response response = taskController.getAllTasks();
    // Check the status code
    assertEquals(200, response.getStatus());
    // Check the id of second element
    assertEquals(2, tasks.get(1).getTaskId());
    // Check size is 2
    assertEquals(2, tasks.size());
  }

  @Test
  public void testGetAllTasksFail() throws SQLException, CannotGetEnvironmentVariableException {
    when(taskService.getAllTasks()).thenReturn(null);

    Response response = taskController.getAllTasks();
    // Check the status code
    assertEquals(500, response.getStatus());
  }

@Test
  public void deleteTaskFail() throws SQLException, CannotGetEnvironmentVariableException {
    Response response = taskController.deleteTask(-3);
  assertEquals(400, response.getStatus());
  }

  @Test
  public void deleteTaskSuccess() throws SQLException, CannotGetEnvironmentVariableException {
  String expectedResult = "VALID";
    Mockito.when(taskService.deleteTask(1)).thenReturn(expectedResult);
    Response response = taskController.deleteTask(1);
    assertEquals(202, response.getStatus());
  }

// TODO: Unit tests for Update Task
}
