package cookbook.controller;

import cookbook.model.Step;
import java.util.List;

/**
 * The {@code StepDaoImpl} class implements the {@code StepDao} interface.
 * It provides functionality for creating and updating steps in a recipe.
 */
public class StepDaoImpl implements StepDao {

  /**
   * Creates steps for a specific recipe.
   *
   * @param steps    a list of {@code Step} objects to be created.
   * @param recipeId the ID of the recipe to which the steps belong.
   */
  @Override
  public void createSteps(List<Step> steps, int recipeId) {
    // Implementation goes here
  }

  /**
   * Updates the steps for a specific recipe.
   *
   * @param steps    a list of {@code Step} objects to be updated.
   * @param recipeId the ID of the recipe to which the steps belong.
   */
  @Override
  public void updateSteps(List<Step> steps, int recipeId) {
    // Implementation goes here
  }
}

