package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import entities.ReservationEvenement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.ReservationEvenementServices;

public class AjouterResv {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField nom;

    @FXML
    private TextField nombredepersonnes;

    @FXML
    private TextField prenom;

    @FXML
    private CheckBox vip;

    @FXML
    void Afficher(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListReserv.fxml"));
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

    @FXML
    void Ajouter(ActionEvent event) {
// Récupérer les valeurs des champs
        String nomValue = nom.getText();
        String prenomValue = prenom.getText();
        String nbrPersonnesValue = nombredepersonnes.getText();
        boolean estVIP = vip.isSelected(); // Si la case à cocher VIP est cochée


// Vérifier si les champs obligatoires ne sont pas vides
        if (!nomValue.isEmpty() && !prenomValue.isEmpty() && !nbrPersonnesValue.isEmpty() ) {
            // Créer une instance de votre classe avec les valeurs récupérées
            ReservationEvenement reservation = new ReservationEvenement();
            // Configurez les propriétés de votre classe avec les valeurs récupérées
            reservation.setNom(nomValue);
            reservation.setPrenom(prenomValue);
            reservation.setNbrpersonnes(Integer.parseInt(nbrPersonnesValue));
            reservation.setVip(estVIP);

            // Appeler la méthode de service pour ajouter l'élément avec les valeurs récupérées
           ReservationEvenementServices reservationServices = new ReservationEvenementServices();
            reservationServices.addEntity(reservation);

            // Effacer les champs après l'ajout
            clearFields();
        } else {
            // Afficher un message d'erreur si des champs obligatoires sont vides
            showAlert(Alert.AlertType.ERROR, "Erreur", null, "Veuillez remplir tous les champs obligatoires.");
        }

    }

    @FXML
    void initialize() {

    }
    private void clearFields() {
       nom.clear();
        nombredepersonnes.clear();
        prenom.clear();
        vip.setSelected(false);
    }
    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }


}
