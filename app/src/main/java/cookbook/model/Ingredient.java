package cookbook.model;

import cookbook.controller.MessageFeedback;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * The {@code Ingredient} class represents an ingredient used in a recipe.
 * It includes details such as the ingredient's ID, name, quantity, owner, unit,
 * and a formatted quantity string.
 */
@SuppressFBWarnings("EI_EXPOSE_REP")
public class Ingredient {

  private int id;
  private String name;
  private double qty;
  private int owner;
  private Unit unit;
  private String qtyStr;

  /**
   * Constructs an empty {@code Ingredient}.
   */
  public Ingredient() {
  }

  /**
   * Sets the internal ID of the ingredient.
   *
   * @param id the internal ID.
   * @throws IllegalArgumentException if the ID is less than 1.
   */
  public void setId(int id) {
    if (id < 1) {
      throw new IllegalArgumentException(MessageFeedback.Error.Ingredient.ID.getMessage());
    }
    this.id = id;
  }

  /**
   * Sets the name of the ingredient.
   *
   * @param name the name of the ingredient.
   * @throws IllegalArgumentException if the name is null or empty.
   */
  public void setName(String name) {
    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException(MessageFeedback.Error.Ingredient.NAME.getMessage());
    }
    this.name = name;
  }

  /**
   * Sets the quantity of the ingredient.
   *
   * @param input the quantity of the ingredient.
   * @throws IllegalArgumentException if the quantity is less than 0.
   */
  public void setQty(String input) {

    try {
      double qty = Double.parseDouble(input);
      setQty(qty);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException(MessageFeedback.Error.Ingredient.QTY_STR.getMessage());
    }
  }

  /**
   * Sets the quantity of the ingredient.
   *
   * @param qty the quantity of the ingredient.
   * @throws IllegalArgumentException if the quantity is less than 0.
   */
  public void setQty(Double qty) {

    if (qty < 0) {
      throw new IllegalArgumentException(MessageFeedback.Error.Ingredient.QTY.getMessage());
    }
    this.qty = qty;
    updateQtyStr();
  }

  /**
   * Sets the owner ID of the ingredient.
   *
   * @param owner the owner ID.
   * @throws IllegalArgumentException if the owner ID is less than 0.
   */
  public void setOwner(int owner) {
    if (owner < 0) {
      throw new IllegalArgumentException(MessageFeedback.Error.Ingredient.OWNER.getMessage());
    }
    this.owner = owner;
  }

  /**
   * Returns the internal ID of the ingredient.
   *
   * @return the internal ID.
   */
  public int getId() {
    return id;
  }

  /**
   * Returns the name of the ingredient.
   *
   * @return the name of the ingredient.
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the quantity of the ingredient.
   *
   * @return the quantity of the ingredient.
   */
  public double getQty() {
    return qty;
  }

  /**
   * Returns the owner ID of the ingredient.
   *
   * @return the owner ID.
   */
  public int getOwner() {
    return owner;
  }

  /**
   * Returns the formatted quantity string of the ingredient.
   *
   * @return the formatted quantity string.
   */
  public String getQtyStr() {
    return qtyStr;
  }

  /**
   * Sets the formatted quantity string of the ingredient.
   *
   * @param qtyStr the formatted quantity string.
   */
  public void setQtyStr(String qtyStr) {
    this.qtyStr = qtyStr;
  }

  /**
   * Updates the formatted quantity string based on the quantity and unit.
   */
  public void updateQtyStr() {
    if (unit == null) {
      return;
    }
    if (qty == 0) {
      this.qtyStr = "";
    } else {
      this.qtyStr = qty + " " + unit.getName();
    }
  }

  /**
   * Returns the unit of the ingredient.
   *
   * @return the unit of the ingredient.
   */
  @SuppressFBWarnings("EI_EXPOSE_REP")
  public Unit getUnit() {
    return this.unit;
  }

  /**
   * Sets the unit of the ingredient.
   *
   * @param unit the unit of the ingredient.
   */
  public void setUnit(Unit unit) {
    this.unit = unit;
    updateQtyStr();
  }
}
