package cookbook.viewer;

import cookbook.controller.MessageFeedback;
import cookbook.controller.UserDao;
import cookbook.controller.UserDaoImpl;
import cookbook.model.User;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

/**
 * Admin page of the application.
 */
public class AdminViewPane extends VBox {
  private UserDao userDao;
  private MainViewInterface mainViewPane;

  /**
   * Creates the admin view pane for application.
   */
  public AdminViewPane(MainViewInterface mainViewPane) {
    this.mainViewPane = mainViewPane;
    this.getStyleClass().add("table-management");
    userDao = new UserDaoImpl();

    LoadingViewPane loadingViewPane = new LoadingViewPane();
    this.getChildren().add(loadingViewPane);
    loadingViewPane.minHeightProperty().bind(this.heightProperty());
    loadingViewPane.minWidthProperty().bind(this.widthProperty());

    Task<VBox> adminTask = new Task<VBox>() {
      @Override
      protected VBox call() {
        StackPane buttonAlignment = new StackPane();
        Button button = new Button("Add user");
        button.setOnMouseClicked(event -> {
          mainViewPane.updateContent(new AdminUserControlView(mainViewPane));
        });
        buttonAlignment.getChildren().add(button);
        StackPane.setAlignment(button, Pos.CENTER_RIGHT);

        TableView<User> tableView = createTableView(userDao.getAllUsers());
        AdminViewPane.setVgrow(tableView, Priority.ALWAYS);
        VBox content = new VBox(buttonAlignment, tableView);
        content.setSpacing(20);
        return content;
      }
    };

    adminTask.setOnSucceeded(event -> {
      this.getChildren().remove(loadingViewPane);
      this.getChildren().add(adminTask.getValue());
      this.setPadding(new Insets(20, 100, 100, 100));
      this.setSpacing(20);
    });

    Thread fetchThread = new Thread(adminTask);
    fetchThread.setDaemon(true); // Ensure the thread stops when the application exits
    fetchThread.start();
  }

  private TableView<User> createTableView(List<User> users) {
    TableView<User> tableView = new TableView<>();
    tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_SUBSEQUENT_COLUMNS);
    tableView.setItems(FXCollections.observableArrayList(users));

    TableColumn<User, String> nameColumn = new TableColumn<>("Name");
    nameColumn.getStyleClass().add("align-left");
    nameColumn.setCellValueFactory(new Callback<CellDataFeatures<User, String>, ObservableValue<String>>() {
      public ObservableValue<String> call(CellDataFeatures<User, String> p) {
        // p.getValue() returns the Person instance for a particular TableView row
        return new SimpleStringProperty(p.getValue().getFirstname() + " " + p.getValue().getLastname());
      }
    });

    TableColumn<User, String> displaynameColumn = new TableColumn<>("Display Name");
    displaynameColumn.getStyleClass().add("align-left");
    displaynameColumn.setCellValueFactory(new Callback<CellDataFeatures<User, String>, ObservableValue<String>>() {
      public ObservableValue<String> call(CellDataFeatures<User, String> p) {
        // p.getValue() returns the Person instance for a particular TableView row
        return new SimpleStringProperty(p.getValue().getDisplayname());
      }
    });

    TableColumn<User, String> usernameColumn = new TableColumn<>("Username");
    usernameColumn.getStyleClass().add("align-left");
    usernameColumn.setCellValueFactory(new Callback<CellDataFeatures<User, String>, ObservableValue<String>>() {
      public ObservableValue<String> call(CellDataFeatures<User, String> p) {
        // p.getValue() returns the Person instance for a particular TableView row
        return new SimpleStringProperty(p.getValue().getUsername());
      }
    });

    TableColumn<User, Button> updateColumn = new TableColumn<>("Update");
    updateColumn.setCellFactory(ButtonCell.<User>forTableColumn("update", (User p) -> {
      updateUser(p);
      tableView.refresh();
      return p;
    }));
    updateColumn.prefWidthProperty().bind(tableView.widthProperty().divide(12));

    updateColumn.setResizable(false);
    updateColumn.setReorderable(false);
    updateColumn.setSortable(false);

    TableColumn<User, Button> deleteColumn = new TableColumn<>("Delete");
    deleteColumn.setCellFactory(ButtonCell.<User>forTableColumn("delete", (User p) -> {
      if (alertConfirmation(p) && deleteUser(p)) {
        tableView.getItems().remove(p);
        tableView.refresh();
      }

      return p;
    }));
    deleteColumn.prefWidthProperty().bind(tableView.widthProperty().divide(12));
    deleteColumn.setResizable(false);
    deleteColumn.setReorderable(false);
    deleteColumn.setSortable(false);

    tableView.getColumns().add(nameColumn);
    tableView.getColumns().add(displaynameColumn);
    tableView.getColumns().add(usernameColumn);
    tableView.getColumns().add(updateColumn);
    tableView.getColumns().add(deleteColumn);
    tableView.getColumns().forEach(column -> column.setMinWidth(50));

    return tableView;
  }

  private boolean alertConfirmation(User user) {
    AlertPopup alert = new AlertPopup(AlertType.CONFIRMATION, mainViewPane);
    alert.setTitle("Confirmation Dialog");
    alert.setHeaderMessage("Permanently delete user: " + user.getUsername() + "? You can't undo this.");
    alert.setContentMessage("Are you sure?");

    ButtonType cancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
    ButtonType confirm = new ButtonType("Delete user", ButtonData.OK_DONE);
    alert.getButtonTypes().setAll(cancel, confirm);

    if (alert.showAndWait().get() == confirm) {
      return true;
    }

    return false;
  }

  /**
   * Deletes user.
   *
   * @param user the user to be deleted
   */
  private boolean deleteUser(User user) {
    try {
      userDao.removeUser(user.getId());
      mainViewPane.toast(ToastType.SUCCESS, MessageFeedback.Success.User.DELETE.getMessage());
      return true;
    } catch (Exception e) {
      mainViewPane.toast(ToastType.ERROR, e.getMessage());
      return false;
    }
  }

  /**
   * Modifies user.
   *
   * @param user the user to be modified
   */
  private void updateUser(User user) {
    try {
      mainViewPane.updateContent(new AdminUserControlView(user, mainViewPane));
    } catch (Exception e) {
      mainViewPane.toast(ToastType.ERROR, e.getMessage());
    }
  }
}
