package qiu.grocerymanagementsystem.grocerymanagementsystem;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        MySQL mySQL = new MySQL();
        mySQL.connectDB();
        mySQL.checkAndCreateAdmin();
        JFrame login = new Login(mySQL);
        login.setLocationRelativeTo(null);
        login.setVisible(true);

    }
}
