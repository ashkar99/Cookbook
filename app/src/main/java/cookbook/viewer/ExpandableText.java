package cookbook.viewer;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.text.Text;

/**
 * ExpandableText. A javafx text with functionality for expanding and condensing
 * a text.
 */
public class ExpandableText extends Text {
  private StringProperty fullTextProperty;
  private int charactersAtCut;
  private BooleanProperty expanded;
  private BooleanProperty expandable;

  /**
   * ExpandableText.
   *
   * @param fullText        the string with the fulltext.
   *
   * @param charactersAtCut how many characters should be displayed when
   *                        minimizing/cutting the
   *                        text?
   *
   * @param expanded        should the expandabletext be full(expanded) at start?
   */
  public ExpandableText(String fullText, int charactersAtCut, boolean expanded) {
    this.fullTextProperty = new SimpleStringProperty(fullText);
    this.charactersAtCut = charactersAtCut;

    this.expanded = new SimpleBooleanProperty(expanded);

    this.expandable = new SimpleBooleanProperty();
    this.expandable.bind(Bindings.createBooleanBinding(
        () -> fullText.length() > charactersAtCut,
        this.textProperty(), // Update when text changes
        fullTextProperty // Update when expanded changes
    ));

    updateBinds();
  }

  public String getFullText() {
    return this.fullTextProperty.getValue();
  }

  private void updateBinds() {
    if (this.expanded.getValue()) {
      expandText();
    } else {
      cutText();
    }
  }

  public void setFullText(String fullText) {
    this.fullTextProperty.set(fullText);
    updateBinds();
  }

  @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "It is read-only.")
  public ReadOnlyBooleanProperty getExpandableProperty() {
    return this.expandable;
  }

  @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "It is read-only.")
  public ReadOnlyBooleanProperty getExpandedProperty() {
    return this.expanded;
  }

  public void setCharactersAtCut(int charactersAtCut) {
    this.charactersAtCut = charactersAtCut;
  }

  /**
   * expand the text to view the fulltext.
   */
  public void expandText() {
    super.textProperty().unbind();
    super.textProperty().bind(fullTextProperty);
    this.expanded.set(true);
  }

  /**
   * cut the text to view maximum the amount set on charactersAtCut. Use
   * setCharactersAtCut to cut
   * less or more.
   */
  public void cutText() {
    StringBinding cutTextBinding = Bindings.createStringBinding(
        () -> fullTextProperty.getValue().substring(0, Math.min(fullTextProperty.getValue().length(), charactersAtCut))
            + (this.expandable.getValue() ? "..." : ""));

    super.textProperty().unbind();
    super.textProperty().bind(cutTextBinding);
    this.expanded.set(false);
  }

}
