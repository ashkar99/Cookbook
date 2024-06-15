package cookbook.controller;

import cookbook.model.Ingredient;
import cookbook.model.Recipe;
import cookbook.model.Step;
import cookbook.model.Tag;
import cookbook.model.Unit;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The {@code RecipeDaoImpl} class implements the {@code RecipeDao} interface.
 * It provides functionality for creating, updating, deleting, and retrieving
 * recipes
 * as well as managing recipe-related operations such as converting portions and
 * tagging.
 */
public class RecipeDaoImpl extends BaseDaoImpl implements RecipeDao {

  PreparedStatement ps = null;

  /**
   * Initializes a new instance of the {@code RecipeDaoImpl} class in production
   * mode.
   */
  public RecipeDaoImpl() {
    super(true);
  }

  /**
   * Initializes a new instance of the {@code RecipeDaoImpl} class with the
   * specified mode.
   *
   * @param production a boolean indicating if the instance is in production mode.
   */
  public RecipeDaoImpl(boolean production) {
    super(production);
  }

  /**
   * Creates a new recipe along with its steps, ingredients, and tags.
   *
   * @param recipe the {@code Recipe} object to be created.
   * @param userId the ID of the user who owns the recipe.
   * @throws IllegalArgumentException if there is an error during the creation
   *                                  process.
   */
  @Override
  public void createRecipe(Recipe recipe, int userId) {

    List<? extends Step> steps = recipe.getSteps();
    List<Ingredient> ingredients = recipe.getIngredients();

    ResultSet rs = null;

    int recipeId = 0;
    String recipeSql = "INSERT INTO recipe(name,owner,portions,description) VALUES(?,?,?,?)";
    String stepSql = "INSERT INTO step(recipe_id, step_index, instruction) VALUES(?,?,?)";
    String ingredientSql = "INSERT INTO r_recipe_ingredient(ingredient_id,recipe_id,qty,unit) VALUES(?,?,?,?)";
    String tagSql = "INSERT INTO r_recipe_tag(recipe_id,tag_id) VALUES(?,?)";

    try {

      try {

        // Dont commit if something fails
        con.setAutoCommit(false);

        ps = con.prepareStatement(recipeSql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, recipe.getName());
        ps.setInt(2, userId);
        ps.setInt(3, recipe.getPortions());
        ps.setString(4, recipe.getDescription());

        ps.executeUpdate();
        rs = ps.getGeneratedKeys();

        // Get the generated key/id
        if (rs.next()) {
          recipe.setId(rs.getInt(1));
          recipeId = rs.getInt(1);
        } else {

          throw new IllegalArgumentException(MessageFeedback.Error.Sql.GENERIC.getMessage());

        }

        // Insert steps
        ps = con.prepareStatement(stepSql);
        for (Step s : steps) {
          ps.setInt(1, recipeId);
          ps.setInt(2, s.getStepIndex());
          ps.setString(3, s.getInstructions());
          ps.executeUpdate();
        }

        // Insert ingredients
        ps = con.prepareStatement(ingredientSql);
        for (Ingredient i : ingredients) {
          ps.setInt(1, i.getId());
          ps.setInt(2, recipeId);
          ps.setDouble(3, i.getQty());
          ps.setInt(4, i.getUnit().getId());
          ps.executeUpdate();
        }

        // Insert tags
        ps = con.prepareStatement(tagSql);
        for (Tag t : recipe.getTags()) {
          ps.setInt(1, recipeId);
          ps.setInt(2, t.getId());
          ps.executeUpdate();
        }

        // If all went well, commit the transaction
        con.commit();
        ps.close();
        rs.close();
        con.setAutoCommit(true);

      } catch (SQLException e) {

        // Rollback if something fails

        con.rollback();
        con.setAutoCommit(true);

        throw new IllegalArgumentException(MessageFeedback.Error.Recipe.CREATE.getMessage());

      } finally {
        if (rs != null) {
          rs.close();
        }
        if (ps != null) {
          ps.close();
        }
      }

    } catch (SQLException e) {
      throw new IllegalArgumentException(MessageFeedback.Error.Recipe.CREATE.getMessage());
    }
  }

