package cookbook;

import cookbook.viewer.LoginViewPane;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Application.
 */
public class App extends Application {
  private static final double width = 400;
  private static final double height = 400;
  private static final String style = "/main.css";

  @Override
  public void start(Stage loginStage) throws Exception {
    Parent firstView = new LoginViewPane(loginStage, style);
    Scene loginScene = new Scene(firstView, width, height);
    loginScene.getStylesheets().add(getClass().getResource(style).toExternalForm());
    loginStage.setScene(loginScene);
    loginStage.setMinHeight(height);
    loginStage.setMinWidth(width);
    loginStage.getIcons().add(new Image(getClass().getResourceAsStream("/SWcookBookLogoSmall.png")));
    loginStage.setTitle("CRUD Recipes");
    loginStage.show();
  }
}
