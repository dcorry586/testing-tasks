package org.kainos.ea.validator;

import org.kainos.ea.cli.TaskRequest;
import org.kainos.ea.cli.TaskUpdateRequest;
import org.kainos.ea.client.TaskTooBigException;

public class TaskUpdateRequestValidator {

  public String isValidTask(TaskUpdateRequest taskRequest) {
    try {
      if (taskRequest.getTask().length() >= 200) {
        throw new TaskTooBigException();
      }
    } catch (TaskTooBigException e) {
      return e.getMessage();
    }
    return "VALID";
  }
}
