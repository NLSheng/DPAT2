import java.util.Objects;

public class Category {
    private String categoryID;
    private String categoryName;

    public Category() {
    }

    public Category(String categoryID, String categoryName) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Category category = (Category) o;
        return Objects.equals(categoryName, category.categoryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryName);
    }

    public static Category fromString(String line) {
        String[] parts = line.split(",");
        if (parts.length == 2) {
            return new Category(parts[0].trim(), parts[1].trim());
        }
        // Return null or throw an exception if parsing fails
        return null;
    }

    @Override
    public String toString() {
        return "," + getCategoryName();
    }
}
