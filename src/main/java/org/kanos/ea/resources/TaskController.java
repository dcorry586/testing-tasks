package org.kanos.ea.resources;

import org.kanos.ea.api.TaskService;
import org.kanos.ea.cli.Task;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/api")
public class TaskController {
  TaskService taskService = new TaskService();
  @GET
  @Path("/tasks")
  @Produces(MediaType.APPLICATION_JSON)
  public List<Task> getAllTasks() {
    return taskService.getAllTasks();
  }
}
