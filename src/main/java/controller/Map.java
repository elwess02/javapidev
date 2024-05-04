package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;


public class Map {

    @FXML
    private WebView webView;
    @FXML
    void back(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AJouterLog.fxml"));
            Parent root = loader.load();

            // Create a new scene
            Scene scene = new Scene(root);

            // Get the current stage
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the new scene on the current stage
            currentStage.setScene(scene);
            currentStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public void initialize() {
        // Obtenez le moteur Web de la WebView
        WebEngine webEngine = webView.getEngine();

        // Chargez la carte Google Maps (ex: New York)
        webEngine.load("https://www.google.com/maps/@36.7948545,10.0608764,12z?entry=ttu");
        //  webEngine.load("https://www.google.com/maps/embed/v1/place?q=New+York&key=AIzaSyDq9B8DFiLaF0EqLOUPjAKeUxZxVq5WvGI");
    }

  /*  @FXML
    private WebView webView;

    public void initialize() {
        // Obtenez le moteur Web de la WebView
        WebEngine webEngine = webView.getEngine();

        // Chargez la carte OpenStreetMap
        webEngine.load(getClass().getResource("/map.html").toExternalForm());
    }*/
}//https://www.google.com/maps/@36.7948545,10.0608764,12z?entry=ttu
//https://www.openstreetmap.org/#map=12/36.7599/10.1452
