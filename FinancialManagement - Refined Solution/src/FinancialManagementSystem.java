public class FinancialManagementSystem {
    public static void main(String[] args) {
        TransactionDecorator transactionDecorator = new LoggingDecorator(new TransactionManager());
        CategoryManagerComponent categoryManager = new CategoryManager();
        Dashboard dashboard = new Dashboard();
        
        FinancialFacade financialFacade = new FinancialFacade(transactionDecorator, categoryManager, dashboard);
        financialFacade.viewDashboard();
    }
}