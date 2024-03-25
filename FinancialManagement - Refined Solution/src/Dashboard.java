import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Dashboard {
    public void displayMainMenu(FinancialFacade financialFacade) {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean isValidOption = false;

            while (!isValidOption) {
                displayTransactionsTable(financialFacade);
                System.out.println("Welcome to the Financial Management System Dashboard!");
                System.out.println("1. Manage Transactions");
                System.out.println("2. Manage Categories");
                System.out.println("3. Exit");
                System.out.print("Choose an option: ");
                String choice = scanner.next();

                switch (choice) {
                    case "1":
                        financialFacade.manageTransactionsMenu();
                        isValidOption = true;
                        break;
                    case "2":
                        financialFacade.manageCategoriesMenu();
                        isValidOption = true;
                        break;
                    case "3":
                        System.out.println("Exiting...");
                        isValidOption = true;
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
        }
    }

    public void displayTransactionsMenu(FinancialFacade financialFacade) {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean isValidOption = false, updateTransaction = false;

            while (!isValidOption) {
                displayTransactionsTable(financialFacade);
                System.out.println("Transaction Management Menu:");
                System.out.println("1. Add Transaction");
                System.out.println("2. Update Transaction");
                System.out.println("3. Delete Transaction");
                System.out.println("4. Return to Dashboard");
                System.out.print("Choose an option: ");
                String transactionOption = scanner.next();

                switch (transactionOption) {
                    case "1":
                        // Implement add transaction functionality
                        addTransaction(financialFacade, scanner);
                        isValidOption = true;
                        break;
                    case "2":
                        // Implement update transaction functionality
                        updateTransaction = true;
                        isValidOption = true;
                        break;
                    case "3":
                        // Implement delete transaction functionality
                        deleteTransaction(financialFacade, scanner);
                        isValidOption = true;
                        break;
                    case "4":
                        isValidOption = true;
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }

            if (updateTransaction) {
                displayUpdateTransactionMenu(financialFacade, scanner);
            } else {
                displayMainMenu(financialFacade);
            }

        }
    }

    private void displaySpecificTransactionTable(FinancialFacade financialFacade, String transactionID,
            Transaction transaction) {
        System.out.printf("\u001B[34m"); // ANSI escape code for blue color
        System.out.printf("+----+----------+----------+------------+----------+-----------------+%n");
        System.out.printf("| ID |   Type   | Category |    Date    |  Amount  |   Description   |%n");
        System.out.printf("+----+----------+----------+------------+----------+-----------------+%n");
        System.out.printf("| %-3s| %-9s| %-9s| %-11s| %-9s| %-16s|%n", transaction.getTransactionID(),
                transaction.getTransactionType(), transaction.getCategory().getCategoryName(),
                transaction.getDate(), transaction.getAmount(), transaction.getDescription());
        System.out.printf("+---+----------+----------+------------+----------+------------------+%n");
        System.out.printf("\u001B[0m"); // Reset color
    }

    private void displayTransactionsTable(FinancialFacade financialFacade) {
        // Display list of categories
        List<Transaction> transactions = financialFacade.getAllTransactions();
        double totalAmount = 0;
        System.out.printf("\u001B[34m"); // ANSI escape code for blue color
        System.out.printf("+--------------------------------------------------------------------+%n");
        System.out.printf("|                        Transaction Table                           |%n");
        System.out.printf("+----+----------+----------+------------+----------+-----------------+%n");
        System.out.printf("| ID |   Type   | Category |    Date    |  Amount  |   Description   |%n");
        System.out.printf("+----+----------+----------+------------+----------+-----------------+%n");
        for (Transaction transaction : transactions) {
            System.out.printf("| %-3s| %-9s| %-9s| %-11s| %-9s| %-16s|%n", transaction.getTransactionID(),
                    transaction.getTransactionType(), transaction.getCategory().getCategoryName(),
                    transaction.getDate(), transaction.getAmount(), transaction.getDescription());
            if (transaction.getTransactionType() == Transaction.TransactionType.EXPENSE) {
                totalAmount = totalAmount - transaction.getAmount();
            } else {
                totalAmount = totalAmount + transaction.getAmount();
            }
        }
        System.out.printf("+---+----------+----------+------------+----------+------------------+%n");
        System.out.printf("| Net Income: " + totalAmount + "                                                 |%n");
        System.out.printf("+---+----------+----------+------------+----------+------------------+%n");
        System.out.printf("\u001B[0m"); // Reset color
    }

    private void addTransaction(FinancialFacade financialFacade, Scanner scanner) {
        // Enter Transaction Type
        int transactionID = financialFacade.getAllTransactions().size() + 1;
        System.out.println("Available Transaction Types:");
        System.out.println("1. INCOME");
        System.out.println("2. EXPENSE");
        int transactionTypeOption;
        do {
            System.out.print("Choose Transaction Type (1 for INCOME, 2 for EXPENSE): ");
            while (!scanner.hasNextInt()) {
                System.out.print("Choose Transaction Type (1 for INCOME, 2 for EXPENSE): ");
                scanner.next(); // Read and discard the invalid input
            }
            transactionTypeOption = scanner.nextInt();
        } while (transactionTypeOption != 1 && transactionTypeOption != 2);
        Transaction.TransactionType transactionType = null;
        if (transactionTypeOption == 1) {
            transactionType = Transaction.TransactionType.INCOME;
        } else if (transactionTypeOption == 2) {
            transactionType = Transaction.TransactionType.EXPENSE;
        }

        // Enter Category
        System.out.println("Available Categories:");
        List<Category> categories = financialFacade.getAllCategories();
        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + ". " + categories.get(i).getCategoryName());
        }

        if (categories.isEmpty()) {
            System.out.println("Category not found. Please create category first!");
            return;
        }

        int categoryIndex;
        do {
            System.out.print("Choose Category (1-" + categories.size() + "): ");
            while (!scanner.hasNextInt()) {
                System.out.print("Choose Category (1-" + categories.size() + "): ");
                scanner.next(); // Read and discard the invalid input
            }
            categoryIndex = scanner.nextInt();
        } while (categoryIndex < 1 || categoryIndex > categories.size());
        Category category = new Category("", categories.get(categoryIndex - 1).getCategoryName());

        // Enter Date
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateFormat.setLenient(false); // Set lenient to false to ensure strict date parsing
        @SuppressWarnings("unused")
        Date parsedDate = null;
        boolean isValidDate = false;
        String dateInput = "";
        while (!isValidDate) {
            System.out.print("Enter a date in DD-MM-YYYY format (Ex. 8-3-2024): ");
            dateInput = scanner.next();
            try {
                // Parse the date string
                parsedDate = dateFormat.parse(dateInput);
                isValidDate = true; // If parsing successful, mark the date as valid
            } catch (ParseException e) {
                System.out.println(
                        "Invalid date format. Please enter the date in DD-MM-YYYY format.");
            }
        }

        // Enter Amount
        boolean isValidInput = false;
        String amountString = "";
        while (!isValidInput) {
            System.out.print("Enter an amount (Ex. 100.50): ");
            amountString = scanner.next();

            if (isValidMoneyFormat(amountString)) {
                isValidInput = true;
            } else {
                System.out.println("Invalid input. Please enter a valid amount.");
            }
        }
        Double amount = Double.parseDouble(amountString);

        // Enter Description
        String description = "";
        while (description.isEmpty()) {
            System.out.print("Enter the description: ");
            description = scanner.next();

            if (description.isEmpty()) {
                System.out.println("Description cannot be empty. Please enter a description.");
            }
        }

        // Create the new transaction using the user inputs
        Transaction newTransaction = new Transaction(String.valueOf(transactionID), transactionType, category,
                dateInput,
                amount, description);
        financialFacade.manageTransactions("add", null, newTransaction, null);
    }

    private void displayUpdateTransactionMenu(FinancialFacade financialFacade, Scanner scanner) {
        int lineNumber = financialFacade.getAllTransactions().size();
        System.out.print("Enter Transaction ID to update (1-" + lineNumber + "): ");
        String transactionIDToUpdate = scanner.next();
        Transaction transactionToUpdate = financialFacade.getSpecificTransactionById(transactionIDToUpdate);
        if (transactionToUpdate == null) {
            System.out.println("Invalid transaction ID. Please try again.");
            displayMainMenu(financialFacade);
            return;
        }
        boolean isValidOption = false;
        Transaction oldTransaction;
        Transaction updatedTransaction;
        while (!isValidOption) {
            displaySpecificTransactionTable(financialFacade, transactionIDToUpdate, transactionToUpdate);
            System.out.println("Category Management Menu:");
            System.out.println("1. Update Transaction Type");
            System.out.println("2. Update Category");
            System.out.println("3. Update Date");
            System.out.println("4. Update Amount");
            System.out.println("5. Update Description");
            System.out.println("6. Return to Dashboard");
            System.out.print("Choose an option: ");
            String categoryOption = scanner.next();

            switch (categoryOption) {
                case "1":
                    System.out.println("Available Transaction Types:");
                    System.out.println("1. INCOME");
                    System.out.println("2. EXPENSE");
                    int transactionTypeOption;
                    do {
                        System.out.print("Choose Transaction Type (1 for INCOME, 2 for EXPENSE): ");
                        while (!scanner.hasNextInt()) {
                            System.out.print("Choose Transaction Type (1 for INCOME, 2 for EXPENSE): ");
                            scanner.next(); // Read and discard the invalid input
                        }
                        transactionTypeOption = scanner.nextInt();
                    } while (transactionTypeOption != 1 && transactionTypeOption != 2);
                    Transaction.TransactionType transactionType = null;
                    if (transactionTypeOption == 1) {
                        transactionType = Transaction.TransactionType.INCOME;
                    } else if (transactionTypeOption == 2) {
                        transactionType = Transaction.TransactionType.EXPENSE;
                    }
                    oldTransaction = new Transaction(transactionIDToUpdate);
                    updatedTransaction = new Transaction(transactionIDToUpdate, transactionType, null, null, 0, null);
                    financialFacade.manageTransactions("update", oldTransaction, updatedTransaction, "TransactionType");
                    isValidOption = true;
                    break;
                case "2":
                    // Display available categories
                    System.out.println("Available Categories:");
                    List<Category> categories = financialFacade.getAllCategories();
                    for (int i = 0; i < categories.size(); i++) {
                        System.out.println((i + 1) + ". " + categories.get(i).getCategoryName());
                    }
                    int categoryIndex;
                    do {
                        System.out.print("Choose Category (1-" + categories.size() + "): ");
                        while (!scanner.hasNextInt()) {
                            System.out.print("Choose Category (1-" + categories.size() + "): ");
                            scanner.next(); // Read and discard the invalid input
                        }
                        categoryIndex = scanner.nextInt();
                    } while (categoryIndex < 1 || categoryIndex > categories.size());
                    Category category = new Category("", categories.get(categoryIndex - 1).getCategoryName());
                    oldTransaction = new Transaction(transactionIDToUpdate);
                    updatedTransaction = new Transaction(transactionIDToUpdate, null, category, null, 0, null);
                    financialFacade.manageTransactions("update", oldTransaction, updatedTransaction, "Category");
                    isValidOption = true;
                    break;
                case "3":
                    // Enter Date
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    dateFormat.setLenient(false); // Set lenient to false to ensure strict date parsing
                    @SuppressWarnings("unused")
                    Date parsedDate = null;
                    boolean isValidDate = false;
                    String dateInput = "";
                    while (!isValidDate) {
                        System.out.print("Enter a date in DD-MM-YYYY format (Ex. 8-3-2024): ");
                        dateInput = scanner.next();
                        try {
                            // Parse the date string
                            parsedDate = dateFormat.parse(dateInput);
                            isValidDate = true; // If parsing successful, mark the date as valid
                        } catch (ParseException e) {
                            System.out.println(
                                    "Invalid date format. Please enter the date in DD-MM-YYYY format.");
                        }
                    }
                    oldTransaction = new Transaction(transactionIDToUpdate);
                    updatedTransaction = new Transaction(transactionIDToUpdate, null, null, dateInput, 0, null);
                    financialFacade.manageTransactions("update", oldTransaction, updatedTransaction, "Date");
                    isValidOption = true;
                    break;
                case "4":
                    boolean isValidInput = false;
                    String amountString = "";
                    while (!isValidInput) {
                        System.out.print("Enter an amount (Ex. 100.50): ");
                        amountString = scanner.next();
                        if (isValidMoneyFormat(amountString)) {
                            isValidInput = true;
                        } else {
                            System.out.println("Invalid input. Please enter a valid amount.");
                        }
                    }
                    Double amount = Double.parseDouble(amountString);
                    oldTransaction = new Transaction(transactionIDToUpdate);
                    updatedTransaction = new Transaction(transactionIDToUpdate, null, null, null, amount, null);
                    financialFacade.manageTransactions("update", oldTransaction, updatedTransaction, "Amount");
                    isValidOption = true;
                    break;
                case "5":
                    String description = "";
                    while (description.isEmpty()) {
                        System.out.print("Enter the description: ");
                        description = scanner.next();
                        if (description.isEmpty()) {
                            System.out.println("Description cannot be empty. Please enter a description.");
                        }
                    }
                    oldTransaction = new Transaction(transactionIDToUpdate);
                    updatedTransaction = new Transaction(transactionIDToUpdate, null, null, null, 0, description);
                    financialFacade.manageTransactions("update", oldTransaction, updatedTransaction, "Description");
                    isValidOption = true;
                    break;
                case "6":
                    isValidOption = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        displayMainMenu(financialFacade);
    }

    private void deleteTransaction(FinancialFacade financialFacade, Scanner scanner) {
        int lineNumber = financialFacade.getAllTransactions().size();
        if (lineNumber == 0) {
            System.out.print("There are no transactions to delete!\n");
            return;
        }

        // Get the index of the category to delete
        System.out.print("Enter the ID of the transaction to delete (1-" + lineNumber + "): ");
        while (!scanner.hasNextInt()) {
            System.out.print("Enter the ID of the transaction to delete (1-" + lineNumber + "): ");
            scanner.next(); // Read and discard the invalid input
        }
        int transactionIDToDelete = scanner.nextInt();

        // Validate the category index
        if (transactionIDToDelete < 1 || transactionIDToDelete > lineNumber) {
            System.out.println("ID not found! ");
            return;
        }

        Transaction transactionToDelete = new Transaction(String.valueOf(transactionIDToDelete));
        financialFacade.manageTransactions("delete", transactionToDelete, null, null);
    }

    public void displayCategoriesMenu(FinancialFacade financialFacade) {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean isValidOption = false;

            while (!isValidOption) {
                displayCategoryTable(financialFacade);

                System.out.println("Category Management Menu:");
                System.out.println("1. Add Category");
                System.out.println("2. Update Category");
                System.out.println("3. Delete Category");
                System.out.println("4. Return to Dashboard");
                System.out.print("Choose an option: ");
                String categoryOption = scanner.next();

                switch (categoryOption) {
                    case "1":
                        addCategory(financialFacade, scanner);
                        isValidOption = true;
                        break;
                    case "2":
                        updateCategory(financialFacade, scanner);
                        isValidOption = true;
                        break;
                    case "3":
                        deleteCategory(financialFacade, scanner);
                        isValidOption = true;
                        break;
                    case "4":
                        isValidOption = true;
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
            displayMainMenu(financialFacade);
        }
    }

    private void displayCategoryTable(FinancialFacade financialFacade) {
        // Display list of categories
        List<Category> categories = financialFacade.getAllCategories();
        System.out.printf("\u001B[34m"); // ANSI escape code for blue color
        System.out.printf("+-------------------+%n");
        System.out.printf("|  Category Table   |%n");
        System.out.printf("+----+--------------+%n");
        System.out.printf("| ID | CategoryName |%n");
        System.out.printf("+----+--------------+%n");
        for (Category category : categories) {
            System.out.printf("| %-3s| %-13s|%n", category.getCategoryID(), category.getCategoryName());
        }
        System.out.printf("+----+--------------+%n");
        System.out.printf("\u001B[0m"); // Reset color
    }

    private void addCategory(FinancialFacade financialFacade, Scanner scanner) {
        System.out.print("Enter the name of the category to add: ");
        String newCategoryName = scanner.next();
        Category newCategory = new Category(null, newCategoryName);
        financialFacade.manageCategories("add", null, newCategory);
    }

    private void updateCategory(FinancialFacade financialFacade, Scanner scanner) {
        List<Transaction> transactions = financialFacade.getAllTransactions();
        List<Category> categories = financialFacade.getAllCategories();
        int lineNumber = categories.size();
        if (lineNumber == 0) {
            System.out.print("There are no categories to modify!\n");
            return;
        }
        boolean categoryExist = false;
        String categoryIDToUpdate = "";
        while (!categoryExist) {
            System.out.print("Enter the ID of the category to update (1-" + lineNumber + "): ");
            categoryIDToUpdate = scanner.next();

            // Get the category to delete
            Category categoryToUpdate = categories.get(Integer.parseInt(categoryIDToUpdate) - 1);

            // Check if the category is associated with any transactions
            boolean categoryUsedInTransactions = transactions.stream()
                    .anyMatch(transaction -> transaction.getCategory().getCategoryName()
                            .equals(categoryToUpdate.getCategoryName()));

            // If the category is used in transactions, do not allow deletion
            if (categoryUsedInTransactions) {
                System.out.println("Fail to modify. It is associated with transactions!");
                return;
            }

            // Check if categoryInput is numeric
            if (!categoryIDToUpdate.matches("\\d+")) {
                System.out.println("Only Numeric allow");
                continue; // Continue the loop if input is not a number
            }
            // Convert categoryInput to integer
            int categoryId = Integer.parseInt(categoryIDToUpdate);
            // Check if categoryId is within the valid range
            if (categoryId <= 0 || categoryId > lineNumber) {
                System.out
                        .println("Out of range");
                continue; // Continue the loop if input is out of range
            }
            categoryExist = true;
        }

        Category oldCategory = new Category(categoryIDToUpdate, null);
        System.out.print("Enter the new name of the category: ");
        String updatedCategoryName = scanner.next();
        System.out.println("Category updated successfully!");
        Category updatedCategory = new Category(categoryIDToUpdate, updatedCategoryName);
        financialFacade.manageCategories("update", oldCategory, updatedCategory);
    }

    private void deleteCategory(FinancialFacade financialFacade, Scanner scanner) {
        List<Transaction> transactions = financialFacade.getAllTransactions();
        List<Category> categories = financialFacade.getAllCategories();

        if (categories.isEmpty()) {
            System.out.println("There are no categories to delete!");
            return;
        }

        // Display available categories
        System.out.println("Available Categories:");
        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + ". " + categories.get(i).getCategoryName());
        }

        // Get the index of the category to delete
        System.out.print("Choose the category to delete (1-" + categories.size() + "): ");
        while (!scanner.hasNextInt()) {
            System.out.print("Choose the category to delete (1-" + categories.size() + "): ");
            scanner.next(); // Read and discard the invalid input
        }
        int categoryIDToDelete = scanner.nextInt();

        // Validate the category index
        if (categoryIDToDelete < 1 || categoryIDToDelete > categories.size()) {
            System.out.println("ID not found! ");
            return;
        }

        // Get the category to delete
        Category categoryToDelete = categories.get(categoryIDToDelete - 1);

        // Check if the category is associated with any transactions
        boolean categoryUsedInTransactions = transactions.stream()
                .anyMatch(transaction -> transaction.getCategory().getCategoryName()
                        .equals(categoryToDelete.getCategoryName()));

        // If the category is used in transactions, do not allow deletion
        if (categoryUsedInTransactions) {
            System.out.println("Fail to delete. It is associated with transactions!");
        } else {
            // Delete the category
            financialFacade.manageCategories("delete", categoryToDelete, null);
            System.out.println("Category deleted successfully.");
        }
    }

    // Function to validate money format input using regular expression
    public static boolean isValidMoneyFormat(String str) {
        // Regular expression to match money format (e.g., 100.50)
        String moneyRegex = "^\\d+(\\.\\d{1,2})?$";
        return str.matches(moneyRegex);
    }
}