package librarySystem.DTO;
import librarySystem.Model.Category;
public class CategoryDTO {
    private int categoryId;
    private String categoryName;
    private String description;

    public CategoryDTO() {}

    public CategoryDTO(int categoryId, String categoryName, String description) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.description = description;
    }

    // Getters and Setters
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

public static CategoryDTO fromCategory(Category category) {
    if (category == null) {
        return null;
    }
    return new CategoryDTO(
        category.getCategoryId(),
        category.getCategoryName(),
        category.getDescription()
    );
}


}