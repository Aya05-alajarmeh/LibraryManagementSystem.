
package librarySystem.service;

import librarySystem.DAO.CategoryDAO;
import librarySystem.DTO.CategoryDTO;
import librarySystem.Model.Category;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryService {

    public enum Mode { Add, Update }

    private Mode mode = Mode.Add;
    private Category category;

    public CategoryService() {
        this.category = new Category();
        this.mode = Mode.Add;
    }

    public CategoryService(Category category) {
        this.category = category;
        this.mode = (category != null && category.getCategoryId() != 0 && category.getCategoryId() != -1) ? Mode.Update : Mode.Add;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    private void _addNewCategory() {
        int newId = CategoryDAO.addCategory(this.category);
        if (newId != -1) {
            this.category.setCategoryId(newId);
        } else {
            throw new RuntimeException("failed to add category to the database.");
        }
    }

    private void _updateCategory() {
        boolean updated = CategoryDAO.updateCategory(this.category);
        if (!updated) {
            throw new RuntimeException("failed to update category in the database.");
        }
    }

    public void save() {
        if (category == null) {
            throw new IllegalArgumentException("failed to save category: Invalid category data.");
        }

        if (category.getCategoryName() == null || category.getCategoryName().trim().isEmpty()) {
            throw new IllegalArgumentException("failed to save category: Category name is required.");
        }

        switch (mode) {
            case Add:
                _addNewCategory();
                mode = Mode.Update;
                break;

            case Update:
                _updateCategory();
                break;
        }
    }

    public static List<CategoryDTO> getAllCategories() {
        try {
            List<Category> categories = CategoryDAO.getAllCategories();
            return categories.stream()
                    .map(CategoryDTO::fromCategory)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("failed to fetch categories: " + e.getMessage());
        }
    }

    public static void delete(int categoryId) {
        if (categoryId <= 0) {
            throw new IllegalArgumentException("failed to delete category: Invalid category ID provided.");
        }
        boolean deleted = CategoryDAO.deleteCategory(categoryId);
        if (!deleted) {
            throw new RuntimeException("failed to delete category with ID: " + categoryId);
        }
    }
}