package cookbook.controller;

import cookbook.model.Comment;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * CommentDaoImpl is a concrete implementation of the CommentDao interface.
 * It provides methods for creating, deleting, updating, and retrieving comments
 * associated with recipes in the database.
 */
public class CommentDaoImpl extends BaseDaoImpl implements CommentDao {

  /**
   * Constructs a CommentDaoImpl object and initializes the database connection
   * in production mode.
   */
  public CommentDaoImpl() {
    super(true);
  }

  /**
   * Constructs a CommentDaoImpl object and initializes the database connection.
   *
   * @param production if true, connects to the production database;
   *                   if false, connects to the test database.
   */
  public CommentDaoImpl(boolean production) {
    super(production);
  }

  /**
   * Creates a new comment for a specific recipe in the database.
   *
   * @param comment  the Comment object containing the comment data.
   * @param recipeId the ID of the recipe to which the comment is associated.
   * @throws IllegalArgumentException if there is an error while creating the
   *                                  comment.
   */
  @Override
  public void createComment(Comment comment, int recipeId) {
    PreparedStatement ps = null;
    String sql = "INSERT INTO r_user_comment_recipe (user_id, recipe_id, text, date, updatedDate) VALUES (?, ?, ?, ?, ?)";

    try {
      ps = con.prepareStatement(sql);
      ps.setInt(1, comment.getUserId());
      ps.setInt(2, recipeId);
      ps.setString(3, comment.getText());
      Long timestamp = Instant.now().getEpochSecond();
      ps.setLong(4, timestamp);
      ps.setLong(5, timestamp);
      ps.executeUpdate();
    } catch (Exception e) {
      throw new IllegalArgumentException("Could not create comment.");
    } finally {
      closeResources(null, ps);
    }
  }

  /**
   * Deletes a comment from the database based on its ID.
   *
   * @param commentId the ID of the comment to delete.
   * @throws IllegalArgumentException if there is an error while deleting the
   *                                  comment.
   */
  @Override
  public void deleteComment(int commentId) {
    PreparedStatement ps = null;
    try {
      ps = con.prepareStatement("DELETE FROM r_user_comment_recipe WHERE id = ?");
      ps.setInt(1, commentId);
      ps.executeUpdate();
    } catch (SQLException e) {
      throw new IllegalArgumentException("Could not delete comment.");
    } finally {
      closeResources(null, ps);
    }
  }

  /**
   * Updates an existing comment in the database.
   *
   * @param comment the Comment object containing the updated comment data.
   * @throws IllegalArgumentException if there is an error while updating the
   *                                  comment.
   */
  @Override
  public void updateComment(Comment comment) {
    PreparedStatement ps = null;
    try {
      ps = con.prepareStatement("UPDATE r_user_comment_recipe SET text = ?, updatedDate = ? WHERE id = ?");
      ps.setString(1, comment.getText());
      ps.setLong(2, Instant.now().getEpochSecond());
      ps.setInt(3, comment.getId());
      ps.executeUpdate();
    } catch (SQLException e) {
      throw new IllegalArgumentException("Could not update comment.");
    } finally {
      closeResources(null, ps);
    }
  }

  /**
   * Retrieves all comments associated with a specific recipe from the database.
   *
   * @param recipeId the ID of the recipe for which to retrieve comments.
   * @return a list of Comment objects associated with the specified recipe.
   * @throws IllegalArgumentException if there is an error while retrieving the
   *                                  comments.
   */
  @Override
  public List<Comment> getCommentsByRecipeId(int recipeId) {
    PreparedStatement ps = null;
    ResultSet rs = null;
    List<Comment> comments = new ArrayList<>();
    String sql = "SELECT r.*, u.displayname as dname "
        + "FROM r_user_comment_recipe r "
        + "JOIN user u on r.user_id = u.id "
        + "WHERE r.recipe_id = ?";

    try {
      ps = con.prepareStatement(sql);
      ps.setInt(1, recipeId);
      rs = ps.executeQuery();
      while (rs.next()) {
        comments.add(new Comment(
            rs.getInt("id"),
            rs.getInt("user_id"),
            rs.getInt("recipe_id"),
            rs.getString("text"),
            rs.getLong("date"),
            rs.getString("dname"),
            rs.getLong("updatedDate")));
      }
    } catch (SQLException e) {
      throw new IllegalArgumentException("Could not get comments.");
    } finally {
      closeResources(rs, ps);
    }
    return comments;
  }
}
