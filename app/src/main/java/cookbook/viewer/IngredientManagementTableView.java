package cookbook.viewer;

import cookbook.controller.IngredientDao;
import cookbook.controller.IngredientDaoImpl;
import cookbook.controller.MessageFeedback;
import cookbook.model.Ingredient;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;

/**
 * A table for users to manage all their ingredients.
 */
public class IngredientManagementTableView extends TableView<Ingredient> {
  private IngredientDao ingredientDao;
  private MainViewInterface mainViewPane;

  /**
   * Creates the table.
   */
  public IngredientManagementTableView(List<Ingredient> ingredients, MainViewInterface mainViewPane) {
    this.mainViewPane = mainViewPane;
    ingredientDao = new IngredientDaoImpl();

    this.setEditable(true);
    this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_SUBSEQUENT_COLUMNS);
    this.setItems(FXCollections.observableArrayList(ingredients));

    TableColumn<Ingredient, String> nameColumn = new TableColumn<>("Name");
    nameColumn.getStyleClass().add("align-left");
    nameColumn.setCellValueFactory(new Callback<CellDataFeatures<Ingredient, String>, ObservableValue<String>>() {
      public ObservableValue<String> call(CellDataFeatures<Ingredient, String> i) {
        return new SimpleStringProperty(i.getValue().getName());
      }
    });
    nameColumn.setCellFactory(col -> new EditableCell<>("Ingredient name", "Enter ingredient name"));
    nameColumn.setEditable(true);
    nameColumn.setOnEditCommit(
        event -> {
          try {
            event.getRowValue().setName(event.getNewValue());
          } catch (Exception e) {
            mainViewPane.toast(ToastType.ERROR, e.getMessage());
          }
          this.refresh();
        });

    TableColumn<Ingredient, Button> saveColumn = new TableColumn<>("Save");
    saveColumn.setCellFactory(ButtonCell.<Ingredient>forTableColumn("save", (Ingredient i) -> {
      i = updateIngredient(i);
      this.refresh();
      return i;
    }));
    saveColumn.prefWidthProperty().bind(this.widthProperty().divide(12));
    saveColumn.setResizable(false);
    saveColumn.setReorderable(false);
    saveColumn.setSortable(false);

    TableColumn<Ingredient, Button> deleteColumn = new TableColumn<>("Delete");
    deleteColumn.setCellFactory(ButtonCell.<Ingredient>forTableColumn("delete", (Ingredient i) -> {
      if (alertConfirmation(i) && deleteIngredient(i)) {
        this.getItems().remove(i);
        this.refresh();
      }

      return i;
    }));
    deleteColumn.prefWidthProperty().bind(this.widthProperty().divide(12));
    deleteColumn.setResizable(false);
    deleteColumn.setReorderable(false);
    deleteColumn.setSortable(false);

    this.getColumns().add(nameColumn);
    this.getColumns().add(saveColumn);
    this.getColumns().add(deleteColumn);
    this.getColumns().forEach(column -> column.setMinWidth(50));
  }

  private boolean alertConfirmation(Ingredient ingredient) {
    AlertPopup alert = new AlertPopup(AlertType.CONFIRMATION, mainViewPane);
    alert.setTitle("Confirmation Dialog");
    alert.setHeaderMessage("Permanently delete ingredient: " + ingredient.getName() + "? You can't undo this.");
    alert.setContentMessage("Are you sure?");

    ButtonType cancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
    ButtonType confirm = new ButtonType("Delete ingredient", ButtonData.OK_DONE);
    alert.getButtonTypes().setAll(cancel, confirm);

    if (alert.showAndWait().get() == confirm) {
      return true;
    }

    return false;
  }

  /**
   * Deletes ingredient.
   *
   * @param tag to be deleted
   */
  private boolean deleteIngredient(Ingredient ingredient) {
    try {
      ingredientDao.deleteIngredient(ingredient.getId());
      mainViewPane.toast(ToastType.SUCCESS, MessageFeedback.Success.Ingredient.DELETE.getMessage());
      return true;
    } catch (Exception e) {
      mainViewPane.toast(ToastType.ERROR, e.getMessage());
      return false;
    }
  }

  /**
   * Modifies ingredient.
   *
   * @param ingredient to be modified
   */
  private Ingredient updateIngredient(Ingredient ingredient) {
    if (ingredient.getId() > 0) {
      try {
        ingredientDao.updateIngredientName(ingredient);
        mainViewPane.toast(ToastType.SUCCESS, MessageFeedback.Success.Ingredient.UPDATE.getMessage());
      } catch (Exception e) {
        mainViewPane.toast(ToastType.ERROR, e.getMessage());
      }
    } else {
      try {
        ingredient = ingredientDao.createOwnedIngredient(ingredient);
        mainViewPane.toast(ToastType.SUCCESS, MessageFeedback.Success.Ingredient.ADD.getMessage());
      } catch (Exception e) {
        mainViewPane.toast(ToastType.ERROR, e.getMessage());
      }
    }
    return ingredient;
  }
}
