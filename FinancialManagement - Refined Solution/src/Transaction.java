import java.util.Objects;

public class Transaction {
    public enum TransactionType {
        INCOME, EXPENSE
    }

    private String transactionID;
    private TransactionType transactionType;
    private Category category;
    private String date;
    private double amount;
    private String description;

    public Transaction(String transactionID, Transaction.TransactionType transactionType, Category category,
            String date, double amount, String description) {
        this.transactionID = transactionID;
        this.transactionType = transactionType;
        this.category = category;
        this.date = date;
        this.amount = amount;
        this.description = description;
    }
  
    public Transaction(String transactionID) {
        this.transactionID = transactionID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Transaction transaction = (Transaction) o;
        return Objects.equals(transactionID, transaction.transactionID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionID);
    }

    public static Transaction fromString(String line) {
        String[] parts = line.split(",");
        if (parts.length == 6) {
            Transaction.TransactionType transactionType = Transaction.TransactionType.valueOf(parts[1].trim());
            Category category = new Category(null, parts[2].trim()); // Assuming Category class has a constructor that accepts category name
            double amount = Double.parseDouble(parts[4].trim());
            return new Transaction(parts[0].trim(), transactionType, category, parts[3].trim(), amount, parts[5].trim());
        }
        // Return null or throw an exception if parsing fails
        return null;
    }

    @Override
    public String toString() {
        return "," + getTransactionType() + "," + getCategory().getCategoryName() + "," + getDate() + "," + getAmount() + "," + getDescription();
    }

    public String getTransactionID() {
        return transactionID;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}