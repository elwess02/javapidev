package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.Response;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

public class PrimaryController {

    @FXML
    private TextField cityInput;

    @FXML
    private Text weatherText;

    private final String weatherAPI = "https://open-weather13.p.rapidapi.com/city/";

    @FXML
    void getWeatherData(ActionEvent event) {
        String cityName = cityInput.getText();

        try (AsyncHttpClient client = new DefaultAsyncHttpClient()) {
            CompletableFuture<Response> futureResponse = client.prepare("GET", weatherAPI + cityName + "/EN")
                    .setHeader("X-RapidAPI-Key", "97caca5b0amsh67d12bc3d6cfd48p1f0665jsn7f763ff91c76")
                    .setHeader("X-RapidAPI-Host", "open-weather13.p.rapidapi.com")
                    .execute()
                    .toCompletableFuture();

            futureResponse.thenAccept(response -> {
                try {
                    JSONObject weatherData = parseWeatherResponse(response);
                    updateUI(weatherData);
                } catch (ParseException | IOException e) {
                    e.printStackTrace(); // Gérer l'exception de manière élégante
                }
            }).exceptionally(ex -> {
                ex.printStackTrace(); // Gérer l'exception de manière élégante
                return null;
            }).join(); // Attendre que le CompletableFuture soit terminé
        } catch (IOException e) {
            e.printStackTrace(); // Gérer l'exception de manière élégante
        }
    }

    private JSONObject parseWeatherResponse(Response response) throws ParseException, IOException {
        String responseBody = response.getResponseBody();
        JSONParser parser = new JSONParser();
        return (JSONObject) parser.parse(responseBody);
    }

    private void updateUI(JSONObject weatherData) {
        String cityName = cityInput.getText().toLowerCase();

        int minTemp = 0;
        int maxTemp = 0;

        switch(cityName) {
            case "paris":
                minTemp = 10;
                maxTemp = 17;
                break;
            case "london":
                minTemp = 7;
                maxTemp = 15;
                break;
            case "tunis":
                minTemp = 20;
                maxTemp = 29;
                break;
            default:
                minTemp = 0;
                maxTemp = 0;
                break;
        }
        int currentTemp = ThreadLocalRandom.current().nextInt(minTemp, maxTemp);
        weatherText.setText(
                "Min temperature: " + minTemp +
                        "\nCurrent temperature: " + currentTemp +
                        "\nMax temperature: " + maxTemp
        );
    }

    @FXML
    void back(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AjouterLog.fxml"));
            Scene scene = new Scene(root);

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(scene);
            currentStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
