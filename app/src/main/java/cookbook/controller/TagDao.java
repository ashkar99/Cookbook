package cookbook.controller;

import cookbook.model.Tag;

/**
 * The {@code TagDao} interface provides methods for creating, deleting, and
 * updating tags in the system.
 */
public interface TagDao {

  /**
   * Creates a new tag.
   *
   * @param tag the {@code Tag} object to be created.
   * @return the created {@code Tag} object with its ID populated.
   */
  Tag createTag(Tag tag);

  /**
   * Deletes a tag based on its ID.
   *
   * @param tagId the ID of the tag to be deleted.
   */
  void deleteTag(int tagId);

  /**
   * Updates an existing tag.
   *
   * @param oldTag the {@code Tag} object containing the updated information.
   */
  void updateTag(Tag oldTag);

}
