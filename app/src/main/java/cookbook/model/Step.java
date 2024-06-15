package cookbook.model;

import cookbook.controller.MessageFeedback;

/**
 * The {@code Step} class represents a single step in a recipe.
 * It includes the step index and the instructions for that step.
 */
public class Step {

  private int stepIndex;
  private String instructions;

  /**
   * Returns the index of the step.
   *
   * @return the step index.
   */
  public int getStepIndex() {
    return stepIndex;
  }

  /**
   * Sets the index of the step.
   *
   * @param stepIndex the index of the step. Must be an integer greater than 0.
   * @throws IllegalArgumentException if the step index is less than 1.
   */
  public void setStepIndex(int stepIndex) {
    if (stepIndex < 1) {
      throw new IllegalArgumentException(MessageFeedback.Error.Step.INDEX.getMessage());
    }
    this.stepIndex = stepIndex;
  }

  /**
   * Returns the instructions for the step.
   *
   * @return the instructions for the step.
   */
  public String getInstructions() {
    return instructions;
  }

  /**
   * Sets the instructions for the step.
   *
   * @param instructions the instructions for the step. Must be a non-empty
   *                     string.
   * @throws IllegalArgumentException if the instructions are null or empty.
   */
  public void setInstructions(String instructions) {
    if (instructions == null || instructions.isEmpty()) {
      throw new IllegalArgumentException(MessageFeedback.Error.Step.INSTRUCTION.getMessage());
    }
    this.instructions = instructions;
  }
}
