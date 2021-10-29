package qiu.grocerymanagementsystem.grocerymanagementsystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Admin extends JFrame {
    private JPanel panel1;
    private JTable table2;
    private JTextField addName;
    private JTextField addSKU;
    private JTextField addPrice;
    private JButton addItemButton;
    private JLabel errorText;
    private JTextField searchSKU;
    private JButton deleteButton;
    private JButton reloadTableButton;

    public Admin(MySQL mySQL) {
        this.setAlwaysOnTop(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setContentPane(panel1);
        this.setLocationRelativeTo(null);
        createTable();
        DefaultTableModel model = (DefaultTableModel) table2.getModel();
        reloadData(model);

        addItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                mySQL.addItem(addName.getText(),  Float.parseFloat(addPrice.getText()), addSKU.getText());
                reloadData(model);

            }
        });
        reloadTableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              reloadData(model);
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               if(searchSKU.getText() != null){
                   mySQL.deleteItem(searchSKU.getText());
                   reloadData(model);
               }else {
                   errorText.setText("SKU required!");
               }
            }
        });

    }

    private void createTable(){
        table2.setModel(new DefaultTableModel(
                null,
                new String[]{"Name", "SKU", "Price",}
        ));
    }

    private void reloadData(DefaultTableModel model){
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/grocerymanagement", "root", "" );
            String getInventorySQL = "SELECT * FROM inventory";

            Statement statement = connection.createStatement();
            ResultSet inv =  statement.executeQuery(getInventorySQL);
            model.setRowCount(0);
            while (inv.next()){
                model.addRow(new Object[]{ inv.getString("name"), inv.getString("sku"), "RM "+ inv.getString("price"), });

            }


            table2.setModel(model);

        }catch (Exception e2){
            e2.printStackTrace();
        }
    }
}
