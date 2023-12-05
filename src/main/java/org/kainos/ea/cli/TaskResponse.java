package org.kainos.ea.cli;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;

public class TaskResponse {
  private int taskId;
  private String task;
  private boolean completed;

  private Timestamp createdAt;
  private Timestamp updatedAt;

  @JsonCreator
  public TaskResponse(@JsonProperty("taskId") int taskId,
                      @JsonProperty("task") String task,
                      @JsonProperty("completed") boolean completed,
                      @JsonProperty("createdAt") Timestamp createdAt) {
    this.taskId = taskId;
    this.task = task;
    this.completed = completed;
    this.createdAt = createdAt;
  }

  public int getTaskId() {
    return taskId;
  }

  public void setTaskId(int taskId) {
    this.taskId = taskId;
  }

  public String getTask() {
    return task;
  }

  public void setTask(String task) {
    this.task = task;
  }

  public boolean isCompleted() {
    return completed;
  }

  public void setCompleted(boolean completed) {
    this.completed = completed;
  }

  public Timestamp getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Timestamp createdAt) {
    this.createdAt = createdAt;
  }

  public Timestamp getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Timestamp updatedAt) {
    this.updatedAt = updatedAt;
  }
}
