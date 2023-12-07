package com.novqigarrix.pos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // Load main view
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        // Get the controller of the main view
        HelloController helloController = fxmlLoader.getController();

        // Load all products
        helloController.loadProducts();

        // Set the title of the window
        stage.setTitle("Point of Sale");

        // Set the scene of the window
        stage.setScene(scene);

        // Show the window
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}