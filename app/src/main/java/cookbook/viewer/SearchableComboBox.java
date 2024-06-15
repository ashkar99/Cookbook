package cookbook.viewer;

import java.util.function.Function;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.skin.ComboBoxListViewSkin;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.util.StringConverter;

/**
 * Searchable combobox.
 */
public class SearchableComboBox<T> extends ComboBox<T> {

  /**
   * Constructor for the searchable combobox.
   *
   * @param userid        the user id of the current logged in user. Dunno why.
   *
   * @param items         the observable list of items in the combobox.
   * 
   * @param nameExtractor a lambda function that gets the string from the item
   *                      that you want to compare on.
   *                      if you have a ingredient, and you want to sort on the
   *                      unitstring,
   *                      do Ingredient::getUnitString, for example.
   */
  public SearchableComboBox(int userid, ObservableList<T> items,
      Function<T, String> nameExtractor) {
    super();

    this.setCellFactory(param -> new ListCell<T>() {
      @Override
      protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
          setText(null);
          setGraphic(null);
          this.setMaxHeight(0); // Set the height to 0 for empty cells
          this.setVisible(false);
        } else {
          setText(nameExtractor.apply(item));
          // Optionally, set graphic if needed
          setGraphic(null);
          // Set the height based on content, e.g., based on text length
          setPrefHeight(Region.USE_COMPUTED_SIZE);
        }
      }
    });

    // Create a FilteredList to dynamically filter items
    final FilteredList<T> filteredItems = new FilteredList<>(items, p -> true);
    this.setItems(filteredItems);

    this.setEditable(true);

    this.getEditor().setOnMouseClicked(e -> {
      this.show();
    });

    /*
     * Below searches based on a java predicate. Solution from
     * https://stackoverflow.com/questions/19010619/javafx-filtered-combobox.
     */
    SortedList<T> sorted = filteredItems.sorted((o1, o2) -> {
      String s1 = nameExtractor.apply(o1);
      String s2 = nameExtractor.apply(o2);

      if (s1 == null && s2 == null) {
        return 0;
      } else if (s1 == null) {
        return -1;
      } else if (s2 == null) {
        return 1;
      } else {
        return s1.compareToIgnoreCase(s2);
      }
    });

    this.getEditor().textProperty().addListener((observableValue, oldValue, newValue) -> {
      final T selected = this.getSelectionModel().getSelectedItem();
      final TextField editor = this.getEditor();

      Platform.runLater(() -> {
        if (selected == null || !nameExtractor.apply(selected).equals(editor.getText())) {
          filteredItems.setPredicate(
              item -> nameExtractor.apply(item).toLowerCase().contains(newValue.toLowerCase()));
          this.setItems(sorted);
          // To avoid focus on first item in list when space is pressed
          ((ListView<?>) ((ComboBoxListViewSkin<?>) this.getSkin()).getPopupContent())
              .getFocusModel().focus(-1);
        }

        // Resize the list and relocate it after sorting.
        // black magic...
        ((ListView<?>) ((ComboBoxListViewSkin<?>) this.getSkin()).getPopupContent()).autosize();
        ((ComboBoxListViewSkin<?>) this.getSkin()).getPopupContent().autosize();
        ((ComboBoxListViewSkin<?>) this.getSkin()).getSkinnable().autosize();

        ((ListView<?>) ((ComboBoxListViewSkin<?>) this.getSkin()).getPopupContent())
            .layoutXProperty().bind(this.getEditor().layoutXProperty());

        ((ListView<?>) ((ComboBoxListViewSkin<?>) this.getSkin()).getPopupContent())
            .layoutYProperty().bind(this.getEditor().layoutYProperty());
      });
    });

    // Fix spacebug - spacebar does some event somewhere else that closes the
    // combobox.
    // consume the spacebar keypress event to solve this.
    ComboBoxListViewSkin<T> skin = new ComboBoxListViewSkin<>(this);
    skin.getPopupContent().addEventFilter(KeyEvent.ANY, e -> {
      if (e.getCode() == KeyCode.SPACE) {
        e.consume();
      } else if (e.getCode() == KeyCode.ENTER) {
        e.consume();
      }
    });

    this.setSkin(skin);
    this.setPlaceholder(new Text("No matches found."));

    ComboBox<T> comboBox = this;
    this.setConverter(new StringConverter<T>() {
      @Override
      public String toString(T object) {
        return object == null ? "" : nameExtractor.apply(object);
      }

      @Override
      public T fromString(String string) {
        return comboBox.getItems().stream().filter(item -> nameExtractor.apply(item).equals(string))
            .findFirst().orElse(null);
      }
    });
  }
}
