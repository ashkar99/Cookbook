package cookbook.viewer;

import cookbook.controller.IngredientDao;
import cookbook.controller.IngredientDaoImpl;
import cookbook.controller.MessageFeedback;
import cookbook.controller.RecipeDao;
import cookbook.controller.RecipeDaoImpl;
import cookbook.model.Ingredient;
import cookbook.model.ObservableStep;
import cookbook.model.Recipe;
import cookbook.model.Step;
import cookbook.model.Tag;
import cookbook.model.Unit;
import cookbook.model.User;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * View for user to create a recipe.
 * 
 */
public class CreateRecipeView extends VBox {

  private RecipeDao recipeDaoImpl;
  private MainViewInterface mainViewPane;

  /**
   * Constructs the CreateRecipeView.
   */
  public CreateRecipeView(MainViewInterface mainView, User user, Recipe recipeToEdit) {
    this.recipeDaoImpl = new RecipeDaoImpl();
    this.mainViewPane = mainView;
    this.getStyleClass().add("create-recipe-view");

    LoadingViewPane loadingViewPane = new LoadingViewPane();
    this.getChildren().add(loadingViewPane);
    loadingViewPane.minHeightProperty().bind(this.heightProperty());
    loadingViewPane.minWidthProperty().bind(this.widthProperty());
    VBox content = displayCreateRecipeView(mainView, user, recipeToEdit);
    this.getChildren().remove(loadingViewPane);
    this.getChildren().add(content);

  }

  private VBox displayCreateRecipeView(MainViewInterface mainView, User user, Recipe recipeToEdit) {

    GridPane recipeBox = new GridPane();

    recipeBox.addColumn(0);
    recipeBox.addColumn(1);
    recipeBox.addColumn(2);
    recipeBox.addColumn(3);
    recipeBox.setHgap(20);
    recipeBox.setVgap(20);

    ColumnConstraints col0 = new ColumnConstraints();
    col0.setPercentWidth(10);
    ColumnConstraints col1 = new ColumnConstraints();
    col1.setPercentWidth(40);
    ColumnConstraints col2 = new ColumnConstraints();
    col2.setPercentWidth(40);
    ColumnConstraints col3 = new ColumnConstraints();
    col3.setPercentWidth(10);
    recipeBox.getColumnConstraints().addAll(col0, col1, col2, col3);

    ScrollPane scrollPane = new ScrollPane();
    HBox.setHgrow(scrollPane, Priority.ALWAYS);
    HBox.setHgrow(recipeBox, Priority.ALWAYS);

    Recipe newRecipe = new Recipe();
    ObservableList<Ingredient> ingredients = FXCollections.observableArrayList(
        recipeToEdit != null ? recipeToEdit.getIngredients() : new ArrayList<Ingredient>());
    StringProperty recipeNameProperty = new SimpleStringProperty();
    StringProperty recipeDescriptionProperty = new SimpleStringProperty();
    VBox titleDescriptionIngredientsBox = createLeftPanel(newRecipe, user.getId(), ingredients, recipeToEdit,
        recipeNameProperty, recipeDescriptionProperty);

    ObservableList<ObservableStep> stepList = FXCollections.observableArrayList();

    if (recipeToEdit != null) {
      for (Step step : recipeToEdit.getSteps()) {
        ObservableStep newStep = new ObservableStep();
        newStep.setInstructions(step.getInstructions());
        newStep.setStepIndex(step.getStepIndex());
        stepList.add(newStep);
      }

      newRecipe.setTags(recipeToEdit.getTags());
    }

    VBox stepBox = createRightPanel(newRecipe, stepList);

    GridPane.setConstraints(titleDescriptionIngredientsBox, 1, 0);
    GridPane.setConstraints(stepBox, 2, 0);

    recipeBox.getChildren().addAll(new Pane(), titleDescriptionIngredientsBox, stepBox, new Pane());

    scrollPane.setContent(recipeBox);
    scrollPane.setFitToWidth(true);
    scrollPane.setPadding(new Insets(10, 0, 10, 0));

    Button addRecipeButton = new Button(recipeToEdit == null ? "Create Recipe" : "Update Recipe");
    HBox addRecipeButtonContainer = new HBox();
    addRecipeButtonContainer.getChildren().add(addRecipeButton);
    addRecipeButtonContainer.setAlignment(Pos.CENTER_RIGHT);

    addRecipeButton.setOnAction(event -> {
      ArrayList<Step> stepsForModel = new ArrayList<Step>();
      for (ObservableStep observableStep : stepList) {
        stepsForModel.add(observableStep);
      }
      try {
        newRecipe.setName(recipeNameProperty.getValue());
        newRecipe.setDescription(recipeDescriptionProperty.getValue());
        newRecipe.setSteps(stepsForModel);
        newRecipe.setIngredients(ingredients);
        newRecipe.setOwner(mainViewPane.getUser().getId());
        if (recipeToEdit != null) {
          newRecipe.setId(recipeToEdit.getId());

          newRecipe.setIsFavourite(recipeToEdit.isFavourite());
          recipeDaoImpl.updateRecipe(newRecipe);
          mainViewPane.toast(ToastType.SUCCESS, MessageFeedback.Success.Recipe.UPDATE.getMessage());
          mainViewPane.updateContent(new RecipeViewPane(newRecipe, mainViewPane));
        } else {
          recipeDaoImpl.createRecipe(newRecipe, user.getId());
          mainViewPane.toast(ToastType.SUCCESS, MessageFeedback.Success.Recipe.ADD.getMessage());
          mainViewPane.updateContent(new RecipeViewPane(newRecipe, mainViewPane));
        }
      } catch (Exception e) {
        mainViewPane.toast(ToastType.ERROR, e.getMessage());
      }
    });

    GridPane buttonContainer = new GridPane();
    buttonContainer.getStyleClass().add("button-container");

    buttonContainer.addColumn(0);
    buttonContainer.addColumn(1);
    buttonContainer.addColumn(2);
    buttonContainer.addColumn(3);

    GridPane.setConstraints(addRecipeButtonContainer, 2, 0);

    buttonContainer.getColumnConstraints().addAll(col0, col1, col2, col3);

    buttonContainer.getChildren().addAll(new Pane(), addRecipeButtonContainer,
        new Pane());

    VBox content = new VBox();
    content.getChildren().addAll(buttonContainer, scrollPane);
    return content;
  }

