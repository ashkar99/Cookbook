package cookbook.controller;

/**
 * EnumSearch is an enumeration that defines the types of searches available
 * in the cookbook application. Each enum value has a description.
 */
public enum EnumSearch {
  /**
   * Search by recipe name.
   */
  NAME("Searches by name"),

  /**
   * Search by recipe ingredient.
   */
  INGREDIENT("Searches by ingredient"),

  /**
   * Search by recipe tag.
   */
  TAG("Searches by tag");

  private String description;

  /**
   * Constructs an EnumSearch with the specified description.
   *
   * @param description the description of the search type.
   */
  private EnumSearch(String description) {
    this.description = description;
  }

  /**
   * Returns the description of the search type.
   *
   * @return the description of the search type.
   */
  @Override
  public String toString() {
    return description;
  }
}
