package cookbook.viewer;

import cookbook.model.User;
import javafx.scene.Parent;

/**
 * Interface to encapsulate main view pane.
 */
public interface MainViewInterface {
  public void updateContent(Parent pane);

  public void toast(ToastType type, String message);

  public void displayAlert(AlertInterface alert);

  public User getUser();
}
