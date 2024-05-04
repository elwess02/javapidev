package services;

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

public class ReservationEvenementServices implements IService<ReservationEvenement> {


    @Override
    public void addEntity(ReservationEvenement reservationEvenement) {
        String requete ="INSERT INTO reservation_event (prenom,vip,nbrpersonnes,nom) VALUES (?,?,?,?)";
        try {
            PreparedStatement pst =  MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1,reservationEvenement.getPrenom());
            pst.setBoolean(2, reservationEvenement.getVip());
            pst.setInt(3, reservationEvenement.getNbrpersonnes());
            pst.setString(4, reservationEvenement.getNom());


            pst.executeUpdate();
            System.out.println("resevation Evenement Added");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void updateEntity(ReservationEvenement reservationevenement) {
        String requete = "UPDATE reservation_event SET prenom=?, vip=?, nbrpersonnes=?, nom=?,WHERE id_RE=?";
        try {
            System.out.println("Executing update query: " + requete);

            PreparedStatement pst =  MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1,reservationevenement.getPrenom());
            pst.setBoolean(2, reservationevenement.getVip());
            pst.setInt(3, reservationevenement.getNbrpersonnes());
            pst.setString(4, reservationevenement.getNom());

            // Assurez-vous d'assigner la valeur de l'ID au bon paramètre dans la requête
            pst.setInt(5, reservationevenement.getId_RE());

            System.out.println("Updating plat with ID " + reservationevenement.getId_RE() + ":");

            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("reservationeEVENEMENT Updated");
            } else {
                System.out.println("reservationEvenement not found or not updated");
            }
        } catch (SQLException e) {
            System.out.println("Error during update: " + e.getMessage());
        }

    }

    @Override
    public void DeleteEntity(ReservationEvenement reservationEvenement) {
        String requete = "DELETE FROM reservation_event WHERE id_RE=?";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(1, reservationEvenement.getId_RE());

            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("reservationeEVENEMENT Deleted");
            } else {
                System.out.println("reservationEVENEMENT not found");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
    public void DeleteEntityWithConfirmation(ReservationEvenement reservationEvenement) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText("Suppression d'un Evenement'");
        confirmationAlert.setContentText("Voulez-vous vraiment supprimer ce Evenement?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // User confirmed deletion, proceed with deletion
            DeleteEntity(reservationEvenement);
        }
    }

    @Override
    public List<ReservationEvenement> getAllData() {
        List<ReservationEvenement> data = new ArrayList<>();
        String requete = "SELECT * FROM reservation_event";
        try {
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                ReservationEvenement r = new ReservationEvenement();
                r.setId_RE(rs.getInt(1));
                r.setNom(rs.getString("NOM"));
                r.setVip(rs.getBoolean("vip"));
                r.setNbrpersonnes(rs.getInt("nbrpersonnes"));
                r.setPrenom(rs.getString("prenom"));




                data.add(r);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return data;
    }
}
