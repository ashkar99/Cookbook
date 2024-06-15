package cookbook.viewer;

import cookbook.model.User;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * the main page of the application. Should maybe get a better name.
 */
public class MainViewPane extends BorderPane implements MainViewInterface {

  private User user;
  private Stage stage;
  private AnchorPane layout;
  private VBox toastContainer;

  /**
   * Creates the main view pane for the application.
   * With toolbar. and gridview for recipes.
   */
  @SuppressFBWarnings({ "EI_EXPOSE_REP", "EI_EXPOSE_REP2", "MS_EXPOSE_REP" })
  public MainViewPane(User user, Stage stage) {
    this.user = user;
    this.stage = stage;
    this.getStyleClass().add("main-view-pane");
    this.stage.getIcons().add(new Image(getClass().getResourceAsStream("/SWcookBookLogoSmall.png")));
    this.stage.setTitle("CRUD Recipes");

    CookBookToolBar toolBar = new CookBookToolBar(this);
    this.setTop(toolBar);

    Pane pane = (user.isAdmin()) ? new AdminViewPane(this) : new DisplayRecipePane(this);
    toastContainer = new VBox();
    updateContent(pane);
  }

  @Override
  public void updateContent(Parent pane) {
    layout = new AnchorPane();
    layout.getChildren().add(pane);
    AnchorPane.setLeftAnchor(pane, 0.0);
    AnchorPane.setRightAnchor(pane, 0.0);
    AnchorPane.setBottomAnchor(pane, 0.0);
    AnchorPane.setTopAnchor(pane, 0.0);

    toastContainer.setSpacing(20);
    layout.getChildren().add(toastContainer);

    AnchorPane.setBottomAnchor(toastContainer, 8.0);
    AnchorPane.setLeftAnchor(toastContainer, 5.0);

    this.setCenter(layout);
  }

  @Override
  public void toast(ToastType type, String message) {
    Toast toast = new Toast(type, message);
    toastContainer.getChildren().addFirst(toast);

    toast.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        if (event.getEventType().equals(ActionEvent.ACTION)) {
          toastContainer.getChildren().remove(toast);
        }
      }
    });
  }

  @Override
  public void displayAlert(AlertInterface alert) {
    alert.setOwner(stage);
  }

  @Override
  public User getUser() {
    User copy = new User();
    copy.setUsername(user.getUsername());
    copy.setId(user.getId());
    copy.setDisplayname(user.getDisplayname());
    copy.setIsAdmin(user.isAdmin());
    copy.setFirstname(user.getFirstname());
    copy.setLastname(user.getLastname());
    return copy;
  }
}
