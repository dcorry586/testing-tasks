package org.kainos.ea.api;

import org.kainos.ea.cli.Task;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

public class TaskService {

  public List<Task> getAllTasks() {
    return Arrays.asList(
            new Task(1, "walk dog", false, new Timestamp(System.currentTimeMillis())),
            new Task(2, "take out bins", false, new Timestamp(System.currentTimeMillis()))
    );

  }
}
