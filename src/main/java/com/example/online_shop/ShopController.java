package com.example.online_shop;

import com.example.online_shop.data.IRepository;
import com.example.online_shop.data.Product;
import com.example.online_shop.data.ProductRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
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
        repository = new ProductRepository();
        updateListView();
        fillComboBox();
        comboBoxModel.getSelectionModel().selectFirst();
    }

    public void fillComboBox(){
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
    public void updateListView() {
        List<Product> products = repository.getAll();
        ObservableList<Product> list = FXCollections.observableList(products);
        listProducts.setItems(list);
    }
    @FXML
    public void addNewProduct(ActionEvent actionEvent) {
        Stage newWindow = new Stage();
        FXMLLoader loader = new FXMLLoader(ShopApplication.class.getResource("newProduct-view.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        newWindow.setTitle("Add new product");
        newWindow.setScene(new Scene(root, 250, 220));

        NewProductController secondController = loader.getController();
        secondController.setRepository(this.repository);
        secondController.setShopController(this);
        newWindow.show();
    }


    public void searchByCategory(ActionEvent actionEvent) {
        String category = comboBoxModel.getSelectionModel().getSelectedItem().toString();

        List<Product> products = repository.getByCategory(category);

        ObservableList<Product> list = FXCollections.observableList(products);
        listProducts.setItems(list);
    }

    public void searchByName(ActionEvent actionEvent) {
        List<Product> products = repository.searchByName(keyWord.getText());
        ObservableList<Product> list = FXCollections.observableList(products);
        listProducts.setItems(list);
    }
}