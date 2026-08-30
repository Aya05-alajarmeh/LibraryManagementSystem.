
package librarySystem.Model;
public class Category {
    private int categoryId;
    private String categoryName;
    private String description;
    private boolean isDeleted;

    public Category() {}

    public Category(int categoryId, String categoryName, String description, boolean isDeleted) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.description = description;
        this.isDeleted = isDeleted;
    }

    // Getters and Setters
    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public boolean isDeleted() { return isDeleted; }
    public void setDeleted(boolean deleted) { isDeleted = deleted; }
}