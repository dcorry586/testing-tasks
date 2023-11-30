package org.kainos.ea.cli;

import java.sql.Timestamp;

public class Task {
  private int taskId;
  private String task;
  private boolean completed;

  private Timestamp createdAt;
  private Timestamp updatedAt;

  public Task(int taskId, String task, boolean completed, Timestamp createdAt) {
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
