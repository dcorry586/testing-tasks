package org.kainos.ea.client;

public class CannotGetEnvironmentVariableException extends Exception {
  public CannotGetEnvironmentVariableException() {
    super("Failed to read an environment variable.");
  }
}