  /**
   * Converts a recipe to use the specified number of portions.
   *
   * @param portions the number of portions to convert to.
   * @param recipe   the {@code Recipe} object to be converted.
   * @return the converted {@code Recipe} object.
   */
  @Override
  public Recipe convertRecipeTo(int portions, Recipe recipe) {

    int defaultPortion = recipe.getPortions();

    if (portions == recipe.getPortions()) {
      recipe.getIngredients().forEach(i -> {
        i.updateQtyStr();
      });
      return recipe;
    }

    recipe.getIngredients().forEach(i -> {

      double qty = i.getQty() / defaultPortion;

      if (i.getQty() == 0 || i.getUnit().getName() == null) {
        i.setQtyStr("");
        return;
      }
      String unit = i.getUnit().getName();
      switch (unit) {

        case "dl":
          i.setQtyStr(this.volumeConverter(qty * 100, portions));
          break;

        case "l":
          i.setQtyStr(this.volumeConverter(qty * 1000, portions));
          break;

        case "msk":
          i.setQtyStr(this.volumeConverter(qty * 15, portions));
          break;

        case "tsk":
          i.setQtyStr(this.volumeConverter(qty * 5, portions));
          break;
        case "kg":
          i.setQtyStr(this.massConverter(qty * 1000, portions));
          break;
        case "g":
          i.setQtyStr(this.massConverter(qty * 1, portions));
          break;
        default:
          double n = qty * portions;
          n = roundToClosetQuarter(n);
          i.setQtyStr(n + " " + unit);
          break;
      }
    });
    return recipe;
  }

  private String massConverter(double qty, int portions) {
    double n = qty * portions;
    double test = 0.0;
    String newUnit = "";
    if (n <= 999) {
      n = roundToClosetQuarter(n);
      return n + " g";
    }
    while (n >= 1) {
      if (n >= 1000) {
        n = n / 1000;
        test += n;
        newUnit = newUnit.equals("") ? "kg" : newUnit;
        break;
      } else {
        break;
      }
    }
    test = roundToClosetQuarter(test);
    return test + " " + newUnit + "";
  }

  private String volumeConverter(double qty, int portions) {
    double n = qty * portions;
    double test = 0.0;
    String newUnit = "";
    // Dont process if n is less than 5ml, just return it in tsk.
    if (n < 5.0) {
      n = n / 5;
      n = roundToClosetQuarter(n);
      return n + " tsk";
    }
    while (n >= 1) {
      if (n >= 1000) {
        n = n / 1000;
        test += n;
        newUnit = newUnit.equals("") ? "l" : newUnit;
        break;
      } else if (n >= 100) {
        n = n / 100;
        test += n;
        newUnit = newUnit.equals("") ? "dl" : newUnit;
        break;
      } else if (n >= 15) {
        n = n / 15;
        test += n;
        newUnit = newUnit.equals("") ? "msk" : newUnit;
        break;
      } else if (n >= 5) {
        n = n / 5;
        test += n;
        newUnit = newUnit.equals("") ? "tsk" : newUnit;
        break;
      } else {
        break;
      }
    }
    test = roundToClosetQuarter(test);
    return test + " " + newUnit + "";
  }

  private double roundToClosetQuarter(double n) {
    double r = (Math.round((n) * 4.0)) / 4.0;
    return r;
  }

  /**
   * Retrieves all common recipes for a specific user.
   *
   * @param userId the ID of the user.
   * @return a list of {@code Recipe} objects representing all common recipes.
   * @throws IllegalArgumentException if there is an error during retrieval.
   */
  @Override
  public List<Recipe> getAllCommonRecipes(int userId) throws IllegalArgumentException {

    List<Recipe> recipes = new ArrayList<Recipe>();

    String sql = "SELECT *, "
        + " IF(rf.user_id IS NOT NULL, TRUE, FALSE) AS is_favourite FROM recipe r "
        + " LEFT JOIN r_user_favourite_recipe rf ON r.id = rf.recipe_id AND rf.user_id = ?";
    Recipe tempRecipe = null;
    ResultSet rs = null;

    try {

      ps = con.prepareStatement(sql);

      try {
        ps.setInt(1, userId);
        rs = ps.executeQuery();
        while (rs.next()) {
          tempRecipe = new Recipe();
          tempRecipe.setId(rs.getInt("id"));
          tempRecipe.setName(rs.getString("name"));
          tempRecipe.setDescription(rs.getString("description"));
          tempRecipe.setPortions(rs.getInt("portions"));
          tempRecipe.setOwner(rs.getInt("owner"));
          tempRecipe.setIsFavourite(rs.getBoolean("is_favourite"));
          this.loadTags(tempRecipe, userId);

          recipes.add(tempRecipe);
        }
      } catch (Exception e) {

        throw new IllegalArgumentException(MessageFeedback.Error.Recipe.LOAD.getMessage());

      } finally {
        if (rs != null) {
          rs.close();
        }
      }
    } catch (SQLException e) {

      throw new IllegalArgumentException(MessageFeedback.Error.Sql.GENERIC.getMessage());

    }
    return recipes;
  }

