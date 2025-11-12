package service;

import model.Transaction;
import java.io.*;
import java.util.ArrayList;

public class TransactionService {
    private ArrayList<Transaction> transactions = new ArrayList<>();
    private final String filePath = "transactions.csv";

    public TransactionService() {
        loadTransactions();
    }

    public void addTransaction(Transaction t) {
        transactions.add(t);
        saveTransactions();
    }

    public void removeTransaction(int index) {
        transactions.remove(index);
        saveTransactions();
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    private void saveTransactions() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {
            for (Transaction t : transactions) {
                pw.println(t.getType() + "," + t.getCategory() + "," + t.getDescription() + "," + t.getAmount());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadTransactions() {
        File file = new File(filePath);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    transactions.add(new Transaction(parts[0], parts[1], parts[2], Double.parseDouble(parts[3])));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double getTotalIncome() {
        return transactions.stream().filter(t -> t.getType().equalsIgnoreCase("Income")).mapToDouble(t -> t.getAmount()).sum();
    }

    public double getTotalExpense() {
        return transactions.stream().filter(t -> t.getType().equalsIgnoreCase("Expense")).mapToDouble(t -> t.getAmount()).sum();
    }
}
