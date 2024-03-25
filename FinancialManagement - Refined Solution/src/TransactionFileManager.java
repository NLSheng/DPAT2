import java.util.List;

public class TransactionFileManager {
    private static final String FILE_PATH = "transaction.txt";

    public static List<Transaction> loadTransactions() {
        GenericFileManager<Transaction> fileManager = new GenericFileManager<>(FILE_PATH);
        return fileManager.loadItems(Transaction::fromString);
    }

    public static void saveTransactions(List<Transaction> transactions) {
        GenericFileManager<Transaction> fileManager = new GenericFileManager<>(FILE_PATH);
        fileManager.saveItems(transactions, Transaction::toString);
    }
}