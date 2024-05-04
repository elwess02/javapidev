package services;

import entities.Evenement;
import entities.ReservationEvenement;
import interfaces.IService;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import tools.MyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;




public class EvenementServices implements IService<Evenement> {

        @Override
        public void addEntity(Evenement evenement) {
            String requete ="INSERT INTO evenement (nom,date,heure,dure,nbreparticipants,lieu,type,organisateur,prix,image) VALUES (?,?,?,?,?,?,?,?,?,?)";
            try {
                PreparedStatement pst =  MyConnection.getInstance().getCnx().prepareStatement(requete);
                pst.setString(1, evenement.getNom());
                pst.setString(2, evenement.getDate());
                pst.setString(3, evenement.getHeure());
                pst.setString(4, evenement.getDure());
                pst.setInt(5, evenement.getNbreparticipants());
                pst.setString(6, evenement.getLieu());
                pst.setString(7, evenement.getType());
                pst.setString(8 , evenement.getOrganisateur());
                pst.setDouble(9 , evenement.getPrix());
                pst.setString(10 , evenement.getImage());


                pst.executeUpdate();
                System.out.println("Evenement Added");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        @Override
        public void updateEntity(Evenement evenement) {
            String requete = "UPDATE evenement SET nom=?, date=?, heure=?, dure=?, nbreparticipants=?, lieu=?,type=?, organisateur=?, prix =?, image=?  WHERE id_E=?";
            try {
                System.out.println("Executing update query: " + requete);
                PreparedStatement pst =  MyConnection.getInstance().getCnx().prepareStatement(requete);
                pst.setString(1, evenement.getNom());
                pst.setString(2, evenement.getDate());
                pst.setString(3, evenement.getHeure());
                pst.setString(4, evenement.getDure());
                pst.setInt(5, evenement.getNbreparticipants());
                pst.setString(6, evenement.getLieu());
                pst.setString(7, evenement.getType());
                pst.setString(8 , evenement.getOrganisateur());
                pst.setDouble(9 , evenement.getPrix());
                pst.setString(10 , evenement.getImage());
                pst.setInt(11 , evenement.getId_E());

                System.out.println("Updating project with ID " + evenement.getId_E() + ":");
                ;

                int rowsAffected = pst.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Projet Updated");
                } else {
                    System.out.println("Projet not found or not updated");
                }
            } catch (SQLException e) {
                System.out.println("Error during update: " + e.getMessage());
            }

        }

        @Override
        public  void DeleteEntity(Evenement evenement) {
            String requete = "DELETE FROM evenement WHERE id_E=?";
            try {
                PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
                pst.setInt(1, evenement.getId_E());

                int rowsAffected = pst.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("evenement Deleted");
                } else {
                    System.out.println("evenement not found");
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        }


        public  List<Evenement> getAllData() {
            List<Evenement> data =new ArrayList<>();
            String requete="SELECT * FROM evenement";
            try {
                Statement st= MyConnection.getInstance().getCnx().createStatement();
                ResultSet rs = st.executeQuery(requete);
                while (rs.next()){
                    Evenement e = new Evenement();
                    e.setId_E(rs.getInt(1));
                    e.setNom(rs.getString("nom"));
                    e.setDate((rs.getString("Date")));
                    e.setHeure(rs.getString("Heure"));
                    e.setDure((rs.getString("Dure")));
                    e.setNbreparticipants((rs.getInt("nbreparticipants")));
                    e.setLieu((rs.getString("Lieu")));
                    e.setType(rs.getString("type"));
                    e.setOrganisateur((rs.getString("organisateur")));
                    e.setPrix(rs.getInt("Prix"));
                    e.setImage((rs.getString("image")));
                    data.add(e);
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            return data;
        }

    public void DeleteEntityWithConfirmation(Evenement evenement) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText("Suppression de Evenement");
        confirmationAlert.setContentText("Voulez-vous vraiment supprimer ce Evenement?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // User confirmed deletion, proceed with deletion
            DeleteEntity(evenement);
        }
    }


}
