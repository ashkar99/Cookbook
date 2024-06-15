package cookbook.controller;

import cookbook.model.User;
import java.util.List;

/**
 * The {@code UserDao} interface provides methods for user authentication,
 * and for creating, updating, deleting, and retrieving users.
 */
public interface UserDao {

  /**
   * Authenticates a user based on the provided username and password.
   *
   * @param username the username of the user attempting to authenticate.
   * @param password the password of the user attempting to authenticate.
   * @return the authenticated {@code User} object if authentication is
   *         successful;
   *         otherwise, returns {@code null}.
   */
  User authenticateUser(String username, String password);

  /**
   * Creates a new user.
   *
   * @param user the {@code User} object to be created.
   */
  void createUser(User user);

  /**
   * Updates the information of an existing user.
   *
   * @param user the {@code User} object containing the updated information.
   */
  void updateUser(User user);

  /**
   * Removes a user based on their ID.
   *
   * @param userId the ID of the user to be removed.
   */
  void removeUser(int userId);

  /**
   * Retrieves a list of all users.
   *
   * @return a list of {@code User} objects representing all users.
   */
  List<User> getAllUsers();

}
