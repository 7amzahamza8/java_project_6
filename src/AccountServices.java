import javax.swing.*;

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
