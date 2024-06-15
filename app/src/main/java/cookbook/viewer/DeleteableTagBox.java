package cookbook.viewer;

import cookbook.model.Recipe;
import cookbook.model.Tag;
import javafx.scene.control.Button;

/**
 * Tag box that can be deleted.
 */
public class DeleteableTagBox extends TagBox {

  /**
   * Create a tag box with a delete button on hover.
   *
   * @param recipe to delete tag from
   * @param tag displayed in a box
   */
  public DeleteableTagBox(Recipe recipe, Tag tag) {
    super(tag);
    Button removeButton = new Button("X");
    removeButton.getStyleClass().clear();
    this.getChildren().add(removeButton);

    removeButton.setVisible(false);
    removeButton.setManaged(false);

    this.setOnMouseEntered(e -> {
      removeButton.setVisible(true);
      removeButton.setManaged(true);
    });
    this.setOnMouseExited(e -> {
      removeButton.setVisible(false);
      removeButton.setManaged(false);
    });
  }
}
