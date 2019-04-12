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
import java.util.List;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.ListView;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tn.esprit.microinsurance.Entities.Heath_quotation;
import tn.esprit.microinsurance.Entities.InsuranceRequest;
import tn.esprit.microinsurance.Entities.Justificatory;
import tn.esprit.microinsurance.Entities.Quotation;
import tn.esprit.microinsurance.Entities.User;
import tn.esprit.microinsurance.Services.Interf.QuoteRemote;

public class InsuranceRequestViewController implements Initializable {
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
	private TextField demandDate;
	@FXML
	private TextField requestDescription;
	@FXML
	private Button reject;
	@FXML
	private Button validate;
	@FXML
	private TextField score;
	@FXML
	private ListView<String> listViewJustif;
	private Quotation q;
	private int ir;
	private int id ;
	private int scoreval;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	System.out.println("hatitou"+id);
	}

	ObservableList<String> items = FXCollections.observableArrayList();

	// Event Listener on Button[#btnExit1].onAction
	@FXML
	public void ExitTheApp(ActionEvent event) {
		Stage stage;
        stage=(Stage) btnExit1.getScene().getWindow();
    	stage.close();
	}

	// Event Listener on Button[#reject].onAction
	@FXML
	public void reject(ActionEvent event) throws NamingException {
		
		String jndiName = "MicroInsurance-ear/MicroInsurance-ejb/QuoatationImpl!tn.esprit.microinsurance.Services.Interf.QuoteRemote";
		Context context;
		context = new InitialContext();
		QuoteRemote proxy = (QuoteRemote) context.lookup(jndiName);
		Quotation qu= proxy.findQuotationById(id);
		InsuranceRequest insurance_request = proxy.findRequestsByQuotation(id);
		List<Justificatory> list = proxy.findJustificatisByRequest(insurance_request.getRequest_id());
		
		if((scoreval<50 )||( list.size()==0) )
		{
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
	        alert.setTitle("Request Rejection");
	        alert.setHeaderText("Request rejected!");
	        alert.showAndWait();
	        alert.close();
		}
			
		
	}

	// Event Listener on Button[#validate].onAction
	@FXML
	public void validate(ActionEvent event) throws NamingException {
		String jndiName = "MicroInsurance-ear/MicroInsurance-ejb/QuoatationImpl!tn.esprit.microinsurance.Services.Interf.QuoteRemote";
		Context context;
		context = new InitialContext();
		QuoteRemote proxy = (QuoteRemote) context.lookup(jndiName);
		Quotation qu= proxy.findQuotationById(id);
		InsuranceRequest insurance_request = proxy.findRequestsByQuotation(id);
		List<Justificatory> list = proxy.findJustificatisByRequest(insurance_request.getRequest_id());
		
		if(scoreval>50 && list.size()!=0 )
		{
			User u = new User();
			System.out.println("adress"+qu.getAddress());
		    u.setAdress(qu.getAddress());
			u.setAge(qu.getAge());
			u.setCodePostale(qu.getCodePostale());
			u.setIncome(qu.getIncome());
			u.setMail(qu.getMail());
			u.setMaritalStatus(qu.getMaritalStatus());
			u.setOccupation(qu.getOccupation());
			u.setNom(qu.getNom());
			u.setPrenom(qu.getPrenom());
			u.setPhone_Number(qu.getPhone_Number());
			proxy.addUser(u);
			insurance_request.setValide(true);
			proxy.updateInsuranceRequest(insurance_request);
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Succesful Operation");
            alert.setHeaderText("User Added");
            alert.showAndWait();
            alert.close();
		}
		else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add error");
            alert.setHeaderText("There is no Justificatories or score not allowed!");
            alert.showAndWait();
            alert.close();}
	}

	public Quotation getQ() {
		return q;
	}

	public void setQ(Heath_quotation q) {
		this.q = q;
		score.setText("" + q.getScore());
	}
	

	public int getIr() {
		return ir;
	}

	public void setIr(int ir) {
		this.ir = ir;
		// demandDate.setText(""+ir.getDemanddate());
		//requestDescription.setText("" + ir.getRequestDescription());
	} 
	public void init (){
		System.out.println("ssssss");
		try {
			Context context;
			String jndiName = "MicroInsurance-ear/MicroInsurance-ejb/QuoatationImpl!tn.esprit.microinsurance.Services.Interf.QuoteRemote";
			context = new InitialContext();
			QuoteRemote proxy = (QuoteRemote) context.lookup(jndiName);
			 SetScore(scoreval);
			 Quotation q2 = proxy.findQuotationById(id); 
			 q2.setScore(scoreval);
			 proxy.updateStatus(q2);
			
			
			System.out.println("ssssss");
			System.out.println(this.id);
			InsuranceRequest insurance_request = proxy.findRequestsByQuotation(id);
			int id1 = insurance_request.getRequest_id();
			List<Justificatory> list = proxy.findJustificatisByRequest(id1);
			for (Justificatory i : list) {
				System.out.println("++++++++++++++"+i.getJustificatory_Id());
				items.add("" + i.getFile() + "");
			listViewJustif.setItems(items);
				//listViewJustif.getItems();
			}if(insurance_request.getDemanddate() != null)
			{
			Quotation q1 = proxy.findQuotationById(id); 
			//score.setText(""+q1.getScore());
			requestDescription.setText(insurance_request.getRequestDescription());
			demandDate.setText(insurance_request.getDemanddate().toString());
			}
			else
			{
				Alert alert = new Alert(Alert.AlertType.NONE);
	            alert.setTitle(" Customised Quotation");
	            alert.setHeaderText("There is no insrance request!");
	            alert.showAndWait();
			}
			

		} catch (NamingException e) {

			e.printStackTrace();
		}
	}

	
	
	public void SetScore(int scoreVAL)
	{
		this.scoreval=scoreVAL;
		this.score.setText(scoreval+"");
		System.out.println(scoreval+"scoreeeee");
	}
	
	
	
	
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	

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
