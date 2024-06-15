package cookbook.viewer;

import cookbook.model.Ingredient;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Callback;

/**
 * Makes a table for the ingredients, quantities and their units.
 */
public class IngredientTableView extends TableView<Ingredient> {

  /**
   * Ctor takes observablelist in case their needs to be a binding for the list. When implementing
   * more/fewer portions etc.
   *
   * @param ingredientList observable list of ingredients.
   */
  public IngredientTableView(ObservableList<Ingredient> ingredientList) {
    this.setItems(ingredientList);
    this.setPlaceholder(new Label("Add some ingredients"));

    TableColumn<Ingredient, String> bulletColumn = new TableColumn<>();

    bulletColumn.setCellValueFactory(
        new Callback<CellDataFeatures<Ingredient, String>, ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(CellDataFeatures<Ingredient, String> i) {
            String bullet = Character.toString(8226);
            return new SimpleStringProperty(bullet + " ");
          }
        }
    );

    TableColumn<Ingredient, Text> ingredientNameColumn = new TableColumn<>("Ingredient");

    ingredientNameColumn.setCellValueFactory(
        new Callback<CellDataFeatures<Ingredient, Text>, ObservableValue<Text>>() {
          @Override
          public ObservableValue<Text> call(CellDataFeatures<Ingredient, Text> i) {
            String name = i.getValue().getName();

            StringBinding textBinding =
                Bindings.createStringBinding(() -> name);
            Text text = new Text();
            text.textProperty().bind(textBinding);

            text.wrappingWidthProperty().bind(ingredientNameColumn.widthProperty());
            return new SimpleObjectProperty<>(text);
          }
        });


    TableColumn<Ingredient, String> quantityUnitColumn = new TableColumn<>("quantity");

    quantityUnitColumn.setCellValueFactory(
        new Callback<CellDataFeatures<Ingredient, String>, ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(CellDataFeatures<Ingredient, String> i) {
            // i.getValue() returns the ingredient instance for a particular TableView row
            // converts qty to string, empty string if qty == 0, and removing zeroes if there are
            // any.
            return new SimpleStringProperty(i.getValue().getQtyStr());
          }
        });

    bulletColumn.prefWidthProperty().bind(this.widthProperty().divide(16));
    bulletColumn.setResizable(false);
    quantityUnitColumn.setResizable(true);
    ingredientNameColumn.maxWidthProperty().bind(this.widthProperty().divide(2));
    this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
    this.getColumns().add(bulletColumn);
    this.getColumns().add(ingredientNameColumn);
    this.getColumns().add(quantityUnitColumn);
  }

  /**
   * Overrides resize function to remove header of this table.
   * https://stackoverflow.com/questions/27118872/how-to-hide-tableview-column-header-in-javafx-8
   */
  @Override
  public void resize(double width, double height) {
    super.resize(width, height);
    Pane header = (Pane) lookup("TableHeaderRow");
    header.setMinHeight(0);
    header.setPrefHeight(0);
    header.setMaxHeight(0);
    header.setVisible(false);
  }
}
