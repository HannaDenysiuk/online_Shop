package com.example.online_shop;

import com.example.online_shop.data.IRepository;
import com.example.online_shop.data.Product;
import com.example.online_shop.data.ProductRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;

public class ShopController implements Initializable {
    @FXML
    ListView listProducts = new ListView<>();
    @FXML
    TextField keyWord;
    @FXML
    ComboBox comboBoxModel = new ComboBox<>();
    private IRepository repository;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        repository = new ProductRepository("clothes.data");
        updateListView();
        fillComboBox();
        comboBoxModel.getSelectionModel().selectFirst();
    }

    private void fillComboBox(){
        List<Product> products = repository.getAll();
        List<String> list = new ArrayList<>();
        list.add("All");
        for (Product p: products
             ) {
            if(!list.contains(p.getCategory()))
           list.add(p.getCategory());
        }
        ObservableList<String> list1 = FXCollections.observableList(list);
        comboBoxModel.setItems(list1);
    }
    private void updateListView() {
        List<Product> products = repository.getAll();
        ObservableList<Product> list = FXCollections.observableList(products);
        listProducts.setItems(list);
    }
    @FXML
    public void addNewProduct(ActionEvent actionEvent) {
        String name = JOptionPane.showInputDialog("name of product");
        String category = JOptionPane.showInputDialog("category of product");
        String brand = JOptionPane.showInputDialog("brand of product");
        String size = JOptionPane.showInputDialog("size of product");
        String priceS = JOptionPane.showInputDialog("price of product");
        double price = Double.parseDouble(priceS);
        int id = 1;
        try{
            if(repository.getAll().size() > 0) {
                id += repository.getAll().stream().mapToInt(c -> c.getId()).max().getAsInt();
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Product product = new Product(id, name, category, brand, size, price);

        if(repository.addNewProduct(product))
        {
            updateListView();
            fillComboBox();
        }
    }


    public void searchByCategory(ActionEvent actionEvent) {
        String category = comboBoxModel.getSelectionModel().getSelectedItem().toString();
        System.out.println(category);
        List<Product> products = repository.getByCategory(category);
        System.out.println(products.size());
        ObservableList<Product> list = FXCollections.observableList(products);
        listProducts.setItems(list);
    }

    public void searchByName(ActionEvent actionEvent) {
        List<Product> products = repository.searchByName(keyWord.getText());
        ObservableList<Product> list = FXCollections.observableList(products);
        listProducts.setItems(list);
    }
}