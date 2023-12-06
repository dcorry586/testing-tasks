package org.kainos.ea.resources;

import io.swagger.annotations.Api;
import org.kainos.ea.api.TaskService;
import org.kainos.ea.cli.TaskRequest;
import org.kainos.ea.cli.TaskResponse;
import org.kainos.ea.client.CannotGetEnvironmentVariableException;
import org.kainos.ea.client.InvalidEntryException;
import org.kainos.ea.validator.TaskValidator;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;

@Api("Tasks API")
@Path("/api")
public class TaskController {
  private static final TaskValidator taskValidator = new TaskValidator();

  TaskService taskService;

  public TaskController(TaskService taskService) {
    this.taskService = taskService;
  }

  @GET
  @Path("/tasks")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getAllTasks() {
    try {

      List<TaskResponse> tasks = taskService.getAllTasks();
      if (tasks != null) {
        return Response.ok().entity(tasks).build();
      } else {
        throw new SQLException("No records found.");
      }
    } catch (SQLException | CannotGetEnvironmentVariableException e) {
      System.err.println("Add Task Controller Err: " + e.getMessage());
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

  @DELETE
  @Path("/tasks/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response deleteTask(@PathParam("id") int id) {
    try {
      String result = taskService.deleteTask(id);
      if (result.equals("VALID")) {
        return Response.accepted().build();
      }
      if (result.equals("Cannot find resource")) {
        return Response.status(Response.Status.BAD_REQUEST).entity(result).build();
      }
    } catch (SQLException e) {
      System.err.println(e.getMessage());
      return Response.serverError().entity(e.getMessage()).build();
    } catch (CannotGetEnvironmentVariableException e) {
      System.err.println(e.getMessage());
    }
    return null;
  }
}