  private VBox createLeftPanel(Recipe newRecipe, int userid, ObservableList<Ingredient> ingredients,
      Recipe recipeToEdit, StringProperty recipeNameProperty, StringProperty recipeDescriptionProperty) {
    VBox leftPanel = new VBox();
    HBox.setHgrow(leftPanel, Priority.ALWAYS);
    leftPanel.setSpacing(10);

    // Add Textfield to enter the title
    Label title = new Label("Title: ");
    title.getStyleClass().add("title");
    TextField titleTextField = new TextField();
    recipeNameProperty.bind(titleTextField.textProperty());

    TextArea descriptionEditor = new TextArea();
    descriptionEditor.setWrapText(true);
    recipeDescriptionProperty.bind(descriptionEditor.textProperty());

    if (recipeToEdit != null) {
      titleTextField.setText(recipeToEdit.getName());
      descriptionEditor.setText(recipeToEdit.getDescription());
    }

    VBox titleDescriptionBox = new VBox();
    // Add tags

    // Add description
    Label description = new Label("Description:");
    HBox tagsContainer = createTagsContainer(newRecipe, recipeToEdit);
    titleDescriptionBox.getChildren().addAll(title, titleTextField, tagsContainer, description,
        descriptionEditor);

    Label portionsLabel = new Label("Portions: ");
    ChoiceBox<Integer> portions = new ChoiceBox<>();

    portions.setOnAction(e -> newRecipe.setPortions(portions.getValue()));

    HBox portionsBox = new HBox();
    portionsBox.getChildren().addAll(portionsLabel, portions);
    portionsBox.setSpacing(10);
    portionsBox.setAlignment(Pos.CENTER);
    HBox portionsBoxContainer = new HBox(portionsBox);
    portionsBoxContainer.setAlignment(Pos.CENTER_LEFT);

    portions.setValue(recipeToEdit == null ? 4 : recipeToEdit.getPortions());
    portions.getItems().addAll(1, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20);

    titleDescriptionBox.getChildren().addAll(portionsBoxContainer);
    titleDescriptionBox.getStyleClass().add("title-description-box");
    titleDescriptionBox.setPadding(new Insets(10, 10, 10, 10));
    titleDescriptionBox.setSpacing(10);

    IngredientDao ingredientDao = new IngredientDaoImpl();

    ObservableList<Ingredient> predefinedIngredients = FXCollections
        .observableArrayList(ingredientDao.getAllIngredients(userid));
    ComboBox<Ingredient> ingredientComboBox = new SearchableComboBox<Ingredient>(userid, predefinedIngredients,
        Ingredient::getName);

    // TextField ingredientTextField = new TextField();
    Label quantityLabel = new Label("Quantity:");
    TextField quantityTextField = new TextField();
    Label unitLabel = new Label("Unit:");

    ObservableList<Unit> predefinedUnits = FXCollections.observableArrayList(ingredientDao.getAllUnits());

    ComboBox<Unit> unitComboBox = new SearchableComboBox<Unit>(userid, predefinedUnits, Unit::getName);

    VBox fieldsAndLabelsBox = new VBox();
    Label ingredientLabel = new Label("Ingredient:");
    fieldsAndLabelsBox.getChildren().addAll(ingredientLabel, ingredientComboBox, quantityLabel,
        quantityTextField, unitLabel, unitComboBox);

    HBox addIngredientInnerHorizontalBox = new HBox();
    final Pane topSpacer = new Pane();
    VBox.setVgrow(topSpacer, Priority.SOMETIMES);
    Button plus = new Button("+");
    VBox plusBox = new VBox(topSpacer, plus);
    plusBox.setPadding(new Insets(0, 0, 0, 10));

    addIngredientInnerHorizontalBox.setPadding(new Insets(10, 10, 10, 10));
    addIngredientInnerHorizontalBox.getChildren().addAll(fieldsAndLabelsBox, plusBox);

    Label errorLabel = new Label();
    VBox addIngredientBox = new VBox();

    addIngredientBox.getChildren().addAll(addIngredientInnerHorizontalBox, errorLabel);
    addIngredientBox.getStyleClass().add("ingredient-box");

    DeleteableIngredientTableView ingredientTable = new DeleteableIngredientTableView(ingredients);

    plus.setOnAction(event -> {
      if (ingredientComboBox.getValue() == null) {
        mainViewPane.toast(ToastType.INFO, MessageFeedback.Info.Ingredient.ADD_FAIL.getMessage());
        return;
      }

      try {
        Ingredient newIngredient = new Ingredient();
        newIngredient.setId(ingredientComboBox.getValue().getId());
        newIngredient.setName(ingredientComboBox.getValue().getName());
        newIngredient.setQty(quantityTextField.getText().isBlank() ? "0" : quantityTextField.getText());
        if (unitComboBox.getValue() == null || unitComboBox.getEditor().getText().isBlank()) {
          FilteredList<Unit> defaultUnit = unitComboBox.getItems().filtered(u -> u.getName().isBlank());
          newIngredient.setUnit(defaultUnit.getFirst());
        } else {
          newIngredient.setUnit(unitComboBox.getValue());
        }

        ingredients.add(newIngredient);
        ingredientComboBox.getSelectionModel().clearSelection();
        quantityTextField.setText("");
        unitComboBox.getSelectionModel().clearSelection();
      } catch (Exception e) {
        mainViewPane.toast(ToastType.ERROR, e.getMessage());
      }
    });

    leftPanel.getChildren().addAll(titleDescriptionBox, addIngredientBox, ingredientTable);

    return leftPanel;

  }

