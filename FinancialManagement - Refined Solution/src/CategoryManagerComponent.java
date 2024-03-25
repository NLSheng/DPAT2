import java.util.List;

public interface CategoryManagerComponent {
    public void addCategory(Category category);
    List<Category> getAllCategories();
    public void updateCategory(Category oldCategory, Category newCategory);
    public void deleteCategory(Category category);
}