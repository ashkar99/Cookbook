package cookbook.model;

import cookbook.controller.MessageFeedback;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.regex.Pattern;

/**
 * The {@code Tag} class represents a tag that can be used to categorize and
 * label recipes.
 * It includes details such as the tag's ID, name, owner ID, background color,
 * and text color.
 */
public final class Tag {

  int id;
  int ownerId;
  String name;
  String backgroundColor;
  String textColor;

  static final int MAX_NAME_LENGTH = 20;
  static final String HEX_COLOR_REGEX = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$";

  /**
   * Constructs a new {@code Tag} with default values.
   */
  public Tag() {
    setName("Newtag");
    setBackgroundColor("#fdf6e3");
    setTextColor("#000000");
  }

  /**
   * Constructs a new {@code Tag} with the specified details.
   *
   * @param id              the internal ID of the tag.
   * @param name            the name of the tag.
   * @param ownerId         the ID of the user that owns the tag.
   * @param backgroundColor the background color of the tag.
   * @param textColor       the text color of the tag.
   */
  @SuppressFBWarnings("EI_EXPOSE_REP")
  public Tag(int id, String name, int ownerId, String backgroundColor, String textColor) {
    setId(id);
    setName(name);
    setOwnerId(ownerId);
    setBackgroundColor(backgroundColor);
    setTextColor(textColor);
  }

  /**
   * Returns the internal ID of the tag.
   *
   * @return the internal ID.
   */
  public int getId() {
    return id;
  }

  /**
   * Sets the internal ID of the tag.
   *
   * @param id the internal ID. Must be an integer greater than 0.
   * @throws IllegalArgumentException if the ID is less than 1.
   */
  public void setId(int id) {
    if (id < 1) {
      throw new IllegalArgumentException(MessageFeedback.Error.Tag.ID.getMessage());
    }
    this.id = id;
  }

  /**
   * Returns the name of the tag.
   *
   * @return the name of the tag.
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name of the tag.
   *
   * @param name the name of the tag. Must be a string of at most 20 characters.
   * @throws IllegalArgumentException if the name is longer than 20 characters.
   */
  public void setName(String name) {
    if (name.isBlank() || name.length() > MAX_NAME_LENGTH) {
      throw new IllegalArgumentException(MessageFeedback.Error.Tag.NAME.getMessage());
    }
    this.name = name;
  }

  /**
   * Returns the ID of the user that owns the tag.
   *
   * @return the owner ID.
   */
  public int getOwnerId() {
    return ownerId;
  }

  /**
   * Sets the owner ID of the tag.
   *
   * @param ownerId the owner ID. Must be an integer greater than or equal to 0.
   * @throws IllegalArgumentException if the owner ID is less than 0.
   */
  public void setOwnerId(int ownerId) {
    if (ownerId < 0) {
      throw new IllegalArgumentException(MessageFeedback.Error.Tag.OWNER.getMessage());
    }
    this.ownerId = ownerId;
  }

  /**
   * Returns the background color of the tag.
   *
   * @return the background color.
   */
  public String getBackgroundColor() {
    return backgroundColor;
  }

  /**
   * Sets the background color of the tag.
   *
   * @param backgroundColor the background color. Must be a valid hex color code.
   * @throws IllegalArgumentException if the background color is null or invalid.
   */
  public void setBackgroundColor(String backgroundColor) {
    if (backgroundColor == null || !Pattern.matches(HEX_COLOR_REGEX, backgroundColor)) {
      throw new IllegalArgumentException(MessageFeedback.Error.Tag.BACKGROUND_COLOR.getMessage());
    }
    this.backgroundColor = backgroundColor;
  }

  /**
   * Returns the text color of the tag.
   *
   * @return the text color.
   */
  public String getTextColor() {
    return textColor;
  }

  /**
   * Sets the text color of the tag.
   *
   * @param textColor the text color. Must be a valid hex color code.
   * @throws IllegalArgumentException if the text color is null or invalid.
   */
  public void setTextColor(String textColor) {
    if (textColor == null || !Pattern.matches(HEX_COLOR_REGEX, textColor)) {
      throw new IllegalArgumentException(MessageFeedback.Error.Tag.TEXT_COLOR.getMessage());
    }
    this.textColor = textColor;
  }
}
