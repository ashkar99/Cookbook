package cookbook.viewer;

import cookbook.controller.CommentDao;
import cookbook.controller.CommentDaoImpl;
import cookbook.controller.MessageFeedback;
import cookbook.controller.RecipeDao;
import cookbook.controller.RecipeDaoImpl;
import cookbook.model.Comment;
import cookbook.model.Ingredient;
import cookbook.model.Recipe;
import cookbook.model.Step;
import cookbook.model.Tag;
import java.time.Instant;
import java.util.List;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * The view that opens when clicking on a recipe in the scrollpane.
 */
public class RecipeViewPane extends ScrollPane {
  int portions;
  private IngredientTableView ingredientTableView;
  private RecipeDao recipeDaoImpl;
  private MainViewInterface mainViewPane;
  private CommentDao commentDaoImpl;
  ObservableList<Comment> comments;
  VBox stepBox;
  VBox titleDescriptionIngredientsBox;

  /**
   * Constructs the Recipe view pane.
   *
   * @param recipe the recipe model that we want to show. Shoul
   */
  public RecipeViewPane(Recipe recipe, MainViewInterface mainViewInterface) {
    portions = recipe.getPortions();
    this.mainViewPane = mainViewInterface;

    this.commentDaoImpl = new CommentDaoImpl();
    this.recipeDaoImpl = new RecipeDaoImpl();
    GridPane recipeViewGrid = new GridPane();

    recipeViewGrid.addColumn(0);
    recipeViewGrid.addColumn(1);
    recipeViewGrid.addColumn(2);
    recipeViewGrid.addColumn(3);
    recipeViewGrid.setHgap(20);
    recipeViewGrid.setVgap(40);

    ColumnConstraints col0 = new ColumnConstraints();
    col0.setPercentWidth(10);
    ColumnConstraints col1 = new ColumnConstraints();
    col1.setPercentWidth(40);
    ColumnConstraints col2 = new ColumnConstraints();
    col2.setPercentWidth(40);
    ColumnConstraints col3 = new ColumnConstraints();
    col3.setPercentWidth(10);
    recipeViewGrid.getColumnConstraints().addAll(col0, col1, col2, col3);

    LoadingViewPane loadingViewPane = new LoadingViewPane();
    this.setContent(loadingViewPane);
    loadingViewPane.minWidthProperty().bind(this.widthProperty());
    loadingViewPane.minHeightProperty().bind(this.heightProperty());

    // Test Task
    Task<CommentDao> commentTask = new Task<>() {
      @Override
      protected CommentDao call() throws Exception {
        // Simulate a long-running operation, replace this with your database fetching
        // logic
        CommentDao commentDao = new CommentDaoImpl();
        comments = FXCollections.observableArrayList(commentDao.getCommentsByRecipeId(recipe.getId()));
        createCommentPane(recipe, mainViewInterface, recipeViewGrid);

        titleDescriptionIngredientsBox = createLeftPanel(recipe);
        stepBox = createRightPanel(recipe);

        GridPane.setConstraints(titleDescriptionIngredientsBox, 1, 0);
        GridPane.setConstraints(stepBox, 2, 0);
        return commentDao;
      }
    };

    commentTask.setOnSucceeded(e -> {
      // Assuming commentDaoImpl is a field in the surrounding class

      recipeViewGrid.getChildren().addAll(new Pane(), titleDescriptionIngredientsBox, stepBox,
          new Pane());
      recipeViewGrid.setPadding(new Insets(10, 10, 10, 10));
      VBox content = new VBox();
      content.getChildren().add(recipeViewGrid);

      content.setAlignment(Pos.TOP_CENTER);

      this.getStyleClass().add("recipe-view");
      this.setFitToWidth(true);
      VBox.setVgrow(this, Priority.ALWAYS);
      VBox.setVgrow(content, Priority.ALWAYS);
      this.setContent(content);

      // If you need to update any UI elements, make sure to do it on the JavaFX
      // Application Thread
      Platform.runLater(() -> {
        // Update your UI elements here
        // Example: someLabel.setText(this.commentDaoImpl.getSomeData());
      });
    });

    commentTask.setOnFailed(e -> {

      // Handle any errors that occurred during the task
      Throwable error = commentTask.getException();
      error.printStackTrace();
    });

    Thread fetchThread = new Thread(commentTask);
    fetchThread.setDaemon(true); // Ensure the thread stops when the application exits
    fetchThread.start();
    // this.commentDaoImpl = new CommentDaoImpl();

  }

