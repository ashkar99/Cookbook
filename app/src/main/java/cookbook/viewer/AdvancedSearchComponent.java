package cookbook.viewer;

import cookbook.controller.EnumSearch;
import cookbook.controller.SearchBy;
import cookbook.controller.SearchByIngredient;
import cookbook.controller.SearchByName;
import cookbook.controller.SearchByTag;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/**
 * Advanced search, used for filter search. 
 */
public class AdvancedSearchComponent extends VBox {
  private ArrayList<CheckBoxSearch> checkBoxSearchOptions;

  /**
   * Creates the advanced searched component.
   */
  public AdvancedSearchComponent() {
    checkBoxSearchOptions = new ArrayList<>();
    setDefaultSearchOptions();

    Button advancedSearchButton = new Button("Advanced search");
    advancedSearchButton.setMaxWidth(Double.MAX_VALUE);
    this.getChildren().add(advancedSearchButton);

    VBox searchOptionsBox = new VBox();
    searchOptionsBox.setPadding(new Insets(5, 0, 0, 0));
    searchOptionsBox.setSpacing(5);
    searchOptionsBox.setMinWidth(USE_PREF_SIZE);
    this.getChildren().add(searchOptionsBox);

    searchOptionsBox.getChildren().addAll(checkBoxSearchOptions);

    this.prefWidthProperty().bind(searchOptionsBox.widthProperty());
    this.prefWidthProperty().addListener(event -> {
      searchOptionsBox.setVisible(false);
      searchOptionsBox.setManaged(false);
    });

    advancedSearchButton.setOnAction(event -> {
      searchOptionsBox.setVisible(!searchOptionsBox.isVisible());
      searchOptionsBox.setManaged(!searchOptionsBox.isManaged());
    });
  }

  private void setDefaultSearchOptions() {
    for (Map.Entry<EnumSearch, SearchBy> entry : getAllSearchOptions().entrySet()) {
      CheckBoxSearch optionItem = new CheckBoxSearch(entry.getKey().toString(), entry.getValue());
      optionItem.setSelected(true);
      checkBoxSearchOptions.add(optionItem);
    }
  }

  private Map<EnumSearch, SearchBy> getAllSearchOptions() {
    Map<EnumSearch, SearchBy> options = new HashMap<>();
    options.put(EnumSearch.NAME, new SearchByName());
    options.put(EnumSearch.INGREDIENT, new SearchByIngredient());
    options.put(EnumSearch.TAG, new SearchByTag());

    return options;
  }

  /**
   * Returns a list of the selected search options.
   *
   * @return a list of search options
   */
  public List<SearchBy> getSelectedSearchOptions() {
    List<SearchBy> options = new ArrayList<>();
    for (CheckBoxSearch checkBoxSearch : checkBoxSearchOptions) {
      if (checkBoxSearch.isSelected()) {
        options.add(checkBoxSearch.getSearchOption());
      }
    }

    return options;
  }
}
