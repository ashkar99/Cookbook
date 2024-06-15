package cookbook.viewer;

import cookbook.controller.RecipeDao;
import cookbook.controller.RecipeDaoImpl;
import cookbook.model.Tag;
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
public class ManageTagView extends VBox {
  private RecipeDao recipeDao;

  /**
   * Creates the admin view pane for application.
   */
  public ManageTagView(MainViewInterface mainViewPane) {
    recipeDao = new RecipeDaoImpl();
    this.getStyleClass().add("table-management");
    LoadingViewPane loadingViewPane = new LoadingViewPane();
    this.getChildren().add(loadingViewPane);
    loadingViewPane.minHeightProperty().bind(this.heightProperty());
    loadingViewPane.minWidthProperty().bind(this.widthProperty());

    Task<VBox> manageTagTask = new Task<VBox>() {
      @Override
      protected VBox call() throws Exception {

        StackPane buttonAlignment = new StackPane();
        Button button = new Button("Add tag");

        buttonAlignment.getChildren().add(button);
        StackPane.setAlignment(button, Pos.CENTER_RIGHT);

        TableView<Tag> tableView = new TagManagementTableView(recipeDao.getOwnedTags(mainViewPane.getUser().getId()),
            mainViewPane);
        ManageTagView.setVgrow(tableView, Priority.ALWAYS);

        button.setOnMouseClicked(event -> {
          Tag tag = new Tag();
          tag.setOwnerId(mainViewPane.getUser().getId());
          tableView.getItems().addLast(tag);
          tableView.scrollTo(tag);
        });

        VBox content = new VBox(buttonAlignment, tableView);
        content.setSpacing(20);
        return content;
      }
    };
    manageTagTask.setOnSucceeded(event -> {
      this.getChildren().remove(loadingViewPane);
      this.getChildren().addAll(manageTagTask.getValue());
      this.setPadding(new Insets(20, 100, 100, 100));
      this.setSpacing(20);
    });

    Thread fetchThread = new Thread(manageTagTask);
    fetchThread.setDaemon(true); // Ensure the thread stops when the application exits
    fetchThread.start();
  }
}