  private void createCommentPane(Recipe recipe, MainViewInterface mainViewInterface,
      GridPane gridPane) {

    comments = FXCollections.observableArrayList(commentDaoImpl.getCommentsByRecipeId(recipe.getId()));

    CommentListView commentList = new CommentListView(comments, mainViewInterface);

    commentList.getStyleClass().add("comment-table");
    HBox newCommentBox = new HBox();
    newCommentBox.getStyleClass().add("new-comment-box");

    Label newCommentLabel = new Label("Add a comment to this recipe:");
    TextArea newCommentTextArea = new TextArea();
    newCommentTextArea.setWrapText(true);
    newCommentTextArea.maxHeightProperty().bind(newCommentBox.widthProperty().divide(3));

    VBox newCommentLabelTextAreaContainer = new VBox();
    newCommentLabelTextAreaContainer.getChildren().addAll(newCommentLabel, newCommentTextArea);
    Button plusButton = new Button("+");

    newCommentBox.getChildren().addAll(newCommentLabelTextAreaContainer, plusButton);
    newCommentBox.setAlignment(Pos.BOTTOM_CENTER);
    newCommentBox.getStyleClass().add("commentCreator");
    newCommentBox.setPadding(new Insets(10, 10, 10, 10));
    gridPane.add(newCommentBox, 1, 1);

    plusButton.setOnAction(event -> {
      try {
        Comment newComment = new Comment();
        newComment.setDisplayName(mainViewInterface.getUser().getDisplayname());
        newComment.setRecipeId(recipe.getId());
        newComment.setText(newCommentTextArea.getText());
        newComment.setUserId(mainViewInterface.getUser().getId());
        newComment.setDate(Instant.now().getEpochSecond());
        newComment.setUpdatedDate(Instant.now().getEpochSecond());

        commentDaoImpl.createComment(newComment, newComment.getRecipeId());
        ObservableList<Comment> newComments = FXCollections
            .observableArrayList(commentDaoImpl.getCommentsByRecipeId(recipe.getId()));
        commentList.setOriginalList(newComments);
        commentList.sortCommentsByDate();
        newCommentTextArea.setText("");
        mainViewInterface.toast(ToastType.SUCCESS, MessageFeedback.Success.Comment.ADD.getMessage());
      } catch (Exception e) {
        mainViewPane.toast(ToastType.INFO, e.getMessage());
      }

    });

    gridPane.add(commentList, 1, 2, 2, 1);
  }

  private boolean alertConfirmation(Recipe recipe) {
    AlertPopup alert = new AlertPopup(AlertType.CONFIRMATION, mainViewPane);
    alert.setTitle("Confirmation Dialog");
    alert.setHeaderMessage(
        "Permanently delete Recipe: " + recipe.getName() + ". You cant undo this");
    alert.setContentMessage("Are you sure?");

    ButtonType cancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
    ButtonType confirm = new ButtonType("Delete Recipe", ButtonData.OK_DONE);
    alert.getButtonTypes().setAll(cancel, confirm);

    if (alert.showAndWait().get() == confirm) {
      return true;
    }

    return false;
  }

