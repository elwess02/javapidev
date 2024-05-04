package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
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

public class UpdateReserv {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField nom;

    @FXML
    private TextField nombre_de_personnes;

    @FXML
    private TextField prenom;

    @FXML
    private CheckBox vip;
    private ReservationEvenement reservationEvenement;
    private ListReserv listReservController;

    private ReservationEvenementServices reservationEvenementServices = new ReservationEvenementServices();


    @FXML
    void Afficher(ActionEvent event) {

    }



    @FXML
    void Modifier(ActionEvent event) {
        // Get the new values
        String nomValue = nom.getText();
        String prenomValue = prenom.getText();
        String nombre_de_personnesValue = nombre_de_personnes.getText();
        boolean vipValue = vip.isSelected();



        // Vérifier la validité des champs
        if (validateFields(nomValue,prenomValue, nombre_de_personnesValue,vipValue)) {
            // Conversion des valeurs
            String newNom = String.valueOf(nom);
            String newPrenom = String.valueOf(prenom);
            boolean newVip = vipValue;
            int newNbreParticipants = Integer.parseInt(nombre_de_personnes.getText());



            // Assurez-vous que la réservation est définie
            if (reservationEvenement != null) {
                // Mettez à jour les champs de la réservation avec les nouvelles valeurs
                reservationEvenement.setNom(newNom);
                reservationEvenement.setPrenom(newPrenom);
                reservationEvenement.setVip( newVip );
                reservationEvenement.setNbrpersonnes(newNbreParticipants );


                // Appeler la méthode updateEntity du service pour mettre à jour la réservation
                reservationEvenementServices.updateEntity(reservationEvenement);

                // Afficher une boîte de dialogue de confirmation
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Modification réussie");
                alert.setHeaderText(null);
                alert.setContentText("La réservation a été modifiée avec succès.");
                alert.showAndWait();

                // Rediriger vers la liste des réservations
                redirectToReservationsList(event);
            } else {
                // Gérer le cas où aucune réservation n'est sélectionnée
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Aucune réservation sélectionnée");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez sélectionner une réservation à modifier.");
                alert.showAndWait();
            }
        }
    }




    // Méthode pour valider les champs
    private boolean validateFields(String nom, String prenom, String nombreDePersonnes, boolean VIP) {
        // Validation du champ cout
        if (nom.isEmpty() ) {
            showAlert(Alert.AlertType.ERROR, "Erreur", null, "verifier le nom.");
            return false;
        }

        // Validation du champ nombre d'invités
        if (nombreDePersonnes.isEmpty() || Integer.parseInt(nombreDePersonnes) < 0) {
            showAlert(Alert.AlertType.ERROR, "Erreur", null, "Le champ nombre de personnes doit être un nombre positif non vide.");
            return false;
        }

        // Validation du champ statut
        if (prenom.isEmpty() ) {
            showAlert(Alert.AlertType.ERROR, "Erreur", null, "verifier le prenom.");
            return false;
        }



        return true;
    }

    // Méthode pour afficher une alerte
    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }


    private void redirectToReservationsList(ActionEvent event) {
        try {
            // Charger le fichier FXML de la liste des réservations
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListReserv.fxml"));
            Parent root = loader.load();

            // Accéder au contrôleur ListReserv
            ListReserv listReservController = loader.getController();

            // Rafraîchir la liste des réservations
            listReservController.refreshList();

            // Créer une nouvelle scène
            Scene scene = new Scene(root);

            // Récupérer la scène actuelle à partir de n'importe quel composant de la scène actuelle
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Changer la scène du stage actuel
            currentStage.setScene(scene);
            currentStage.show();
        } catch (IOException e) {
            // Gérer l'exception en lançant une RuntimeException
            throw new RuntimeException(e);
        }
    }

    public void setReservationEvenement(ReservationEvenement reservationEvenement) {
        this.reservationEvenement = reservationEvenement;

        // Initialize the fields with the values of the reservation
        nom.setText(String.valueOf(reservationEvenement.getNom()));
        prenom.setText(String.valueOf(reservationEvenement.getPrenom()));
        nombre_de_personnes.setText(String.valueOf(reservationEvenement.getNbrpersonnes()));

        vip.setText(String.valueOf(reservationEvenement.getVip()));

    }

    public void setListReservController(ListReserv listReservController) {
        this.listReservController = listReservController;
    }
}


