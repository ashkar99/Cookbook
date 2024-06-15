package cookbook.viewer;

import cookbook.controller.EnumSearch;
import cookbook.controller.RecipeDao;
import cookbook.controller.RecipeDaoImpl;
import cookbook.controller.SearchBy;
import cookbook.model.Recipe;
import cookbook.model.Tag;
import java.util.List;
import java.util.Map;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Scrollable tilepane.
 */
public class RecipesScrollPane extends ScrollPane {
  private RecipeDao recipeDaoImpl;
  private MainViewInterface mainViewPane;
  private String allRecipes = "All recipes";
  private String favouriteRecipes = "All favourite recipes";

  /**
   * Creates the scroll pane for tile layout.
   */
  public RecipesScrollPane(MainViewInterface mainViewPane) {
    this.setFitToWidth(true);
    recipeDaoImpl = new RecipeDaoImpl();
    this.mainViewPane = mainViewPane;
    displayRecipes();
  }

  /**
   * Search recipes.
   *
   * @param options list of options to search
   * @param search  search text
   */
  public void searchRecipes(List<SearchBy> options, String search) {
    setLoading();
    Task<Map<EnumSearch, List<Recipe>>> searchTask = new Task<Map<EnumSearch, List<Recipe>>>() {
      @Override
      protected Map<EnumSearch, List<Recipe>> call() throws Exception {
        Map<EnumSearch, List<Recipe>> recipes = recipeDaoImpl.search(options, search, mainViewPane.getUser().getId());
        return recipes;
      }
    };

    searchTask.setOnSucceeded(event -> {
      if (search == null || search.equals("") || options.size() == 0) {
        displayRecipes();
      } else {
        displayRecipes(searchTask.getValue());
      }
    });
    Thread fetchThread = new Thread(searchTask);
    fetchThread.setDaemon(true); // Ensure the thread stops when the application exits
    fetchThread.start();

  }

  /**
   * Load all common recipes in a task.
   */
  public void displayRecipes() {
    setLoading();
    Task<List<Recipe>> task = new Task<List<Recipe>>() {
      @Override
      protected List<Recipe> call() throws Exception {

        List<Recipe> recipes = recipeDaoImpl.getAllCommonRecipes(mainViewPane.getUser().getId());

        return recipes;
      }
    };

    task.setOnSucceeded(event -> {

      displayRecipes(task.getValue(), allRecipes);
    });

    Thread fetchThread = new Thread(task);
    fetchThread.setDaemon(true); // Ensure the thread stops when the application exits
    fetchThread.start();

    // displayRecipes(recipeDaoImpl.getAllCommonRecipes(mainViewPane.getUser().getId()),
    // allRecipes);
  }

  /**
   * Toggles between displaying favourites and all recipes.
   *
   * @param displayFavourites true for favourites and false for all recipes
   */
  public void displayRecipes(boolean displayFavourites) {
    setLoading();
    Task<List<Recipe>> favouriteTask = new Task<List<Recipe>>() {
      @Override
      protected List<Recipe> call() throws Exception {
        List<Recipe> recipes = recipeDaoImpl.getFavourites(mainViewPane.getUser().getId());
        return recipes;
      }
    };

    favouriteTask.setOnSucceeded(event -> {
      if (displayFavourites) {
        displayRecipes(favouriteTask.getValue(), favouriteRecipes);
      } else {
        displayRecipes();
      }
    });

    Thread fetchThread = new Thread(favouriteTask);
    fetchThread.setDaemon(true); // Ensure the thread stops when the application exits
    fetchThread.start();
  }

  /**
   * Displays recipes in stackpane.
   *
   * @param recipes to be displayed
   */
  private void displayRecipes(List<Recipe> recipes, String name) {
    StackPane stackPane = new StackPane();
    VBox recipesResult = createRecipesResult(recipes, name);
    stackPane.getChildren().add(recipesResult);
    this.setContent(stackPane);
  }

  /**
   * Displays recipes in stackpane.
   *
   * @param map key value enum serach and list of recipes
   */
  private void displayRecipes(Map<EnumSearch, List<Recipe>> map) {
    StackPane stackPane = new StackPane();
    VBox vbox = new VBox();
    vbox.setSpacing(50);
    stackPane.getChildren().add(vbox);
    this.setContent(stackPane);

    for (Map.Entry<EnumSearch, List<Recipe>> entry : map.entrySet()) {
      String name = entry.getKey().toString();
      List<Recipe> recipes = entry.getValue();
      VBox recipesResult = createRecipesResult(recipes, name);
      vbox.getChildren().add(recipesResult);
    }
  }

  private VBox createRecipesResult(List<Recipe> recipes, String name) {
    VBox vbox = new VBox();
    Text title = new Text(name);
    title.getStyleClass().add("search-result-title");
    vbox.getChildren().addAll(title, createTilePane(recipes));
    vbox.setAlignment(Pos.CENTER);

    return vbox;
  }

