package org.kainos.ea.resources;

import io.swagger.annotations.Api;
import org.checkerframework.checker.units.qual.C;
import org.kainos.ea.api.TaskService;
import org.kainos.ea.cli.Task;
import org.kainos.ea.cli.TaskRequest;
import org.kainos.ea.client.CannotGetEnvironmentVariableException;
import org.kainos.ea.client.InvalidEntryException;
import org.kainos.ea.db.DatabaseConnector;
import org.kainos.ea.db.TaskDao;
import org.kainos.ea.validator.TaskValidator;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Api("Tasks API")
@Path("/api")
public class TaskController {

  TaskDao taskDao = new TaskDao();
  private static final TaskValidator taskValidator = new TaskValidator();
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

  @POST
  @Path("/tasks")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response addTask(TaskRequest taskRequest) {
    System.out.println("ADD TASK!!!");
    try {
      if (taskValidator.isValidTask(taskRequest).equals("VALID")) {
        if (taskService.addTask(taskRequest) == 0) {
          throw new InvalidEntryException();
        } else {
          return Response.status(Response.Status.CREATED).build();
        }
      }
    } catch (SQLException | CannotGetEnvironmentVariableException e) {
      System.err.println(e.getMessage());
      return Response.serverError().entity(e.getMessage()).build();
    } catch (InvalidEntryException e) {
      System.err.println(e.getMessage());
      return Response.status(Response.Status.BAD_REQUEST).build();
    }
    return null;
  }
}
