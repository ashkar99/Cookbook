package cookbook.viewer;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

/**
 * HBox which contains searchbar and update button.
 */
public class SearchComponent extends HBox {
  /**
   * Creates the search component of the page.
   * Contains searchbar and update button.
   *
   * @param recipesScrollPane buttons call display recipes in scroll pane
   */
  public SearchComponent(RecipesScrollPane recipesScrollPane, MainViewInterface mainViewPane) {
    TextField input = new TextField();

    HBox favouriteBox = new HBox();
    favouriteBox.getStyleClass().add("favourite-box");
    Region starIcon = new Region();
    starIcon.getStyleClass().add("star-icon");
    ToggleButton starButton = new ToggleButton();
    starButton.setOnAction(event -> {
      updateFavouriteButton(starButton, starIcon);
      recipesScrollPane.displayRecipes(starButton.isSelected());
      input.setText("");
    });
    starButton.setGraphic(starIcon);
    favouriteBox.getChildren().add(starButton);

    AdvancedSearchComponent advancedSearchComponent = new AdvancedSearchComponent();
    Button searchButton = new Button("Search");
    input.setOnAction(
        event -> {
          recipesScrollPane.searchRecipes(advancedSearchComponent.getSelectedSearchOptions(), input.getText());
          starButton.setSelected(false);
          updateFavouriteButton(starButton, starIcon);
        });
    searchButton.setOnAction(
        event -> {
          recipesScrollPane.searchRecipes(advancedSearchComponent.getSelectedSearchOptions(), input.getText());
          starButton.setSelected(false);
          updateFavouriteButton(starButton, starIcon);
        });

    Button updateButton = new Button();
    Region updateIcon = new Region();
    updateIcon.getStyleClass().add("update");
    updateButton.setGraphic(updateIcon);
    updateButton.setOnAction(event -> {
      recipesScrollPane.displayRecipes();
      input.setText("");
      starButton.setSelected(false);
      updateFavouriteButton(starButton, starIcon);
    });

    Button createRecipeButton = new Button("Create recipe");
    createRecipeButton
        .setOnAction(e -> mainViewPane.updateContent(new CreateRecipeView(mainViewPane, mainViewPane.getUser(), null)));

    HBox searchBarBox = new HBox();
    searchBarBox.getStyleClass().add("searchBarBox");
    searchBarBox.setPadding(new Insets(10, 10, 10, 10));
    searchBarBox.setSpacing(5);
    searchBarBox.getChildren().addAll(advancedSearchComponent, input, searchButton, createRecipeButton, updateButton,
        favouriteBox);
    favouriteBox.setAlignment(Pos.TOP_CENTER);

    final Pane leftSpacer = new Pane();
    HBox.setHgrow(leftSpacer, Priority.SOMETIMES);
    final Pane rightSpacer = new Pane();
    HBox.setHgrow(rightSpacer, Priority.SOMETIMES);

    this.getChildren().addAll(leftSpacer, searchBarBox, rightSpacer);
    this.setPadding(new Insets(10, 10, 10, 10));
  }

  private void updateFavouriteButton(ToggleButton starButton, Region starIcon) {
    starIcon.getStyleClass().remove(!starButton.isSelected() ? "star-filled-icon" : "star-icon");
    starIcon.getStyleClass().add(starButton.isSelected() ? "star-filled-icon" : "star-icon");
  }
}
