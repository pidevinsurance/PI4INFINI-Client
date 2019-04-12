package tn.esprit.microInsurance.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javafx.event.ActionEvent;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tn.esprit.microinsurance.Entities.Heath_quotation;
import tn.esprit.microinsurance.Entities.InsuranceRequest;

import tn.esprit.microinsurance.Entities.QuotationMode;
import tn.esprit.microinsurance.Services.Interf.QuoteRemote;

public class HealthDetailsController implements Initializable {
	@FXML
	private AnchorPane objet;
	@FXML
	private Button btnOverview;
	@FXML
	private Button btnOrders;
	@FXML
	private Button btnCustomers;
	@FXML
	private Button btnMenus;
	@FXML
	private Button btnPackages;
	@FXML
	private Button btnSettings;
	@FXML
	private Button btnExit;
	@FXML
	private Button btnExit1;
	@FXML
	private TextField diabetes;
	@FXML
	private TextField mentalIllness;
	@FXML
	private TextField suddenDeaths;
	@FXML
	private TextField overweight;
	@FXML
	private TextField hypertension;
	@FXML
	private TextField Surgeries_number;
	@FXML
	private TextField Hospitalisations_number;
	@FXML
	private TextField longTermTreatments;
	@FXML
	private TextField chronic_diseases;
	@FXML
	private Button CalculateQuotation;
	@FXML
	private Button InsuranceRequest;
	private Heath_quotation q;
	private InsuranceRequest ir;
	private static int score=0;
    private static float amount;
	// Event Listener on Button[#btnExit1].onAction
	@FXML
	public void ExitTheApp(ActionEvent event) {
		Stage stage;
        stage=(Stage) btnExit1.getScene().getWindow();
    	stage.close();
	}
	// Event Listener on Button[#CalculateQuotation].onAction
	@FXML
	public void CalculateQuotation(ActionEvent event) throws NamingException {
		String jndiName = "MicroInsurance-ear/MicroInsurance-ejb/QuoatationImpl!tn.esprit.microinsurance.Services.Interf.QuoteRemote";
		Context context;
		context = new InitialContext();
		QuoteRemote proxy = (QuoteRemote) context.lookup(jndiName);
		int risk_rate=CalculateScore()/100;
        if(risk_rate<0.5)
        {
        	amount=50;
        }
        else
        {
        	
        	amount=100;
        }
    	q.setAmount(amount);
    	proxy.updateStatus(q);
	}
	public Heath_quotation getQ() {
		return q;
	}
	public void setQ(Heath_quotation q) { 
		this.q = q;
		diabetes.setText(""+q.getDiabetes());
		mentalIllness.setText(""+q.getMentalIllness());
		suddenDeaths.setText(""+q.getSuddenDeaths());
		overweight.setText(""+q.getOverweight());
		hypertension.setText(""+q.getHypertension());
		Surgeries_number.setText(""+q.getSurgeries_number());
		Hospitalisations_number.setText(""+q.getHospitalisations_number());
		longTermTreatments.setText(""+q.getLongTermTreatments());
		chronic_diseases.setText(""+q.getChronic_diseases());
		score=CalculateScore();
		
	}
	public  int CalculateScore ()
	{
		
		if(q.getDiabetes()==false)
		{
			score+=10;
		}
		if(q.getMentalIllness()==false)
		{
			score+=10;
		}
		if(q.getSuddenDeaths()==false)
		{
			score+=10;
		}
		if(q.getOverweight()==false)
		{
			score+=10;
		}
		if(q.getHypertension()==false)
		{
			score+=10;
		}
		if(q.getSurgeries_number()==0)
		{
			score+=10;
		}
		if(q.getHospitalisations_number()==0)
		{
			score+=10;
		}
		if(q.getLongTermTreatments()==false)
		{
			score+=15;
		}
		if(q.getChronic_diseases()==0)
		{
			score+=15;
		}
		System.out.println(" health"+score);
		return score;
		
	}
	
	@FXML
	public void InsuranceRequest(ActionEvent event) throws IOException{
		QuotationMode mode=q.getMode();
		
		if(mode.equals(q.getMode().CustomisedQuotation))
		{
			System.out.println("///////////////////"+mode+"///////////////////");
			/*FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/microInsurance/views/InsuranceRequestView.fxml"));
	        //Parent root = loader.load();
			Parent root=loader.load();
	        Scene tableViewScene = new Scene(root);
	        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
	    	window.setScene(tableViewScene);
	    	window.show();
	        InsuranceRequestController lf = loader.getController();
	        lf.setQ((Heath_quotation)q);*/
			
			
			/*FXMLLoader loader = new FXMLLoader();
			loader.setClassLoader(getClass().getClassLoader()); // set the plugin's class loader
			loader.setLocation(getClass().getResource("/fxml/PluginFXML.fxml"));*/
			
			FXMLLoader root = new FXMLLoader(getClass().getResource("/tn/esprit/microInsurance/views/InsuranceRequestView.fxml"));
		
			System.out.println("ahlaaaa");
			//System.out.println(ir.getRequest_id());
			//c.setIr(ir);
			AnchorPane x = root.load(); 

			InsuranceRequestViewController c = root.getController();
			System.out.println(q.getId());
			c.setId(q.getId());
			c.SetScore(score);
			//c.setIr(ir.getRequest_id());
			c.init();
			
			
			//c.init();
			
	    	
	    	System.out.println("c bn");
	    	 
	    	
	    	objet.getChildren().clear();
	    	objet.getChildren().add((Node) x);
		}
		else
		{
			Alert alert = new Alert(Alert.AlertType.NONE);
            alert.setTitle(" Express Quotation");
            alert.setHeaderText("There is no insrance request!");
            alert.showAndWait();
		}
		
	}
	public static int getScore() {
		return score;
	}
	public static void setScore(int score) {
		HealthDetailsController.score = score;
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	 @FXML
	    void Return(ActionEvent event) throws IOException {
		 
		    Parent home_page_parent =   FXMLLoader.load(getClass().getResource("/tn/esprit/microInsurance/views/QuotesView.fxml"));
	        Scene home_page_scene = new Scene(home_page_parent);
	        Stage app_stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
	       app_stage.hide(); //optional
	       app_stage.setScene(home_page_scene);
	       app_stage.show();
	    

	    	

	    }
	
}
