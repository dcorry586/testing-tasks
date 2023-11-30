package org.kainos.ea.resources;

import io.swagger.annotations.Api;
import org.kainos.ea.api.TaskService;
import org.kainos.ea.cli.Task;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Api("Tasks API")
@Path("/api")
public class TaskController {
  TaskService taskService = new TaskService();

  @GET
  @Path("/tasks")
  @Produces(MediaType.APPLICATION_JSON)
  public List<Task> getAllTasks() {
    return taskService.getAllTasks();
  }

  @GET
  @Path("/tasks/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Task getTasksById(@PathParam("id") int id) {
    return taskService.getTaskById(id);
  }
}
