package cookbook.controller;

import cookbook.model.Ingredient;
import cookbook.model.Unit;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * IngredientDaoImpl is a concrete implementation of the IngredientDao
 * interface.
 * It provides methods for creating, retrieving, updating, and deleting
 * ingredients,
 * as well as managing units in the cookbook application.
 */
public class IngredientDaoImpl extends BaseDaoImpl implements IngredientDao {

  ResultSet rs = null;
  PreparedStatement ps = null;

  /**
   * Constructs an IngredientDaoImpl object and initializes the database
   * connection
   * in production mode.
   */
  public IngredientDaoImpl() {
    super(true);
  }

  /**
   * Constructs an IngredientDaoImpl object and initializes the database
   * connection.
   *
   * @param production if true, connects to the production database;
   *                   if false, connects to the test database.
   */
  public IngredientDaoImpl(boolean production) {
    super(production);
  }

  /**
   * Creates a new owned ingredient in the database specific to a user.
   *
   * @param ingredient the Ingredient object containing the ingredient data.
   * @return the created Ingredient object with its assigned ID.
   * @throws IllegalArgumentException if there's an error while creating the owned
   *                                  ingredient.
   */
  @Override
  public Ingredient createOwnedIngredient(Ingredient ingredient) throws IllegalArgumentException {
    if (ingredient.getOwner() <= 0) {
      throw new IllegalArgumentException("Owned Ingredient must have owner");
    }

    ResultSet rs = null;
    PreparedStatement ps = null;
    String sql = "INSERT INTO ingredient (name, owner) VALUES (?, ?)";

    try {
      ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      ps.setString(1, ingredient.getName());
      ps.setInt(2, ingredient.getOwner());
      ps.executeUpdate();

      rs = ps.getGeneratedKeys();

      if (rs.next()) {
        ingredient.setId(rs.getInt(1));
      } else {
        throw new SQLException("Failed to generate key");
      }
    } catch (Exception e) {
      throw new IllegalArgumentException(e.getMessage());
    } finally {
      closeResources(null, ps);
    }

    return ingredient;
  }

  /**
   * Creates a new common ingredient in the database.
   *
   * @param ingredient the Ingredient object containing the ingredient data.
   * @throws IllegalArgumentException if there's an error while creating the
   *                                  common ingredient.
   */
  @Override
  public void createCommonIngredient(Ingredient ingredient) throws IllegalArgumentException {
    try {
      if (ingredient.getOwner() > 0) {
        throw new IllegalArgumentException("Common Ingredient must not have owner");
      }

      try {
        String sql = "INSERT INTO ingredient (id, name) VALUES (?, ?)";
        ps = con.prepareStatement(sql);

        ps.setInt(1, ingredient.getId());
        ps.setString(2, ingredient.getName());

        ps.executeUpdate();
      } catch (SQLException e) {
        throw new IllegalArgumentException(e.getMessage());
      }
    } catch (Exception e) {
      throw new IllegalArgumentException(e.getMessage());
    }
  }

  /**
   * Retrieves all ingredients visible to a specific user from the database.
   *
   * @param userId the ID of the user for whom to retrieve the ingredients.
   * @return a list of Ingredient objects visible to the specified user.
   * @throws IllegalArgumentException if there is an error while retrieving the
   *                                  ingredients.
   */
  @Override
  public List<Ingredient> getAllIngredients(int userId) {
    ResultSet rs = null;
    PreparedStatement ps = null;
    Ingredient ingredient = null;

    List<Ingredient> ingredients = new ArrayList<Ingredient>();

    try {
      if (userId <= 0) {
        throw new IllegalArgumentException("Invalid userID");
      }

      try {
        // Get all ingredients that are common or owned by the user
        String sql = "SELECT * FROM ingredient WHERE owner = ? OR owner IS NULL";
        ps = con.prepareStatement(sql);
        ps.setInt(1, userId);

        rs = ps.executeQuery();
        while (rs.next()) {
          ingredient = new Ingredient();
          ingredient.setId(rs.getInt("id"));
          ingredient.setName(rs.getString("name"));
          ingredient.setOwner(rs.getInt("owner"));

          ingredients.add(ingredient);
        }
      } catch (SQLException e) {
        throw new IllegalArgumentException(e.getMessage());
      } finally {
        if (rs != null) {
          rs.close();
        }
        ps.close();
      }
    } catch (Exception e) {
      throw new IllegalArgumentException(e.getMessage());
    }

    return ingredients;
  }

