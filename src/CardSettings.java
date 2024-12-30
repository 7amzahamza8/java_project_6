import javax.swing.*;
import java.util.Map;

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
