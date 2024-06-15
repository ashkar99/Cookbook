package cookbook.controller;

import cookbook.model.Comment;
import java.util.List;

/**
 * CommentDao is an interface that defines the CRUD operations
 * for managing comments associated with recipes.
 */
public interface CommentDao {

  /**
   * Creates a new comment for a specific recipe.
   *
   * @param comment  the Comment object containing the comment data.
   * @param recipeId the ID of the recipe to which the comment is associated.
   */
  void createComment(Comment comment, int recipeId);

  /**
   * Deletes a comment based on its ID.
   *
   * @param commentId the ID of the comment to delete.
   */
  void deleteComment(int commentId);

  /**
   * Updates an existing comment.
   *
   * @param comment the Comment object containing the updated comment data.
   */
  void updateComment(Comment comment);

  /**
   * Retrieves all comments associated with a specific recipe.
   *
   * @param recipeId the ID of the recipe for which to retrieve comments.
   * @return a list of Comment objects associated with the specified recipe.
   */
  List<Comment> getCommentsByRecipeId(int recipeId);
}

