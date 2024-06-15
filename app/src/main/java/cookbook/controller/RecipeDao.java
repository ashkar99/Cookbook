package cookbook.controller;

import cookbook.model.Recipe;
import cookbook.model.Tag;
import java.util.List;
import java.util.Map;

/**
 * RecipeDao is an interface that defines the CRUD operations
 * for managing recipes and their related entities in the cookbook
 * application.
 */
public interface RecipeDao {

  /**
   * Creates a new recipe in the database.
   *
   * @param recipe the Recipe object containing the recipe data.
   * @param userId the ID of the user who creates the recipe.
   */
  void createRecipe(Recipe recipe, int userId);

  /**
   * Retrieves all common recipes visible to a specific user from the database.
   *
   * @param userId the ID of the user for whom to retrieve the common recipes.
   * @return a list of Recipe objects visible to the specified user.
   */
  List<Recipe> getAllCommonRecipes(int userId);

  /**
   * Retrieves a recipe from the database by its ID and user ID.
   *
   * @param id     the ID of the recipe.
   * @param userId the ID of the user to whom the recipe belongs.
   * @return the Recipe object corresponding to the specified ID and user ID.
   */
  Recipe getRecipeById(int id, int userId);

  /**
   * Updates the personal tags for a user on a recipe.
   *
   * @param recipe the Recipe object to be updated
   * @param userId the User whos tags should be updated
   */
  void updatePersonalTags(Recipe recipe, int userId);

  /**
   * Updates an existing recipe in the database.
   *
   * @param recipe the Recipe object containing the updated recipe data.
   */
  void updateRecipe(Recipe recipe);

  /**
   * Deletes a recipe from the database by its ID.
   *
   * @param id the ID of the recipe to delete.
   */
  void deleteRecipe(int id);

  /**
   * Searches for recipes based on various search options.
   *
   * @param opts   the list of SearchBy options.
   * @param search the search string containing keywords.
   * @param userId the ID of the user performing the search.
   * @return a map with EnumSearch as keys and lists of Recipe objects as values.
   */
  Map<EnumSearch, List<Recipe>> search(List<SearchBy> opts, String search, int userId);

  /**
   * Marks or unmarks a recipe as favourite for a specific user.
   *
   * @param recipeId    the ID of the recipe.
   * @param userId      the ID of the user.
   * @param isFavourite if true, marks the recipe as favourite; if false, unmarks
   *                    it.
   */
  void doFavourite(int recipeId, int userId, boolean isFavourite);

  /**
   * Loads the ingredients of a recipe from the database.
   *
   * @param recipe the Recipe object to load the ingredients into.
   */
  void loadIngredients(Recipe recipe);

  /**
   * Loads the steps of a recipe from the database.
   *
   * @param recipe the Recipe object to load the steps into.
   */
  void loadSteps(Recipe recipe);

  /**
   * Retrieves all favourite recipes of a specific user from the database.
   *
   * @param userId the ID of the user for whom to retrieve the favourite recipes.
   * @return a list of Recipe objects marked as favourites by the specified user.
   */
  List<Recipe> getFavourites(int userId);

  /**
   * Converts a recipe to a specified number of portions.
   *
   * @param portions the number of portions to convert the recipe to.
   * @param recipe   the Recipe object to convert.
   * @return the converted Recipe object.
   */
  Recipe convertRecipeTo(int portions, Recipe recipe);

  /**
   * Retrieves all tags visible to a specific user from the database.
   *
   * @param userId the ID of the user for whom to retrieve the tags.
   * @return a list of Tag objects visible to the specified user.
   */
  List<Tag> getAllTags(int userId);

  /**
   * Retrieves all tags owned by a specific user from the database.
   *
   * @param userId the ID of the user who owns the tags.
   * @return a list of Tag objects owned by the specified user.
   */
  List<Tag> getOwnedTags(int userId);

  /**
   * Retrieves all base tags from the database (tags that are not user-specific).
   *
   * @return a list of base Tag objects.
   */
  List<Tag> getBaseTags();

  /**
   * Loads the tags of a recipe from the database.
   *
   * @param recipe the Recipe object to load the tags into.
   * @param userId the ID of the user to whom the recipe belongs.
   */
  void loadTags(Recipe recipe, int userId);

  /**
   * Adds a tag to a recipe for a specific user in the database.
   *
   * @param recipeId the ID of the recipe.
   * @param tagId    the ID of the tag.
   * @param userId   the ID of the user adding the tag.
   */
  void addTagToRecipe(int recipeId, int tagId, int userId);
}
