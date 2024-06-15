package cookbook.viewer;

import javafx.beans.binding.Bindings;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

/**
 * An editable cell for tableview.
 */
public class EditableCell<S> extends TableCell<S, String> {
  private TextField textField;

  /**
   * Creates the editable cell.
   */
  public EditableCell(String promptText, String tooltip) {
    textField = new TextField();
    textField.setPromptText(promptText);
    textField.setTooltip(new Tooltip(tooltip));

    textField.setOnAction(event -> {
      commitEdit(textField.getText());

    });

    itemProperty().addListener((observableValue, o, newValue) -> {
      if (newValue != null) {
        textField.setText(newValue);
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
      setText(textField.getText());
      setGraphic(textField);
    }
  }

  @Override
  public void startEdit() {
    super.startEdit();
    if (!isEmpty()) {
      textField.setText(getItem());
    }
  }
}