  private VBox createRightPanel(Recipe newRecipe, ObservableList<ObservableStep> stepList) {
    Label addStepDescriptionLabel = new Label("Step Description:");
    TextArea stepDescriptionTextArea = new TextArea();
    stepDescriptionTextArea.setWrapText(true);
    VBox stepDescriptionVerticalBox = new VBox(addStepDescriptionLabel, stepDescriptionTextArea);

    HBox addStepBox = new HBox();
    addStepBox.setPadding(new Insets(10, 10, 10, 10));
    addStepBox.getChildren().add(stepDescriptionVerticalBox);
    addStepBox.getStyleClass().add("add-step-box");

    final Pane topSpacer = new Pane();
    VBox.setVgrow(topSpacer, Priority.SOMETIMES);
    Button plus = new Button("+");
    VBox plusBox = new VBox(topSpacer, plus);
    plusBox.setPadding(new Insets(0, 0, 0, 10));

    addStepBox.getChildren().add(plusBox);
    HBox.setHgrow(stepDescriptionVerticalBox, Priority.ALWAYS);

    VBox rightPanel = new VBox();
    rightPanel.setSpacing(10);

    rightPanel.getChildren().add(addStepBox);

    StepTableView stepTable = new StepTableView(stepList);

    plus.setOnAction(event -> {

      try {
        ObservableStep newStep = new ObservableStep();
        newStep.setInstructions(stepDescriptionTextArea.getText());

        stepList.add(newStep);
        stepDescriptionTextArea.clear();
      } catch (Exception e) {
        mainViewPane.toast(ToastType.INFO, e.getMessage());
      }
    });

    rightPanel.getChildren().add(stepTable);

    return rightPanel;
  }