  /**
   * Retrieves a recipe by its ID.
   *
   * @param id     the ID of the recipe.
   * @param userId the ID of the user making the request.
   * @return the {@code Recipe} object found.
   * @throws IllegalArgumentException if the recipe ID is invalid or if there is
   *                                  an error during retrieval.
   */
  @Override
  public Recipe getRecipeById(int id, int userId) throws IllegalArgumentException {

    if (id <= 0) {

      throw new IllegalArgumentException(MessageFeedback.Error.Recipe.LOAD.getMessage());

    }

    // String sql = "SELECT * FROM recipe WHERE id = ?";

    String sql = "SELECT u.displayname, r.*, "
        + "IF(rf.user_id IS NOT NULL, TRUE, FALSE) AS is_favourite "
        + "FROM recipe r "
        + "LEFT JOIN r_user_favourite_recipe rf ON r.id = rf.recipe_id AND rf.user_id = ? "
        + "LEFT JOIN user u ON u.id = r.owner "
        + "WHERE r.id = ?";

    Recipe recipe = null;
    ResultSet rs = null;

    // Fetch from database, use setters to set values and return the Recipe object.
    try {

      ps = con.prepareStatement(sql);
      ps.setInt(1, userId);
      ps.setInt(2, id);

      try {
        rs = ps.executeQuery();

        if (rs.next()) {
          recipe = new Recipe();
          recipe.setId(rs.getInt("id"));
          recipe.setName(rs.getString("name"));
          recipe.setDescription(rs.getString("description"));
          recipe.setPortions(rs.getInt("portions"));
          recipe.setIsFavourite(rs.getBoolean("is_favourite"));
          recipe.setOwnerDisplayName(rs.getString("displayname"));
          recipe.setOwner(rs.getInt("owner"));

        }
      } catch (SQLException e) {
        throw new IllegalArgumentException(MessageFeedback.Error.Recipe.LOAD.getMessage());
      } finally {
        if (rs != null) {
          rs.close();
        }
      }

    } catch (SQLException e) {
      throw new IllegalArgumentException(MessageFeedback.Error.Sql.GENERIC.getMessage());
    }

    // Load ingredients
    this.loadIngredients(recipe);
    this.loadSteps(recipe);
    this.loadTags(recipe, userId);

    return recipe;
  }

  /**
   * Retrieves all favourite recipes for a specific user.
   *
   * @param userId the ID of the user.
   * @return a list of {@code Recipe} objects representing all favourite recipes.
   */
  @Override
  public List<Recipe> getFavourites(int userId) {

    ResultSet rs = null;

    try {
      List<Recipe> recipes = new ArrayList<Recipe>();
      String sql = "SELECT r.*, "
          + " IF(rf.user_id IS NOT NULL, TRUE, FALSE) AS is_favourite FROM recipe r "
          + " JOIN r_user_favourite_recipe rf ON r.id = rf.recipe_id AND rf.user_id = ?";

      ps = con.prepareStatement(sql);
      ps.setInt(1, userId);
      rs = ps.executeQuery();

      while (rs.next()) {
        Recipe recipe = new Recipe();
        recipe.setId(rs.getInt("id"));
        recipe.setName(rs.getString("name"));
        recipe.setDescription(rs.getString("description"));
        recipe.setPortions(rs.getInt("portions"));
        recipe.setOwner(rs.getInt("owner"));
        recipe.setIsFavourite(rs.getBoolean("is_favourite"));
        this.loadTags(recipe, userId);
        recipes.add(recipe);

      }
      return recipes;
    } catch (SQLException e) {
      throw new IllegalArgumentException(MessageFeedback.Error.Recipe.FAVOURITE.getMessage());
    } finally {
      try {
        if (rs != null) {
          rs.close();
        }
      } catch (SQLException e) {
        throw new IllegalArgumentException(MessageFeedback.Error.Sql.GENERIC.getMessage());

      }
    }

  }

