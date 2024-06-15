package cookbook.viewer;

import javafx.beans.binding.Bindings;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.paint.Color;

/**
 * A color picking cell for tableview.
 */
public class ColorPickingCell<S> extends TableCell<S, String> {

  private final ColorPicker colorPicker;

  /**
   * Creates the color picking cell.
   */
  public ColorPickingCell() {
    colorPicker = new ColorPicker();

    colorPicker.setOnAction(event -> {
      commitEdit(convertToWebColor());
    });

    itemProperty().addListener((observableValue, o, newValue) -> {
      if (newValue != null) {
        colorPicker.setValue(Color.web(newValue));
      }
    });

    contentDisplayProperty().bind(Bindings.when(editingProperty())
        .then(ContentDisplay.GRAPHIC_ONLY)
        .otherwise(ContentDisplay.TEXT_ONLY));
  }

  @Override
  public void updateItem(String color, boolean empty) {
    super.updateItem(color, empty);
    if (empty) {
      setGraphic(null);
      setText(null);
    } else {
      setText(convertToWebColor());
      setGraphic(colorPicker);
    }
  }

  @Override
  public void startEdit() {
    super.startEdit();
    if (!isEmpty()) {
      colorPicker.setValue(Color.web(getItem()));
    }
  }

  private String convertToWebColor() {
    int r = (int) Math.round(colorPicker.getValue().getRed() * 255.0);
    int g = (int) Math.round(colorPicker.getValue().getGreen() * 255.0);
    int b = (int) Math.round(colorPicker.getValue().getBlue() * 255.0);
    return String.format("#%02x%02x%02x", r, g, b);
  }
}
