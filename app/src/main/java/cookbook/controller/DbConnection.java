package cookbook.controller;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Our DbConnection class.
 */
@SuppressFBWarnings({ "EI_EXPOSE_REP", "EI_EXPOSE_REP2", "MS_EXPOSE_REP" })
public class DbConnection {

  private static Connection con = null;

  /**
   * Sets the database connection based on the production flag.
   * Loads the database properties from the 'config.properties' file.
   *
   * @param production if true, connects to the production database;
   *                   if false, connects to the test database.
   */
  public static final Connection getConnection(boolean production) {

    if (con == null) {

      try (FileInputStream fileInputStream = new FileInputStream("config.properties")) {

        final Properties properties = new Properties();
        String url = null;

        properties.load(fileInputStream);
        if (production) {
          url = properties.getProperty("database.url");
        } else {
          url = properties.getProperty("database.urlTest");
        }
        final String user = properties.getProperty("database.user");
        final String pass = properties.getProperty("database.password");

        fileInputStream.close();

        try {
          con = DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
          throw new IllegalArgumentException("Connection to database failed.");
        }

      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    return con;
  }

}
