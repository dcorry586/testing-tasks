package org.kainos.ea.resources;

import io.swagger.annotations.Api;
import org.checkerframework.checker.units.qual.C;
import org.kainos.ea.api.TaskService;
import org.kainos.ea.cli.Task;
import org.kainos.ea.client.CannotGetEnvironmentVariableException;
import org.kainos.ea.db.DatabaseConnector;
import org.kainos.ea.db.TaskDao;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Api("Tasks API")
@Path("/api")
public class TaskController {

  TaskDao taskDao = new TaskDao();
  DatabaseConnector databaseConnector = new DatabaseConnector();

  TaskService taskService = new TaskService(taskDao, databaseConnector);

  @GET
  @Path("/tasks")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getAllTasks() {
    try {
      return Response.ok(taskService.getAllTasks()).build();
    } catch (SQLException | CannotGetEnvironmentVariableException e) {
      System.err.println(e.getMessage());
      return Response.serverError().build();
    }
  }

  @GET
  @Path("/tasks/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getTasksById(@PathParam("id") int id) {
    try {
      return Response.ok(taskService.getTaskById(id)).build();
    } catch (SQLException | CannotGetEnvironmentVariableException e) {
      System.err.println(e.getMessage());
      return Response.serverError().build();
    }
  }
}
