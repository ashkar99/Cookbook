package cookbook.viewer;

import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Alert popup.
 */
public class AlertPopup extends Alert implements AlertInterface {
  private String alertCss = "/alert.css";

  /**
   * Creates alert popup.
   *
   * @param alertType type of alert
   * @param mainViewPane mainviewpane interface to set owner
   */
  public AlertPopup(AlertType alertType, MainViewInterface mainViewPane) {
    super(alertType);
    this.getDialogPane().getStylesheets().add(getClass().getResource(alertCss).toExternalForm());

    mainViewPane.displayAlert(this);
  }

  @Override
  public void setOwner(Stage stage) {
    this.initModality(Modality.APPLICATION_MODAL);
    this.initOwner(stage);
  }

  public void setHeaderMessage(String headerMessage) {
    this.getDialogPane().setHeaderText(headerMessage);
  }

  public void setContentMessage(String contentMessagef) {
    this.getDialogPane().setContentText(contentMessagef);
  }
}
