package cookbook.viewer;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * A toast to give feedback to the user.
 */
public class Toast extends AnchorPane {
  private String toastCss = "/toast.css";

  /**
   * Creates a toast for feedback.
   *
   * @param type of toast
   * @param message to the user
   */

  public Toast(ToastType type, String message) {
    this.getStylesheets().add(getClass().getResource(toastCss).toExternalForm());
    this.getStyleClass().add("notification");
    this.setMinWidth(300);
    this.setMaxWidth(300);
    this.setMinHeight(60);
    this.setMaxHeight(60);

    VBox text = new VBox();
    text.setAlignment(Pos.CENTER);
    HBox textContainer = new HBox();
    textContainer.maxHeight(text.getHeight());
    textContainer.setPadding(new Insets(0, 24, 0, 8));
    text.getChildren().add(textContainer);

    Label toastMessage = new Label(message);
    toastMessage.setWrapText(true);
    Region svgIcon = new Region();
    svgIcon.getStyleClass().add("message-icon");
    toastMessage.setGraphic(svgIcon);

    textContainer.getChildren().add(toastMessage);

    Button button = new Button();
    button.setCursor(Cursor.HAND);
    Region svgShape = new Region();
    svgShape.getStyleClass().addAll("icon");
    button.setGraphic(svgShape);

    AnchorPane.setTopAnchor(button, 8.0);
    AnchorPane.setRightAnchor(button, 8.0);

    AnchorPane.setLeftAnchor(text, 0.0);
    AnchorPane.setRightAnchor(text, 0.0);
    AnchorPane.setBottomAnchor(text, 0.0);
    AnchorPane.setTopAnchor(text, 0.0);
    this.getChildren().addAll(text, button);

    switch (type) {
      case INFO:
        this.getStyleClass().add("info");
        svgIcon.getStyleClass().add("info-icon");
        break;
      case SUCCESS:
        this.getStyleClass().add("success");
        svgIcon.getStyleClass().add("success-icon");
        break;
      case WARNING:
        this.getStyleClass().add("warning");
        svgIcon.getStyleClass().add("warning-icon");
        break;
      case ERROR:
        this.getStyleClass().add("error");
        svgIcon.getStyleClass().add("error-icon");
        break;
      default:
        break;
    }

    animation();
  }

  private void animation() {
    FadeTransition fade = new FadeTransition(Duration.millis(1000));
    fade.setDelay(Duration.millis(4000));
    fade.setNode(this);
    fade.setFromValue(1);
    fade.setToValue(0);
    fade.play();

    PauseTransition pause = new PauseTransition(Duration.millis(5000));
    pause.setOnFinished(event -> {
      this.fireEvent(new ActionEvent());
    });
    pause.play();

    this.setOnMouseEntered(event -> {
      fade.jumpTo(Duration.ZERO);
      fade.stop();
      pause.stop();
    });

    this.setOnMouseExited(event -> {
      fade.jumpTo(Duration.ZERO);
      fade.play();
      pause.play();
    });
  }
}
