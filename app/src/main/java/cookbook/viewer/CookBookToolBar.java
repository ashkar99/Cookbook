package cookbook.viewer;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

/**
 * Custom Toolbar for the cookbook Application.
 */
public class CookBookToolBar extends ToolBar {
  private MainViewInterface mainViewPane;

  /**
   * Toolbar Constructor. Will need rootswitcher etc. in the future.
   */
  public CookBookToolBar(MainViewInterface mainViewPane) {
    super();
    this.mainViewPane = mainViewPane;

    final Pane leftSpacer = new Pane();
    HBox.setHgrow(leftSpacer, Priority.SOMETIMES);

    this.setPrefHeight(64);
    this.getStyleClass().add("toolbar");

    MenuButton home = new MenuButton();
    home.getStyleClass().add("home");
    home.setOnMouseClicked(event -> {
      mainViewPane.updateContent(mainViewPane.getUser().isAdmin() ? new AdminViewPane(mainViewPane)
          : new DisplayRecipePane(mainViewPane));
    });

    MenuButton hamburger = new MenuButton();
    hamburger.getStyleClass().add("hamburger");
    hamburger.getItems().addAll(getTabs());

    MenuItem logout = new MenuItem("Logout");
    logout.setOnAction(e -> {
      this.getScene().getWindow().hide();
      String style = "/main.css";
      int width = 400;
      int height = 400;
      Stage loginStage = new Stage();
      Parent firstView = new LoginViewPane(loginStage, style);
      Scene loginScene = new Scene(firstView, width, height);
      loginStage.setScene(loginScene);
      loginStage.setMinHeight(height);
      loginStage.setMinWidth(width);
      loginStage.show();
    });
    hamburger.getItems().add(logout);

    Label userDisplayname = new Label(mainViewPane.getUser().getDisplayname());

    HBox buttonsBox = new HBox();
    buttonsBox.getChildren().addAll(userDisplayname, home, hamburger);
    buttonsBox.getStyleClass().add("buttonsBox");
    buttonsBox.setPadding(new Insets(10, 15, 10, 15));
    buttonsBox.setSpacing(10);
    buttonsBox.setAlignment(Pos.CENTER_LEFT);
    buttonsBox.setMaxHeight(USE_PREF_SIZE);

    this.setOrientation(Orientation.HORIZONTAL);
    this.getItems().addAll(leftSpacer, buttonsBox);
    this.setPadding(new Insets(0, 10, 0, 10));
  }

  private List<MenuItem> getTabs() {
    List<MenuItem> menuItems = new ArrayList<>();

    if (!mainViewPane.getUser().isAdmin()) {
      MenuItem customTags = new MenuItem("Manage your tags");
      customTags.setOnAction(event -> mainViewPane.updateContent(new ManageTagView(mainViewPane)));
      menuItems.add(customTags);
      MenuItem customIngredients = new MenuItem("Manage your ingredients");
      customIngredients.setOnAction(event -> mainViewPane.updateContent(new ManageIngredientView(mainViewPane)));
      menuItems.add(customIngredients);
    }

    return menuItems;
  }
}
