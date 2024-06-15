package cookbook.viewer;

import cookbook.model.Tag;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * Display tag in a box.
 */
public class TagBox extends HBox {
  /**
   * Creates the box for the tag.
   *
   * @param tag displayed in box
   */
  public TagBox(Tag tag) {
    this.setAlignment(Pos.CENTER);
    this.getStyleClass().add("tag-box");
    this.setSpacing(3);
    Label name = new Label(tag.getName());
    this.getChildren().add(name);

    String backgroundColor = "-fx-background-color:" + tag.getBackgroundColor() + ";";
    String borderColor = "-fx-border-color: derive(" + tag.getBackgroundColor() + ", -20%);";
    String textColor = "-fx-text-fill:" + tag.getTextColor() + ";";
    this.setStyle(backgroundColor + borderColor);
    name.setStyle(textColor);
  }
}