  /**
   * Retrieves all ingredients owned by a specific user from the database.
   *
   * @param userId the ID of the user who owns the ingredients.
   * @return a list of Ingredient objects owned by the specified user.
   * @throws IllegalArgumentException if there is an error while retrieving the
   *                                  owned ingredients.
   */
  @Override
  public List<Ingredient> getAllOwnedIngredients(int userId) {
    ResultSet rs = null;
    PreparedStatement ps = null;
    Ingredient ingredient = null;

    List<Ingredient> ingredients = new ArrayList<Ingredient>();

    try {
      if (userId <= 0) {
        throw new IllegalArgumentException("Invalid userID");
      }

      try {
        // Get all ingredients that are owned by the user
        String sql = "SELECT * FROM ingredient WHERE owner = ?";
        ps = con.prepareStatement(sql);
        ps.setInt(1, userId);

        rs = ps.executeQuery();
        while (rs.next()) {
          ingredient = new Ingredient();
          ingredient.setId(rs.getInt("id"));
          ingredient.setName(rs.getString("name"));
          ingredient.setOwner(rs.getInt("owner"));

          ingredients.add(ingredient);
        }
      } catch (SQLException e) {
        throw new IllegalArgumentException(e.getMessage());
      } finally {
        if (rs != null) {
          rs.close();
        }
        ps.close();
      }
    } catch (Exception e) {
      throw new IllegalArgumentException(e.getMessage());
    }

    return ingredients;
  }

  /**
   * Retrieves a common ingredient from the database by its ID.
   *
   * @param id the ID of the common ingredient.
   * @return the Ingredient object corresponding to the specified ID.
   * @throws IllegalArgumentException if there's an error while retrieving the
   *                                  common ingredient.
   */
  @Override
  public Ingredient getCommonIngredientById(int id) throws IllegalArgumentException {
    Ingredient ingredient = null;
    try {
      if (id <= 0) {
        throw new IllegalArgumentException("Invalid ID");
      }

      try {
        String sql = "SELECT * FROM ingredient WHERE id = ?";
        ps = con.prepareStatement(sql);
        ps.setInt(1, id);

        rs = ps.executeQuery();

        if (rs == null) {
          throw new IllegalArgumentException("No ingredient found");
        }

        ingredient = new Ingredient();
        if (rs.next()) {
          ingredient.setId(rs.getInt("id"));
          ingredient.setName(rs.getString("name"));
        }
      } catch (SQLException e) {
        throw new IllegalArgumentException(e.getMessage());
      }
    } catch (Exception e) {
      throw new IllegalArgumentException(e.getMessage());
    }

    return ingredient;
  }

  /**
   * Retrieves an owned ingredient from the database by its ID and user ID.
   *
   * @param id     the ID of the owned ingredient.
   * @param userId the ID of the user who owns the ingredient.
   * @return the Ingredient object corresponding to the specified ID and user ID.
   * @throws IllegalArgumentException if there's an error while retrieving the
   *                                  owned ingredient.
   */
  @Override
  public Ingredient getOwnedIngredientById(int id, int userId) throws IllegalArgumentException {
    Ingredient ingredient = null;
    try {
      if (id <= 0) {
        throw new IllegalArgumentException("Invalid ID");
      } else if (userId <= 0) {
        throw new IllegalArgumentException("Invalid userID");
      }

      try {
        String sql = "SELECT * FROM ingredient WHERE id = ? and owner = ?";
        ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ps.setInt(2, userId);

        rs = ps.executeQuery();

        if (rs == null) {
          throw new IllegalArgumentException("No ingredient found");
        }

        ingredient = new Ingredient();
        if (rs.next()) {
          ingredient.setId(rs.getInt("id"));
          ingredient.setName(rs.getString("name"));
          ingredient.setOwner(rs.getInt("owner"));
        }
      } catch (SQLException e) {
        throw new IllegalArgumentException(e.getMessage());
      }
    } catch (Exception e) {
      throw new IllegalArgumentException(e.getMessage());
    }

    return ingredient;
  }

