package com.example.online_shop.data;


import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProductRepository implements IRepository{
    private final String url="jdbc:h2:file:./clothes";// ./ in root project
    private final String user="hanna";
    private final String password="";
   // private String fileName;
    private List<Product> products;

    public ProductRepository() {
        this.products = new ArrayList<>();
        this.createTable();
    }
    private void createTable() {
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connected");
            String createStatement =
                    "CREATE TABLE IF NOT EXISTS Clothes\n" +
                            "(id int auto_increment primary key, Name VARCHAR(50), Category VARCHAR(50)," +
                            "Brand VARCHAR(50), Size VARCHAR(10), Price DOUBLE);";
            Statement statement = conn.createStatement();
            statement.executeUpdate(createStatement);
            statement.close();
            System.out.println("end");
        } catch (SQLException e) {
            System.out.println("Mistake in creating table");
            e.printStackTrace();
        }

    }

    @Override
    public List<Product> getAll() {
        try {
            reloadData();
            for (Product p: this.products
                 ) {
                System.out.println(p);
            }
            return products;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return products;
    }
    private void reloadData() {
        try {
            Class.forName("org.h2.Driver").getDeclaredConstructor().newInstance();
            try(Connection connection = DriverManager.getConnection(url,user,password)){
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery("select * from  Clothes");
                this.products = new ArrayList<>();
                while(rs.next()){
                    System.out.println(rs.getString("Name"));
                    Product product = new Product(rs.getString("Name"),rs.getString("Category"),
                            rs.getString("Brand"),rs.getString("Size"),rs.getDouble("Price"));
                    this.products.add(product);
                }
                for (Product p: products
                     ) {
                    System.out.println(p);
                }
                rs.close();

            } catch (SQLException exception) {
                System.out.println("The connection to the database failed");
                exception.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            System.out.println("drivers are absent");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Product> searchByName(String name) {
       return getAll().stream().filter(product -> product.getName().contains(name)).toList();
    }

    @Override
    public List<Product> getByCategory(String category) {
        if(category.equals("All"))
            return getAll().stream().toList();
        else
        return getAll().stream().filter(product -> Objects.equals(product.getCategory(), category)).toList();
    }

    @Override
    public Boolean addNewProduct(Product product) {
        try {
            Class.forName("org.h2.Driver");
            System.out.println("Driver Loaded");

            try (Connection conn = DriverManager.getConnection(url, user, password)) {
                PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO Clothes (Name, Category," +
                        "Brand, Size, Price) VALUES (?,?,?,?,?)");
                preparedStatement.setString(1, product.getName());
                preparedStatement.setString(2, product.getCategory());
                preparedStatement.setString(3, product.getBrand());
                preparedStatement.setString(4, product.getSize());
                preparedStatement.setDouble(5, product.getPrice());
                int updCount = preparedStatement.executeUpdate();
                if(updCount > 0) {
                    System.out.println("Add new Product: " + product);
                    return true;
                }
            } catch (SQLException e) {
                System.out.println("connection doesn't exist");
                e.printStackTrace();
                return false;
            }
        } catch (ClassNotFoundException e) {
            System.out.println("drivers are absent");
            return false;
        }
        return  false;
    }
}
