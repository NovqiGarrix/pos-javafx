package com.novqigarrix.pos.services;

import com.novqigarrix.pos.HelloController;
import com.novqigarrix.pos.controllers.AddedProductController;
import com.novqigarrix.pos.models.AddedProduct;
import com.novqigarrix.pos.models.AddedProductUserData;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class AddedProductService {

    // Load the added product component
    public HBox loadAndSetAddedProduct(HelloController myClass, AddedProduct addedProduct) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(myClass.getClass().getResource("components/added-product.fxml"));

        HBox addedProductComponent = fxmlLoader.load();

        addedProductComponent.setUserData(new AddedProductUserData(addedProduct.getId(), myClass));

        AddedProductController addedProductController = fxmlLoader.getController();
        addedProductController.setAddedProduct(addedProduct, true);

        addedProductComponent.getProperties().put("controller", addedProductController);
        return addedProductComponent;
    }

    // Add the added product component to the container
    public void addAddedProduct(VBox addedProductContainer, HBox addedProductComponent) {
        addedProductContainer.setSpacing(20);
        addedProductContainer.getChildren().add(addedProductComponent);
    }

}
