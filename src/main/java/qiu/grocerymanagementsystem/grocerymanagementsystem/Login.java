package qiu.grocerymanagementsystem.grocerymanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton loginButton;
    private JButton resetButton;
    private JPanel mainpanel;
    private JLabel errorText;


    public Login(MySQL mySQL)  {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainpanel);
        this.setLocationRelativeTo(null);
        this.pack();

        loginButton.addActionListener(e -> {
            try {
                String userid = textField1.getText();
                String password = passwordField1.getText();
                String username = mySQL.checkLogin(userid, password);
                if(!username.equals("err")){
                    this.setVisible(false);
                    JFrame home = new Home(username, mySQL);
                    home.setVisible(true);
                    home.setExtendedState(Frame.MAXIMIZED_BOTH);
                }else {
                    errorText.setText("Wrong password or userid does not exist!");
                }
            }catch (Exception e2){
                e2.printStackTrace();
            }
        });
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField1.setText("");
                passwordField1.setText("");
                errorText.setText("");

            }
        });
    }
}
