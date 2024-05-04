package entities;
import java.sql.Date;

public class Evenement {

    private int id_E;
    private String nom;
    private String date;
    private String heure;
    private String dure;
    private int nbreparticipants;
    private String lieu;
    private String type;
    private String organisateur;
    private double prix	;
    private String image;

    public Evenement() {
    }

    public Evenement(String nom, String date, String heure, String dure, int nbreparticipants, String lieu, String type, String organisateur, Double prix, String image) {
        this.nom = nom;
        this.date = date;
        this.heure = heure;
        this.dure = dure;
        this.nbreparticipants = nbreparticipants;
        this.lieu = lieu;
        this.type = type;
        this.organisateur = organisateur;
        this.prix = prix;
        this.image = image;
    }

    public Evenement(int id_E, String nom, String date, String heure, String dure, int nbreparticipants, String lieu, String type, String organisateur,double prix, String image) {
        this.id_E = id_E;
        this.nom = nom;
        this.date = date;
        this.heure = heure;
        this.dure = dure;
        this.nbreparticipants = nbreparticipants;
        this.lieu = lieu;
        this.type = type;
        this.organisateur = organisateur;
        this.prix = prix;
        this.image = image;
    }

    public int getId_E() {
        return id_E;
    }

    public void setId_E(int id_E) {
        this.id_E = id_E;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public String getDure() {
        return dure;
    }

    public void setDure(String dure) {
        this.dure = dure;
    }

    public int getNbreparticipants() {
        return nbreparticipants;
    }

    public void setNbreparticipants(int nbreparticipants) {
        this.nbreparticipants = nbreparticipants;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrganisateur() {
        return organisateur;
    }

    public void setOrganisateur(String organisateur) {
        this.organisateur = organisateur;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Evenement{" +
                "id_E=" + id_E +
                ", nom='" + nom + '\'' +
                ", date=" + date +
                ", heure='" + heure + '\'' +
                ", dure='" + dure + '\'' +
                ", nbreparticipants=" + nbreparticipants +
                ", lieu='" + lieu + '\'' +
                ", type='" + type + '\'' +
                ", organisateur='" + organisateur + '\'' +
                ", prix=" + prix +
                ", image='" + image + '\'' +
                '}';
    }
}

