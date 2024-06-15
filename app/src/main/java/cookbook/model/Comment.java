package cookbook.model;

import cookbook.controller.MessageFeedback;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * The {@code Comment} class represents a comment made by a user on a recipe.
 * It contains information about the user, the recipe, the comment text, and
 * timestamps.
 */
public final class Comment {

  int id;
  int userId;
  int recipeId;
  String text;
  Long createdDate;
  Long updatedDate;
  String timeSince;
  String displayName;

  static final int MAX_NAME_LENGTH = 20;

  /**
   * Constructs a new {@code Comment} with the specified details.
   *
   * @param id          the ID of the comment.
   * @param userId      the user that created the comment.
   * @param recipeId    the ID of the recipe that the comment belongs to.
   * @param text        the comment text.
   * @param date        the time/date when the comment was made.
   * @param displayName the display name of the user who made the comment.
   * @param updatedDate the time/date when the comment was last updated.
   */
  @SuppressFBWarnings("EI_EXPOSE_REP")
  public Comment(int id, int userId, int recipeId, String text, Long date, String displayName, Long updatedDate) {
    setId(id);
    setUserId(userId);
    setRecipeId(recipeId);
    setText(text);
    setDate(date);
    setDisplayName(displayName);
    setUpdatedDate(updatedDate);
  }

  /**
   * Constructs an empty {@code Comment}.
   */
  public Comment() {
  }

  /**
   * Returns the ID of the user who created the comment.
   *
   * @return the user ID.
   */
  public int getUserId() {
    return userId;
  }

  /**
   * Sets the ID of the user who created the comment.
   *
   * @param userId the user ID.
   * @throws IllegalArgumentException if the user ID is invalid.
   */
  public void setUserId(int userId) {
    if (userId < 1) {
      throw new IllegalArgumentException(MessageFeedback.Error.Comment.USER_ID.getMessage());
    }
    this.userId = userId;
  }

  /**
   * Returns the text of the comment.
   *
   * @return the comment text.
   */
  public String getText() {
    return text;
  }

  /**
   * Sets the text of the comment.
   *
   * @param text the comment text.
   * @throws IllegalArgumentException if the text is null or empty.
   */
  public void setText(String text) {
    if (text == null || text.isBlank()) {
      throw new IllegalArgumentException(MessageFeedback.Error.Comment.TEXT.getMessage());
    }
    this.text = text;
  }

  /**
   * Returns the creation date of the comment as a formatted string.
   *
   * @return the creation date.
   */
  public String getDate() {
    return convertToDateTime(createdDate);
  }

  /**
   * Sets the creation date of the comment.
   *
   * @param date the creation date.
   * @throws IllegalArgumentException if the date is invalid.
   */
  public void setDate(Long date) {
    if (date < 1) {
      throw new IllegalArgumentException(MessageFeedback.Error.Comment.DATE.getMessage());
    }
    this.createdDate = date;
  }

  /**
   * Returns the ID of the recipe that the comment belongs to.
   *
   * @return the recipe ID.
   */
  public int getRecipeId() {
    return recipeId;
  }

  /**
   * Sets the ID of the recipe that the comment belongs to.
   *
   * @param recipeId the recipe ID..
   * @throws IllegalArgumentException if the recipe ID is invalid.
   */
  public void setRecipeId(int recipeId) {
    if (recipeId < 1) {
      throw new IllegalArgumentException(MessageFeedback.Error.Comment.RECIPE_ID.getMessage());
    }
    this.recipeId = recipeId;
  }

  /**
   * Returns the display name of the user who made the comment.
   *
   * @return the display name.
   */
  public String getDisplayName() {
    return displayName;
  }

  /**
   * Sets the display name of the user who made the comment.
   *
   * @param displayName the display name.
   * @throws IllegalArgumentException if the display name is null, empty, or too
   *                                  long.
   */
  public void setDisplayName(String displayName) {
    if (displayName == null || displayName.isEmpty() || displayName.length() > MAX_NAME_LENGTH) {
      throw new IllegalArgumentException(MessageFeedback.Error.Comment.DISPLAY_NAME.getMessage());
    }
    this.displayName = displayName;
  }

  /**
   * Returns the ID of the comment.
   *
   * @return the comment ID.
   */
  public int getId() {
    return id;
  }

  /**
   * Sets the ID of the comment.
   *
   * @param id the comment ID.
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Returns the last updated date of the comment as a formatted string.
   *
   * @return the last updated date.
   */
  public String getUpdatedDate() {
    return convertToDateTime(updatedDate);
  }

  /**
   * Sets the last updated date of the comment.
   *
   * @param updatedDate the last updated date.
   * @throws IllegalArgumentException if the updated date is invalid.
   */
  public void setUpdatedDate(Long updatedDate) {
    if (updatedDate < 1) {
      throw new IllegalArgumentException(MessageFeedback.Error.Comment.UPDATED_DATE.getMessage());

    }
    this.updatedDate = updatedDate;
  }

  /**
   * Helper method to convert a timestamp to a formatted date-time string.
   *
   * @param date the timestamp to convert.
   * @return the formatted date-time string.
   */
  private String convertToDateTime(Long date) {
    Instant instant = Instant.ofEpochSecond(date);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        .withZone(ZoneId.of("Europe/Stockholm"));
    return formatter.format(instant);
  }

  /**
   * Returns the time since the comment was created or last updated as a
   * human-readable string.
   *
   * @return the time since the comment was created or updated.
   * @throws IllegalArgumentException if there is a timestamp error.
   */
  public String getTimeSince() {
    try {
      Instant creationInstant = Instant.ofEpochSecond(createdDate);
      Instant updateInstant = Instant.ofEpochSecond(updatedDate);
      Instant now = Instant.now();

      Duration duration;
      String word;

      if (updateInstant.equals(creationInstant)) {
        duration = Duration.between(creationInstant, now);
        word = "Created ";
      } else {
        duration = Duration.between(updateInstant, now);
        word = "Updated ";
      }

      long totalSeconds = Math.abs(duration.getSeconds());
      long minutes = totalSeconds / 60;
      long hours = minutes / 60;
      long days = hours / 24;

      minutes %= 60;
      hours %= 24;

      if (days > 0) {
        this.timeSince = String.format(word + "%d day(s) %d hour(s) %d minute(s) ago", days, hours, minutes);
      } else if (hours > 0) {
        this.timeSince = String.format(word + "%d hour(s) %d minute(s) ago", hours, minutes);
      } else if (minutes > 0) {
        this.timeSince = String.format(word + "%d minute(s) ago", minutes);
      } else {
        this.timeSince = word + "Just now";
      }
    } catch (Exception e) {
      this.timeSince = "Timestamp error.";

      throw new IllegalArgumentException(MessageFeedback.Error.Comment.TIMESTAMP.getMessage());

    }

    return timeSince;
  }
}
