package controller;

import java.io.File;
import java.io.IOException;
import entities.Evenement;
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
import services.EvenementServices;

public class UpdateLog {
    private Evenement evenement;

    @FXML
    private TextField NomTextField;


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
    private String imagePath;

    @FXML
    private TextField typeTextField2;
    EvenementServices evenementServices = new EvenementServices();
    private ListLog listEvenementController;


    @FXML
    void Afficher(ActionEvent event) {

    }

    @FXML
    void Ajouter(ActionEvent event) {

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
    void Modifier(ActionEvent event) {
        // Récupérer les nouvelles valeurs
        String nouveauNom = NomTextField.getText();
        String nouvelDate = dateTextField.getText();
        String nouvelleDure = dureTextField.getText();
        String nouvelleHeure = heureTextField1.getText();
        String nouvelleLieu =  lieuTextField3.getText();
        Integer nouveauNbredeparticipants = (Integer) nbredeparicipantsBox.getValue();
         String nouvelleOrganisateeur =  organisateurTextField1.getText();
        Double nouveauPrix = Double.parseDouble(prixTextField.getText());


        if (evenement != null) {
            // Mettez à jour les champs du evenement avec les nouvelles valeurs
           evenement.setNom(nouveauNom);
            evenement.setDate(nouvelDate);
            evenement.setDure(nouvelleDure);
            evenement.setHeure(nouvelleHeure);
            evenement.setLieu(String.valueOf(nouvelleLieu));
            evenement.setNbreparticipants(nouveauNbredeparticipants);
            evenement.setOrganisateur(nouvelleOrganisateeur);
            evenement.setPrix(nouveauPrix);
            // Vous devez définir l'image sur votre objet evenement
            // evenement.setImage(imagePath);

            // Appeler la méthode updateEntity du service
            evenementServices.updateEntity(evenement);

            // Afficher une boîte de dialogue de confirmation
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Modification réussie");
            alert.setHeaderText(null);
            alert.setContentText("Le evenement a été modifié avec succès.");
            alert.showAndWait();

            // Rediriger vers la liste des evenements
            redirectToevenementList(event);
        } else {
            // Gérer le cas où aucun evenement n'est sélectionné
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucun evenement sélectionné");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un evenement à modifier.");
            alert.showAndWait();
        }
    }
    private void redirectToevenementList(ActionEvent event) {
        try {
            // Charger le fichier FXML de la liste des evenements
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListLog.fxml"));
            Parent root = loader.load();

            // Accéder au contrôleur ListLog
            ListLog listLogController = loader.getController();

            // Rafraîchir la liste des evenements
            listLogController.refreshList();

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


    public void setevenement(Evenement evenement) {
        this.evenement = evenement;

        // Initialize the fields with the values of the evenement
        NomTextField.setText(evenement.getNom());
        dateTextField.setText(evenement.getDate());
        dureTextField.setText(evenement.getDure());
        heureTextField1.setText(evenement.getHeure());
        lieuTextField3.setText(evenement.getLieu());
        organisateurTextField1.setText(evenement.getOrganisateur());
        nbredeparicipantsBox.setValue(evenement.getNbreparticipants());
        prixTextField.setText(String.valueOf(evenement.getPrix()));
        // Assuming imagePath is a String property
        imagePath = evenement.getImage();
        // If imagePath is a TextField, use setText() instead
        // imagePath.setText(evenement.getImage());
    }
    public void setListEvenementController(ListLog listEvenementController) {
        this.listEvenementController = listEvenementController;
    }
}




