package cookbook.viewer;

import cookbook.model.Ingredient;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;

/**
 * deletable ingredient.
 */
public class DeleteableIngredientTableView extends IngredientTableView {

  /**
   * ctor.
   *
   * @param ingredients observablelist of ingredients. 
   */
  public DeleteableIngredientTableView(ObservableList<Ingredient> ingredients) {
    super(ingredients);

    TableColumn<Ingredient, Button> deleteColumn = new TableColumn<>("Delete");
    deleteColumn.setCellFactory(ButtonCell.<Ingredient>forTableColumn("delete", (Ingredient p) -> {
      this.getItems().remove(p);
      this.refresh();

      return p;
    }));
    deleteColumn.prefWidthProperty().bind(this.widthProperty().divide(12));
    deleteColumn.setResizable(true);
    deleteColumn.setMinWidth(40);
    deleteColumn.setReorderable(false);
    deleteColumn.setSortable(false);


    this.getColumns().add(deleteColumn);
  }
}
