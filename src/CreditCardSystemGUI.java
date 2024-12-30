import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class CreditCardSystemGUI {
    private JFrame frame;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    private JPanel authPanel;
    private JTextField cardNumberField;
    private JPasswordField passwordField;
    private JLabel authMessageLabel;

    private JPanel menuPanel;
    private JLabel balanceLabel;

    private AccountServices accountServices;
    private CardSettings cardSettings;
    private Map<String, Integer> userData;
    private String currentLanguage;

    public CreditCardSystemGUI() {
        initializeData();
        initializeGUI();
    }

    private void initializeData() {
        userData = new HashMap<>();
        userData.put("jo", 852003);
        userData.put("homiza", 2008);
        userData.put("nour", 2007);
        userData.put("malak", 2009);
        accountServices = new AccountServices(1000.0);
        cardSettings = new CardSettings();
    }

    private void initializeGUI() {
        frame = new JFrame("Credit Card System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        mainPanel = new JPanel();
        cardLayout = new CardLayout();
        mainPanel.setLayout(cardLayout);

        createAuthPanel();
        createMenuPanel();

        mainPanel.add(authPanel, "AuthPanel");
        mainPanel.add(menuPanel, "MenuPanel");

        frame.add(mainPanel);
        frame.setVisible(true);

        cardLayout.show(mainPanel, "AuthPanel");
    }

    private void createAuthPanel() {
        authPanel = new JPanel();
        authPanel.setLayout(new GridLayout(5, 1, 10, 10));

        JLabel languageLabel = new JLabel("Select Language:");
        JComboBox<String> languageComboBox = new JComboBox<>(new String[]{"English", "Arabic"});
        languageComboBox.addActionListener(e -> currentLanguage = (String) languageComboBox.getSelectedItem());
        currentLanguage = "English";

        JLabel cardNumberLabel = new JLabel("Card Number:");
        cardNumberField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

        JButton loginButton = new JButton("Login");
        authMessageLabel = new JLabel("", SwingConstants.CENTER);

        loginButton.addActionListener(e -> authenticateUser());

        authPanel.add(languageLabel);
        authPanel.add(languageComboBox);
        authPanel.add(cardNumberLabel);
        authPanel.add(cardNumberField);
        authPanel.add(passwordLabel);
        authPanel.add(passwordField);
        authPanel.add(loginButton);
        authPanel.add(authMessageLabel);
    }

    private void createMenuPanel() {
        menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(6, 1, 10, 10));

        JButton checkBalanceButton = new JButton("Check Balance");
        JButton depositFundsButton = new JButton("Deposit Funds");
        JButton withdrawFundsButton = new JButton("Withdraw Funds");
        JButton changePasswordButton = new JButton("Change Password");
        JButton exitButton = new JButton("Exit");

        balanceLabel = new JLabel("", SwingConstants.CENTER);

        checkBalanceButton.addActionListener(e -> accountServices.checkBalance(currentLanguage));
        depositFundsButton.addActionListener(e -> accountServices.depositFunds(currentLanguage));
        withdrawFundsButton.addActionListener(e -> accountServices.withdrawFunds(currentLanguage));
        changePasswordButton.addActionListener(e -> cardSettings.changePassword(userData, currentLanguage));
        exitButton.addActionListener(e -> System.exit(0));

        menuPanel.add(balanceLabel);
        menuPanel.add(checkBalanceButton);
        menuPanel.add(depositFundsButton);
        menuPanel.add(withdrawFundsButton);
        menuPanel.add(changePasswordButton);
        menuPanel.add(exitButton);
    }

    private void authenticateUser() {
        String cardNumber = cardNumberField.getText();
        String password = new String(passwordField.getPassword());

        if (!userData.containsKey(cardNumber) || !userData.get(cardNumber).equals(Integer.parseInt(password))) {
            authMessageLabel.setText(currentLanguage.equals("Arabic") ? "فشل المصادقة" : "Authentication Failed");
        } else {
            authMessageLabel.setText("");
            cardLayout.show(mainPanel, "MenuPanel");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CreditCardSystemGUI::new);
    }
}

class AccountServices {
    private double balance;

    public AccountServices(double initialBalance) {
        this.balance = initialBalance;
    }

    public void checkBalance(String language) {
        JOptionPane.showMessageDialog(null, language.equals("Arabic") ?
                "رصيدك الحالي هو: " + balance : "Your current balance is: " + balance);
    }

    public void depositFunds(String language) {
        String amountStr = JOptionPane.showInputDialog(null, language.equals("Arabic") ? "أدخل المبلغ للإيداع:" : "Enter amount to deposit:");
        try {
            double amount = Double.parseDouble(amountStr);
            if (amount > 0) {
                balance += amount;
                JOptionPane.showMessageDialog(null, language.equals("Arabic") ? "تم إيداع المبلغ بنجاح" : "Amount deposited successfully");
            } else {
                JOptionPane.showMessageDialog(null, language.equals("Arabic") ? "مبلغ غير صالح" : "Invalid amount");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, language.equals("Arabic") ? "مبلغ غير صالح" : "Invalid amount");
        }
    }

    public void withdrawFunds(String language) {
        String amountStr = JOptionPane.showInputDialog(null, language.equals("Arabic") ? "أدخل المبلغ للسحب:" : "Enter amount to withdraw:");
        try {
            double amount = Double.parseDouble(amountStr);
            if (amount > 0 && amount <= balance) {
                balance -= amount;
                JOptionPane.showMessageDialog(null, language.equals("Arabic") ? "تم السحب بنجاح" : "Withdrawal successful");
            } else {
                JOptionPane.showMessageDialog(null, language.equals("Arabic") ? "رصيد غير كافٍ" : "Insufficient balance");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, language.equals("Arabic") ? "مبلغ غير صالح" : "Invalid amount");
        }
    }
}

class CardSettings {
    public void changePassword(Map<String, Integer> userData, String language) {
        String cardNumber = JOptionPane.showInputDialog(null, language.equals("Arabic") ? "أدخل رقم البطاقة:" : "Enter card number:");
        if (!userData.containsKey(cardNumber)) {
            JOptionPane.showMessageDialog(null, language.equals("Arabic") ? "رقم البطاقة غير موجود" : "Card number not found");
            return;
        }

        String oldPasswordStr = JOptionPane.showInputDialog(null, language.equals("Arabic") ? "أدخل كلمة المرور القديمة:" : "Enter old password:");
        int oldPassword = Integer.parseInt(oldPasswordStr);
        if (userData.get(cardNumber) != oldPassword) {
            JOptionPane.showMessageDialog(null, language.equals("Arabic") ? "كلمة المرور القديمة غير صحيحة" : "Incorrect old password");
            return;
        }

        String newPasswordStr = JOptionPane.showInputDialog(null, language.equals("Arabic") ? "أدخل كلمة المرور الجديدة:" : "Enter new password:");
        int newPassword = Integer.parseInt(newPasswordStr);
        userData.put(cardNumber, newPassword);
        JOptionPane.showMessageDialog(null, language.equals("Arabic") ? "تم تغيير كلمة المرور بنجاح" : "Password changed successfully");
    }
}
