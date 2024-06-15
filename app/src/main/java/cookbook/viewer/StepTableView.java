package cookbook.viewer;

import cookbook.model.ObservableStep;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Callback;

/**
 * StepTableView for showing the recipe steps.
 */
public class StepTableView extends TableView<ObservableStep> {

  private static final DataFormat SERIALIZED_MIME_TYPE = new DataFormat("application/x-java-serialized-object");

  /**
   * ctor.
   *
   * @param stepList a observablelist of observablesteps.
   */
  public StepTableView(ObservableList<ObservableStep> stepList) {
    super(stepList);
    this.setPlaceholder(new Label("Add some steps"));

    // stepTable.getColumns().add(new TableColumn<ObservableStep, String>());

    TableColumn<ObservableStep, Integer> stepNumberColumn = new TableColumn<>("#");

    stepNumberColumn.setCellValueFactory(
        new Callback<CellDataFeatures<ObservableStep, Integer>, ObservableValue<Integer>>() {
          @Override
          public ObservableValue<Integer> call(CellDataFeatures<ObservableStep, Integer> i) {
            int rowIndex = i.getTableView().getItems().indexOf(i.getValue());

            i.getValue().setStepIndex(rowIndex + 1);

            return new SimpleIntegerProperty(rowIndex + 1).asObject();
          }
        });

    stepNumberColumn.prefWidthProperty().bind(this.widthProperty().divide(12));
    stepNumberColumn.setResizable(false);

    TableColumn<ObservableStep, Text> stepDescriptionColumn = new TableColumn<>("description");

    this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

    stepDescriptionColumn.setCellValueFactory(
        new Callback<CellDataFeatures<ObservableStep, Text>, ObservableValue<Text>>() {
          @Override
          public ObservableValue<Text> call(CellDataFeatures<ObservableStep, Text> i) {
            StringBinding textBinding = Bindings.createStringBinding(() -> i.getValue().instructionsProperty().get());
            Text text = new Text();
            text.textProperty().bind(textBinding);

            text.wrappingWidthProperty().bind(stepDescriptionColumn.widthProperty());
            return new SimpleObjectProperty<>(text);
          }
        });

    TableColumn<ObservableStep, Button> deleteColumn = new TableColumn<>("Delete");
    deleteColumn.setCellFactory(ButtonCell.<ObservableStep>forTableColumn("delete", (ObservableStep p) -> {
      this.getItems().remove(p);
      this.refresh();
      return p;
    }));
    deleteColumn.prefWidthProperty().bind(this.widthProperty().divide(12));
    deleteColumn.setResizable(true);
    deleteColumn.setMinWidth(40);
    deleteColumn.setReorderable(false);
    deleteColumn.setSortable(false);
    stepNumberColumn.getStyleClass().add("align-left");
    stepDescriptionColumn.getStyleClass().add("align-left");

    this.getColumns().add(stepNumberColumn);
    this.getColumns().add(stepDescriptionColumn);
    this.getColumns().add(deleteColumn);

    this.setRowFactory(tv -> {
      TableRow<ObservableStep> row = new TableRow<>();

      row.setOnDragDetected(event -> {
        if (!row.isEmpty()) {
          Integer index = row.getIndex();
          Dragboard db = row.startDragAndDrop(TransferMode.MOVE);
          db.setDragView(row.snapshot(null, null));
          ClipboardContent cc = new ClipboardContent();
          cc.put(SERIALIZED_MIME_TYPE, index);
          db.setContent(cc);
          event.consume();
        }
      });

      row.setOnDragOver(event -> {
        Dragboard db = event.getDragboard();
        if (db.hasContent(SERIALIZED_MIME_TYPE)) {
          if (row.getIndex() != ((Integer) db.getContent(SERIALIZED_MIME_TYPE)).intValue()) {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            event.consume();
          }
        }
      });

      row.setOnDragDropped(event -> {
        Dragboard db = event.getDragboard();
        if (db.hasContent(SERIALIZED_MIME_TYPE)) {
          int draggedIndex = (Integer) db.getContent(SERIALIZED_MIME_TYPE);
          ObservableStep draggedPerson = this.getItems().remove(draggedIndex);

          int dropIndex;

          if (row.isEmpty()) {
            dropIndex = this.getItems().size();
          } else {
            dropIndex = row.getIndex();
          }

          this.getItems().add(dropIndex, draggedPerson);

          event.setDropCompleted(true);
          this.getSelectionModel().select(dropIndex);
          event.consume();
        }
      });

      return row;
    });
  }

  /**
   * Overrides resize function to remove header of this table.
   * https://stackoverflow.com/questions/27118872/how-to-hide-tableview-column-header-in-javafx-8
   */
  @Override
  public void resize(double width, double height) {
    super.resize(width, height);
    Pane header = (Pane) lookup("TableHeaderRow");
    header.setMinHeight(0);
    header.setPrefHeight(0);
    header.setMaxHeight(0);
    header.setVisible(false);
  }
}