  private TilePane createTilePane(List<Recipe> recipes) {
    TilePane tilePane = new TilePane();
    tilePane.setPadding(new Insets(10, 10, 10, 10));

    tilePane.setAlignment(Pos.CENTER);
    tilePane.setPrefColumns(8);
    tilePane.setPrefTileHeight(200);
    tilePane.setPrefTileWidth(250);
    tilePane.setHgap(25);
    tilePane.setVgap(25);
    for (Recipe recipe : recipes) {
      tilePane.getChildren().add(createRecipe(recipe));
    }

    return tilePane;
  }

  private VBox createRecipe(Recipe recipe) {
    VBox vbox = new VBox();
    vbox.getStyleClass().add("recipe");

    HBox header = createRecipeHeader(vbox, recipe);
    vbox.getChildren().add(header);

    HBox descriptionBox = new HBox();
    vbox.getChildren().add(descriptionBox);
    Label description = new Label(recipe.getDescription());
    VBox.setVgrow(descriptionBox, Priority.ALWAYS);
    descriptionBox.getChildren().add(description);
    description.getStyleClass().add("description");
    description.setWrapText(false);

    FlowPane tagsBox = createTagsBox(recipe);
    VBox.setVgrow(tagsBox, Priority.NEVER);
    vbox.getChildren().add(tagsBox);

    vbox.setOnMouseClicked(event -> {
      try {
        mainViewPane.updateContent(new RecipeViewPane(
            recipeDaoImpl.getRecipeById(recipe.getId(), mainViewPane.getUser().getId()), mainViewPane));
      } catch (Exception e) {
        mainViewPane.toast(ToastType.ERROR, e.getMessage());
      }

    });

    vbox.setOnMouseEntered(event -> {
      vbox.setMinHeight(vbox.getHeight() + 25);
      description.setWrapText(true);
      tagsBox.setManaged(false);
      tagsBox.setVisible(false);
    });
    vbox.setOnMouseExited(event -> {
      vbox.setMinHeight(vbox.getPrefHeight());
      description.setWrapText(false);
      tagsBox.setManaged(true);
      tagsBox.setVisible(true);
    });

    return vbox;
  }

  private HBox createRecipeHeader(VBox recipeCard, Recipe recipe) {
    HBox header = new HBox();
    header.setSpacing(10);

    HBox titleBox = new HBox();
    Label title = new Label(recipe.getName());
    titleBox.getChildren().add(title);
    title.getStyleClass().add("title");
    title.setWrapText(true);

    HBox starBox = new HBox();
    starBox.setPrefSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
    Region starIcon = new Region();

    starIcon.getStyleClass().addAll("icon", (recipe.isFavourite() ? "star-filled-icon" : "star-icon"));
    starIcon.setVisible(recipe.isFavourite());
    recipeCard.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        starIcon.setVisible(true);
      }
    });
    recipeCard.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        starIcon.setVisible(recipe.isFavourite());
      }
    });

    Button star = new Button();
    star.setOnAction(event -> {
      try {
        recipe.setIsFavourite(!recipe.isFavourite());
        recipeDaoImpl.doFavourite(recipe.getId(), mainViewPane.getUser().getId(), recipe.isFavourite());
        starIcon.getStyleClass().remove(!recipe.isFavourite() ? "star-filled-icon" : "star-icon");
        starIcon.getStyleClass().add(recipe.isFavourite() ? "star-filled-icon" : "star-icon");
      } catch (Exception e) {
        mainViewPane.toast(ToastType.ERROR, e.getMessage());
      }
    });
    star.setGraphic(starIcon);
    star.getStyleClass().add("icon-button");
    starBox.getChildren().add(star);

    HBox.setHgrow(titleBox, Priority.ALWAYS);
    header.getChildren().addAll(titleBox, starBox);

    return header;
  }

  private FlowPane createTagsBox(Recipe recipe) {
    FlowPane tagsBox = new FlowPane();
    tagsBox.setHgap(5);
    tagsBox.setVgap(5);

    List<Tag> tags = recipe.getTags();
    int tagsToBeDisplayed = Math.min(tags.size(), 5);
    for (int i = 0; i < tagsToBeDisplayed; i++) {
      Tag tag = tags.get(i);
      TagBox tagBox = new TagBox(tag);
      tagsBox.getChildren().add(tagBox);
    }

    return tagsBox;
  }

  private void setLoading() {
    LoadingViewPane loadingViewPane = new LoadingViewPane();
    this.setContent(loadingViewPane);
    loadingViewPane.minHeightProperty().bind(this.heightProperty());
    loadingViewPane.minWidthProperty().bind(this.widthProperty());
  }
}
