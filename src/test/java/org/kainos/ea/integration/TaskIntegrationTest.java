package org.kainos.ea.integration;

import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kainos.ea.TestingTasksApplication;
import org.kainos.ea.TestingTasksConfiguration;
import org.kainos.ea.cli.Task;
import org.kainos.ea.cli.TaskRequest;
import org.kainos.ea.cli.TaskResponse;
import org.kainos.ea.cli.TaskUpdateRequest;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.util.List;

@ExtendWith(DropwizardExtensionsSupport.class)
public class TaskIntegrationTest {

  static final DropwizardAppExtension<TestingTasksConfiguration> APP = new DropwizardAppExtension<>(
          TestingTasksApplication.class, null,
          new ResourceConfigurationSourceProvider()
  );

  @Test
  void getListOfTasks_shouldReturnListOfTaskResponse() {
    List<TaskResponse> response = APP.client()
            .target("http://localhost:8080/api/tasks")
            .request().get(List.class);

    Assertions.assertTrue(response.size() > 0);
  }

  @Test
  void getTask_shouldReturnTask() {
    Response response = APP.client()
            .target("http://localhost:8080/api/tasks/1")
            .request().get();
    Assertions.assertEquals(200, response.getStatus());
    Assertions.assertEquals(1, response.readEntity(TaskResponse.class).getTaskId());
  }

  @Test
  void addTask_shouldReturn201Status() {
    TaskRequest taskRequest = new TaskRequest("pints later?", false);
    Response response = APP.client()
            .target("http://localhost:8080/api/tasks/")
                    .request().post(Entity.json(taskRequest));

    Assertions.assertEquals(201, response.getStatus());
  }

  @Test
  void deleteTask_shouldReturn204Status() {
    Response response = APP.client()
            .target("http://localhost:8080/api/tasks/7")
            .request().delete();

    Assertions.assertEquals(204, response.getStatus());
  }

  @Test
  void updateTask_shouldReturn201Status() {
    TaskUpdateRequest taskUpdateRequest =
    new TaskUpdateRequest(2, "UPDATED", false);

    Response response = APP.client()
            .target("http://localhost:8080/api/tasks/5")
            .request().put(Entity.json(taskUpdateRequest));

    Assertions.assertEquals(204, response.getStatus());
  }
}
