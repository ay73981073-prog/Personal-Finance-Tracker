package model;

public class Transaction {
    private String type; // Income or Expense
    private String category;
    private String description;
    private double amount;

    public Transaction(String type, String category, String description, double amount) {
        this.type = type;
        this.category = category;
        this.description = description;
        this.amount = amount;
    }

    public String getType() { return type; }
    public String getCategory() { return category; }
    public String getDescription() { return description; }
    public double getAmount() { return amount; }

    public void setType(String type) { this.type = type; }
    public void setCategory(String category) { this.category = category; }
    public void setDescription(String description) { this.description = description; }
    public void setAmount(double amount) { this.amount = amount; }
}
