package cookbook.controller;

import cookbook.model.Ingredient;
import cookbook.model.Unit;
import java.util.List;

/**
 * IngredientDao is an interface that defines the CRUD operations
 * for managing ingredients and units in the cookbook application.
 */
public interface IngredientDao {

  /**
   * Creates a new common ingredient in the database.
   *
   * @param ingredient the Ingredient object containing the ingredient data.
   */
  void createCommonIngredient(Ingredient ingredient);

  /**
   * Creates a new owned ingredient in the database specific to a user.
   *
   * @param ingredient the Ingredient object containing the ingredient data.
   * @return the created Ingredient object with its assigned ID.
   */
  Ingredient createOwnedIngredient(Ingredient ingredient);

  /**
   * Retrieves a common ingredient from the database by its ID.
   *
   * @param id the ID of the common ingredient.
   * @return the Ingredient object corresponding to the specified ID.
   */
  Ingredient getCommonIngredientById(int id);

  /**
   * Retrieves an owned ingredient from the database by its ID and user ID.
   *
   * @param id     the ID of the owned ingredient.
   * @param userId the ID of the user who owns the ingredient.
   * @return the Ingredient object corresponding to the specified ID and user ID.
   */
  Ingredient getOwnedIngredientById(int id, int userId);

  /**
   * Updates the name of an ingredient in the database.
   *
   * @param ingredient the Ingredient object containing the updated ingredient
   *                   data.
   */
  void updateIngredientName(Ingredient ingredient);

  /**
   * Deletes an ingredient from the database by its ID.
   *
   * @param id the ID of the ingredient to delete.
   */
  void deleteIngredient(int id);

  /**
   * Retrieves all units from the database.
   *
   * @return a list of Unit objects representing all units.
   */
  List<Unit> getAllUnits();

  /**
   * Retrieves all ingredients visible to a specific user from the database.
   *
   * @param userId the ID of the user for whom to retrieve the ingredients.
   * @return a list of Ingredient objects visible to the specified user.
   */
  List<Ingredient> getAllIngredients(int userId);

  /**
   * Retrieves all ingredients owned by a specific user from the database.
   *
   * @param userId the ID of the user who owns the ingredients.
   * @return a list of Ingredient objects owned by the specified user.
   */
  List<Ingredient> getAllOwnedIngredients(int userId);

  /**
   * Retrieves the name of a unit from the database by its ID.
   *
   * @param unitId the ID of the unit.
   * @return the name of the unit corresponding to the specified ID.
   */
  String getUnitNameById(int unitId);
}

