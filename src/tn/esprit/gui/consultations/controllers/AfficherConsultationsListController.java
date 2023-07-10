package tn.esprit.gui.consultations.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import tn.esprit.entities.Consulation;
import tn.esprit.services.ConsulationService;
import javafx.collections.FXCollections;
import javafx.scene.control.ListCell;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import tn.esprit.gui.consultations.Consultations;

public class AfficherConsultationsListController implements Initializable {
    @FXML
    AnchorPane consultationDetails ;
    AnchorPane consultationDetail ;
    @FXML
    private ListView<Consulation> listView;
    @FXML 
    Button newConsBtn ; 
    private int  consultationId ; 

    private final ConsulationService consultationService = new ConsulationService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populateConsultationsList();
    }
   public void populateConsultationsList() {
    List<Consulation> consultations = consultationService.afficher();
    if(!consultations.isEmpty()){
        listView.setItems(FXCollections.observableArrayList(consultations));

    // Set a cell factory to customize each item in the ListView
    listView.setCellFactory(param -> new ListCell<Consulation>() {
        @Override
        protected void updateItem(Consulation item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
                setGraphic(null);
                setStyle(null);
            } else {
                setText(item.getInformationToDisplay());

                // Add padding
                setStyle("-fx-padding: 5px;");

                // Set hover effect
                setOnMouseEntered(event -> setStyle("-fx-background-color: lightgray;"));
                setOnMouseExited(event -> setStyle(null));
                
                // Set an action when an item is clicked
                setOnMouseClicked(event -> {
                   loadConsultationInformation(item.getConsultation_id());
                });
            }
        }
    });
}
    }
    
    private void setNode(Node node ) {
        consultationDetails.getChildren().clear(); 
        consultationDetails.getChildren().add( (Node)node); 
        FadeTransition ft = new FadeTransition(Duration.millis(1500));
        ft.setNode(node);
        ft.setFromValue(0.5);
        ft.setToValue(1); 
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }

    public int getConsultationId() {
        return consultationId;
    }

    public void setConsultationId(int consultationId) {
        this.consultationId = consultationId;
        loadConsultationInformation(consultationId); 
    }
       public void loadConsultationInformation(int id ) {
        try {
            ConsulationService cs = new ConsulationService();
            Consulation consulation = cs.getConsultationById(id);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/afficherConsultation.fxml"));
            Parent root = loader.load();
            AfficherConsultationController ac = loader.getController();
            ac.setDate(consulation.getDate());
            ac.setDocId(consulation.getDoctor_id());
            ac.setIsPayed(consulation.getIsPayed());
            ac.setPrice(consulation.getPrice());
            ac.setPatientId(consulation.getPatient_id());
            ac.setTime(consulation.getTime());
            ac.setId(consulation.getConsultation_id());
            ac.setRapport(consulation.getRapport());
            Consultations.setTitle("Detail de la consultation"); 
            consultationDetail = (AnchorPane) root ; 
            setNode(consultationDetail);
        }
        catch( Exception e ) {
            System.out.println(e);
        }
    }
    public void createNewConsultation(){
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/HomePage.fxml"));
        Parent root = loader.load();
        HomePageController ac = loader.getController();
        Stage stage = (Stage) consultationDetails.getScene().getWindow(); // Replace `button` with your actual button object
        Scene scene = new Scene(root);
        stage.setScene(scene);
        ac.CreateNewConsultation();
        stage.show();

        } catch (IOException ex) {
            System.out.println(ex);
            Logger.getLogger(AfficherConsultationsListController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }   
    
}
