package cookbook.model;

import cookbook.controller.MessageFeedback;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code Recipe} class represents a cooking recipe.
 * It contains information about the recipe such as its ID, name, description,
 * portions, owner, ingredients, steps, tags, etc.
 */
public final class Recipe {

  private int id;
  private String name;
  private String description;
  private int portions;
  private int owner;
  private boolean isFavourite;
  private String ownerDisplayName;

  private List<Ingredient> ingredients = new ArrayList<>();
  private List<Step> steps = new ArrayList<>();
  private List<Tag> tags = new ArrayList<>();

  static final int MAX_NAME_LENGTH = 75;
  static final int MAX_DESCRIPTION_LENGTH = 1000;

  /**
   * Constructs a new {@code Recipe}.
   */
  public Recipe() {
    // Fetch ingredients from database and update List ingredients.
  }

  /**
   * Returns the ID of the recipe.
   *
   * @return the ID of the recipe.
   */
  public int getId() {
    return id;
  }

  /**
   * Sets the ID of the recipe.
   *
   * @param id the ID of the recipe. Must be an integer greater than 0.
   * @throws IllegalArgumentException if the ID is less than 1.
   */
  public void setId(int id) {
    if (id < 1) {
      throw new IllegalArgumentException(MessageFeedback.Error.Recipe.ID.getMessage());
    }
    this.id = id;
  }

  /**
   * Returns the name of the recipe.
   *
   * @return the name of the recipe.
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name of the recipe.
   *
   * @param name the name of the recipe. Must be a string of at least size 1 and
   *             less than 75 characters.
   * @throws IllegalArgumentException if the name is null, empty, or longer than
   *                                  75 characters.
   */
  public void setName(String name) {
    if (name == null || name.isEmpty() || name.length() > MAX_NAME_LENGTH) {
      throw new IllegalArgumentException(MessageFeedback.Error.Recipe.NAME.getMessage());
    }
    this.name = name;
  }

  /**
   * Returns the description of the recipe.
   *
   * @return the description of the recipe.
   */
  public String getDescription() {
    return description;
  }

  /**
   * Sets the description of the recipe.
   *
   * @param description the description of the recipe. Must be a string of at
   *                    least size 1 and less than 1000 characters.
   * @throws IllegalArgumentException if the description is null, blank, or longer
   *                                  than 1000 characters.
   */
  public void setDescription(String description) {
    if (description == null || description.isBlank() || description.length() > MAX_DESCRIPTION_LENGTH) {
      throw new IllegalArgumentException(MessageFeedback.Error.Recipe.DESCRIPTION.getMessage());
    }
    this.description = description;
  }

  /**
   * Returns the number of portions the recipe makes.
   *
   * @return the number of portions.
   */
  public int getPortions() {
    return portions;
  }

  /**
   * Sets the number of portions the recipe makes.
   *
   * @param portions the number of portions. Must be an integer greater than 0.
   * @throws IllegalArgumentException if the number of portions is less than 1.
   */
  public void setPortions(int portions) {
    if (portions < 1) {
      throw new IllegalArgumentException(MessageFeedback.Error.Recipe.PORTIONS.getMessage());
    }
    this.portions = portions;
  }

  /**
   * Returns the owner ID of the recipe.
   *
   * @return the owner ID of the recipe.
   */
  public int getOwner() {
    return owner;
  }

  /**
   * Sets the owner ID of the recipe.
   *
   * @param owner the owner ID. Must be an integer greater than 0.
   * @throws IllegalArgumentException if the owner ID is less than 0.
   */
  public void setOwner(int owner) {
    if (owner < 0) {
      throw new IllegalArgumentException(MessageFeedback.Error.Recipe.OWNER.getMessage());
    }
    this.owner = owner;
  }

  /**
   * Returns the list of ingredients of the recipe.
   *
   * @return the list of ingredients.
   */
  @SuppressFBWarnings("EI_EXPOSE_REP")
  public List<Ingredient> getIngredients() {
    return ingredients;
  }

  /**
   * Sets the list of ingredients of the recipe.
   *
   * @param ingredients the list of ingredients. Must be a non-empty list.
   * @throws IllegalArgumentException if the list of ingredients is empty.
   */
  @SuppressFBWarnings("EI_EXPOSE_REP")
  public void setIngredients(List<Ingredient> ingredients) {
    if (ingredients.isEmpty()) {
      throw new IllegalArgumentException(MessageFeedback.Error.Recipe.INGREDIENTS.getMessage());
    }
    this.ingredients = ingredients;
  }

  /**
   * Returns the list of steps of the recipe.
   *
   * @return the list of steps.
   */
  @SuppressFBWarnings("EI_EXPOSE_REP")
  public List<Step> getSteps() {
    return steps;
  }

  /**
   * Sets the list of steps of the recipe.
   *
   * @param steps the list of steps. Must be a non-empty list.
   * @throws IllegalArgumentException if the list of steps is empty.
   */
  @SuppressFBWarnings("EI_EXPOSE_REP")
  public void setSteps(List<Step> steps) {
    if (steps.isEmpty()) {
      throw new IllegalArgumentException(MessageFeedback.Error.Recipe.STEPS.getMessage());
    }
    this.steps = steps;
  }

  /**
   * Returns the list of tags of the recipe.
   *
   * @return the list of tags.
   */
  @SuppressFBWarnings("EI_EXPOSE_REP")
  public List<Tag> getTags() {
    return tags;
  }

  /**
   * Sets the list of tags of the recipe.
   *
   * @param tags the list of tags.
   */
  @SuppressFBWarnings("EI_EXPOSE_REP")
  public void setTags(List<Tag> tags) {
    this.tags = tags;
  }

  /**
   * Returns whether the recipe is a favourite.
   *
   * @return {@code true} if the recipe is a favourite, otherwise {@code false}.
   */
  public boolean isFavourite() {
    return isFavourite;
  }

  /**
   * Sets whether the recipe is a favourite.
   *
   * @param isFavourite {@code true} if the recipe is a favourite, otherwise
   *                    {@code false}.
   */
  public void setIsFavourite(boolean isFavourite) {
    this.isFavourite = isFavourite;
  }

  /**
   * Returns the display name of the recipe's owner.
   *
   * @return the owner's display name.
   */
  public String getOwnerDisplayName() {
    return ownerDisplayName;
  }

  /**
   * Sets the display name of the recipe's owner.
   *
   * @param ownerDisplayName the owner's display name.
   */
  public void setOwnerDisplayName(String ownerDisplayName) {
    this.ownerDisplayName = ownerDisplayName;
  }
}
