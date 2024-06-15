package cookbook.viewer;

import cookbook.controller.MessageFeedback;
import cookbook.controller.TagDaoImpl;
import cookbook.model.Tag;
import java.util.List;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;

/**
 * A table for users to manage all their tags.
 */
public class TagManagementTableView extends TableView<Tag> {
  private TagDaoImpl tagDao;
  private MainViewInterface mainViewPane;

  /**
   * Creates the table.
   */
  public TagManagementTableView(List<Tag> tags, MainViewInterface mainViewPane) {
    this.mainViewPane = mainViewPane;
    tagDao = new TagDaoImpl();

    this.setEditable(true);
    this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_SUBSEQUENT_COLUMNS);
    this.setItems(FXCollections.observableArrayList(tags));

    TableColumn<Tag, Tag> tagPreviewColumn = new TableColumn<>("Tag preview");
    tagPreviewColumn.getStyleClass().add("align-left");
    tagPreviewColumn.setCellValueFactory(new Callback<CellDataFeatures<Tag, Tag>, ObservableValue<Tag>>() {
      public ObservableValue<Tag> call(CellDataFeatures<Tag, Tag> t) {
        // p.getValue() returns the Person instance for a particular TableView row
        return new SimpleObjectProperty<>(t.getValue());
      }
    });
    tagPreviewColumn.setCellFactory(col -> new TagPreviewCell());

    TableColumn<Tag, String> nameColumn = new TableColumn<>("Name");
    nameColumn.getStyleClass().add("align-left");
    nameColumn.setCellValueFactory(new Callback<CellDataFeatures<Tag, String>, ObservableValue<String>>() {
      public ObservableValue<String> call(CellDataFeatures<Tag, String> t) {
        // p.getValue() returns the Person instance for a particular TableView row
        return new SimpleStringProperty(t.getValue().getName());
      }
    });
    nameColumn.setCellFactory(col -> new EditableCell<>("Tag name", "Enter tag name"));
    nameColumn.setEditable(true);
    nameColumn.setOnEditCommit(
        event -> {
          try {
            event.getRowValue().setName(event.getNewValue());
          } catch (Exception e) {
            mainViewPane.toast(ToastType.ERROR, e.getMessage());
          }
          this.refresh();
        });

    TableColumn<Tag, String> backgroundColorColumn = new TableColumn<>("Background color");
    backgroundColorColumn.getStyleClass().add("align-left");
    backgroundColorColumn.setCellValueFactory(new Callback<CellDataFeatures<Tag, String>, ObservableValue<String>>() {
      public ObservableValue<String> call(CellDataFeatures<Tag, String> t) {
        return new SimpleStringProperty(t.getValue().getBackgroundColor());
      }
    });
    backgroundColorColumn.setCellFactory(col -> new ColorPickingCell<>());
    backgroundColorColumn.setEditable(true);
    backgroundColorColumn.setOnEditCommit(
        event -> {
          event.getRowValue().setBackgroundColor(event.getNewValue());
          this.refresh();
        });

    TableColumn<Tag, String> textColorColumn = new TableColumn<>("Text color");
    textColorColumn.getStyleClass().add("align-left");
    textColorColumn.setCellFactory(col -> new ColorPickingCell<>());
    textColorColumn.setCellValueFactory(new Callback<CellDataFeatures<Tag, String>, ObservableValue<String>>() {
      public ObservableValue<String> call(CellDataFeatures<Tag, String> t) {
        return new SimpleStringProperty(t.getValue().getTextColor());
      }
    });
    textColorColumn.setEditable(true);
    textColorColumn.setOnEditCommit(
        event -> {
          event.getRowValue().setTextColor(event.getNewValue());
          this.refresh();
        });

    TableColumn<Tag, Button> saveColumn = new TableColumn<>("Save");
    saveColumn.setCellFactory(ButtonCell.<Tag>forTableColumn("save", (Tag t) -> {
      t = updateTag(t);
      this.refresh();
      return t;
    }));
    saveColumn.prefWidthProperty().bind(this.widthProperty().divide(12));
    saveColumn.setResizable(false);
    saveColumn.setReorderable(false);
    saveColumn.setSortable(false);

    TableColumn<Tag, Button> deleteColumn = new TableColumn<>("Delete");
    deleteColumn.setCellFactory(ButtonCell.<Tag>forTableColumn("delete", (Tag t) -> {
      if (alertConfirmation(t) && deleteTag(t)) {
        this.getItems().remove(t);
        this.refresh();
      }

      return t;
    }));
    deleteColumn.prefWidthProperty().bind(this.widthProperty().divide(12));
    deleteColumn.setResizable(false);
    deleteColumn.setReorderable(false);
    deleteColumn.setSortable(false);

    this.getColumns().add(tagPreviewColumn);
    this.getColumns().add(nameColumn);
    this.getColumns().add(backgroundColorColumn);
    this.getColumns().add(textColorColumn);
    this.getColumns().add(saveColumn);
    this.getColumns().add(deleteColumn);
    this.getColumns().forEach(column -> column.setMinWidth(50));
  }

  private boolean alertConfirmation(Tag tag) {
    AlertPopup alert = new AlertPopup(AlertType.CONFIRMATION, mainViewPane);
    alert.setTitle("Confirmation Dialog");
    alert.setHeaderMessage("Permanently delete tag: " + tag.getName() + "? You can't undo this.");
    alert.setContentMessage("Are you sure?");

    ButtonType cancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
    ButtonType confirm = new ButtonType("Delete tag", ButtonData.OK_DONE);
    alert.getButtonTypes().setAll(cancel, confirm);

    if (alert.showAndWait().get() == confirm) {
      return true;
    }

    return false;
  }

  /**
   * Deletes tag.
   *
   * @param tag to be deleted
   */
  private boolean deleteTag(Tag tag) {
    try {
      tagDao.deleteTag(tag.getId());
      mainViewPane.toast(ToastType.SUCCESS, MessageFeedback.Success.Tag.DELETE.getMessage());
      return true;
    } catch (Exception e) {
      mainViewPane.toast(ToastType.ERROR, e.getMessage());
      return false;
    }
  }

  /**
   * Modifies tag.
   *
   * @param tag to be modified
   */
  private Tag updateTag(Tag tag) {
    if (tag.getId() > 0) {
      try {
        tagDao.updateTag(tag);
        mainViewPane.toast(ToastType.SUCCESS, MessageFeedback.Success.Tag.UPDATE.getMessage());
      } catch (Exception e) {
        mainViewPane.toast(ToastType.ERROR, e.getMessage());
      }
    } else {
      try {
        tag = tagDao.createTag(tag);
        mainViewPane.toast(ToastType.SUCCESS, MessageFeedback.Success.Tag.ADD.getMessage());
      } catch (Exception e) {
        mainViewPane.toast(ToastType.ERROR, e.getMessage());
      }
    }
    return tag;
  }
}
