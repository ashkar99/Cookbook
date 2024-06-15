package cookbook.viewer;

import com.google.common.base.Function;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.Region;
import javafx.util.Callback;

/**
 * A button cell for tableview.
 */
public class ButtonCell<S> extends TableCell<S, Button> {
  private Button actionButton;

  /**
   * Creates the button cell.
   *
   * @param styleClass css class to style the class
   * @param function triggers on click
   */
  public ButtonCell(String styleClass, Function<S, S> function) {
    Region svgShape = new Region();
    svgShape.getStyleClass().addAll("icon", styleClass + "-icon");

    actionButton = new Button();
    actionButton.getStyleClass().add("icon-button");
    actionButton.setGraphic(svgShape);
    this.setAlignment(Pos.CENTER);

    actionButton.setOnAction((ActionEvent e) -> {
      function.apply(getCurrentItem());
    });
    actionButton.setMaxWidth(Double.MAX_VALUE);
  }

  public S getCurrentItem() {
    return (S) getTableView().getItems().get(getIndex());
  }

  public static <S> Callback<TableColumn<S, Button>, TableCell<S, Button>> forTableColumn(String styleClass,
      Function<S, S> function) {
    return param -> new ButtonCell<S>(styleClass, function);
  }

  @Override
  public void updateItem(Button item, boolean empty) {
    super.updateItem(item, empty);

    if (empty) {
      setGraphic(null);
    } else {
      setGraphic(actionButton);
    }
  }
}
