package entities;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.util.List;

public class PDFExporter {

    public static void exportToPDF(ObservableList<Evenement> data) {
        try {
            // Créer un nouveau document PDF
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);

            // Initialiser le contenu du PDF
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Ajouter le titre
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 18);
            contentStream.newLineAtOffset(100, 700);
            contentStream.showText("Liste des événements");
            contentStream.endText();

            // Dessiner le tableau des données
            drawTable(contentStream, 680, 100, data);

            // Fermer le contenu du PDF
            contentStream.close();

            // Sauvegarder le document PDF
            document.save("Liste_evenements.pdf");
            document.close();
            System.out.println("PDF généré avec succès !");
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Succès");
            successAlert.setHeaderText(null);
            successAlert.setContentText("PDF généré avec succès !");
            successAlert.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void drawTable(PDPageContentStream contentStream, float y, float margin, ObservableList<Evenement> evenements) throws IOException {
        final int rows = evenements.size() + 1; // Nombre de lignes (y compris la ligne de titre)
        final int cols = 9; // Nombre de colonnes (correspondant aux attributs de l'entité Evenement)
        final float rowHeight = 20f;
        final float tableWidth = 500f; // Largeur du tableau
        final float colWidth = tableWidth / (float) cols;
        final float cellMargin = 5f;

        // Définir la police
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);

// Définir la couleur
        contentStream.setNonStrokingColor(255, 165, 0); // Couleur orange : RVB(255, 165, 0)


        // Dessiner les cellules de titre
        String[] titles = {"Nom", "Date", "Heure", "Durée", "NbParticipants", "Lieu", "Type", "Organisateur", "Prix"};
        float nextY = y;
        float nextX = margin;
        for (String title : titles) {
            drawCell(contentStream, nextX, nextY, title, colWidth, rowHeight);
            nextX += colWidth;
        }

        // Dessiner les cellules de données
       nextY -= rowHeight;
        for (Evenement event : evenements) {
            nextX = margin;
       //   drawCell(contentStream, nextX, nextY, String.valueOf(event.getId_E()), colWidth, rowHeight);
       //   nextX += colWidth;
            drawCell(contentStream, nextX, nextY, event.getNom(), colWidth, rowHeight);
            nextX += colWidth;
            drawCell(contentStream, nextX, nextY, event.getDate(), colWidth, rowHeight);
            nextX += colWidth;
            drawCell(contentStream, nextX, nextY, event.getHeure(), colWidth, rowHeight);
            nextX += colWidth;
            drawCell(contentStream, nextX, nextY, event.getDure(), colWidth, rowHeight);
            nextX += colWidth;
            drawCell(contentStream, nextX, nextY, String.valueOf(event.getNbreparticipants()), colWidth, rowHeight);
            nextX += colWidth;
            drawCell(contentStream, nextX, nextY, event.getLieu(), colWidth, rowHeight);
            nextX += colWidth;
            drawCell(contentStream, nextX, nextY, event.getType(), colWidth, rowHeight);
            nextX += colWidth;
            drawCell(contentStream, nextX, nextY, event.getOrganisateur(), colWidth, rowHeight);
            nextX += colWidth;
            drawCell(contentStream, nextX, nextY, String.valueOf(event.getPrix()), colWidth, rowHeight);
            nextX += colWidth;
            //drawCell(contentStream, nextX, nextY, event.getImage(), colWidth, rowHeight);
          nextY -= rowHeight;
        }

        // Dessiner les bordures du tableau
        contentStream.setLineWidth(1f);
        contentStream.moveTo(margin, y);
        contentStream.lineTo(margin + tableWidth, y);
        contentStream.stroke();

        for (int i = 0; i <= rows; i++) {
            contentStream.moveTo(margin, y - (i * rowHeight));
            contentStream.lineTo(margin + tableWidth, y - (i * rowHeight));
            contentStream.stroke();
        }

        for (int i = 0; i <= cols; i++) {
            contentStream.moveTo(margin + (i * colWidth), y);
            contentStream.lineTo(margin + (i * colWidth), y - (rowHeight * rows));
            contentStream.stroke();
        }
    }


    private static void drawCell(PDPageContentStream contentStream, float x, float y, String text, float width, float height) throws IOException {
        contentStream.beginText();
        contentStream.newLineAtOffset(x + 2, y - 15);
        contentStream.showText(text);
        contentStream.endText();
    }
}
