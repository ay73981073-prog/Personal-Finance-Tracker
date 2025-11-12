package ui;

import model.Transaction;
import service.TransactionService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FinanceTrackerUI {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;
    private TransactionService service;

    public FinanceTrackerUI() {
        service = new TransactionService();
        initialize();
        loadTableData();
    }

    private void initialize() {
        frame = new JFrame("Personal Finance Tracker");
        frame.setSize(700, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new Object[]{"Type", "Category", "Description", "Amount"}, 0);
        table = new JTable(tableModel);
        frame.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel panel = new JPanel();
        JButton addBtn = new JButton("Add Transaction");
        JButton deleteBtn = new JButton("Delete Transaction");
        JButton summaryBtn = new JButton("Show Summary");

        addBtn.addActionListener(e -> showAddDialog());
        deleteBtn.addActionListener(e -> deleteSelected());
        summaryBtn.addActionListener(e -> showSummary());

        panel.add(addBtn);
        panel.add(deleteBtn);
        panel.add(summaryBtn);
        frame.add(panel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void loadTableData() {
        tableModel.setRowCount(0);
        for (Transaction t : service.getTransactions()) {
            tableModel.addRow(new Object[]{t.getType(), t.getCategory(), t.getDescription(), t.getAmount()});
        }
    }

    private void showAddDialog() {
        JTextField typeField = new JTextField();
        JTextField categoryField = new JTextField();
        JTextField descField = new JTextField();
        JTextField amountField = new JTextField();

        Object[] message = {
                "Type (Income/Expense):", typeField,
                "Category:", categoryField,
                "Description:", descField,
                "Amount:", amountField
        };

        int option = JOptionPane.showConfirmDialog(frame, message, "Add Transaction", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                Transaction t = new Transaction(
                        typeField.getText(),
                        categoryField.getText(),
                        descField.getText(),
                        Double.parseDouble(amountField.getText())
                );
                service.addTransaction(t);
                loadTableData();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid amount entered!");
            }
        }
    }

    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            service.removeTransaction(row);
            loadTableData();
        } else {
            JOptionPane.showMessageDialog(frame, "Select a transaction to delete!");
        }
    }

    private void showSummary() {
        double income = service.getTotalIncome();
        double expense = service.getTotalExpense();
        double balance = income - expense;
        JOptionPane.showMessageDialog(frame, "Total Income: " + income +
                "\nTotal Expense: " + expense + "\nBalance: " + balance);
    }
}