  /*
   * private Comment createComment(String text) { VBox commentBox = new VBox();
   * commentBox.getChildren().addAll(new Label(text)); return commentBox; }
   */
  private VBox createRightPanel(Recipe recipe) {
    VBox rightPanel = new VBox();
    Label instructions = new Label("Instructions");
    instructions.getStyleClass().add("instructions");
    rightPanel.getChildren().add(instructions);

    ListView<Text> stepListView = new ListView<>();

    stepListView.getStyleClass().add("ingredient-list");
    HBox.setHgrow(stepListView, Priority.ALWAYS);

    for (Step step : recipe.getSteps()) {
      Text stepText = new Text(step.getStepIndex() + ". " + step.getInstructions());
      stepText.wrappingWidthProperty().bind(stepListView.widthProperty().subtract(50));
      stepListView.getItems().add(stepText);
    }

    ScrollPane stepScrollPane = new ScrollPane();
    stepScrollPane.setContent(stepListView);
    stepScrollPane.setFitToWidth(true);
    stepScrollPane.setFitToHeight(true);
    stepScrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
    VBox.setVgrow(stepScrollPane, Priority.ALWAYS);

    rightPanel.getChildren().add(stepScrollPane);

    return rightPanel;
  }

  private VBox createLeftPanel(Recipe recipe) {
    VBox leftPanel = new VBox();
    HBox.setHgrow(leftPanel, Priority.ALWAYS);

    // Add title
    HBox header = createHeader(recipe);
    leftPanel.getChildren().add(header);

    HBox tagsContainer = createTagsContainer(recipe);
    leftPanel.getChildren().add(tagsContainer);

    // Add description
    Label description = new Label(recipe.getDescription());
    description.setWrapText(true);
    description.getStyleClass().add("description");
    description.setMinHeight(Region.USE_PREF_SIZE);
    description.setPadding(new Insets(10, 10, 10, 10));

    VBox portionsBoxContainer = createPortionsBox(recipe);

    leftPanel.getChildren().addAll(description, portionsBoxContainer);

    List<Ingredient> ingredients = recipe.getIngredients();

    ScrollPane ingredientScrollPane = new ScrollPane();

    ingredientTableView = new IngredientTableView(FXCollections.observableArrayList(ingredients));
    ingredientTableView.minHeightProperty().bind(ingredientScrollPane.heightProperty().subtract(5));
    ingredientTableView.minWidthProperty().bind(ingredientScrollPane.widthProperty().subtract(5));

    HBox.setHgrow(ingredientTableView, Priority.ALWAYS);

    ingredientScrollPane.setContent(ingredientTableView);
    ingredientScrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);

    leftPanel.getChildren().add(ingredientScrollPane);

    leftPanel.setSpacing(10);

