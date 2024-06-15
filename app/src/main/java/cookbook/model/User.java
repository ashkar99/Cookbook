package cookbook.model;

import cookbook.controller.MessageFeedback;

/**
 * Represents a user in the system.
 * It includes details such as the user's ID, username, password, first name,
 * last name,
 * display name, and whether the user has administrative privileges.
 */
public final class User {

  int id;
  boolean isAdmin;
  String username;
  String firstname;
  String lastname;
  String password;
  String displayname;

  static final int MIN_PASSWORD_LEN = 7;
  static final int LASTNAME_LENGTH = 45;
  static final int MAX_USERNAME_LENGTH = 45;
  static final int FIRSTNAME_LENGTH = 45;
  static final int DISPLAYNAME_LENGTH = 45;

  /**
   * Constructs a new {@code User} object with the specified attributes.
   *
   * @param id          The unique identifier of the user.
   * @param isAdmin     Indicates whether the user has administrative privileges.
   * @param username    The username of the user.
   * @param firstname   The first name of the user.
   * @param lastname    The last name of the user.
   * @param password    The password of the user.
   * @param displayname The display name of the user.
   */
  public User(int id, boolean isAdmin, String username, String firstname, String lastname, String password,
      String displayname) {
    this.setId(id);
    this.setIsAdmin(isAdmin);
    this.setUsername(username);
    this.setFirstname(firstname);
    this.setLastname(lastname);
    this.setPassword(password);
    this.setDisplayname(displayname);
  }

  /**
   * Constructs a new empty {@code User} object.
   */
  public User() {
  }

  /**
   * Gets the username of the user.
   *
   * @return The username of the user.
   */
  public String getUsername() {
    return username;
  }

  /**
   * Sets the username of the user.
   *
   * @param username The username to be set.
   * @throws IllegalArgumentException If the username is null, empty, or exceeds
   *                                  45 characters.
   */
  public void setUsername(String username) {
    if (username == null || username.isBlank() || username.length() > MAX_USERNAME_LENGTH) {
      throw new IllegalArgumentException(MessageFeedback.Error.User.USERNAME.getMessage());
    }

    this.username = username;
  }

  /**
   * Gets the first name of the user.
   *
   * @return The first name of the user.
   */
  public String getFirstname() {
    return firstname;
  }

  /**
   * Sets the first name of the user.
   *
   * @param firstname The first name to be set.
   * @throws IllegalArgumentException If the first name is null, empty, or exceeds
   *                                  45 characters.
   */
  public void setFirstname(String firstname) {
    if (firstname == null || firstname.isBlank() || firstname.length() > FIRSTNAME_LENGTH) {
      throw new IllegalArgumentException(MessageFeedback.Error.User.FIRSTNAME.getMessage());
    }

    this.firstname = firstname;
  }

  /**
   * Gets the last name of the user.
   *
   * @return The last name of the user.
   */
  public String getLastname() {
    return lastname;
  }

  /**
   * Sets the last name of the user.
   *
   * @param lastname The last name to be set.
   * @throws IllegalArgumentException If the last name is null, empty, or exceeds
   *                                  45 characters.
   */
  public void setLastname(String lastname) {
    if (lastname == null || lastname.isBlank() || lastname.length() > LASTNAME_LENGTH) {
      throw new IllegalArgumentException(MessageFeedback.Error.User.LASTNAME.getMessage());
    }

    this.lastname = lastname;
  }

  /**
   * Gets the password of the user.
   *
   * @return The password of the user.
   */
  public String getPassword() {
    return password;
  }

  /**
   * Sets the password of the user.
   *
   * @param password The password to be set.
   * @throws IllegalArgumentException If the password is null, empty, or less than
   *                                  8 characters long.
   */
  public void setPassword(String password) {
    if (password == null || password.isBlank() || password.length() <= MIN_PASSWORD_LEN) {
      throw new IllegalArgumentException(MessageFeedback.Error.User.PASSWORD.getMessage());
    }
    this.password = password;
  }

  /**
   * Gets the unique identifier of the user.
   *
   * @return The unique identifier of the user.
   */
  public int getId() {
    return id;
  }

  /**
   * Sets the unique identifier of the user.
   *
   * @param id The unique identifier to be set.
   * @throws IllegalArgumentException If the id is negative.
   */
  public void setId(int id) {
    if (id < 0) {
      throw new IllegalArgumentException("Id cannot be negative.");
    }
    this.id = id;
  }

  /**
   * Checks if the user has administrative privileges.
   *
   * @return {@code true} if the user has administrative privileges, {@code false}
   *         otherwise.
   */
  public boolean isAdmin() {
    return isAdmin;
  }

  /**
   * Sets whether the user has administrative privileges.
   *
   * @param isAdmin Indicates whether the user has administrative privileges.
   */
  public void setIsAdmin(boolean isAdmin) {
    this.isAdmin = isAdmin;
  }

  /**
   * Gets the display name of the user.
   *
   * @return The display name of the user.
   */
  public String getDisplayname() {
    return displayname;
  }

  /**
   * Sets the display name of the user.
   *
   * @param displayname The display name to be set.
   * @throws IllegalArgumentException If the display name is null, empty, or
   *                                  exceeds 45 characters.
   */
  public void setDisplayname(String displayname) {
    if (displayname == null || displayname.isBlank() || displayname.length() > DISPLAYNAME_LENGTH) {
      throw new IllegalArgumentException(MessageFeedback.Error.User.SETDISPLAYNAME.getMessage());
    }

    this.displayname = displayname;
  }
}
