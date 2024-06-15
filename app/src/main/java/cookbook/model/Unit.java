package cookbook.model;

import cookbook.controller.MessageFeedback;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * The {@code Unit} class represents a unit of cooking measurement.
 * It includes details such as the unit's ID and name.
 */
public final class Unit {

  private int id;
  private String name;

  /**
   * Constructs a new {@code Unit} with the specified details.
   *
   * @param id   the ID of the unit.
   * @param name the name of the unit.
   */
  @SuppressFBWarnings("EI_EXPOSE_REP")
  public Unit(int id, String name) {
    setId(id);
    setName(name);
  }

  /**
   * Constructs an empty {@code Unit}.
   */
  @SuppressFBWarnings("EI_EXPOSE_REP")
  public Unit() {
  }

  /**
   * Returns the ID of the unit.
   *
   * @return the ID of the unit.
   */
  public int getId() {
    return id;
  }

  /**
   * Sets the ID of the unit.
   *
   * @param id the ID of the unit. Must be an integer greater than 0.
   * @throws IllegalArgumentException if the ID is less than 1.
   */
  public void setId(int id) {
    if (id < 1) {
      throw new IllegalArgumentException(MessageFeedback.Error.Unit.ID.getMessage());

    }
    this.id = id;
  }

  /**
   * Returns the name of the unit.
   *
   * @return the name of the unit.
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name of the unit.
   *
   * @param name the name of the unit. Must be a non-null, non-empty string.
   * @throws IllegalArgumentException if the name is null or empty.
   */
  public void setName(String name) {
    if (name == null || name.isEmpty()) {

      throw new IllegalArgumentException(MessageFeedback.Error.Unit.NAME.getMessage());
    }
    this.name = name;
  }
}