    return leftPanel;
  }

  private HBox createHeader(Recipe recipe) {
    HBox header = new HBox();
    header.setSpacing(10);

    HBox titleBoxAndEditButtonBox = new HBox();
    VBox titleAndCreatorBox = new VBox(titleBoxAndEditButtonBox);
    Label title = new Label(recipe.getName());

    titleBoxAndEditButtonBox.getChildren().add(title);

    String nameOfCreator = recipe.getOwnerDisplayName();

    if (recipe.getOwner() == (mainViewPane.getUser().getId())) {
      Button edit = new Button();
      Region editSvgShape = new Region();
      editSvgShape.getStyleClass().addAll("icon", "update-icon");

      edit.getStyleClass().add("icon-button");
      edit.setGraphic(editSvgShape);

      edit.setOnAction(e -> {
        mainViewPane
            .updateContent(new CreateRecipeView(mainViewPane, mainViewPane.getUser(), recipe));
      });

      Button delete = new Button();

      Region deleteSvgShape = new Region();
      deleteSvgShape.getStyleClass().addAll("icon", "delete-icon");
      delete.getStyleClass().add("icon-button");
      delete.setGraphic(deleteSvgShape);

      delete.setOnAction(e -> {
        try {
          if (this.alertConfirmation(recipe)) {
            recipeDaoImpl.deleteRecipe(recipe.getId());
            mainViewPane.toast(ToastType.SUCCESS, MessageFeedback.Success.Recipe.DELETE.getMessage());
            mainViewPane.updateContent(new DisplayRecipePane(mainViewPane));
          }
        } catch (Exception e6) {
          mainViewPane.toast(ToastType.ERROR, e6.getMessage());
        }

      });

      titleBoxAndEditButtonBox.getChildren().addAll(edit, delete);

    } else if (nameOfCreator != null) {
      Label owner = new Label("Written by " + nameOfCreator + ".");
      titleAndCreatorBox.getChildren().add(owner);
      owner.getStyleClass().add("recipe-owner");
    }

    title.getStyleClass().add("title");

    title.setWrapText(true);

    HBox starBox = new HBox();
    starBox.setPrefSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
    Region starIcon = new Region();

    starIcon.getStyleClass().addAll("icon",
        (recipe.isFavourite() ? "star-filled-icon" : "star-icon"));

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

    HBox.setHgrow(title, Priority.ALWAYS);
    header.getChildren().addAll(titleAndCreatorBox, starBox);

    return header;
  }

  private VBox createPortionsBox(Recipe recipe) {
    HBox portionsBoxContainer = new HBox();
    portionsBoxContainer.setAlignment(Pos.CENTER_LEFT);

    HBox portionsBox = new HBox();
    portionsBox.getStyleClass().add("portions-box");
    portionsBox.setSpacing(10);
    portionsBox.setAlignment(Pos.CENTER);
    portionsBox.setPrefSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);

    HBox warningBox = new HBox();
    warningBox.getStyleClass().add("warning-box");
    Label note = new Label("Note:");
    note.setMinWidth(Region.USE_PREF_SIZE);
    note.getStyleClass().add("note");
    Label warningMessage = new Label(
        "Ingredient quantity may not scale linearly. Quantity and units are not changed under instructions.");
    warningMessage.setWrapText(true);
    warningBox.setMinHeight(Region.USE_PREF_SIZE);
    warningBox.setPadding(new Insets(10));

    warningBox.getChildren().addAll(note, warningMessage);
    warningBox.setSpacing(10);
    warningBox.setAlignment(Pos.CENTER);

    warningBox.setVisible(false);
    warningBox.setManaged(false);

    Label portionsLabel = new Label(String.valueOf(portions));
    Region utensilsIcon = new Region();
    utensilsIcon.setPrefSize(30, 30);
    utensilsIcon.getStyleClass().add("utensils");
    portionsLabel.setGraphic(utensilsIcon);
    portionsLabel.setPrefSize(USE_COMPUTED_SIZE + 60, USE_COMPUTED_SIZE);

    Button subtractButton = new Button("-");
    Button addButton = new Button("+");

    subtractButton.setOnAction(event -> {
      if (portions == 2) {
        portions--;
        subtractButton.setVisible(false);
      } else if (portions % 2 == 0) {
        portions = portions - 2;
      }
      if (!addButton.isVisible()) {
        addButton.setVisible(true);
      }

      boolean showWarning = portions != recipe.getPortions();
      warningBox.setVisible(showWarning);
      warningBox.setManaged(showWarning);

      portionsLabel.setText(String.valueOf(portions));
      updatePortions(recipe);
    });

    addButton.setOnAction(event -> {
      if (portions == 1) {
        portions++;
      } else if (portions % 2 == 0 && portions <= 18) {
        portions = portions + 2;
      }
      if (portions == 20) {
        addButton.setVisible(false);
      }
      if (!subtractButton.isVisible()) {
        subtractButton.setVisible(true);
      }

      boolean showWarning = portions != recipe.getPortions();
      warningBox.setVisible(showWarning);
      warningBox.setManaged(showWarning);

      portionsLabel.setText(String.valueOf(portions));
      updatePortions(recipe);
    });

    portionsBox.getChildren().addAll(subtractButton, portionsLabel, addButton);
    portionsBoxContainer.getChildren().addAll(portionsBox);

    VBox portionsContainer = new VBox();
    portionsContainer.getChildren().addAll(portionsBoxContainer, warningBox);
    portionsContainer.setSpacing(20);

    return portionsContainer;
  }

  private void updatePortions(Recipe recipe) {
    // RecipeDao recipeDao = new RecipeDaoImpl();
    recipeDaoImpl.convertRecipeTo(portions, recipe);

    ingredientTableView.refresh();
  }

  private HBox createTagsContainer(Recipe recipe) {
    HBox tagsContainer = new HBox();
    FlowPane recipesTagsBox = createTagsBox(recipe);
    HBox.setHgrow(recipesTagsBox, Priority.SOMETIMES);
    tagsContainer.getChildren().add(recipesTagsBox);

    HBox addTagComboboxContainer = new HBox();
    tagsContainer.getChildren().add(addTagComboboxContainer);
    addTagComboboxContainer.setMaxHeight(USE_PREF_SIZE);

    ObservableList<Tag> allTags = FXCollections
        .observableArrayList(recipeDaoImpl.getOwnedTags(mainViewPane.getUser().getId()));
    ComboBox<Tag> tagsCombobox = new SearchableComboBox<>(mainViewPane.getUser().getId(), allTags, Tag::getName);
    tagsCombobox.setMinWidth(100);
    addTagComboboxContainer.getChildren().add(tagsCombobox);

    Button addTag = new Button("Add tag");
    HBox.setHgrow(addTag, Priority.ALWAYS);
    addTag.setMinWidth(USE_PREF_SIZE);
    addTag.setMinHeight(USE_PREF_SIZE);
    addTag.setMaxHeight(Double.MAX_VALUE);
    addTagComboboxContainer.getChildren().add(addTag);

    addTag.setOnAction(event -> {
      Tag selectedTag = tagsCombobox.getSelectionModel().getSelectedItem();
      if (selectedTag == null) {
        mainViewPane.toast(ToastType.ERROR, MessageFeedback.Error.Tag.ADD_FAIL.getMessage());
        return;
      }

      for (Tag tag : recipe.getTags()) {
        if (tag.getId() == selectedTag.getId()) {
          mainViewPane.toast(ToastType.ERROR, MessageFeedback.Error.Tag.EXIST.getMessage());
          return;
        }
      }

      try {
        recipeDaoImpl.addTagToRecipe(recipe.getId(), selectedTag.getId(),
            mainViewPane.getUser().getId());
        recipe.getTags().add(selectedTag);
        TagBox tagBox = new DeleteableTagBox(recipe, selectedTag);
        recipesTagsBox.getChildren().add(tagBox);

        tagBox.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            if (event.getEventType().equals(ActionEvent.ACTION)) {
              recipesTagsBox.getChildren().remove(tagBox);
              recipe.getTags().remove(selectedTag);
              recipeDaoImpl.updatePersonalTags(recipe, mainViewPane.getUser().getId());
            }
          }
        });
      } catch (Exception e) {
        mainViewPane.toast(ToastType.ERROR, e.getMessage());
      }
    });

    return tagsContainer;
  }

  private FlowPane createTagsBox(Recipe recipe) {
    FlowPane tagsBox = new FlowPane();
    tagsBox.setHgap(5);
    tagsBox.setVgap(5);

    for (Tag tag : recipe.getTags()) {
      if (tag.getOwnerId() != mainViewPane.getUser().getId()) {
        tagsBox.getChildren().add(new TagBox(tag));
        continue;
      }

      TagBox tagBox = new DeleteableTagBox(recipe, tag);
      tagBox.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          if (event.getEventType().equals(ActionEvent.ACTION)) {
            tagsBox.getChildren().remove(tagBox);
            recipe.getTags().remove(tag);
            recipeDaoImpl.updatePersonalTags(recipe, mainViewPane.getUser().getId());
          }
        }
      });
      tagsBox.getChildren().add(tagBox);
    }

    return tagsBox;
  }
}
