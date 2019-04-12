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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tn.esprit.microinsurance.Entities.Heath_quotation;
import tn.esprit.microinsurance.Entities.Quotation;
import tn.esprit.microinsurance.Entities.Quotation.Status;
import tn.esprit.microinsurance.Services.Interf.QuoteRemote;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javafx.event.ActionEvent;

public class TreatedDetailsController implements Initializable {
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
	private Button showMore;
	@FXML
	private Button btnExit;
	@FXML
	private Button btnExit1;
	@FXML
	private TextField id; 
	@FXML
	private TextField name; 
	@FXML
	private TextField surname; 
	@FXML
	private TextField age; 
	@FXML
	private TextField phone; 
	@FXML
	private TextField mail; 
	@FXML
	private TextField marital; 
	@FXML
	private TextField occupation; 
	@FXML
	private TextField income; 
	@FXML
	private TextField job; 
	@FXML
	private TextField address; 
	@FXML
	private TextField postal; 
	@FXML
	private TextField mode; 
	@FXML
	private TextField insurance_type; 
	@FXML
	private TextField insurance_product; 
	@FXML
	private TextField date_beginning;
	@FXML
	private TextField date_end; 
	@FXML
	private TextField amount;
	@FXML
	private TextField status; 
	@FXML
	private AnchorPane objet;
	@FXML
	private Button Remove;
	@FXML
	private Button Return;
	private Quotation q ;

	public Quotation getQ() {
		return q;
	}

	public void setQ(Quotation q) throws ParseException {
		
		this.q = q;
		id.setText(""+q.getId());
		name.setText(""+q.getNom());
		surname.setText(""+q.getPrenom());
		age.setText(""+q.getAge());
		phone.setText(""+q.getPhone_Number());
		mail.setText(""+q.getMail());
		marital.setText(""+q.getMaritalStatus());
		job.setText(""+q.getOccupation());
		income.setText(""+q.getIncome());
		address.setText(""+q.getAddress());
		postal.setText(""+q.getCodePostale());
		mode.setText(""+q.getMode());
		insurance_type.setText(""+q.getProduct().getType());
		insurance_product.setText(""+q.getProduct().getLabel());
		//String s =(q.getQuotationBegining().toString());
		//System.out.println(s);
		date_beginning.setText(q.getQuotationBegining()+"");
		date_end.setText(q.getQuotationEnd()+"");
		
	
		/*Date mydate =q.getQuotationBegining();
		System.out.println(mydate);
		SimpleDateFormat dt = new SimpleDateFormat("MM-dd-yyyy"); 
         String strDate = dt.format(mydate);
         System.out.println(strDate);
		 date_beggining.setText(strDate);*/
	

		status.setText(""+q.getStatus());
		
		

		

		

		
		
	}


	// Event Listener on Button[#btnExit1].onAction
	@FXML
	public void ExitTheApp(ActionEvent event) {
		Stage stage;
        stage=(Stage) btnExit1.getScene().getWindow();
    	stage.close();
		 
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
		
		
		
	}
	public void setKhorm(){
		System.out.println(q.getId()+"++++++++++++++++++");
		
	}
	@FXML
	public void RemoveQuotations()
	{
		String jndiname="MicroInsurance-ear/MicroInsurance-ejb/QuoatationImpl!tn.esprit.microinsurance.Services.Interf.QuoteRemote";
		Context context=null;
		try {
			context = new InitialContext();
			QuoteRemote proxy=(QuoteRemote) context.lookup(jndiname);
			
			//int i = TableViewQuote.getSelectionModel().getSelectedIndex(); //index
			Quotation.Status s=q.getStatus();
			System.out.println("******"+s);
			Status s1 = Status.Untreated;
			if ( s.equals(s1)){
				System.out.println("untreated");
				Alert alert = new Alert(Alert.AlertType.WARNING);
	            alert.setTitle(" Status Removing Error ");
	            alert.setHeaderText(" An Untreated Quotation can't be removed");
	            alert.showAndWait();
			}
			else{
				
				int id=q.getId();
				proxy.removeQuoteees(id);
				System.out.println("okkk"+id);
			}
		} catch (NamingException e1) {
			
			e1.printStackTrace();
		}
	}
	@FXML
	public void ShowMore (ActionEvent event) throws IOException
	{
		if(status.equals(q.getStatus().Untreated))
		{
		if(q.getMode().equals("ExpressQuotation"))
		{
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(" No more Details ");
            alert.setHeaderText("Express Quotation");
            alert.showAndWait();
		}
		else
		{
			if(q.getProduct().getType().equals("LifeMicroInsurance"))
			{
				FXMLLoader root = new FXMLLoader(getClass().getResource("/tn/esprit/microInsurance/views/LifeDetails.fxml"));
		    	AnchorPane x = root.load();
		    	HealthDetailsController c = new HealthDetailsController();
		    	c.setQ((Heath_quotation)q);
		    	objet.getChildren().clear();
		    	objet.getChildren().add((Node) x);
			}
		}
		}
		else
		{
			System.out.println("treaaaaaaaaaaaaaaated");
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("REMINDER");
            alert.setHeaderText("The quotation is treated no need  to show more details");
            alert.showAndWait();
		}
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
