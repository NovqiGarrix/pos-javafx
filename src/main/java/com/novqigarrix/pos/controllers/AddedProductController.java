package com.novqigarrix.pos.controllers;

import com.novqigarrix.pos.GlobalState;
import com.novqigarrix.pos.HelloController;
import com.novqigarrix.pos.Utils;
import com.novqigarrix.pos.exceptions.MyException;
import com.novqigarrix.pos.models.AddedProduct;
import com.novqigarrix.pos.models.AddedProductUserData;
import com.novqigarrix.pos.models.Product;
import com.novqigarrix.pos.services.ProductService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddedProductController implements Initializable {
    @FXML
    private HBox addedProductWrapper;

    @FXML
    private Button addQty;

    @FXML
    private ImageView productImg;

    @FXML
    private Label productPriceXQty;

    @FXML
    private Label productTitle;

    @FXML
    private Label qty;

    @FXML
    private Button reduceQty;

    public HBox getAddedProductWrapper() {
        return addedProductWrapper;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // When add quantity button is clicked
        addQty.setOnMouseClicked(mouseEvent -> {
            var addedProductUserData = (AddedProductUserData) addedProductWrapper.getUserData();

            AddedProduct currentProduct = GlobalState.addedProductList.get(addedProductUserData.getProductId());
            HelloController helloController = addedProductUserData.getHelloController();

            if(currentProduct.getQty() + 1 <= currentProduct.getStock()) {
                currentProduct.setQty(currentProduct.getQty() + 1);
            }

            GlobalState.addedProductList.put(currentProduct.getId(), currentProduct);

            helloController.calculateTotal();
            this.qty.setText(String.valueOf(currentProduct.getQty()));
        });

        // When reduce quantity button is clicked
        reduceQty.setOnMouseClicked(mouseEvent -> {
            var addedProductUserData = (AddedProductUserData) addedProductWrapper.getUserData();

            HelloController helloController = addedProductUserData.getHelloController();
            AddedProduct currentProduct = GlobalState.addedProductList.get(addedProductUserData.getProductId());

            currentProduct.setQty(currentProduct.getQty() - 1);

            helloController.calculateTotal();
            this.qty.setText(String.valueOf(currentProduct.getQty()));

            if(currentProduct.getQty() < 1) {
                // Hapus produk nya dari list added product
                GlobalState.addedProductList.remove(currentProduct.getId());

                var allAddedProductComponents = helloController.getAddedProductContainer().getChildren();
                var currentProductComponent = allAddedProductComponents.stream().filter(addedProductComponent -> {
                    var addedProductController = (AddedProductController) addedProductComponent.getProperties().get("controller");
                    return addedProductController.getAddedProductWrapper() == addedProductWrapper;
                }).findFirst();

                currentProductComponent.ifPresent(node -> helloController.getAddedProductContainer().getChildren().remove(node));
            }

        });

        // Disable the reduce button when the qty is less than 2
        this.qty.textProperty().addListener((observableValue, oldValue, newValue) -> {
            var addedProductUserData = (AddedProductUserData) addedProductWrapper.getUserData();
            var productId = addedProductUserData.getProductId();
            var helloController = addedProductUserData.getHelloController();

            try {

                int stock;
                AddedProduct addedProduct = GlobalState.addedProductList.get(productId);

                if(addedProduct != null) {
                    stock = addedProduct.getStock();
                } else {
                    Optional<Product> currentProduct = ProductService.getProductById(productId);
                    stock = currentProduct.get().getStock();
                }

                int qty = Integer.parseInt(this.qty.getText());

                addQty.setDisable(qty >= stock);

                // Recalculate the total price
                helloController.calculateTotal();
            } catch (MyException | SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });

    }

    private void setImage(String imgUrl) {
        var imageSrc = new Image(imgUrl, true);
        productImg.setImage(imageSrc);
    }

    public void setAddedProduct(AddedProduct addedProduct, boolean setImage) {

        if(setImage) {
            this.setImage(addedProduct.getImg());
        }

        // Set the product title
        productTitle.setText(addedProduct.getTitle());

        // Set the product price
        var totalPrice = addedProduct.getPrice() * addedProduct.getQty();
        productPriceXQty.setText("Rp" + Utils.getPriceFormatter().format(totalPrice));

        // Set the product quantity
        qty.setText(String.valueOf(addedProduct.getQty()));

    }

}