  /**
   * Updates the name of an ingredient in the database.
   *
   * @param ingredient the Ingredient object containing the updated ingredient
   *                   data.
   * @throws IllegalArgumentException if there's an error while updating the
   *                                  ingredient name.
   */
  @Override
  public void updateIngredientName(Ingredient ingredient) throws IllegalArgumentException {
    try {
      String sql = "UPDATE ingredient SET name = ? WHERE id = ?";
      ps = con.prepareStatement(sql);
      ps.setString(1, ingredient.getName());
      ps.setInt(2, ingredient.getId());
      ps.executeUpdate();
    } catch (SQLException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
  }

  /**
   * Deletes an ingredient from the database by its ID.
   *
   * @param id the ID of the ingredient to delete.
   * @throws IllegalArgumentException if there is an error while deleting the
   *                                  ingredient.
   */
  @Override
  public void deleteIngredient(int id) throws IllegalArgumentException {
    try {
      String sql = "DELETE FROM ingredient WHERE id = ?";
      ps = con.prepareStatement(sql);
      ps.setInt(1, id);
      ps.executeUpdate();
    } catch (SQLException e) {
      throw new IllegalArgumentException(MessageFeedback.Error.Ingredient.DELETE.getMessage());
    }
  }

  /**
   * Retrieves all units from the database.
   *
   * @return a list of Unit objects representing all units.
   * @throws IllegalArgumentException if there is an error while retrieving the
   *                                  units.
   */
  @Override
  public List<Unit> getAllUnits() {
    List<Unit> units = new ArrayList<>();
    String sql = "SELECT * FROM unit";
    ResultSet rs = null;
    Unit tempUnit = null;

    try {
      ps = con.prepareStatement(sql);

      try {
        rs = ps.executeQuery();
        while (rs.next()) {
          tempUnit = new Unit();
          tempUnit.setId(rs.getInt("id"));
          tempUnit.setName(rs.getString("name"));
          units.add(tempUnit);
        }
      } catch (Exception e) {
        throw new IllegalArgumentException("Error: " + e.getMessage());
      } finally {
        if (rs != null) {
          rs.close();
        }
      }
    } catch (SQLException e) {
      throw new IllegalArgumentException("Unit Error: " + e.getMessage());
    }

    return units;
  }

  /**
   * Retrieves the name of a unit from the database by its ID.
   *
   * @param unitId the ID of the unit.
   * @return the name of the unit corresponding to the specified ID.
   * @throws IllegalArgumentException if there is an error while retrieving the
   *                                  unit name.
   */
  @Override
  public String getUnitNameById(int unitId) {
    ResultSet rs = null;
    PreparedStatement ps = null;
    String sql = "SELECT name FROM unit WHERE id = ?";
    String unitName = "";

    try {
      try {
        ps = con.prepareStatement(sql);
        ps.setInt(1, unitId);
        rs = ps.executeQuery();

        if (rs.next()) {
          unitName = rs.getString("name");
        }
      } catch (SQLException e) {
        throw new IllegalArgumentException(e.getMessage());
      } finally {
        if (rs != null) {
          rs.close();
        }
        ps.close();
      }
    } catch (SQLException e) {
      throw new IllegalArgumentException(e.getMessage());
    }

    return unitName;
  }
}
