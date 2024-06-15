package cookbook.controller;

/**
 * MessageFeedback is an enumeration that defines the types of feedback messages
 * that can be displayed to the user in the cookbook application. Each enum
 * value
 * has a message associated with it.
 */
public enum MessageFeedback {
  ;

  /**
   * Enumerates the types of information messages that can be displayed to the
   * user.
   */
  public enum Info {
    ;

    /**
     * Enumerates the types of information messages that can be displayed to the
     * user.
     */
    public enum Ingredient {

      ADD_FAIL("Please choose an ingredient.");

      private String message;

      private Ingredient(String message) {
        this.message = message;
      }

      public String getMessage() {
        return message;
      }
    }

  }

  /**
   * Enumerates the types of error messages that can be displayed to the user.
   */
  public enum Error {
    ;

    /**
     * Enumerates the types of error messages that can be displayed to the user.
     */
    public enum Comment {
      ID("Comment ID must be greater than 0"),
      RECIPE_ID("Comment recipe ID must be greater than 0"),
      TEXT("Comment text cannot be empty"),
      RECIPE("Comment recipe ID must be greater than 0"),
      TIMESTAMP("Comment timestamp cannot be null"),
      DATE("Comment date must be greater than 0"),
      OWNER("Comment owner ID must be greater than 0"),
      ADD("Comment could not be added"),
      DISPLAY_NAME("Comment display name cannot be empty"),
      UPDATED_DATE("Comment updated date must be greater than 0"),
      UPDATE("Comment could not be updated"),
      USER_ID("Comment user ID must be greater than 0"),
      DELETE("Comment could not be deleted");

      private String message;

      private Comment(String message) {
        this.message = message;
      }

      public String getMessage() {
        return message;
      }
    }

    /**
     * Enumerates the types of Unit error messages.
     */
    public enum Unit {
      ID("Unit ID must be greater than 0"),
      NAME("Unit name cannot be empty"),
      ADD("Unit could not be added"),
      UPDATE("Unit could not be updated"),
      DELETE("Unit could not be deleted");

      private String message;

      private Unit(String message) {
        this.message = message;
      }

      public String getMessage() {
        return message;
      }
    }

    /**
     * Enumerates the types of SQL error messages that can be displayed to the user.
     */
    public enum Sql {
      KEY("SQL Error: Key could not be generated"),
      ADD("SQL Error: Recipe could not be added"),
      UPDATE("SQL Error: Recipe could not be updated"),
      DELETE("SQL Error: Recipe could not be deleted"),
      GENERIC("Unknown database error, contact administrator");

      private String message;

      private Sql(String message) {
        this.message = message;
      }

      public String getMessage() {
        return message;
      }
    }

    /**
     * Enumerates the types of validation error messages that can be displayed to
     * the user.
     */
    public enum Recipe {
      ID("Recipe ID must be greater than 0"),
      TITLE("Name cannot be empty or must be less than 75 characters"),
      DESCRIPTION("Description cannot be empty or must be less than 1000 characters"),
      INGREDIENT("You must add atleast one ingredient"),
      STEP("You must add atleast one step"),
      LOAD("Could not load recipe"),
      NAME("Recipe name cannot be empty"),
      FAVOURITE("Could not load favourites"),
      DOFAVOURITE("Could not favourite recipe"),
      DELETE("Recipe could not be deleted"),
      CREATE("Recipe could not be created"),
      PORTIONS("Portions must be greater than 0"),
      OWNER("Owner ID must be greater than 0"),
      INGREDIENTS("You must add atleast one ingredient"),
      STEPS("You must add atleast one step"),
      UPDATE("Recipe could not be updated");

      private String message;

      private Recipe(String message) {
        this.message = message;
      }

      public String getMessage() {
        return message;
      }

    }

    /**
     * Enumerates the types of validation error messages that can be displayed to
     * the user.
     */
    public enum User {
      FIRSTNAME("First name cannot be empty or must be less than 45 characters"),
      LASTNAME("Last name cannot be empty or must be less than 45 characters"),
      USERNAME("Username cannot be empty or must be less than 45 characters"),
      PASSWORD("Password cannot be empty or must atleast 8 characters"),
      SETDISPLAYNAME("Display name cannot be empty or must be less than 45 characters"),
      EMAIL("Email cannot be empty or must be less than 75 characters"),
      ADD("User could not be added"),
      UPDATE("User could not be updated"),
      DELETE("User could not be deleted"),
      AUTHENTICATION("Invalid credentials");

