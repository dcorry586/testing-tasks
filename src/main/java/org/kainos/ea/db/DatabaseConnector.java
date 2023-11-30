package org.kainos.ea.db;

import org.kainos.ea.client.CannotGetEnvironmentVariableException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
  public static Connection connection;

  public DatabaseConnector() {
  }

  public Connection getConnection() throws SQLException, CannotGetEnvironmentVariableException {
    String user;
    String password;
    String host;
    String name;

    if (connection != null && !connection.isClosed()) {
      return connection;
    }

    try {
      user = System.getenv("DB_USERNAME");
      password = System.getenv("DB_PASSWORD");
      host = System.getenv("DB_HOST");
      name = System.getenv("DB_NAME");

      if (user == null || password == null || host == null || name == null) {
        throw new CannotGetEnvironmentVariableException();
      }

      connection = DriverManager.getConnection("jdbc:mysql://" + host + "/"
              + name + "?useSSL=false", user, password);

      return connection;
    } catch (CannotGetEnvironmentVariableException e) {
      System.err.println(e.getMessage());
    }

    return null;
  }
}
