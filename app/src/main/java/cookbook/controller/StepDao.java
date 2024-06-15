package cookbook.controller;

import cookbook.model.Step;
import java.util.List;

/**
 * The {@code StepDao} interface defines the methods required for creating and
 * updating steps in a recipe.
 */
public interface StepDao {

  /**
   * Creates steps for a specific recipe.
   *
   * @param steps    a list of {@code Step} objects to be created.
   * @param recipeId the ID of the recipe to which the steps belong.
   */
  void createSteps(List<Step> steps, int recipeId);

  /**
   * Updates the steps for a specific recipe.
   *
   * @param steps    a list of {@code Step} objects to be updated.
   * @param recipeId the ID of the recipe to which the steps belong.
   */
  void updateSteps(List<Step> steps, int recipeId);
}

