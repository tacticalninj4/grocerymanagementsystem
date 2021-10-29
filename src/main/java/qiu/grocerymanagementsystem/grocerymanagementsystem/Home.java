package qiu.grocerymanagementsystem.grocerymanagementsystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Home extends JFrame {
    private JPanel panel1;
    private JTextField skuSearch;
    private JTextField productName;
    private JSpinner quantitySpinner;
    private JButton logoutButton;
    private JLabel username;
    private JButton addButton;
    private JTable table1;
    private JButton searchButton;
    private JTextField subtotal;
    private JButton saveButton;
    private JButton adminButton;
    private JTextField productPrice;
    private JLabel errorText;
    private JButton clearButton;

    public Home(String userName, MySQL mySQL){
        this.setAlwaysOnTop(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(panel1);
        this.setLocationRelativeTo(null);

        this.pack();
        username.setText(userName);

        createTable();
        DefaultTableModel model = (DefaultTableModel) table1.getModel();
        adminButton.addActionListener(e -> {

            JFrame admin = new Admin(mySQL);
            admin.setVisible(true);
            admin.setExtendedState(Frame.MAXIMIZED_BOTH);

        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!skuSearch.getText().equals("")){
                    String[] product = mySQL.searchItem(skuSearch.getText());
                    if(!product[0].equals("error")){
                        productName.setText(product[0]);
                        productPrice.setText(product[1]);
                    }else {
                        errorText.setText("Item does not exist!");
                    }
                }
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!productName.getText().equals("") && ((int) quantitySpinner.getValue() > 0)){
                    updateTable(model, productName.getText(), skuSearch.getText(), (int)  quantitySpinner.getValue(), Float.parseFloat(productPrice.getText()));

                }else {
                    errorText.setText("Search a SKU first!");
                }
            }
        });
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setRowCount(0);
                productPrice.setText("");
                productName.setText("");
                skuSearch.setText("");
                quantitySpinner.setValue(1);
                subtotal.setText("0.0");
            }
        });
        logoutButton.addActionListener(e -> {

            this.setVisible(false);

            JFrame login = new Login(mySQL);
            login.setVisible(true);
        });
    }

    private void createTable(){
        table1.setModel(new DefaultTableModel(
               null,
               new String[]{ "Name", "SKU", "Quantity", "Price", "Total"}
        ));
    }

    private void updateTable(DefaultTableModel model,String name, String sku, int quantity, float price){
        model.addRow(new Object[]{name, sku, quantity, price, (price * quantity)});
        float total = Float.parseFloat(subtotal.getText());
        total += (price * quantity);

        subtotal.setText(String.valueOf(total));

    }
}
