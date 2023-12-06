package org.kainos.ea.client;

public class TaskTooBigException extends Throwable {
  public TaskTooBigException() {
    super("The task has too many characters.");
  }
}
