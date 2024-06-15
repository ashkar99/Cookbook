package cookbook.controller;

/**
 * The {@code SearchByName} class extends {@code BaseSearchBy} and
 * implements a search functionality based on recipe names.
 */
public class SearchByName extends BaseSearchBy {

  /**
   * Initializes a new instance of the {@code SearchByName} class.
   */
  public SearchByName() {

  }

  /**
   * Specifies the type of search as {@code EnumSearch.NAME}.
   */
  private EnumSearch searchType = EnumSearch.NAME;

  /**
   * Returns the type of search implemented by this class.
   *
   * @return the search type, which is {@code EnumSearch.NAME}.
   */
  public EnumSearch getSearchType() {
    return searchType;
  }

  /**
   * Constructs an SQL query string to search for recipes based on recipe names.
   *
   * @param names  an array of recipe names to be included in the search.
   * @param userId the ID of the user performing the search.
   * @return the constructed SQL query string.
   * @throws IllegalArgumentException if an error occurs while constructing the
   *                                  SQL query.
   */
  @Override
  public String getSqlString(String[] names, int userId) throws IllegalArgumentException {
    String sql = "SELECT *, "
        + " IF(rf.user_id IS NOT NULL, TRUE, FALSE) AS is_favourite FROM recipe r "
        + " LEFT JOIN r_user_favourite_recipe rf ON r.id = rf.recipe_id AND rf.user_id = ? WHERE r.id IN(";

    try {
      for (int i = 0; i < names.length; i++) {
        sql += " SELECT recipe.id FROM recipe "
            + " WHERE r.name LIKE CONCAT('%', '" + names[i] + "', '%')";
        if (i < names.length - 1) {
          sql += " ) AND r.id IN (";
        }
      }
    } catch (Exception e) {
      throw new IllegalArgumentException(e.getMessage());
    }

    return sql;
  }
}
