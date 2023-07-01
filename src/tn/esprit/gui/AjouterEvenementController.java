package tn.esprit.gui;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.entities.Evenement;
import tn.esprit.entities.User;
import tn.esprit.services.EvenementService;

public class AjouterEvenementController implements Initializable {

    @FXML
    private TextField TextOrganizer;
    @FXML
    private TextField TextName;
    @FXML
    private DatePicker  TextDate;
    @FXML
    private TextField TextTime;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO: Initialization code
    }

   @FXML
private void AddEvenement(ActionEvent event) {
    String organizerText = TextOrganizer.getText();
    String eventName = TextName.getText();
    LocalDate eventDate = TextDate.getValue();
    String eventTimeText = TextTime.getText();

    if (organizerText.isEmpty() || eventName.isEmpty() || eventDate == null || eventTimeText.isEmpty()) {
        // Show an error message to the user indicating that all fields must be filled
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de saisie");
        alert.setHeaderText(null);
        alert.setContentText("Tous les champs sont obligatoires.");
        alert.showAndWait();
        return;
    }

    int organizer_id;
    try {
        organizer_id = Integer.parseInt(organizerText);
    } catch (NumberFormatException e) {
        // Show an error message to the user indicating that the organizer ID must be a number
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de saisie");
        alert.setHeaderText(null);
        alert.setContentText("ID de l'organisateur doit être un nombre entier.");
        alert.showAndWait();
        return;
    }

    // Convert eventTimeText to LocalTime
    LocalTime eventTime;
    try {
        eventTime = LocalTime.parse(eventTimeText);
    } catch (DateTimeParseException e) {
        // Show an error message to the user indicating that the event time is in an invalid format
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de saisie");
        alert.setHeaderText(null);
        alert.setContentText("Heure de l'événement est dans un format invalide.");
        alert.showAndWait();
        return;
    }

    User organizer = new User();
    organizer.setUser_id(organizer_id);
    Evenement events = new Evenement(organizer, eventName, eventDate, eventTime);
    EvenementService eventService = new EvenementService();
    eventService.ajouter(events);

    // Close the current window
    try {
                FXMLLoader loader = new  FXMLLoader(getClass().getResource("afficher.fxml"));

                Parent root = loader.load();
                Stage stage = (Stage) TextOrganizer.getScene().getWindow(); // Replace button with your actual button object
        Scene scene = new Scene(root);
        stage.setScene(scene);
        
        stage.show();
               
                
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
}

    @FXML
    private void Annuler(ActionEvent event) {
        // Close the current window
        TextOrganizer.getScene().getWindow().hide();
    }

}