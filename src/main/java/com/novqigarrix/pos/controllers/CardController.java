package com.novqigarrix.pos.controllers;


// Controller digunakan untuk mengontrol
// spesifik fxml file

import com.novqigarrix.pos.Utils;
import com.novqigarrix.pos.models.Product;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class CardController implements Initializable {

    @FXML
    private VBox cardContainer;

    @FXML
    private Label productDesc;

    @FXML
    private ImageView productImg;

    @FXML
    private Label productPrice;

    @FXML
    private Label productQty;

    @FXML
    private Label productTitle;

    public void setProduct(Product product) {

        // Set the product image
        var imageSource = new Image(product.getImg(), true);
        productImg.setImage(imageSource);

        // Set the product title
        productTitle.setText(product.getTitle());

        // Set the product description
        productDesc.setText(product.getDesc());

        // Set the product quantity
        productQty.setText("Sisa: " + product.getStock());

        // Set the product price
        productPrice.setText("Rp" + Utils.getPriceFormatter().format(product.getPrice()));

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set cursor to hand when mouse entered
        cardContainer.setOnMouseEntered(mouseEvent -> {
            cardContainer.setCursor(Cursor.HAND);
        });

        // Set cursor to default when mouse exited
        cardContainer.setOnMouseExited(mouseEvent -> {
            cardContainer.setCursor(Cursor.DEFAULT);
        });
    }
}
