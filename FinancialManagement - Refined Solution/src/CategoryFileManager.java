import java.util.List;

public class CategoryFileManager {
    private static final String FILE_PATH = "category.txt";

    public static List<Category> loadCategories() {
        GenericFileManager<Category> fileManager = new GenericFileManager<>(FILE_PATH);
        return fileManager.loadItems(Category::fromString);
    }

    public static void saveCategories(List<Category> categories) {
        GenericFileManager<Category> fileManager = new GenericFileManager<>(FILE_PATH);
        fileManager.saveItems(categories, Category::toString);
    }
}