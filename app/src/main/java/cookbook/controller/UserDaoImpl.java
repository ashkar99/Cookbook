package cookbook.controller;

import cookbook.model.User;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code UserDaoImpl} class implements the {@code UserDao} interface.
 * It provides functionality for user authentication, creation, updating,
 * deletion, and retrieval of users.
 */
public class UserDaoImpl extends BaseDaoImpl implements UserDao {

  /**
   * Initializes a new instance of the {@code UserDaoImpl} class in production
   * mode.
   */
  public UserDaoImpl() {
    super(true);
  }

  /**
   * Initializes a new instance of the {@code UserDaoImpl} class with the
   * specified mode.
   *
   * @param production a boolean indicating if the instance is in production mode.
   */
  public UserDaoImpl(boolean production) {
    super(production);
  }

  /**
   * Authenticates a user based on the provided username and password.
   *
   * @param username the username of the user attempting to authenticate.
   * @param password the password of the user attempting to authenticate.
   * @return the authenticated {@code User} object if authentication is
   *         successful;
   *         otherwise, returns {@code null}.
   * @throws IllegalArgumentException if the credentials are invalid or user not
   *                                  found.
   */
  @Override
  public User authenticateUser(String username, String password) throws IllegalArgumentException {
    PreparedStatement ps = null;
    String sql = "SELECT * FROM user WHERE username = ? AND password = MD5(?)";
    User user = null;
    ResultSet rs = null;

    try {
      ps = con.prepareStatement(sql);
      ps.setString(1, username);
      ps.setString(2, password);
      rs = ps.executeQuery();

      if (rs.next()) {
        user = new User();
        user.setUsername(username);
        user.setId(rs.getInt("id"));
        user.setDisplayname(rs.getString("displayname"));
        user.setIsAdmin(rs.getInt("isadmin") == 1);
        user.setFirstname(rs.getString("firstname"));
        user.setLastname(rs.getString("lastname"));
        user.setPassword(rs.getString("password"));
      } else {
        throw new IllegalArgumentException(MessageFeedback.Error.User.AUTHENTICATION.getMessage());
      }
    } catch (SQLException e) {
      throw new IllegalArgumentException(MessageFeedback.Error.Sql.GENERIC.getMessage());
    } finally {
      closeResources(rs, ps);
    }

    return user;
  }

  /**
   * Creates a new user.
   *
   * @param user the {@code User} object to be created.
   * @throws IllegalArgumentException if there is an error during the creation
   *                                  process.
   */
  @Override
  public void createUser(User user) throws IllegalArgumentException {
    String sql = "INSERT INTO user (firstname, lastname, username, password, displayname, isadmin) VALUES (?, ?, ?, md5(?), ?, ?)";
    PreparedStatement ps = null;

    try {
      ps = con.prepareStatement(sql);
      ps.setString(1, user.getFirstname());
      ps.setString(2, user.getLastname());
      ps.setString(3, user.getUsername());
      ps.setString(4, user.getPassword());
      ps.setString(5, user.getDisplayname());
      ps.setBoolean(6, user.isAdmin());
      ps.executeUpdate();
    } catch (SQLException e) {
      throw new IllegalArgumentException(MessageFeedback.Error.Sql.GENERIC.getMessage());
    } finally {
      closeResources(null, ps);
    }
  }

  /**
   * Retrieves the password for a user based on their ID.
   *
   * @param userId the ID of the user whose password is being retrieved.
   * @return the user's password.
   * @throws IllegalArgumentException if there is an error during the retrieval
   *                                  process.
   */
  private String getUserPassword(int userId) throws IllegalArgumentException {
    String sql = "SELECT password FROM user WHERE id = ?";
    PreparedStatement ps = null;
    ResultSet rs = null;
    String password = null;

    try {
      ps = con.prepareStatement(sql);
      ps.setInt(1, userId);
      rs = ps.executeQuery();
      if (rs.next()) {
        password = rs.getString("password");
      }
    } catch (SQLException e) {
      throw new IllegalArgumentException(MessageFeedback.Error.Sql.GENERIC.getMessage());
    } finally {
      closeResources(rs, ps);
    }

    return password;
  }

  /**
   * Updates the information of an existing user.
   *
   * @param user the {@code User} object containing the updated information.
   * @throws IllegalArgumentException if there is an error during the update
   *                                  process.
   */
  @Override
  public void updateUser(User user) throws IllegalArgumentException {
    String existingPassword = getUserPassword(user.getId());
    boolean changedPassword = !existingPassword.equals(user.getPassword());
    String sql = changedPassword
        ? "UPDATE user SET firstname = ?, lastname = ?, username = ?, displayname = ?, isadmin = ?, password = md5(?) WHERE id = ?"
        : "UPDATE user SET firstname = ?, lastname = ?, username = ?, displayname = ?, isadmin = ? WHERE id = ?";
    PreparedStatement ps = null;

    try {
      ps = con.prepareStatement(sql);
      ps.setString(1, user.getFirstname());
      ps.setString(2, user.getLastname());
      ps.setString(3, user.getUsername());
      ps.setString(4, user.getDisplayname());
      ps.setBoolean(5, user.isAdmin());
      if (changedPassword) {
        ps.setString(6, user.getPassword());
        ps.setInt(7, user.getId());
      } else {
        ps.setInt(6, user.getId());
      }
      if (ps.executeUpdate() == 0) {
        throw new SQLException(MessageFeedback.Error.User.UPDATE.getMessage());
      }
    } catch (SQLException e) {
      throw new IllegalArgumentException(e.getMessage());
    } finally {
      closeResources(null, ps);
    }
  }

  /**
   * Deletes a user based on their ID.
   *
   * @param userId the ID of the user to be deleted.
   * @throws IllegalArgumentException if there is an error during the deletion
   *                                  process.
   */
  @Override
  public void removeUser(int userId) {
    String sql = "DELETE FROM user WHERE id = ?";
    PreparedStatement ps = null;

    try {
      ps = con.prepareStatement(sql);
      ps.setInt(1, userId);
      if (ps.executeUpdate() == 0) {
        throw new SQLException(MessageFeedback.Error.User.DELETE.getMessage());
      }
    } catch (SQLException e) {
      throw new IllegalArgumentException(e.getMessage());
    } finally {
      closeResources(null, ps);
    }
  }

  /**
   * Retrieves a list of all users.
   *
   * @return a list of {@code User} objects representing all users.
   * @throws IllegalArgumentException if there is an error during the retrieval
   *                                  process.
   */
  @Override
  @SuppressFBWarnings({ "EI_EXPOSE_REP", "EI_EXPOSE_REP2", "MS_EXPOSE_REP" })
  public List<User> getAllUsers() throws IllegalArgumentException {
    List<User> users = new ArrayList<>();
    String sql = "SELECT * FROM user WHERE isadmin = 0";

    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
      ps = con.prepareStatement(sql);
      rs = ps.executeQuery();
      while (rs.next()) {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setFirstname(rs.getString("firstname"));
        user.setLastname(rs.getString("lastname"));
        user.setUsername(rs.getString("username"));
        user.setDisplayname(rs.getString("displayname"));
        user.setIsAdmin(rs.getInt("isadmin") == 1);
        user.setPassword(rs.getString("password"));
        users.add(user);
      }
    } catch (SQLException e) {
      throw new IllegalArgumentException("Error: " + e.getMessage());
    } finally {
      closeResources(rs, ps);
    }

    return users;
  }
}
