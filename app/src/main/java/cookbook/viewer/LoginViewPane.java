package cookbook.viewer;

import cookbook.controller.UserDao;
import cookbook.controller.UserDaoImpl;
import cookbook.model.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * The view that opens on start-up, when logging in.
 */
public class LoginViewPane extends HBox {

  private static final double mainStageWidth = Screen.getPrimary().getBounds().getWidth() / 1.5;
  private static final double mainStageHeight = Screen.getPrimary().getBounds().getHeight() / 1.5;
  private String style;

  /**
   * Creates the login view seen when starting the application.
   *
   * @param loginStage the stage seen on start-up, needs to be closed on
   *                   successful login
   * @param style      css, applied to
   */
  public LoginViewPane(Stage loginStage, String style) {
    loginStage.setResizable(false);

    this.style = style;
    this.getStylesheets().add(getClass().getResource(style).toExternalForm());
    loginStage.getIcons().add(new Image(getClass().getResourceAsStream("/SWcookBookLogoSmall.png")));
    loginStage.setTitle("CRUD Recipes");
    loginStage.setHeight(650);

    this.getStyleClass().add("login-view");

    VBox logoBox = new VBox();
    ImageView logoView = new ImageView();
    logoBox.setSpacing(10);
    Label titleLabel = new Label("CRUD Recipes");
    titleLabel.getStyleClass().add("title");
    Image logo = new Image(getClass().getResourceAsStream("/SWcookBookLogoSmall.png"));
    logoView.setImage(logo);
    logoView.setFitWidth(150);
    logoView.setPreserveRatio(true);
    logoView.setSmooth(true);
    logoBox.getChildren().addAll(titleLabel, logoView);
    logoBox.setAlignment(Pos.CENTER);

    int fieldWidth = 200;
    int fieldHeight = 30;
    VBox usernameBox = new VBox();
    TextField usernameField = new TextField();
    usernameField.setPrefSize(fieldWidth, fieldHeight);
    usernameBox.setSpacing(10);
    Label usernameLabel = new Label("Username: ");
    usernameBox.getChildren().addAll(usernameLabel, usernameField);

    VBox passwordBox = new VBox();
    PasswordField passwordField = new PasswordField();
    passwordField.setPrefSize(fieldWidth, fieldHeight);
    passwordBox.setSpacing(10);
    Label passwordLabel = new Label("Password: ");
    passwordBox.getChildren().addAll(passwordLabel, passwordField);

    VBox buttonLabelBox = new VBox();
    Button loginButton = new Button("Login");
    Label msg = new Label("");
    buttonLabelBox.setSpacing(10);
    buttonLabelBox.setAlignment(Pos.CENTER);
    buttonLabelBox.getChildren().addAll(loginButton, msg);

    passwordField.setOnAction(e -> tryLogin(loginStage, usernameField.getText(), passwordField.getText(), msg));
    usernameField.setOnAction(e -> tryLogin(loginStage, usernameField.getText(), passwordField.getText(), msg));
    loginButton.setOnAction(e -> tryLogin(loginStage, usernameField.getText(), passwordField.getText(), msg));

    VBox loginBox = new VBox();
    loginBox.getStyleClass().add("login-box");
    loginBox.setSpacing(30);
    loginBox.setPadding(new Insets(20, 20, 20, 20));
    loginBox.setAlignment(Pos.CENTER);
    loginBox.getChildren().addAll(logoBox, usernameBox, passwordBox, buttonLabelBox);

    VBox loginBoxContainer = new VBox();
    loginBoxContainer.getChildren().add(loginBox);
    loginBoxContainer.setAlignment(Pos.CENTER);

    final Pane leftSpacer = new Pane();
    HBox.setHgrow(leftSpacer, Priority.ALWAYS);
    final Pane rightSpacer = new Pane();
    HBox.setHgrow(rightSpacer, Priority.ALWAYS);

    this.getChildren().addAll(leftSpacer, loginBoxContainer, rightSpacer);
  }

  /**
   * Attempts to login using the username and password entered in the fields.
   *
   * @param loginStage if login is successful, this stage needs to be closed
   */
  private void tryLogin(Stage loginStage, String username, String password, Label msg) {
    try {
      // Send username and password to be checked
      UserDao userDao = new UserDaoImpl();
      User user = userDao.authenticateUser(username, password);

      // If user object was returned, switch to main view
      Stage primaryStage = new Stage();
      Parent mainView = new MainViewPane(user, primaryStage);
      Scene mainScene = new Scene(mainView, mainStageWidth, mainStageHeight);
      mainScene.getStylesheets().add(getClass().getResource(style).toExternalForm());
      loginStage.hide();
      primaryStage.setScene(mainScene);
      primaryStage.setMinWidth(400);
      primaryStage.setMinHeight(400);
      primaryStage.show();
    } catch (Exception e) {
      msg.setText(e.getMessage());
    }
  }
}
