package cookbook.viewer;

import javafx.concurrent.Task;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * Display searchComponent + recipesScrollPane.
 */
public class DisplayRecipePane extends VBox {

  RecipesScrollPane recipesScrollPane;

  /**
   * Construct a DisplayRecipePane, showing all recipes.
   *
   * @param mainViewPane the mainview
   */
  public DisplayRecipePane(MainViewInterface mainViewPane) {

    LoadingViewPane loadingViewPane = new LoadingViewPane();
    this.getChildren().add(loadingViewPane);
    loadingViewPane.minHeightProperty().bind(this.heightProperty());
    loadingViewPane.minWidthProperty().bind(this.widthProperty());

    Task<RecipesScrollPane> task = new Task<RecipesScrollPane>() {
      @Override
      protected RecipesScrollPane call() throws Exception {

        recipesScrollPane = new RecipesScrollPane(mainViewPane);
        return recipesScrollPane;
      }
    };

    task.setOnSucceeded(event -> {
      this.getChildren().remove(loadingViewPane);
      SearchComponent searchComponent = new SearchComponent(recipesScrollPane, mainViewPane);

      this.getChildren().addAll(searchComponent, recipesScrollPane);
      DisplayRecipePane.setVgrow(recipesScrollPane, Priority.ALWAYS);
    });

    Thread fetchThread = new Thread(task);
    fetchThread.setDaemon(true); // Ensure the thread stops when the application exits
    fetchThread.start();

  }
}
