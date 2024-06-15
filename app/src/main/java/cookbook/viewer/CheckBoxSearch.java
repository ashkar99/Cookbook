package cookbook.viewer;

import cookbook.controller.SearchBy;
import javafx.scene.control.CheckBox;

/**
 * Checkbox for advanced search.
 */
public class CheckBoxSearch extends CheckBox {
  private SearchBy searchOption;

  public CheckBoxSearch(String text, SearchBy searchOption) {
    super(text);
    this.searchOption = searchOption;
  }

  public SearchBy getSearchOption() {
    return searchOption;
  }
}
