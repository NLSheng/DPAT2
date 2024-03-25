import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GenericFileManager<T> {
    private final String filePath;

    public GenericFileManager(String filePath) {
        this.filePath = filePath;
        // Check if the file exists, if not, create a new file
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("An error occurred while creating the file.");
                e.printStackTrace();
            }
        }
    }

    public List<T> loadItems(ItemLoader<T> itemLoader) {
        List<T> items = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                T item = itemLoader.loadItem(line);
                if (item != null) {
                    items.add(item);
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading from the file.");
            e.printStackTrace();
        }
        return items;
    }

    public void saveItems(List<T> items, ItemWriter<T> itemWriter) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (int i = 0; i < items.size(); i++) {
                String line = itemWriter.writeItem(items.get(i));
                writer.write((i + 1) + line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }

    public interface ItemLoader<T> {
        T loadItem(String line);
    }

    public interface ItemWriter<T> {
        String writeItem(T item);
    }
}