  private HBox createTagsContainer(Recipe recipe, Recipe recipeToEdit) {
    HBox tagsContainer = new HBox();
    FlowPane recipesTagsBox = createTagsBox(recipeToEdit == null ? recipe : recipeToEdit);
    HBox.setHgrow(recipesTagsBox, Priority.ALWAYS);
    recipesTagsBox.setPrefWidth(USE_PREF_SIZE);
    tagsContainer.getChildren().add(recipesTagsBox);

    HBox addTagComboboxContainer = new HBox();
    tagsContainer.getChildren().add(addTagComboboxContainer);
    addTagComboboxContainer.setMaxHeight(USE_PREF_SIZE);
    addTagComboboxContainer.setSpacing(5);

    ObservableList<Tag> allTags = FXCollections.observableArrayList(recipeDaoImpl.getBaseTags());
    ComboBox<Tag> tagsCombobox = new SearchableComboBox<>(mainViewPane.getUser().getId(), allTags, Tag::getName);
    tagsCombobox.setMinWidth(100);
    tagsCombobox.setMaxWidth(175);
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
        recipe.getTags().add(selectedTag);

        TagBox tagBox = new DeleteableTagBox(recipeToEdit, selectedTag);
        recipesTagsBox.getChildren().add(tagBox);
        tagBox.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            if (event.getEventType().equals(ActionEvent.ACTION)) {
              recipesTagsBox.getChildren().remove(tagBox);
              recipe.getTags().remove(selectedTag);
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

    List<Tag> baseTags = recipeDaoImpl.getBaseTags();
    ArrayList<Integer> baseTagIds = new ArrayList<Integer>();

    for (Tag tag : baseTags) {
      baseTagIds.add(tag.getId());
    }

    for (Tag tag : recipe.getTags()) {
      if (baseTagIds.contains(tag.getId())) {
        TagBox tagBox = new DeleteableTagBox(recipe, tag);
        tagsBox.getChildren().add(tagBox);
        tagBox.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            if (event.getEventType().equals(ActionEvent.ACTION)) {
              tagsBox.getChildren().remove(tagBox);
              recipe.getTags().remove(tag);
            }
          }
        });
      }
    }

    return tagsBox;
  }
}
