package cookbook.controller;

import cookbook.model.Recipe;
import java.util.List;

/**
 * The {@code SearchBy} interface provides methods to search for recipes
 * based on a search string and user ID, and to get the type of search
 * being implemented.
 */
public interface SearchBy {

  /**
   * Searches for recipes that match the given search string and user ID.
   *
   * @param searchString the search string to look for in the recipes.
   * @param userId       the ID of the user performing the search.
   * @return a list of recipes that match the search criteria.
   */
  public List<Recipe> search(String searchString, int userId);

  /**
   * Returns the type of search implemented by this interface.
   *
   * @return the search type as an EnumSearch.
   */
  public EnumSearch getSearchType();
}
