package cookbook.controller;

import cookbook.model.Tag;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * The {@code TagDaoImpl} class implements the {@code TagDao} interface.
 * It provides functionality for creating, deleting, and updating tags in the
 * system.
 */
public class TagDaoImpl extends BaseDaoImpl implements TagDao {

  /**
   * Initializes a new instance of the {@code TagDaoImpl} class in production
   * mode.
   */
  public TagDaoImpl() {
    super(true);
  }

  /**
   * Initializes a new instance of the {@code TagDaoImpl} class with the specified
   * mode.
   *
   * @param production a boolean indicating if the instance is in production mode.
   */
  public TagDaoImpl(boolean production) {
    super(production);
  }

  /**
   * Creates a new user-defined tag.
   *
   * @param tag the {@code Tag} object to be created.
   * @return the created {@code Tag} object with its ID populated.
   * @throws IllegalArgumentException if the user ID is invalid or if the tag
   *                                  could not be created.
   */
  @Override
  public Tag createTag(Tag tag) {
    if (tag.getOwnerId() <= 0) {
      throw new IllegalArgumentException("Illegal user id.");
    }

    ResultSet rs = null;
    PreparedStatement ps = null;
    String sql = "INSERT INTO tag (name, owner, backgroundColor, textColor) VALUES (?, ?, ?, ?)";

    try {
      ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      ps.setString(1, tag.getName());
      ps.setInt(2, tag.getOwnerId());
      ps.setString(3, tag.getBackgroundColor());
      ps.setString(4, tag.getTextColor());
      ps.executeUpdate();

      // Get the generated key/id
      rs = ps.getGeneratedKeys();

      if (rs.next()) {
        tag.setId(rs.getInt(1));
      } else {
        throw new SQLException(MessageFeedback.Error.Sql.KEY.getMessage());
      }
    } catch (SQLException e) {
      throw new IllegalArgumentException(MessageFeedback.Error.Tag.ADD.getMessage());
    } finally {
      closeResources(null, ps);
    }

    return tag;
  }

  /**
   * Deletes a tag based on its ID.
   *
   * @param tagId the ID of the tag to be deleted.
   * @throws IllegalArgumentException if the tag could not be deleted.
   */
  @Override
  public void deleteTag(int tagId) {
    PreparedStatement ps = null;
    String sql = "DELETE FROM tag WHERE id = ?";

    try {
      ps = con.prepareStatement(sql);
      ps.setInt(1, tagId);
      ps.executeUpdate();
    } catch (SQLException e) {
      throw new IllegalArgumentException(MessageFeedback.Error.Tag.DELETE.getMessage());
    } finally {
      closeResources(null, ps);
    }
  }

  /**
   * Updates an existing tag.
   *
   * @param tag the {@code Tag} object containing the updated information.
   * @throws IllegalArgumentException if the tag could not be updated.
   */
  @Override
  public void updateTag(Tag tag) {
    PreparedStatement ps = null;

    try {
      ps = con.prepareStatement("UPDATE tag SET name = ?, backgroundcolor = ?, textcolor = ? WHERE id = ?");
      ps.setString(1, tag.getName());
      ps.setString(2, tag.getBackgroundColor());
      ps.setString(3, tag.getTextColor());
      ps.setInt(4, tag.getId());
      ps.executeUpdate();
    } catch (SQLException e) {
      throw new IllegalArgumentException(MessageFeedback.Error.Tag.UPDATE.getMessage());
    } finally {
      closeResources(null, ps);
    }
  }
}
