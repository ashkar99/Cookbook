package cookbook.viewer;

import cookbook.model.Tag;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;

/**
 * A preview cell for the user to preview their tags in tableview.
 */
public class TagPreviewCell extends TableCell<Tag, Tag> {

  /**
   * Creates the tag preview cell.
   */
  public TagPreviewCell() {
    itemProperty().addListener((observableValue, o, newValue) -> {
      if (newValue != null) {
        Node tag = createTagBox();
        graphicProperty().bind(Bindings.when(emptyProperty()).then((Node) null).otherwise(tag));
      }
    });
  }

  private Node createTagBox() {
    HBox tagsBox = new HBox();
    tagsBox.setAlignment(Pos.CENTER_LEFT);
    Tag tag = getItem();
    TagBox tagBox = new TagBox(tag);
    tagsBox.getChildren().add(tagBox);

    return tagsBox;
  }
}