  /**
   * Marks or unmarks a recipe as favourite for a specific user.
   *
   * @param recipeId    the ID of the recipe.
   * @param userId      the ID of the user.
   * @param isFavourite whether the recipe should be marked as favourite.
   */
  @Override
  public void doFavourite(int recipeId, int userId, boolean isFavourite) {

    try {
      if (isFavourite) {
        ps = con.prepareStatement("INSERT INTO r_user_favourite_recipe (user_id, recipe_id) VALUES (?, ?)");
      } else {
        ps = con.prepareStatement("DELETE FROM r_user_favourite_recipe WHERE user_id = ? AND recipe_id = ?");
      }
      ps.setInt(1, userId);
      ps.setInt(2, recipeId);
      ps.executeUpdate();
    } catch (SQLException e) {
      throw new IllegalArgumentException(MessageFeedback.Error.Recipe.DOFAVOURITE.getMessage());
    }
  }

  /**
   * Loads steps for a given recipe.
   *
   * @param recipe the {@code Recipe} object to load steps for.
   * @throws IllegalArgumentException if the recipe is invalid or if there is an
   *                                  error during retrieval.
   */
  public void loadSteps(Recipe recipe) throws IllegalArgumentException {

    ResultSet rs = null;
    List<Step> steps = new ArrayList<Step>();

    try {
      if (recipe == null) {
        throw new IllegalArgumentException(MessageFeedback.Error.Recipe.LOAD.getMessage());
      }

      try {

        String sql = "SELECT step_index, instruction FROM step WHERE recipe_id = ?";

        ps = con.prepareStatement(sql);
        ps.setInt(1, recipe.getId());

        try {
          rs = ps.executeQuery();

          while (rs.next()) {
            Step step = new Step();

            step.setStepIndex(rs.getInt("step_index"));
            step.setInstructions(rs.getString("instruction"));
            steps.add(step);
          }
        } catch (Exception e) {
          throw new IllegalArgumentException(MessageFeedback.Error.Recipe.LOAD.getMessage());
        } finally {
          if (rs != null) {
            rs.close();
          }
        }
      } catch (SQLException e) {
        throw new IllegalArgumentException(MessageFeedback.Error.Sql.GENERIC.getMessage());
      }
      recipe.setSteps(steps);

    } catch (Exception e) {
      throw new IllegalArgumentException(e.getMessage());
    }

  }

  /**
   * Loads ingredients for a given recipe.
   *
   * @param recipe the {@code Recipe} object to load ingredients for.
   * @throws IllegalArgumentException if the recipe is invalid or if there is an
   *                                  error during retrieval.
   */
  public void loadIngredients(Recipe recipe) throws IllegalArgumentException {

    ResultSet rs = null;
    List<Ingredient> ingredients = new ArrayList<Ingredient>();

    try {
      if (recipe == null) {
        throw new IllegalArgumentException(MessageFeedback.Error.Recipe.LOAD.getMessage());
      }

      try {

        String sql = "SELECT *, i.name as iname, i.id as ingredient_id , u.name as unitName FROM recipe r"
            + " JOIN r_recipe_ingredient rr on rr.recipe_id = r.id"
            + " JOIN ingredient i on rr.ingredient_id = i.id "
            + " JOIN unit u on rr.unit = u.id "
            + " WHERE r.id = ? ORDER BY unitName desc";

        ps = con.prepareStatement(sql);
        ps.setInt(1, recipe.getId());

        try {
          rs = ps.executeQuery();

          while (rs.next()) {
            Ingredient ingredient = new Ingredient();
            Unit unit = new Unit();

            unit.setId(rs.getInt("unit"));
            unit.setName(rs.getString("unitName"));

            Double qty = rs.getDouble("qty");

            ingredient.setId(rs.getInt("ingredient_id"));
            ingredient.setName(rs.getString("iname"));
            ingredient.setQty(qty);
            ingredient.setUnit(unit);

            if (ingredient.getQty() == 0) {
              ingredient.setQtyStr("");
            } else {
              ingredient.setQtyStr(qty + " " + ingredient.getUnit().getName());
            }

            ingredients.add(ingredient);
          }
        } catch (Exception e) {
          throw new IllegalArgumentException(MessageFeedback.Error.Ingredient.LOADING.getMessage());

        } finally {
          if (rs != null) {
            rs.close();
          }
        }

      } catch (SQLException e) {
        throw new IllegalArgumentException(MessageFeedback.Error.Sql.GENERIC.getMessage());
      }

      recipe.setIngredients(ingredients);
    } catch (Exception e) {
      throw new IllegalArgumentException(e.getMessage());
    }

  }

