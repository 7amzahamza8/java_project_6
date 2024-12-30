import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.*;

public class Main {
    private static final Color BG_COLOR = Color.BLACK;
    private static final Color FG_COLOR = Color.CYAN;

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/Atm_system?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        String user = "root";
        String password = "sql123";
        DatabaseConnection dbConnection = new DatabaseConnection(url, user, password);

        // Login Screen
        JFrame loginFrame = createFrame("ATM System - Login", 400, 300);

        JLabel cardLabel = createLabel("Card Number:");
        JTextField cardField = createTextField();
        JLabel passwordLabel = createLabel("Password:");
        JPasswordField passwordField = createPasswordField();
        JButton loginButton = createButton("Login");

        loginFrame.setLayout(new GridLayout(3, 2));
        loginFrame.add(cardLabel);
        loginFrame.add(cardField);
        loginFrame.add(passwordLabel);
        loginFrame.add(passwordField);
        loginFrame.add(new JLabel()); // Empty Cell
        loginFrame.add(loginButton);

        loginButton.addActionListener(e -> {
            try {
                long cardNumber = Long.parseLong(cardField.getText());
                int passwordInput = Integer.parseInt(new String(passwordField.getPassword()));

                if (dbConnection.validateCredentials(cardNumber, passwordInput)) {
                    dbConnection.setActiveUser(cardNumber);
                    JOptionPane.showMessageDialog(loginFrame, "Login Successful!");
                    loginFrame.dispose();
                    showOperationsMenu(dbConnection);
                } else {
                    JOptionPane.showMessageDialog(loginFrame, "Invalid Credentials. Please Try Again.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(loginFrame, "Invalid Input. Please Try Again.");
            }
        });

        loginFrame.setVisible(true);
    }

    private static void showOperationsMenu(DatabaseConnection dbConnection) {
        JFrame operationsFrame = createFrame("ATM System - Operations", 400, 300);

        JButton depositButton = createButton("Deposit");
        JButton withdrawButton = createButton("Withdraw");
        JButton balanceButton = createButton("Check Balance");
        JButton transferButton = createButton("Transfer Money");
        JButton changePasswordButton = createButton("Change Password");

        operationsFrame.setLayout(new GridLayout(3, 2));
        operationsFrame.add(depositButton);
        operationsFrame.add(withdrawButton);
        operationsFrame.add(balanceButton);
        operationsFrame.add(transferButton);
        operationsFrame.add(changePasswordButton);

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

        depositFrame.setLayout(new GridLayout(3, 1));
        depositFrame.add(amountLabel);
        depositFrame.add(amountField);
        depositFrame.add(submitButton);

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

        withdrawFrame.setLayout(new GridLayout(3, 1));
        withdrawFrame.add(amountLabel);
        withdrawFrame.add(amountField);
        withdrawFrame.add(submitButton);

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

        transferFrame.setLayout(new GridLayout(4, 1));
        transferFrame.add(recipientLabel);
        transferFrame.add(recipientField);
        transferFrame.add(amountLabel);
        transferFrame.add(amountField);
        transferFrame.add(submitButton);

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

        changePasswordFrame.setLayout(new GridLayout(3, 1));
        changePasswordFrame.add(newPasswordLabel);
        changePasswordFrame.add(newPasswordField);
        changePasswordFrame.add(submitButton);

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

    // Utility methods for creating UI components with the black-and-cyan theme
    private static JFrame createFrame(String title, int width, int height) {
        JFrame frame = new JFrame(title);
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setBackground(BG_COLOR);
        return frame;
    }

    private static JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(FG_COLOR);
        return label;
    }

    private static JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setBackground(BG_COLOR);
        textField.setForeground(FG_COLOR);
        return textField;
    }

    private static JPasswordField createPasswordField() {
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBackground(BG_COLOR);
        passwordField.setForeground(FG_COLOR);
        return passwordField;
    }

    private static JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(FG_COLOR);
        button.setForeground(BG_COLOR);
        return button;
    }
}


class DatabaseConnection {
    private String url;
    private String user;
    private String password;
    private Connection connection;
    private Long activeUser;

    public DatabaseConnection(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;

        try {
            this.connection = DriverManager.getConnection(url, user, password);
            System.out.println("Database connection established.");
        } catch (SQLException e) {
            System.out.println("Failed to connect to database: " + e.getMessage());
        }
    }

    public void setActiveUser(long cardNumber) {
        this.activeUser = cardNumber;
    }

    public Long getActiveUser() {
        return this.activeUser;
    }

    public boolean validateCredentials(long cardNumber, int password) {
        String sql = "SELECT COUNT(*) AS count FROM Credit_Card WHERE credit_card_number = ? AND password = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, cardNumber);
            statement.setInt(2, password);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next() && resultSet.getInt("count") > 0;
        } catch (SQLException e) {
            System.out.println("Validation error: " + e.getMessage());
        }
        return false;
    }

    public boolean deposit(long cardNumber, int amount) {
        String sql = "UPDATE Credit_Card SET balance = balance + ? WHERE credit_card_number = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, amount);
            statement.setLong(2, cardNumber);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Deposit error: " + e.getMessage());
        }
        return false;
    }

    public boolean withdraw(long cardNumber, int amount) {
        String sql = "UPDATE Credit_Card SET balance = balance - ? WHERE credit_card_number = ? AND balance >= ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, amount);
            statement.setLong(2, cardNumber);
            statement.setInt(3, amount);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Withdraw error: " + e.getMessage());
        }
        return false;
    }

    public int checkBalance(long cardNumber) {
        String sql = "SELECT balance FROM Credit_Card WHERE credit_card_number = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, cardNumber);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("balance");
            }
        } catch (SQLException e) {
            System.out.println("Check balance error: " + e.getMessage());
        }
        return -1;
    }

    public boolean transferMoney(long senderCardNumber, long recipientCardNumber, int amount) {
        if (withdraw(senderCardNumber, amount)) {
            String sql = "UPDATE Credit_Card SET balance = balance + ? WHERE credit_card_number = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, amount);
                statement.setLong(2, recipientCardNumber);
                return statement.executeUpdate() > 0;
            } catch (SQLException e) {
                System.out.println("Transfer error: " + e.getMessage());
            }
        }
        return false;
    }

    public boolean changePassword(long cardNumber, int newPassword) {
        String sql = "UPDATE Credit_Card SET password = ? WHERE credit_card_number = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, newPassword);
            statement.setLong(2, cardNumber);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Change password error: " + e.getMessage());
        }
        return false;
    }
}
