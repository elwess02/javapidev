package entities;

public class ReservationEvenement {
    private int id_RE ;
    private String prenom ;
    private boolean vip ;
    private int nbrpersonnes ;
    private String nom ;


    public ReservationEvenement() {
    }

    public ReservationEvenement(String prenom, boolean vip, int nbrpersonnes, String nom) {
        this.prenom = prenom;
        this.vip = vip;
        this.nbrpersonnes = nbrpersonnes;
        this.nom = nom;
    }

    public ReservationEvenement(int id_RE, String prenom, boolean vip, int nbrpersonnes, String nom) {
        this.id_RE = id_RE;
        this.prenom = prenom;
        this.vip = vip;
        this.nbrpersonnes = nbrpersonnes;
        this.nom = nom;
    }


    public int getId_RE() {
        return id_RE;
    }

    public void setId_RE(int id_RE) {
        this.id_RE = id_RE;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public boolean getVip() {
        return vip;
    }

    public void setVip(boolean vip) {
        this.vip = vip;
    }

    public int getNbrpersonnes() {
        return nbrpersonnes;
    }

    public void setNbrpersonnes(int nbrpersonnes) {
        this.nbrpersonnes = nbrpersonnes;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }


    @Override
    public String toString() {
        return "ReservationEvenement{" +
                "id_RE=" + id_RE +
                ", prenom='" + prenom + '\'' +
                ", vip=" + vip +
                ", nbrpersonnes=" + nbrpersonnes +
                ", nom='" + nom + '\'' +
                '}';
    }
}


