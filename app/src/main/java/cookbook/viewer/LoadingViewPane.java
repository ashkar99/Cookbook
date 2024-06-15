package cookbook.viewer;

import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/**
 * View which is displayed while application is loading.
 */
public class LoadingViewPane extends VBox {

  /**
   * Construct a new LoadingViewPane, which displays a label.
   */
  public LoadingViewPane() {
    final Label loadingLabel = new Label("Loading");
    loadingLabel.getStyleClass().add("loading");

    this.getChildren().add(loadingLabel);
    this.getChildren().add(createCircleAnim());
    // this.getChildren().add(createDotsAnim());
    this.setAlignment(Pos.CENTER);

  }

  private StackPane createCircleAnim() {
    Circle backgroundCircle = new Circle();
    backgroundCircle.setRadius(40);
    backgroundCircle.setFill(Color.web("#98CC81"));

    Arc arc = new Arc();
    arc.setRadiusX(40);
    arc.setRadiusY(40);
    arc.setStartAngle(0);
    arc.setLength(270.0f);
    arc.setFill(Color.BLACK);
    arc.setType(ArcType.ROUND);
    arc.setOpacity(0.5);

    Group group = new Group();
    group.getChildren().add(arc);
    arc.setTranslateX(100);
    arc.setTranslateY(100);

    Circle coverCircle = new Circle();
    coverCircle.setRadius(30);
    coverCircle.setFill(Color.web("#fdf6e3"));

    RotateTransition rt2 = new RotateTransition(Duration.millis(1000), group);
    rt2.setByAngle(360);
    rt2.setCycleCount(Animation.INDEFINITE);
    rt2.play();

    StackPane stackPane = new StackPane();
    stackPane.setAlignment(Pos.CENTER);
    stackPane.getChildren().addAll(backgroundCircle, group, coverCircle);
    return stackPane;
  }

}
