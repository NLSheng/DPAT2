public class LoggingDecorator extends TransactionDecorator {
    public LoggingDecorator(TransactionManagerComponent transactionManagerComponent) {
        super(transactionManagerComponent);
    }

    @Override
    public void addTransaction(Transaction transaction) {
        logBefore();
        super.addTransaction(transaction);
        logAfter();
    }

    @Override
    public void updateTransaction(Transaction oldTransaction, Transaction newTransaction, String field) {
        logBefore();
        super.updateTransaction(oldTransaction, newTransaction, field);
        logAfter();
    }

    @Override
    public void deleteTransaction(Transaction transaction) {
        logBefore();
        super.deleteTransaction(transaction);
        logAfter();
    }

    private void logBefore() {
        System.out.printf("\u001B[32m");
        System.out.println("-------------------------------------------------------------------");
        System.out.println("Processing...");
    }

    private void logAfter() {
        System.out.println("Complete!");
        System.out.println("--------------------------------------------------------------------");
        System.out.printf("\u001B[0m");
    }
}