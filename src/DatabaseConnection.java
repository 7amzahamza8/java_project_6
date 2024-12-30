import java.sql.*;

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
        }catch (SQLException e) {
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