      private String message;

      private User(String message) {
        this.message = message;
      }

      public String getMessage() {
        return message;
      }
    }

    /**
     * Enumerates the types of validation error messages that can be displayed to
     * the user.
     */
    public enum Ingredient {
      ID("Ingredient ID must be greater than 0"),
      NAME("Ingredient name cannot be empty"),
      QTY("Quantity must be greater than 0"),
      QTY_STR("Quantity has to be a number"),
      OWNER("Ingredient owner ID must be greater than or equal to 0"),
      ADD("Ingredient could not be added"),
      UPDATE("Ingredient could not be updated"),
      DELETE(
          "Could not delete ingredient, possible reason could be that this ingredient your trying to delete is used in a recipe."),
      LOADING("Could not load ingredients");

      private String message;

      private Ingredient(String message) {
        this.message = message;
      }

      public String getMessage() {
        return message;
      }
    }

    /**
     * Enumerates the types of validation error messages that can be displayed to
     * the user.
     */
    public enum Tag {
      ID("Tag ID must be greater than 0"),
      NAME("Tag name cannot be empty or contain more than 20 characters"),
      ADD("Tag could not be added"),
      ADD_FAIL("Please choose a tag to add"),
      EXIST("Tag already added"),
      UPDATE("Tag could not be updated"),
      DELETE("Tag could not be deleted"),
      OWNER("Tag owner ID must be greater than or equal to 0"),
      BACKGROUND_COLOR("Tag background color must be a valid hex color code"),
      TEXT_COLOR("Tag text color must be a valid hex color code"),
      LOADING("Could not load tags");

      private String message;

      private Tag(String message) {
        this.message = message;
      }

      public String getMessage() {
        return message;
      }
    }

    /**
     * Enumerates the types of validation error messages that can be displayed to
     * the user.
     */
    public enum Step {
      INSTRUCTION("Step instruction cannot be empty"),
      INDEX("Step index must be greater than 0"),
      ADD("Step could not be added"),
      UPDATE("Step could not be updated"),
      DELETE("Step could not be deleted");

      private String message;

      private Step(String message) {
        this.message = message;
      }

      public String getMessage() {
        return message;
      }
    }

  }

  /**
   * Enumerates the types of success messages that can be displayed to the user.
   */
  public enum Success {
    ;
    /**
     * Enumerates the types of success messages that can be displayed to the user.
     */
    public enum Comment {
      ADD("Comment added successfully"),
      DELETE("Comment deleted successfully"),
      UPDATE("Comment updated successfully");

      private String message;

      private Comment(String message) {
        this.message = message;
      }

      public String getMessage() {
        return message;
      }
    }

    /**
     * Enumerates the types of success messages that can be displayed to the user.
     */
    public enum Recipe {
      ADD("Recipe added successfully"),
      UPDATE("Recipe updated successfully"),
      DELETE("Recipe deleted successfully");

      private String message;

      private Recipe(String message) {
        this.message = message;
      }

      public String getMessage() {
        return message;
      }
    }

    /**
     * Enumerates the types of success messages that can be displayed to the user.
     */
    public enum User {
      ADD("User added successfully"),
      UPDATE("User updated successfully"),
      DELETE("User deleted successfully");

      private String message;

      private User(String message) {
        this.message = message;
      }

      public String getMessage() {
        return message;
      }
    }

    /**
     * Enumerates the types of success messages that can be displayed to the user.
     */
    public enum Ingredient {
      ADD("Ingredient added successfully"),
      UPDATE("Ingredient updated successfully"),
      DELETE("Ingredient deleted successfully");

      private String message;

      private Ingredient(String message) {
        this.message = message;
      }

      public String getMessage() {
        return message;
      }
    }

    /**
     * Enumerates the types of success messages that can be displayed to the user.
     */
    public enum Tag {
      ADD("Tag added successfully"),
      UPDATE("Tag updated successfully"),
      DELETE("Tag deleted successfully");

      private String message;

      private Tag(String message) {
        this.message = message;
      }

      public String getMessage() {
        return message;
      }
    }

    /**
     * Enumerates the types of success messages that can be displayed to the user.
     */
    public enum Step {
      ADD("Step added successfully"),
      UPDATE("Step updated successfully"),
      DELETE("Step deleted successfully");

      private String message;

      private Step(String message) {
        this.message = message;
      }

      public String getMessage() {
        return message;
      }
    }
  }
}
