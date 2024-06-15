package cookbook.model;

import cookbook.controller.MessageFeedback;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * The {@code ObservableStep} class extends {@code Step} and provides observable
 * properties
 * that can have listeners attached. This class is particularly useful within a
 * JavaFX application
 * where changes to properties need to be observed by the UI for updates.
 */
public class ObservableStep extends Step {

  private final IntegerProperty stepIndex;
  private final StringProperty instructions;

  static final int MAX_INSTRUCTIONS_LENGTH = 1000;

  /**
   * Constructs a new {@code ObservableStep}.
   * Initializes the step index and instructions properties and sets up listeners
   * to update
   * the superclass's fields on change.
   */
  public ObservableStep() {
    this.stepIndex = new SimpleIntegerProperty(Integer.valueOf(0));
    stepIndex.addListener(changeEvent -> {
      super.setStepIndex(this.stepIndex.get());
    });

    this.instructions = new SimpleStringProperty("");
    instructions.addListener(changeEvent -> {
      super.setInstructions(this.instructions.get());
    });
  }

  /**
   * Returns the {@code StringProperty} representing the instructions.
   * This property allows for listeners to be attached, which will be notified of
   * changes.
   *
   * @return the instructions property.
   */
  @SuppressFBWarnings("EI_EXPOSE_REP")
  public final StringProperty instructionsProperty() {
    return this.instructions;
  }

  /**
   * Returns the {@code IntegerProperty} representing the step index.
   * This property allows for listeners to be attached, which will be notified of
   * changes.
   *
   * @return the step index property.
   */
  @SuppressFBWarnings("EI_EXPOSE_REP")
  public final IntegerProperty stepIndexProperty() {
    return this.stepIndex;
  }

  /**
   * Sets the step index using an observable property.
   * If the step index is invalid (less than 1), an
   * {@code IllegalArgumentException} is thrown.
   * Overrides the {@code setStepIndex} method in the {@code Step} class.
   *
   * @param stepIndex the step index.
   * @throws IllegalArgumentException if the step index is less than 1.
   */
  @Override
  public void setStepIndex(int stepIndex) {
    if (stepIndex < 1) {
      throw new IllegalArgumentException(MessageFeedback.Error.Step.INDEX.getMessage());
    }
    this.stepIndex.set(stepIndex);
  }

  /**
   * Sets the instructions using an observable property.
   * If the instructions are invalid (null, empty, or exceed max length), an
   * {@code IllegalArgumentException} is thrown.
   * Overrides the {@code setInstructions} method in the {@code Step} class.
   *
   * @param instructions the instructions for the step.
   * @throws IllegalArgumentException if the instructions are null, empty, or
   *                                  exceed 1000 characters.
   */
  @Override
  public void setInstructions(String instructions) {
    if (instructions == null || instructions.isBlank() || instructions.length() > MAX_INSTRUCTIONS_LENGTH) {
      throw new IllegalArgumentException(MessageFeedback.Error.Step.INSTRUCTION.getMessage());

    }
    this.instructions.set(instructions);
  }
}