  /**
   * Loads tags for a given recipe and user.
   *
   * @param recipe the {@code Recipe} object to load tags for.
   * @param userId the ID of the user.
   */
  @Override
  public void loadTags(Recipe recipe, int userId) {

    ResultSet rs = null;
    List<Tag> tags = new ArrayList<Tag>();

    try {
      if (recipe == null) {
        throw new IllegalArgumentException(MessageFeedback.Error.Recipe.LOAD.getMessage());
      }

      try {

        String sql = "SELECT * FROM r_recipe_tag rt "
            + "JOIN (SELECT * FROM tag WHERE owner IS NULL OR owner = ?) jt ON jt.id = rt.tag_id "
            + "WHERE rt.recipe_id = ? AND (jt.owner IS NULL OR jt.owner = ?) "
            + "ORDER BY jt.owner DESC";

        ps = con.prepareStatement(sql);
        ps.setInt(1, userId);
        ps.setInt(2, recipe.getId());
        ps.setInt(3, userId);

        try {
          rs = ps.executeQuery();

          while (rs.next()) {

            Tag tag = new Tag(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getInt("owner"),
                rs.getString("backgroundcolor"),
                rs.getString("textcolor"));

            tags.add(tag);
          }
        } catch (Exception e) {
          throw new IllegalArgumentException(MessageFeedback.Error.Tag.LOADING.getMessage());

        } finally {
          if (rs != null) {
            rs.close();
          }
        }

      } catch (SQLException e) {
        throw new IllegalArgumentException(MessageFeedback.Error.Sql.GENERIC.getMessage());
      }

      recipe.setTags(tags);
    } catch (Exception e) {
      throw new IllegalArgumentException(e.getMessage());
    }
  }

  @Override
  public void updatePersonalTags(Recipe recipe, int userId) throws IllegalArgumentException {
    try {

      try {

        con.setAutoCommit(true);
        String sql = "DELETE FROM r_recipe_tag WHERE recipe_id = ? AND tag_id IN (SELECT id FROM tag WHERE owner = ?);";
        ps = con.prepareStatement(sql);
        ps.setInt(1, recipe.getId());
        ps.setInt(2, userId);
        ps.executeUpdate();
      } catch (SQLException e) {
        throw new IllegalArgumentException(e.getMessage());
      }
      recipe.getTags().forEach(tag -> {
        try {
          if (tag.getOwnerId() == userId) {
            String tagSql = "INSERT INTO r_recipe_tag(recipe_id, tag_id) VALUES(?, ?)";
            ps = con.prepareStatement(tagSql);
            ps.setInt(1, recipe.getId());
            ps.setInt(2, tag.getId());
            ps.executeUpdate();
          }
        } catch (SQLException e) {
          throw new IllegalArgumentException(e.getMessage());
        }
      });
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(MessageFeedback.Error.Tag.UPDATE.getMessage());
    }
  }

