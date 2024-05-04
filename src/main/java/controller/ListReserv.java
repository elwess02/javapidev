package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import entities.ReservationEvenement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.paint.Color;
import services.ReservationEvenementServices;

public class ListReserv {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;


    @FXML
    private TableColumn<ReservationEvenement,Void> actions;

    @FXML
    private TableColumn<ReservationEvenement, Void> nbr;

    @FXML
    private TableColumn<ReservationEvenement, String> nom;

    @FXML
    private TableColumn<ReservationEvenement, String> prenom;

    @FXML
    private TableView<ReservationEvenement> reserv_list;

    @FXML
    private TableColumn<ReservationEvenement, Boolean> vip;

    ReservationEvenementServices reservationEvenementServices = new ReservationEvenementServices();

    @FXML
    void initialize() {
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        nbr.setCellValueFactory(new PropertyValueFactory<>("nbrpersonnes"));
        vip.setCellValueFactory(new PropertyValueFactory<>("vip"));

        // Utiliser une cell factory personnalisée pour la colonne VIP
        vip.setCellFactory(column -> {
            return new TableCell<ReservationEvenement, Boolean>() {
                @Override
                protected void updateItem(Boolean item, boolean empty) {
                    super.updateItem(item, empty);

                    // Si la cellule est vide, ne rien faire
                    if (empty || item == null) {
                        setText(null);
                        setStyle("");
                    } else {
                        // Si le client est VIP, colorier la cellule en jaune
                        if (item) {
                            setText("VIP");
                            setStyle("-fx-text-fill: blue;");
                        } else {
                            setText("Non VIP");
                            setStyle("-fx-text-fill: yellow;");
                        }
                    }
                }
            };
        });

        actions.setCellFactory(createActionsCellFactory());

        reserv_list.setItems(FXCollections.observableList(reservationEvenementServices.getAllData()));
    }
    @FXML
    void back(ActionEvent event) {
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

    // Méthode pour créer les boutons dans la colonne d'action
    private Callback<TableColumn<ReservationEvenement, Void>, TableCell<ReservationEvenement, Void>> createActionsCellFactory() {
        return new Callback<TableColumn<ReservationEvenement, Void>, TableCell<ReservationEvenement, Void>>() {
            @Override
            public TableCell<ReservationEvenement, Void> call(final TableColumn<ReservationEvenement, Void> param) {
                return new TableCell<ReservationEvenement, Void>() {
                    private final Button btnUpdate = new Button("Update");
                    private final Button btnDelete = new Button("Delete");

                    {
                        btnUpdate.setOnAction(event -> handleUpdate());
                        btnDelete.setOnAction(event -> handleDelete());
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox buttons = new HBox(btnUpdate, btnDelete);
                            setGraphic(buttons);
                        }
                    }
                };
            }
        };
    }

    // Méthode pour rafraîchir les données de la TableView
    public void refreshList() {
        ObservableList<ReservationEvenement> updatedList = FXCollections.observableList(reservationEvenementServices.getAllData());
        reserv_list.setItems(updatedList);
    }

    // Méthode pour gérer la mise à jour
    @FXML
    void handleUpdate() {
        ReservationEvenement selectedProjet = reserv_list.getSelectionModel().getSelectedItem();
        if (selectedProjet != null) {
            // Charger la scène de mise à jour
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateRerv.fxml"));
            Parent updateScene;
            try {
                updateScene = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            // Obtenir le contrôleur de la scène de mise à jour
            UpdateReserv updateController = loader.getController();
            updateController.setReservationEvenement(selectedProjet);

            // Créer un nouveau stage pour la scène de mise à jour
            Stage updateStage = new Stage();
            updateStage.setTitle("Update Evenement");
            updateStage.setScene(new Scene(updateScene));

            // Afficher le stage de mise à jour
            updateStage.showAndWait();

            // Après la fermeture de la scène de mise à jour, rafraîchir la TableView
            refreshList();
        } else {
            // Aucun élément sélectionné, afficher une alerte d'information
            showAlert(Alert.AlertType.INFORMATION, "Information", null, "Veuillez sélectionner un projet à mettre à jour.");
        }
    }

    // Méthode pour gérer la suppression
    @FXML
    void handleDelete() {
        ReservationEvenement selectedProjet = reserv_list.getSelectionModel().getSelectedItem();
        if (selectedProjet != null) {
            // Afficher une boîte de dialogue de confirmation
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Confirmation de suppression");
            alert.setContentText("Voulez-vous vraiment supprimer ce projet?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // L'utilisateur a confirmé, supprimer l'élément sélectionné
                reservationEvenementServices.DeleteEntityWithConfirmation(selectedProjet);
                refreshList(); // Rafraîchir la TableView
            }
        } else {
            // Aucun élément sélectionné, afficher une alerte d'information
            showAlert(Alert.AlertType.INFORMATION, "Information", null, "Veuillez sélectionner un projet à supprimer.");
        }
    }

    // Méthode pour afficher une alerte
    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }


}
