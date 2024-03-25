import java.util.List;

public class TransactionManager implements TransactionManagerComponent {
    private List<Transaction> transactions;

    public TransactionManager() {
    }

    public TransactionManager(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        TransactionFileManager.saveTransactions(transactions);
        System.out.println("Transaction added successfully!");
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactions;
    }

    @Override
    public void updateTransaction(Transaction oldTransaction, Transaction newTransaction, String field) {
        boolean found = false;
        for (Transaction transaction : transactions) {
            if (transaction.getTransactionID().equals(oldTransaction.getTransactionID())) {
                if (field.equals("TransactionType")) {
                    transaction.setTransactionType(newTransaction.getTransactionType());
                    System.out.println("Transaction Type Update Successfully!");
                } else if (field.equals("Category")) {
                    transaction.setCategory(newTransaction.getCategory());
                    System.out.println("Transaction Category Update Successfully!");
                } else if (field.equals("Date")) {
                    transaction.setDate(newTransaction.getDate());
                    System.out.println("Transaction Date Update Successfully!");
                } else if (field.equals("Amount")) {
                    transaction.setAmount(newTransaction.getAmount());
                    System.out.println("Transaction Amount Update Successfully!");
                } else {
                    transaction.setDescription(newTransaction.getDescription());
                    System.out.println("Transaction Description Update Successfully!");
                }
                TransactionFileManager.saveTransactions(transactions);
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("ID not found: " + oldTransaction.getTransactionID());
        }
    }

    @Override
    // Delete operation: Delete an existing category
    public void deleteTransaction(Transaction transaction) {
        Transaction transactionToDelete = transactions.stream()
                .filter(c -> c.getTransactionID().equals(transaction.getTransactionID()))
                .findFirst()
                .orElse(null);
        transactions.remove(transactionToDelete);
        TransactionFileManager.saveTransactions(transactions);
        transactions = TransactionFileManager.loadTransactions();
        System.out.println("Transaction deleted succesfully!");
    }
}