package org.kainos.ea.client;

public class InvalidEntryException extends Throwable {
  public InvalidEntryException() {
 super("Invalid entry. Please check your inputs and try again.");
  }
}
