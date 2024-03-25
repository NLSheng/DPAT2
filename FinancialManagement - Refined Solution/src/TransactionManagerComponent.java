import java.util.List;

public interface TransactionManagerComponent {
    public void addTransaction(Transaction transaction);
    List<Transaction> getAllTransactions();
    public void updateTransaction(Transaction oldTransaction, Transaction newTransaction, String field);
    public void deleteTransaction(Transaction transaction);
}