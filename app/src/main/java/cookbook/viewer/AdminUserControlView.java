package cookbook.viewer;

import cookbook.controller.MessageFeedback;
import cookbook.controller.UserDao;
import cookbook.controller.UserDaoImpl;
import cookbook.model.User;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * view for the admin, when adding a user to the User database table.
 */
public class AdminUserControlView extends HBox {

  private MainViewInterface mainViewPane;
  static final int textFieldWidth = 200;
  static final int textFieldHeight = 30;

  /**
   * Creates a admin user view.
   */
  public AdminUserControlView(MainViewInterface mainViewPane) {
    this.mainViewPane = mainViewPane;
    GridPane fieldGrid = createLayout();
    addUserFields(fieldGrid, null);
  }

  /**
   * Creates an admin user view to modify existing user.
   *
   * @param user to be modified
   */
  public AdminUserControlView(User user, MainViewInterface mainViewPane) {
    this.mainViewPane = mainViewPane;
    GridPane fieldGrid = createLayout();
    addUserFields(fieldGrid, user);
  }

  private GridPane createLayout() {
    final Pane leftSpacer = new Pane();
    HBox.setHgrow(leftSpacer, Priority.ALWAYS);
    final Pane rightSpacer = new Pane();
    HBox.setHgrow(rightSpacer, Priority.ALWAYS);

    leftSpacer.setMinWidth(20);
    rightSpacer.setMinWidth(20);

    GridPane fieldGrid = new GridPane();

    fieldGrid.addColumn(0);
    fieldGrid.addColumn(1);
    fieldGrid.setVgap(20);
    fieldGrid.setHgap(20);

    VBox fieldGridContainer = new VBox();
    fieldGridContainer.setAlignment(Pos.CENTER);
    fieldGridContainer.getChildren().add(fieldGrid);

    fieldGrid.setPadding(new Insets(10, 10, 10, 10));

    this.getStyleClass().add("admin-user-control-view");
    fieldGrid.setCenterShape(true);
    fieldGrid.getStyleClass().add("field-grid");

    ColumnConstraints col1 = new ColumnConstraints();
    col1.setPercentWidth(35);
    col1.setHalignment(HPos.RIGHT);
    ColumnConstraints col2 = new ColumnConstraints();
    col2.setPercentWidth(65);
    fieldGrid.getColumnConstraints().addAll(col1, col2);

    HBox.setHgrow(this, Priority.ALWAYS);
    HBox.setHgrow(fieldGrid, Priority.ALWAYS);

    this.getChildren().addAll(leftSpacer, fieldGridContainer, rightSpacer);
    return fieldGrid;
  }

  private void addUserFields(GridPane gridPane, User user) {
    TextField userNameTextField = new TextField();
    Label userNameLabel = new Label("Username:");
    userNameTextField.setPrefSize(textFieldWidth, textFieldHeight);

    gridPane.add(userNameLabel, 0, 0);
    gridPane.add(userNameTextField, 1, 0);

    TextField firstNameTextField = new TextField();
    Label firstNameLabel = new Label("First Name:");
    firstNameTextField.setPrefSize(textFieldWidth, textFieldHeight);

    gridPane.add(firstNameLabel, 0, 1);
    gridPane.add(firstNameTextField, 1, 1);

    TextField lastNameTextField = new TextField();
    Label lastNameLabel = new Label("Last Name:");
    lastNameTextField.setPrefSize(textFieldWidth, textFieldHeight);

    gridPane.add(lastNameLabel, 0, 2);
    gridPane.add(lastNameTextField, 1, 2);

    TextField displayNameTextField = new TextField();
    Label displayNameLabel = new Label("Display Name:");
    displayNameTextField.setPrefSize(textFieldWidth, textFieldHeight);

    gridPane.add(displayNameLabel, 0, 3);
    gridPane.add(displayNameTextField, 1, 3);

    PasswordField passwordTextField = new PasswordField();
    Label passwordLabel = new Label("Password:");
    passwordTextField.setPrefSize(textFieldWidth, textFieldHeight);

    gridPane.add(passwordLabel, 0, 4);
    gridPane.add(passwordTextField, 1, 4);

    Button btn = new Button();

    if (user != null) {
      // Update user
      btn.setText("Update User");
      userNameTextField.setText(user.getUsername());
      firstNameTextField.setText(user.getFirstname());
      lastNameTextField.setText(user.getLastname());
      displayNameTextField.setText(user.getDisplayname());
      btn.setOnAction(event -> {
        try {
          user.setUsername(userNameTextField.getText());
          user.setFirstname(firstNameTextField.getText());
          user.setLastname(lastNameTextField.getText());
          user.setDisplayname(displayNameTextField.getText());
          if (!passwordTextField.getText().isBlank()) {
            user.setPassword(passwordTextField.getText());
          }

          try {
            UserDao userDao = new UserDaoImpl();
            userDao.updateUser(user);
            mainViewPane.toast(ToastType.SUCCESS, MessageFeedback.Success.User.UPDATE.getMessage());
          } catch (Exception e) {
            mainViewPane.toast(ToastType.ERROR, e.getMessage());
          }
        } catch (Exception e) {
          mainViewPane.toast(ToastType.ERROR, e.getMessage());
        }
      });

    } else {
      // Add user
      btn.setText("Add User");
      btn.setOnAction(event -> {
        try {
          User newUser = new User();
          newUser.setUsername(userNameTextField.getText());
          newUser.setFirstname(firstNameTextField.getText());
          newUser.setLastname(lastNameTextField.getText());
          newUser.setDisplayname(displayNameTextField.getText());
          newUser.setPassword(passwordTextField.getText());

          try {
            UserDao userDao = new UserDaoImpl();
            userDao.createUser(newUser);
            mainViewPane.toast(ToastType.SUCCESS, MessageFeedback.Success.User.ADD.getMessage());

            displayNameTextField.setText("");
            firstNameTextField.setText("");
            lastNameTextField.setText("");
            userNameTextField.setText("");
            passwordTextField.setText("");
          } catch (Exception e) {
            mainViewPane.toast(ToastType.ERROR, e.getMessage());
          }
        } catch (Exception e) {
          mainViewPane.toast(ToastType.ERROR, e.getMessage());
        }
      });
    }
    gridPane.add(btn, 1, 5);
  }

}
