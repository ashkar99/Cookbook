
package cookbook.controller;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * BaseDaoImpl is an abstract class that provides basic database operations
 * such as establishing a connection to a database and cleaning up resources.
 * The connection can be set to either a production or test environment based on
 * the provided flag.
 */
public abstract class BaseDaoImpl {

  Connection con = null;

  /**
   * Constructs a BaseDaoImpl object and initializes the database connection.
   *
   * @param production if true, connects to the production database;
   *                   if false, connects to the test database.
   * @throws IllegalArgumentException if the connection to the database fails.
   */
  @SuppressFBWarnings // Finalizer "attacks"
  protected BaseDaoImpl(boolean production) {

    this.con = DbConnection.getConnection(production);

    if (con == null) {
      throw new IllegalArgumentException("Connection to database failed.");
    }
  }

  /**
   * Closes the provided ResultSet and PreparedStatement resources if they are not
   * null.
   *
   * @param rs the ResultSet to close.
   * @param ps the PreparedStatement to close.
   * @throws IllegalArgumentException if there is an error while closing the
   *                                  resources.
   */
  protected void closeResources(ResultSet rs, PreparedStatement ps) {
    if (rs != null) {
      try {
        rs.close();
      } catch (Exception e) {
        throw new IllegalArgumentException("There was an internal error.");
      }
    }

    if (ps != null) {
      try {
        ps.close();
      } catch (Exception e) {
        throw new IllegalArgumentException("There was an internal error.");
      }
    }
  }
}
