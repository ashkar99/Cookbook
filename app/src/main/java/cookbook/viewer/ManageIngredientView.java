package cookbook.viewer;

import cookbook.controller.IngredientDao;
import cookbook.controller.IngredientDaoImpl;
import cookbook.model.Ingredient;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * Admin page of the application.
 */
public class ManageIngredientView extends VBox {
  private IngredientDao ingredientDao;

  /**
   * Creates the admin view pane for application.
   */
  public ManageIngredientView(MainViewInterface mainViewPane) {
    ingredientDao = new IngredientDaoImpl();
    this.getStyleClass().add("table-management");
    LoadingViewPane loadingViewPane = new LoadingViewPane();
    this.getChildren().add(loadingViewPane);
    loadingViewPane.minHeightProperty().bind(this.heightProperty());
    loadingViewPane.minWidthProperty().bind(this.widthProperty());

    Task<VBox> manageIngredientTask = new Task<VBox>() {
      @Override
      protected VBox call() throws Exception {
        StackPane buttonAlignment = new StackPane();
        Button button = new Button("Add ingredient");

        buttonAlignment.getChildren().add(button);
        StackPane.setAlignment(button, Pos.CENTER_RIGHT);

        TableView<Ingredient> tableView = new IngredientManagementTableView(
            ingredientDao.getAllOwnedIngredients(mainViewPane.getUser().getId()), mainViewPane);

        ManageTagView.setVgrow(tableView, Priority.ALWAYS);

        button.setOnMouseClicked(event -> {
          Ingredient ingredient = new Ingredient();
          ingredient.setOwner(mainViewPane.getUser().getId());
          ingredient.setName("New ingredient");
          tableView.getItems().addLast(ingredient);
          tableView.scrollTo(ingredient);
        });

        VBox content = new VBox(buttonAlignment, tableView);
        content.setSpacing(20);
        return content;
      }
    };
    manageIngredientTask.setOnSucceeded(event -> {
      this.getChildren().remove(loadingViewPane);
      this.getChildren().addAll(manageIngredientTask.getValue());
      this.setPadding(new Insets(20, 100, 100, 100));
      this.setSpacing(20);
    });

    Thread fetchThread = new Thread(manageIngredientTask);
    fetchThread.setDaemon(true); // Ensure the thread stops when the application exits
    fetchThread.start();
  }
}
