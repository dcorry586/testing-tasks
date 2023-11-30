package org.kanos.ea.api;

import org.kanos.ea.cli.Task;

import java.sql.Timestamp;
import java.util.ArrayList;
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
