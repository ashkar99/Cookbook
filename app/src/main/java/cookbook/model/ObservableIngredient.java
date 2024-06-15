package cookbook.model;

import cookbook.controller.MessageFeedback;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * The {@code ObservableIngredient} class extends {@code Ingredient} and
 * provides
 * observable properties that can have listeners attached. This class is
 * particularly
 * useful within a JavaFX application where changes to properties need to be
 * observed
 * by the UI for updates.
 */
public class ObservableIngredient extends Ingredient {

  private StringProperty name;

  /**
   * Constructs a new {@code ObservableIngredient}.
   * Initializes the name property and sets up a listener to update the super
   * class's name field on change.
   */
  public ObservableIngredient() {
    this.name = new SimpleStringProperty();
    name.addListener(changeEvent -> {
      super.setName(this.name.get());
    });
  }

  /**
   * Returns the {@code StringProperty} representing the name of the ingredient.
   * This property allows for listeners to be attached, which will be notified of
   * changes.
   *
   * @return the name property.
   */
  @SuppressFBWarnings("EI_EXPOSE_REP")
  public final StringProperty nameProperty() {
    return this.name;
  }

  /**
   * Sets the name of the ingredient using an observable property.
   * If the name is invalid (null or empty), an {@code IllegalArgumentException}
   * is thrown.
   * Overrides the {@code setName} method in the {@code Ingredient} class.
   *
   * @param name the name of the ingredient.
   * @throws IllegalArgumentException if the name is null or empty.
   */
  @Override
  public void setName(String name) {
    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException(MessageFeedback.Error.Ingredient.NAME.getMessage());
    }
    this.name.set(name);
  }
}
