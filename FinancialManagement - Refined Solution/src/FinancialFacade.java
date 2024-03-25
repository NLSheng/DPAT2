import java.util.List;

public class FinancialFacade {
    private TransactionDecorator transactionDecorator;
    private CategoryManagerComponent categoryManager;
    private Dashboard dashboard;

    public FinancialFacade(TransactionDecorator transactionDecorator, CategoryManagerComponent categoryManager,
            Dashboard dashboard) {
        this.transactionDecorator = transactionDecorator;
        this.categoryManager = categoryManager;
        this.dashboard = dashboard;
        List<Category> categories = CategoryFileManager.loadCategories();
        this.categoryManager = new CategoryManager(categories);
        List<Transaction> transactions = TransactionFileManager.loadTransactions();
        this.transactionDecorator = new LoggingDecorator(new TransactionManager(transactions));
    }

    public void viewDashboard() {
        dashboard.displayMainMenu(this);
    }
    
    public void manageTransactionsMenu() {
        dashboard.displayTransactionsMenu(this);
    }

    public void manageCategoriesMenu() {
        dashboard.displayCategoriesMenu(this);
    }

    public void manageTransactions(String operation, Transaction oldTransaction, Transaction newTransaction, String field) {
        switch (operation) {
            case "add":
                transactionDecorator.addTransaction(newTransaction);
                break;
            case "update":
                transactionDecorator.updateTransaction(oldTransaction, newTransaction, field);
                break;
            case "delete":
                transactionDecorator.deleteTransaction(oldTransaction);
                break;
            default:
                System.out.println("Invalid operation for managing transactions.");
        }
    }

    public Transaction getSpecificTransactionById(String transactionID) {
        List<Transaction> transactions = getAllTransactions();
        for (Transaction transaction : transactions) {
            if (transaction.getTransactionID().equals(transactionID)) {
                return transaction;
            }
        }
        return null; // Transaction not found
    }

    public List<Transaction> getAllTransactions() {
        return transactionDecorator.getAllTransactions();
    }

    public void manageCategories(String operation, Category oldCategory, Category newCategory) {
        switch (operation) {
            case "add":
                categoryManager.addCategory(newCategory);
                break;
            case "update":
                categoryManager.updateCategory(oldCategory, newCategory);
                break;
            case "delete":
                categoryManager.deleteCategory(oldCategory);
                break;
            default:
                System.out.println("Invalid operation for managing categories.");
        }
    }

    public List<Category> getAllCategories() {
        return categoryManager.getAllCategories();
    }
}