package com.example.online_shop;

import com.example.online_shop.data.IRepository;
import com.example.online_shop.data.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewProductController {
    @FXML
    TextField name;
    @FXML
    TextField category;
    @FXML
    TextField brand;
    @FXML
    TextField size;
    @FXML
    TextField price;
    public ShopController shopController;
    public IRepository repository;

    public void setRepository(IRepository repository) {
        this.repository = repository;
    }

    public void setShopController(ShopController shopController) {
        this.shopController = shopController;
    }

    public void addNewProduct(ActionEvent actionEvent) {
        String pName = name.getText();
        String pCategory = category.getText();
        String pBrand = brand.getText();
        String pSize = size.getText();
        String pPrice = price.getText();
        double price = Double.parseDouble(pPrice);
        Product product = new Product(pName, pCategory, pBrand, pSize, price);
        if(repository.addNewProduct(product))
        {
            shopController.updateListView();
            shopController.fillComboBox();
        }
        final Node source = (Node) actionEvent.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
