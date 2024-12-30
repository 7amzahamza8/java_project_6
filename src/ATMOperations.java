import javax.swing.*;
import java.awt.*;

public class ATMOperations {

    public static void showOperationsMenu(DatabaseConnection dbConnection) {
        JFrame operationsFrame = createFrame("ATM System - Operations", 400, 300);

        JButton depositButton = createButton("Deposit");
        JButton withdrawButton = createButton("Withdraw");
        JButton balanceButton = createButton("Check Balance");
        JButton transferButton = createButton("Transfer Money");
        JButton changePasswordButton = createButton("Change Password");

        operationsFrame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(15, 15, 15, 15);

        gbc.gridx = 0;
        gbc.gridy = 0;
        operationsFrame.add(depositButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        operationsFrame.add(withdrawButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        operationsFrame.add(balanceButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        operationsFrame.add(transferButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        operationsFrame.add(changePasswordButton, gbc);

        depositButton.addActionListener(e -> showDepositScreen(dbConnection));
        withdrawButton.addActionListener(e -> showWithdrawScreen(dbConnection));
        balanceButton.addActionListener(e -> showCheckBalanceScreen(dbConnection));
        transferButton.addActionListener(e -> showTransferScreen(dbConnection));
        changePasswordButton.addActionListener(e -> showChangePasswordScreen(dbConnection));

        operationsFrame.setVisible(true);
    }

    private static void showDepositScreen(DatabaseConnection dbConnection) {
        JFrame depositFrame = createFrame("Deposit", 400, 200);

        JLabel amountLabel = createLabel("Enter Amount:");
        JTextField amountField = createTextField();
        JButton submitButton = createButton("Submit");

        depositFrame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        depositFrame.add(amountLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        depositFrame.add(amountField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        depositFrame.add(submitButton, gbc);

        submitButton.addActionListener(e -> {
            try {
                int amount = Integer.parseInt(amountField.getText());
                if (dbConnection.deposit(dbConnection.getActiveUser(), amount)) {
                    JOptionPane.showMessageDialog(depositFrame, "Deposit Successful!");
                } else {
                    JOptionPane.showMessageDialog(depositFrame, "Deposit Failed. Please Try Again.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(depositFrame, "Invalid Amount.");
            }
        });

        depositFrame.setVisible(true);
    }

    private static void showWithdrawScreen(DatabaseConnection dbConnection) {
        JFrame withdrawFrame = createFrame("Withdraw", 400, 200);

        JLabel amountLabel = createLabel("Enter Amount:");
        JTextField amountField = createTextField();
        JButton submitButton = createButton("Submit");

        withdrawFrame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        withdrawFrame.add(amountLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        withdrawFrame.add(amountField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        withdrawFrame.add(submitButton, gbc);

        submitButton.addActionListener(e -> {
            try {
                int amount = Integer.parseInt(amountField.getText());
                if (dbConnection.withdraw(dbConnection.getActiveUser(), amount)) {
                    JOptionPane.showMessageDialog(withdrawFrame, "Withdrawal Successful!");
                } else {
                    JOptionPane.showMessageDialog(withdrawFrame, "Insufficient Balance or Error.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(withdrawFrame, "Invalid Amount.");
            }
        });

        withdrawFrame.setVisible(true);
    }

    private static void showCheckBalanceScreen(DatabaseConnection dbConnection) {
        int balance = dbConnection.checkBalance(dbConnection.getActiveUser());
        JOptionPane.showMessageDialog(null, "Your Balance is: " + balance);
    }

    private static void showTransferScreen(DatabaseConnection dbConnection) {
        JFrame transferFrame = createFrame("Transfer Money", 400, 300);

        JLabel recipientLabel = createLabel("Recipient Card Number:");
        JTextField recipientField = createTextField();
        JLabel amountLabel = createLabel("Enter Amount:");
        JTextField amountField = createTextField();
        JButton submitButton = createButton("Submit");

        transferFrame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        transferFrame.add(recipientLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        transferFrame.add(recipientField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        transferFrame.add(amountLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        transferFrame.add(amountField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        transferFrame.add(submitButton, gbc);

        submitButton.addActionListener(e -> {
            try {
                long recipientCard = Long.parseLong(recipientField.getText());
                int amount = Integer.parseInt(amountField.getText());
                if (dbConnection.transferMoney(dbConnection.getActiveUser(), recipientCard, amount)) {
                    JOptionPane.showMessageDialog(transferFrame, "Transfer Successful!");
                } else {
                    JOptionPane.showMessageDialog(transferFrame, "Transfer Failed.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(transferFrame, "Invalid Input.");
            }
        });

        transferFrame.setVisible(true);
    }

    private static void showChangePasswordScreen(DatabaseConnection dbConnection) {
        JFrame changePasswordFrame = createFrame("Change Password", 400, 200);

        JLabel newPasswordLabel = createLabel("Enter New Password:");
        JPasswordField newPasswordField = createPasswordField();
        JButton submitButton = createButton("Submit");

        changePasswordFrame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        changePasswordFrame.add(newPasswordLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        changePasswordFrame.add(newPasswordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        changePasswordFrame.add(submitButton, gbc);

        submitButton.addActionListener(e -> {
            try {
                int newPassword = Integer.parseInt(new String(newPasswordField.getPassword()));
                if (dbConnection.changePassword(dbConnection.getActiveUser(), newPassword)) {
                    JOptionPane.showMessageDialog(changePasswordFrame, "Password Changed Successfully!");
                } else {
                    JOptionPane.showMessageDialog(changePasswordFrame, "Password Change Failed.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(changePasswordFrame, "Invalid Password.");
            }
        });

        changePasswordFrame.setVisible(true);
    }

    // Utility methods for creating UI components with white and blue theme
    private static JFrame createFrame(String title, int width, int height) {
        JFrame frame = new JFrame(title);
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(240, 248, 255)); // Light blue background
        return frame;
    }

    private static JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 14)); // Set font to bold for readability
        label.setForeground(new Color(50, 50, 150)); // Dark blue color
        return label;
    }

    private static JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setBackground(new Color(255, 250, 250)); // Very light pink background for the field
        textField.setForeground(new Color(0, 0, 0)); // Black text
        textField.setPreferredSize(new Dimension(200, 30));
        textField.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 255), 2)); // Light blue border
        return textField;
    }

    private static JPasswordField createPasswordField() {
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBackground(new Color(255, 250, 250));
        passwordField.setForeground(new Color(0, 0, 0)); // Black text
        passwordField.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 255), 2));
        return passwordField;
    }

    private static JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(70, 130, 180)); // Steel blue background
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 150), 2)); // Dark blue border
        button.setPreferredSize(new Dimension(150, 40)); // Bigger button for better user interaction

        // Adding hover effect
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setFocusPainted(false);
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(100, 149, 237)); // Lighter blue when hovered
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(70, 130, 180)); // Original blue when not hovered
            }
        });

        return button;
    }
}
