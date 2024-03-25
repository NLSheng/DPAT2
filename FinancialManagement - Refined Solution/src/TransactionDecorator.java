import java.util.List;

public abstract class TransactionDecorator implements TransactionManagerComponent {
    protected TransactionManagerComponent transactionManagerComponent;

    public TransactionDecorator(TransactionManagerComponent transactionManagerComponent) {
        this.transactionManagerComponent = transactionManagerComponent;
    }

    @Override
    public void addTransaction(Transaction transaction) {
        transactionManagerComponent.addTransaction(transaction);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionManagerComponent.getAllTransactions();
    }

    @Override
    public void updateTransaction(Transaction oldTransaction, Transaction newTransaction, String field) {
        transactionManagerComponent.updateTransaction(oldTransaction, newTransaction, field);
    }

    @Override
    public void deleteTransaction(Transaction transaction) {
        transactionManagerComponent.deleteTransaction(transaction);
    }
}