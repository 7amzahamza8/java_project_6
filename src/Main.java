import javax.swing.*;
import java.awt.*;

public class Main {
    static final Color BG_COLOR = new Color(240, 248, 255);  // Light blue background
    static final Color FG_COLOR = new Color(50, 50, 150);   // Dark blue for foreground (text and labels)
    static final Color BUTTON_BG_COLOR = new Color(70, 130, 180); // Steel blue background for buttons
    static final Color BUTTON_FG_COLOR = Color.WHITE; // White text for buttons

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/Atm_system?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        String user = "root";
        String password = "sql123";
        DatabaseConnection dbConnection = new DatabaseConnection(url, user, password);


        JFrame loginFrame = createFrame("ATM System - Login", 400, 300);

        JLabel cardLabel = createLabel("    Card Number:");
        JTextField cardField = createTextField();
        JLabel passwordLabel = createLabel("    Password:");
        JPasswordField passwordField = createPasswordField();
        JButton loginButton = createButton("Login");

        loginFrame.setLayout(new GridLayout(3, 2));
        loginFrame.add(cardLabel);
        loginFrame.add(cardField);
        loginFrame.add(passwordLabel);
        loginFrame.add(passwordField);
        loginFrame.add(new JLabel());
        loginFrame.add(loginButton);

        loginButton.addActionListener(e -> {
            try {
                long cardNumber = Long.parseLong(cardField.getText());
                int passwordInput = Integer.parseInt(new String(passwordField.getPassword()));

                if (dbConnection.validateCredentials(cardNumber, passwordInput)) {
                    dbConnection.setActiveUser(cardNumber);
                    JOptionPane.showMessageDialog(loginFrame, "Login Successful!");
                    loginFrame.dispose();
                    ATMOperations.showOperationsMenu(dbConnection);
                } else {
                    JOptionPane.showMessageDialog(loginFrame, "Invalid Credentials. Please Try Again.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(loginFrame, "Invalid Input. Please Try Again.");
            }
        });

        loginFrame.setVisible(true);
    }

    private static JFrame createFrame(String title, int width, int height) {
        JFrame frame = new JFrame(title);
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setBackground(BG_COLOR);  // Set the background color to light blue
        return frame;
    }

    private static JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 14));  // Set font to bold for readability
        label.setForeground(FG_COLOR);  // Dark blue text color
        return label;
    }

    private static JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setBackground(Color.WHITE);  // White background for input fields
        textField.setForeground(FG_COLOR);  // Dark blue text color
        textField.setPreferredSize(new Dimension(200, 30));
        textField.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 255), 2));  // Light blue border
        return textField;
    }

    private static JPasswordField createPasswordField() {
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBackground(Color.WHITE);  // White background
        passwordField.setForeground(FG_COLOR);  // Dark blue text color
        passwordField.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 255), 2));  // Light blue border
        return passwordField;
    }

    private static JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));  // Bold font
        button.setBackground(BUTTON_BG_COLOR);  // Steel blue background for the button
        button.setForeground(BUTTON_FG_COLOR);  // White text
        button.setBorder(BorderFactory.createLineBorder(FG_COLOR, 2));  // Dark blue border

        // Adding hover effect
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setFocusPainted(false);
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(100, 149, 237));  // Light blue when hovered
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_BG_COLOR);  // Original blue when not hovered
            }
        });

        return button;
    }
}
