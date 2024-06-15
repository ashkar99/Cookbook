package cookbook.controller;

import cookbook.model.Recipe;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * BaseSearchBy is an abstract class that provides the base implementation
 * for searching recipes from a database. This class extends BaseDaoImpl and
 * implements the SearchBy interface.
 */
public abstract class BaseSearchBy extends BaseDaoImpl implements SearchBy {
  private PreparedStatement ps = null;
  private RecipeDao recipeDao;

  /**
   * Constructs a BaseSearchBy object, initializes the database connection,
   * and sets up the RecipeDao.
   */
  public BaseSearchBy() {
    super(true);
    recipeDao = new RecipeDaoImpl();
  }

  /**
   * Returns the SQL query string used for searching recipes. Subclasses must
   * implement this method.
   *
   * @param names  the search keywords split into an array.
   * @param userId the ID of the user performing the search.
   * @return the SQL query string.
   */
  protected abstract String getSqlString(String[] names, int userId);

  /**
   * Returns the type of search. Subclasses must implement this method.
   *
   * @return the type of search as an EnumSearch.
   */
  public abstract EnumSearch getSearchType();

  /**
   * Searches for recipes based on the provided search string and user ID.
   *
   * @param searchString the search string containing keywords.
   * @param userId       the ID of the user performing the search.
   * @return a list of recipes that match the search criteria.
   * @throws IllegalArgumentException if there is a database error or if the
   *                                  search fails.
   */
  @Override
  public List<Recipe> search(String searchString, int userId) throws IllegalArgumentException {
    String[] names = searchString.split(" ");

    // Get the SQL string from the subclass
    String sql = getSqlString(names, userId);

    List<Recipe> recipes = new ArrayList<Recipe>();

    Recipe recipe = null;
    ResultSet rs = null;

    try {
      sql += ")";

      try {
        ps = con.prepareStatement(sql);
        ps.setInt(1, userId);
        rs = ps.executeQuery();

        while (rs.next()) {
          recipe = new Recipe();
          recipe.setId(rs.getInt("id"));
          recipe.setName(rs.getString("name"));
          recipe.setDescription(rs.getString("description"));
          recipe.setIsFavourite(rs.getBoolean("is_favourite"));

          recipeDao.loadTags(recipe, userId);

          recipes.add(recipe);
        }
      } catch (SQLException e) {
        throw new IllegalArgumentException("Error: " + e.getMessage());
      } finally {
        if (rs != null) {
          rs.close();
        }
        if (ps != null) {
          ps.close();
        }
      }
    } catch (SQLException e) {
      throw new IllegalArgumentException("Recipe Error: " + e.getMessage());
    }

    return recipes;
  }
}
