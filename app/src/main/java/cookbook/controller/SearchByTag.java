package cookbook.controller;

/**
 * The {@code SearchByTag} class extends {@code BaseSearchBy} and
 * implements a search functionality based on recipe tags.
 */
public class SearchByTag extends BaseSearchBy {

  /**
   * Initializes a new instance of the {@code SearchByTag} class.
   */
  public SearchByTag() {

  }

  /**
   * Specifies the type of search as {@code EnumSearch.TAG}.
   */
  private EnumSearch searchType = EnumSearch.TAG;

  /**
   * Returns the type of search implemented by this class.
   *
   * @return the search type, which is {@code EnumSearch.TAG}.
   */
  public EnumSearch getSearchType() {
    return searchType;
  }

  /**
   * Constructs an SQL query string to search for recipes based on recipe tags.
   *
   * @param names  an array of tag names to be included in the search.
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
        sql += " SELECT r_recipe_tag.recipe_id FROM r_recipe_tag "
            + " JOIN tag t ON r_recipe_tag.tag_id = t.id "
            + " WHERE t.name LIKE CONCAT('%', '" + names[i] + "' , '%') AND ( t.owner = " + userId
            + " OR t.owner IS NULL)";
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
