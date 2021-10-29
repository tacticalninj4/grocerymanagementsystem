package qiu.grocerymanagementsystem.grocerymanagementsystem;

import java.sql.*;

public class MySQL {


    public boolean connectDB(){
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/grocerymanagement", "root", "" );
            if(connection != null){
                System.out.println("Connected to Database");
                connection.close();
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public void checkAndCreateAdmin(){

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/grocerymanagement", "root", "" );
            String createTableCredentials = "CREATE TABLE IF NOT EXISTS credential (\n" +
                    "  id int(11) NOT NULL AUTO_INCREMENT,\n" +
                    "  name varchar(300) NOT NULL,\n" +
                    "  userid varchar(300) NOT NULL UNIQUE,\n" +
                    "  password varchar(300) NOT NULL,\n" +
                    "  PRIMARY KEY (`id`)\n" +
                    ");";

            String createTableInventory = "CREATE TABLE IF NOT EXISTS inventory (\n" +
                    "  id int(11) NOT NULL AUTO_INCREMENT,\n" +
                    "  name varchar(300) NOT NULL,\n" +
                    "  price float NOT NULL,\n" +
                    "  sku varchar(300) NOT NULL UNIQUE,\n" +
                    "  timestamp timestamp NOT NULL DEFAULT current_timestamp(),\n" +
                    "  PRIMARY KEY (`id`)\n" +
                    ");";

            String checkIfAdminExist = "SELECT * FROM credential WHERE userid = ?";

            String createAdmin = "INSERT INTO credential (name, userid, password) VALUES (?, ?, ?);";




            Statement statement1 = connection.createStatement();
            Statement statement2 = connection.createStatement();
            PreparedStatement statement4 = connection.prepareStatement(checkIfAdminExist);

            statement1.execute(createTableCredentials);
            statement2.execute(createTableInventory);

            statement4.setString(1, "admin");
            ResultSet resultSet = statement4.executeQuery();

            if(!resultSet.next()){
                PreparedStatement statement5 = connection.prepareStatement(createAdmin);
                statement5.setString(1, "Admin");
                statement5.setString(2, "admin");
                statement5.setString(3, "password");

                int created = statement5.executeUpdate();
                if(created> 0){
                    System.out.println("admin user created");
                }
            }



            connection.close();

        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public String checkLogin(String userid, String password){

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/grocerymanagement", "root", "" );

            String getLoginCred= "SELECT * FROM credential WHERE userid = ?";

            PreparedStatement statement = connection.prepareStatement(getLoginCred);
            statement.setString(1,userid);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){
                if(password.equals(resultSet.getNString("password"))){
                    return resultSet.getNString("name");
                }else {
                    return "err";
                }
            }




            connection.close();
        }catch (Exception e){
            e.printStackTrace();

        }

        return "err";
    }




    public boolean addUser(String name, String userid, String password){


        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/grocerymanagement", "root", "" );
            String getLoginCred= "SELECT * FROM credential WHERE userid = ?";

            PreparedStatement statement = connection.prepareStatement(getLoginCred);
            statement.setString(1,userid);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){
                connection.close();
                return false;
            }else {
                String createUser = "INSERT INTO credential (name, userid, password) VALUES (?, ?, ?);";
                PreparedStatement statement1 = connection.prepareStatement(createUser);
                statement1.setString(1, name);
                statement1.setString(2, userid);
                statement1.setString(3, password);

                int created = statement1.executeUpdate();
                if(created> 0){
                    System.out.println("admin user created");
                }
                connection.close();
                return true;
            }

        }catch (Exception e){
            e.printStackTrace();

        }

        return false;
    }

    public boolean addItem(String name, float price, String sku){

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/grocerymanagement", "root", "" );
            String addItemSQL = "INSERT INTO inventory (name, price, sku) VALUES (?, ?, ?);";
            PreparedStatement statement = connection.prepareStatement(addItemSQL);
            statement.setString(1, name);
            statement.setFloat(2, price);
            statement.setString(3, sku);

            int rows = statement.executeUpdate();

            if(rows > 0){
                connection.close();
                return true;
            }else {
                connection.close();
                return false;
            }




        }catch (Exception e){
            e.printStackTrace();

        }

        return false;
    }

    public boolean deleteItem(String sku){

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/grocerymanagement", "root", "" );
            String delSQL = "DELETE FROM inventory WHERE sku = ?";

            PreparedStatement statement = connection.prepareStatement(delSQL);
            statement.setString(1, sku);

            int row = statement.executeUpdate();

            if(row > 0){
                connection.close();

                return true;

            }else return false;



        }catch (Exception e2){
            e2.printStackTrace();
        }

        return false;
    }

    public String[] searchItem(String sku){

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/grocerymanagement", "root", "" );
            String searchQ = "SELECT * FROM inventory WHERE sku = ?";

            PreparedStatement statement = connection.prepareStatement(searchQ);
            statement.setString(1, sku);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){
                return new String[]{resultSet.getString("name"), String.valueOf(resultSet.getFloat("price"))};
            }else {
                return new String[]{"error"};
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return new String[]{"error"};
    }

}