  /**
   * Updates an existing recipe.
   *
   * @param recipe the {@code Recipe} object containing the updated information.
   * @throws IllegalArgumentException if there is an error during the update
   *                                  process.
   */
  @Override
  public void updateRecipe(Recipe recipe) throws IllegalArgumentException {

    try {

      try {
        String sql = "UPDATE recipe SET name = ?, portions = ?, description = ? WHERE id = ?";
        ps = con.prepareStatement(sql);

        ps.setString(1, recipe.getName());
        ps.setInt(2, recipe.getPortions());
        ps.setString(3, recipe.getDescription());
        ps.setInt(4, recipe.getId());

        ps.executeUpdate();

      } catch (SQLException e) {

        throw new IllegalArgumentException(MessageFeedback.Error.Recipe.UPDATE.getMessage());

      }
      // Delete all tags
      try {
        String sql = "DELETE FROM r_recipe_tag WHERE recipe_id = ? AND tag_id IN (SELECT id FROM tag WHERE owner is NULL)";
        ps = con.prepareStatement(sql);
        ps.setInt(1, recipe.getId());
        ps.executeUpdate();
      } catch (SQLException e) {
        throw new IllegalArgumentException(e.getMessage());
      }

      // Insert the tags!
      recipe.getTags().forEach(tag -> {
        try {
          if (tag.getOwnerId() == 0) {
            String tagSql = "INSERT INTO r_recipe_tag(recipe_id, tag_id) VALUES(?, ?)";
            ps = con.prepareStatement(tagSql);
            ps.setInt(1, recipe.getId());
            ps.setInt(2, tag.getId());
            ps.executeUpdate();
          }
        } catch (SQLException e) {
          throw new IllegalArgumentException(e.getMessage());
        }
      });

      // Delete all steps
      try {
        String sql = "DELETE FROM step WHERE recipe_id = ?";
        ps = con.prepareStatement(sql);
        ps.setInt(1, recipe.getId());
        ps.executeUpdate();
      } catch (SQLException e) {

        throw new IllegalArgumentException(MessageFeedback.Error.Recipe.UPDATE.getMessage());

      }

      // Insert the steps!
      recipe.getSteps().forEach(step -> {
        try {
          String stepSql = "INSERT INTO step(instruction, step_index, recipe_id) VALUES(?, ?, ?)";
          ps = con.prepareStatement(stepSql);
          ps.setString(1, step.getInstructions());
          ps.setInt(2, step.getStepIndex());
          ps.setInt(3, recipe.getId());
          ps.executeUpdate();

        } catch (SQLException e) {
          throw new IllegalArgumentException(MessageFeedback.Error.Recipe.UPDATE.getMessage());
        }
      });

      // Delete the ingredient relation
      try {
        String sql = "DELETE FROM r_recipe_ingredient WHERE recipe_id = ?";
        ps = con.prepareStatement(sql);
        ps.setInt(1, recipe.getId());
        ps.executeUpdate();
      } catch (SQLException e) {
        throw new IllegalArgumentException(MessageFeedback.Error.Recipe.UPDATE.getMessage());
      }

      // Insert the ingredient relation again
      recipe.getIngredients().forEach(ingredient -> {
        try {
          String recipeRelations = "INSERT INTO r_recipe_ingredient(ingredient_id, recipe_id, qty, unit) VALUES(?, ?, ?, ?)";
          ps = con.prepareStatement(recipeRelations);

          ps.setInt(1, ingredient.getId());
          ps.setInt(2, recipe.getId());
          ps.setDouble(3, ingredient.getQty());
          ps.setInt(4, ingredient.getUnit().getId());
          ps.executeUpdate();

        } catch (SQLException e) {
          throw new IllegalArgumentException(MessageFeedback.Error.Recipe.UPDATE.getMessage());
        }
      });

    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
  }

  /**
   * Deletes a recipe based on its ID.
   *
   * @param id the ID of the recipe to be deleted.
   * @throws IllegalArgumentException if there is an error during the deletion
   *                                  process.
   */
  @Override
  public void deleteRecipe(int id) throws IllegalArgumentException {

    String sql = "DELETE FROM recipe WHERE id = ?";

    try {

      ps = con.prepareStatement(sql);
      ps.setInt(1, id);
      ps.executeUpdate();

    } catch (SQLException e) {
      throw new IllegalArgumentException(MessageFeedback.Error.Recipe.DELETE.getMessage());
    }
  }

  /**
   * Searches for recipes based on specified criteria.
   *
   * @param opts   a list of {@code SearchBy} options to use for the search.
   * @param search the search string.
   * @param userId the ID of the user performing the search.
   * @return a map of {@code EnumSearch} to lists of {@code Recipe} objects
   *         representing the search results.
   */
  @Override
  public Map<EnumSearch, List<Recipe>> search(List<SearchBy> opts, String search, int userId) {

    Map<EnumSearch, List<Recipe>> map = new HashMap<EnumSearch, List<Recipe>>();
    opts.forEach(s -> map.put(s.getSearchType(), s.search(search, userId)));

    return map;
  }

  /**
   * Adds a tag to a recipe.
   *
   * @param recipeId the ID of the recipe.
   * @param tagId    the ID of the tag.
   * @param userId   the ID of the user adding the tag.
   */
  @Override
  public void addTagToRecipe(int recipeId, int tagId, int userId) {

    PreparedStatement ps = null;

    String sql = "INSERT INTO r_recipe_tag (recipe_id, tag_id) VALUES (?, ?)";

    try {
      ps = con.prepareStatement(sql);
      ps.setInt(1, recipeId);
      ps.setInt(2, tagId);
      ps.executeUpdate();
    } catch (SQLException e) {

      throw new IllegalArgumentException(MessageFeedback.Error.Tag.ADD.getMessage());
    } finally {
      closeResources(null, ps);
    }
  }

  /**
   * Retrieves all tags.
   *
   * @param userId the ID of the user making the request.
   * @return a list of {@code Tag} objects representing all tags.
   */
  @Override
  public List<Tag> getAllTags(int userId) {
    List<Tag> tags = new ArrayList<Tag>();

    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = "SELECT * FROM tag WHERE owner IS NULL OR owner = ?";

    try {
      ps = con.prepareStatement(sql);
      ps.setInt(1, userId);
      rs = ps.executeQuery();
      while (rs.next()) {
        tags.add(new Tag(rs.getInt("id"), rs.getString("name"), rs.getInt("owner"), rs.getString("backgroundcolor"),
            rs.getString("textcolor")));
      }
    } catch (SQLException e) {
      throw new IllegalArgumentException(MessageFeedback.Error.Tag.LOADING.getMessage());

    } finally {
      closeResources(rs, ps);
    }

    return tags;
  }

  /**
   * Retrieves all base tags.
   *
   * @return a list of {@code Tag} objects representing all base tags.
   */
  public List<Tag> getBaseTags() {

    List<Tag> tags = new ArrayList<Tag>();

    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = "SELECT * FROM tag WHERE owner IS NULL";

    try {
      ps = con.prepareStatement(sql);

      rs = ps.executeQuery();
      while (rs.next()) {
        tags.add(new Tag(rs.getInt("id"), rs.getString("name"), rs.getInt("owner"), rs.getString("backgroundcolor"),
            rs.getString("textcolor")));
      }
    } catch (SQLException e) {

      throw new IllegalArgumentException(MessageFeedback.Error.Tag.LOADING.getMessage());

    } finally {
      closeResources(rs, ps);
    }

    return tags;

  }

  /**
   * Retrieves all tags owned by a user.
   *
   * @param userId the ID of the user.
   * @return a list of {@code Tag} objects representing all tags owned by the
   *         user.
   */
  @Override
  public List<Tag> getOwnedTags(int userId) {
    List<Tag> tags = new ArrayList<Tag>();

    PreparedStatement ps = null;
    ResultSet rs = null;

    String sql = "SELECT * FROM tag WHERE owner = ?";
    try {
      ps = con.prepareStatement(sql);
      ps.setInt(1, userId);
      rs = ps.executeQuery();
      while (rs.next()) {
        tags.add(new Tag(rs.getInt("id"), rs.getString("name"), rs.getInt("owner"), rs.getString("backgroundcolor"),
            rs.getString("textcolor")));
      }
    } catch (SQLException e) {
      throw new IllegalArgumentException(MessageFeedback.Error.Tag.LOADING.getMessage());

    } finally {
      closeResources(rs, ps);
    }
    return tags;
  }

}
