package cookbook.viewer;

import cookbook.controller.CommentDao;
import cookbook.controller.CommentDaoImpl;
import cookbook.controller.MessageFeedback;
import cookbook.model.Comment;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.time.Instant;
import java.util.Comparator;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;

/**
 * Creates a list view for comments.
 */
@SuppressFBWarnings("EI_EXPOSE_REP")
public class CommentListView extends ListView<Comment> {
  private CommentDao commentDao;
  private MainViewInterface mainViewInterface;
  private ObservableList<Comment> originalList;

  /**
   * use this, sets the original list. And sort it after with sortCommentsByDate.
   *
   * @param originalList the unsorted list.
   */
  public void setOriginalList(ObservableList<Comment> originalList) {
    this.originalList = originalList;
  }

  /**
   * Constructor for CommentListView.
   *
   * @param list              list of comments.
   * @param mainViewInterface has getters for user id, to know which comments
   *                          belong to the user.
   */
  public CommentListView(ObservableList<Comment> list, MainViewInterface mainViewInterface) {
    this.mainViewInterface = mainViewInterface;

    this.commentDao = new CommentDaoImpl();

    this.originalList = list;
    sortCommentsByDate();

    this.setPlaceholder(new Label("Be the first to comment!"));
    this.setCellFactory(param -> new CommentListCell(this.widthProperty()));
    this.setWidth(USE_COMPUTED_SIZE);
    this.getStyleClass().add("comment-list");

  }

  private class CommentListCell extends ListCell<Comment> {

    private TextFlow textAndExpandFlow;
    private Hyperlink expandTextButton;

    public CommentListCell(ReadOnlyDoubleProperty listWidthProperty) {
      super();
      this.setWrapText(true);
      this.prefWidthProperty().bind(listWidthProperty.subtract(100));
      this.maxWidthProperty().bind(listWidthProperty.subtract(100));
    }

    @Override
    protected void updateItem(Comment item, boolean empty) {
      super.updateItem(item, empty);

      if (empty || item == null) {
        setGraphic(null);
      } else {
        String fullText = item.getText();
        int charactersToPrintWithoutButton = 100;

        ExpandableText expandableText = new ExpandableText(fullText, charactersToPrintWithoutButton, false);

        expandTextButton = new Hyperlink();

        expandTextButton.visibleProperty().bind(expandableText.getExpandableProperty());

        expandTextButton.textProperty().bind(new StringBinding() {
          {
            super.bind(expandableText.getExpandedProperty());
          }

          @Override
          protected String computeValue() {
            return expandableText.getExpandedProperty().get() ? "Show less." : "Show More.";
          }
        });

        expandTextButton.setOnAction(e -> {
          // if it is expanded ->
          if (expandableText.getExpandedProperty().getValue()) {
            expandableText.cutText();
          } else {
            // else we want to expand it.
            expandableText.expandText();
          }
        });

        textAndExpandFlow = new TextFlow(expandableText, expandTextButton);
        BorderPane contentBox = new BorderPane();

        contentBox.getStyleClass().add("comment");

        contentBox.setCenter(textAndExpandFlow);

        Label nameLabel = new Label(item.getDisplayName());
        Label timeLabel = new Label(item.getTimeSince());

        timeLabel.getStyleClass().add("time-label");
        HBox nameTimeHorizontalBox = new HBox(nameLabel, timeLabel);
        nameTimeHorizontalBox.setSpacing(20);
        contentBox.setTop(nameTimeHorizontalBox);

        contentBox.setPadding(new Insets(10, 10, 10, 10));

        Button updateButton = new Button();
        Region updateIcon = new Region();
        updateIcon.getStyleClass().addAll("icon", "update-icon");
        updateButton.getStyleClass().add("icon-button");
        updateButton.setGraphic(updateIcon);

        updateButton.setOnAction(event -> {
          TextArea editTextArea = new TextArea();
          editTextArea.setWrapText(true);
          editTextArea.setText(expandableText.getFullText());

          Button saveButton = new Button("Save.");
          Node previousCenter = contentBox.getCenter();
          Node previousRight = contentBox.getRight();
          contentBox.setCenter(editTextArea);
          saveButton.setOnAction(event2 -> {
            try {
              item.setText(editTextArea.getText());
              commentDao.updateComment(item);
              expandableText.setFullText(editTextArea.getText());
              item.setUpdatedDate(Instant.now().getEpochSecond());
              timeLabel.setText(item.getTimeSince());
              contentBox.setCenter(previousCenter); // change back to the previous view.
              contentBox.setRight(previousRight);
              mainViewInterface.toast(ToastType.SUCCESS, MessageFeedback.Success.Comment.UPDATE.getMessage());
            } catch (Exception e) {
              mainViewInterface.toast(ToastType.ERROR, e.getMessage());
            }
          });

          contentBox.setRight(saveButton);
        });

        Button deleteButton = new Button();
        Region deleteIcon = new Region();
        deleteIcon.getStyleClass().addAll("icon", "delete-icon");
        deleteButton.getStyleClass().add("icon-button");
        deleteButton.setGraphic(deleteIcon);

        deleteButton.setOnAction(event -> {
          if (alertConfirmation(item)) {
            try {
              commentDao.deleteComment(item.getId());
              originalList.remove(item);
              mainViewInterface.toast(ToastType.SUCCESS, MessageFeedback.Success.Comment.DELETE.getMessage());
            } catch (Exception e) {
              mainViewInterface.toast(ToastType.ERROR, e.getMessage());
            }
          }
        });

        deleteButton.getStyleClass().add("delete-icon");
        VBox buttonsBox = new VBox();
        buttonsBox.getChildren().addAll(updateButton, deleteButton);

        // make it only visible if its the correct user logged in.
        buttonsBox.setVisible(item.getUserId() == mainViewInterface.getUser().getId());

        contentBox.setRight(buttonsBox);

        setGraphic(contentBox);
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
      }
    }

    private boolean alertConfirmation(Comment comment) {
      AlertPopup alert = new AlertPopup(AlertType.CONFIRMATION, mainViewInterface);
      alert.setTitle("Confirmation Dialog");
      alert.setHeaderMessage("Permanently delete comment? You cant undo this");
      alert.setContentMessage("Are you sure?");

      ButtonType cancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
      ButtonType confirm = new ButtonType("Delete Comment", ButtonData.OK_DONE);
      alert.getButtonTypes().setAll(cancel, confirm);

      if (alert.showAndWait().get() == confirm) {
        return true;
      }

      return false;
    }
  }

  /**
   * sorts the comments in the list by date created.
   */
  public void sortCommentsByDate() {
    Comparator<Comment> comparator = new Comparator<Comment>() {
      @Override
      public int compare(Comment c1, Comment c2) {
        return c1.getDate().compareTo(c2.getDate());
      }
    }.reversed();

    this.setItems(new SortedList<Comment>(originalList, comparator));
  }
}
