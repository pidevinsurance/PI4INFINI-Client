package tn.esprit.microInsurance.controllers;

import java.awt.font.TextHitInfo;
import java.net.URL;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.Window;
import tn.esprit.microinsurance.Entities.TechnicalAgent;
import tn.esprit.microinsurance.services.Interfaces.ITechnicalAgentServiceRemote;

public class AffectTechnicalAgentController implements Initializable{
	

    @FXML
    private ComboBox<String> cmbxAffectTechnicalAgent;

    @FXML
    private Button btnConfirmTechnicalAgentAffect;

    @FXML
    private ImageView btnReturn;
    
    
    List<TechnicalAgent>ListTechnicalAgents;
    
    
    
    public List<TechnicalAgent> ServiceGetAllTechnicalAgents() throws Exception
	{
		String jndiName = "MicroInsurance-ear/MicroInsurance-ejb/TechnicalAgentServiceImpl!tn.esprit.microinsurance.services.Interfaces.ITechnicalAgentServiceRemote";
		Context context = new InitialContext();
		ITechnicalAgentServiceRemote proxy = (ITechnicalAgentServiceRemote) context.lookup(jndiName);
		
		
		return proxy.getAllTechnicalAgents();
			
	}
    
    
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
		
    	try {
			ListTechnicalAgents = ServiceGetAllTechnicalAgents();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	for(TechnicalAgent t : ListTechnicalAgents)
    	{
    		cmbxAffectTechnicalAgent.getItems().add(t.getUsername());
    		
    	}
    	
    	cmbxAffectTechnicalAgent.getSelectionModel().select(0);
    	
    	
    	
    	
    	btnReturn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
            	
            	
            	Stage stage;
                stage=(Stage) btnReturn.getScene().getWindow();
            	stage.close();
            	
                event.consume();
            }
       });
    	
    	
		
	}
    

    @FXML
    void AffectTechnicalAgent(ActionEvent event) {

    }

    @FXML
    void ConfirmTechnicalAgentAffect(ActionEvent event) {

    }

	
}
