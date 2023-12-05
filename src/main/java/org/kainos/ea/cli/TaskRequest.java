package org.kainos.ea.cli;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;

public class TaskRequest {
  private String task;
  private boolean completed;
  private Timestamp createdAt;
  private Timestamp updatedAt;

  @JsonCreator
  public TaskRequest(@JsonProperty("task") String task,
                     @JsonProperty("completed") boolean completed) {
    this.task = task;
    this.completed = completed;
    setCreatedAt(); // SERIALISE AND DESERIALISE JSON
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

  public void setCreatedAt() {
    long now = System.currentTimeMillis();
    this.createdAt = new Timestamp(now);
  }

  public Timestamp getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Timestamp updatedAt) {
    this.updatedAt = updatedAt;
  }
}
