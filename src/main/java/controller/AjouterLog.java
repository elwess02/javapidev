package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import entities.Evenement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;
import services.EvenementServices;

public class AjouterLog {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField NomTextField;
    @FXML
    private ComboBox<String> typeBox;

    @FXML
    private TextField PrenomTextField;

    @FXML
    private TextField dateTextField;

    @FXML
    private TextField dureTextField;

    @FXML
    private TextField heureTextField1;

    @FXML
    private ImageView imagview;

    @FXML
    private TextField lieuTextField3;

    @FXML
    private ComboBox<Integer> nbredeparicipantsBox;

    @FXML
    private TextField organisateurTextField1;

    @FXML
    private TextField prixTextField;
    private EvenementServices evnementsServices = new EvenementServices();
    @FXML
    private TextField typeTextField2;
    private String imagePath;

    @FXML
    void Afficher(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListLog.fxml"));
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
        String nom = NomTextField.getText();
        //String prenom = PrenomTextField.getText();
        String date = dateTextField.getText();
        String duree = dureTextField.getText();
        String heure = heureTextField1.getText();
        String lieu = lieuTextField3.getText();
        String organisateur = organisateurTextField1.getText();
        Integer nombreDeParticipants = nbredeparicipantsBox.getValue();
        String type = typeBox.getItems().toString();
      //  String type = typeTextField2.getText();
        Double prix = null;

      // Assurez-vous que tous les champs sont remplis
        if (nom.isEmpty()  || date.isEmpty() || duree.isEmpty() || heure.isEmpty() ||
                lieu.isEmpty() || organisateur.isEmpty() || nombreDeParticipants == null) {
            // Affichez une alerte si un champ est manquant
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Champs manquants");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
            return;
        }


        // Try parsing prix
        try {
            prix = Double.parseDouble(prixTextField.getText());
        } catch (NumberFormatException e) {
            // Handle the case where the string cannot be parsed to a double
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez saisir un montant valide pour le prix.");
            alert.showAndWait();
            return;
        }

        // Créer un objet Evenement avec les valeurs récupérées
        Evenement evenement = new Evenement(nom,date,heure,duree, nombreDeParticipants ,lieu,type,organisateur,prix, imagePath);

        // Utilisez votre service pour ajouter l'événement à la base de données
        evnementsServices.addEntity(evenement);

        // Affichez un message de réussite
        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setTitle("Succès");
        successAlert.setHeaderText(null);
        successAlert.setContentText("L'événement a été ajouté avec succès.");
        successAlert.showAndWait();
        Notifications.create().title("Done").text("L'evenement est ajouté.").showConfirm();
        // Effacez les champs de saisie
        clearFields();

    }

    @FXML
    void image(ActionEvent event) {
        // Ouvrir un dialogue de sélection de fichier pour choisir une image
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionner une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        // Obtenir la scène actuelle pour afficher le dialogue de sélection de fichier
        Stage stage = (Stage) imagview.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        // Si un fichier est sélectionné, charger l'image dans l'ImageView et enregistrer le chemin de l'image
        if (selectedFile != null) {
            imagePath = selectedFile.toURI().toString();
            Image image = new Image(imagePath);
            imagview.setImage(image);
        }

    }

    @FXML
    void initialize() {

        nbredeparicipantsBox.getItems().addAll( 50,100,150,200);
        nbredeparicipantsBox.setPromptText("Sélectionnez le nombre de participants");
        // Convertir la liste en ObservableList
        ObservableList<String> items = FXCollections.observableArrayList("culture", "sport", "randonnee");

// Ajouter les éléments à la ComboBox
        typeBox.setItems(items);

// Définit un texte de rappel pour la ComboBox
        typeBox.setPromptText("Sélectionnez le type de l'événement");

    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private void clearFields() {
        NomTextField.clear();
        PrenomTextField.clear();
        dateTextField.clear();
        heureTextField1.clear();
        nbredeparicipantsBox.getSelectionModel().clearSelection();
        prixTextField.clear();
        typeTextField2.clear();
        organisateurTextField1.clear();
        imagview.setImage(null); // Efface également l'image affichée dans l'ImageView
    }
    @FXML
    void Reservation(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterResv.fxml"));
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
    void meteo(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/primary.fxml"));
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
    void map(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/map.fxml"));
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
    }



