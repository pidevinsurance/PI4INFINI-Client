package tn.esprit.microInsurance.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tn.esprit.microinsurance.Entities.MailSender;
import javax.mail.MessagingException;
import tn.esprit.microinsurance.Entities.Heath_quotation;
import tn.esprit.microinsurance.Entities.Life_quotation;
import tn.esprit.microinsurance.Entities.MailSender;
import tn.esprit.microinsurance.Entities.ProductType;
import tn.esprit.microinsurance.Entities.Property_quotation;
import tn.esprit.microinsurance.Entities.Quotation;
import tn.esprit.microinsurance.Entities.Quotation.Status;
import tn.esprit.microinsurance.Services.Interf.QuoteRemote;
import tn.esprit.microinsurance.Entities.QuotationMode;
import tn.esprit.microinsurance.Entities.TypesMicroInsurance;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.remoting3.MessageInputStream;

import javafx.event.ActionEvent;

public class UntreatedDetailsController implements Initializable{
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
	private Button showMore;
	@FXML
	private TextField id; 
	@FXML
	private TextField name; 
	@FXML
	private TextField surname; 
	@FXML
	private TextField age; 
	@FXML
	private TextField Phone; 
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
	private TextField date_beggining; 
	@FXML
	private TextField date_end; 
	@FXML
	private TextField amount;
	@FXML
	private TextField status; 
	@FXML
	private AnchorPane objet;
	@FXML 
	private RadioButton radiobtnTreated;
	@FXML
	private Label label;
	
	
	
	
	private Quotation q ;

	public Quotation getQ() {
		return q;
	}

	public void setQ(Quotation q) {
		this.q = q;
		System.out.println("-----------------------------"+q.getStatus());
		id.setText(""+q.getId());
		name.setText(""+q.getNom());
		surname.setText(""+q.getPrenom());
		age.setText(""+q.getAge());
		Phone.setText(""+q.getPhone_Number());
		mail.setText(""+q.getMail());
		marital.setText(""+q.getMaritalStatus());
		job.setText(""+q.getOccupation());
		income.setText(""+q.getIncome());
		address.setText(""+q.getAddress());
		postal.setText(""+q.getCodePostale());
		mode.setText(""+q.getMode());
		insurance_type.setText(""+q.getProduct().getType());
		insurance_product.setText(""+q.getProduct().getLabel());
		date_beggining.setText(q.getQuotationBegining()+"");
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
	public void setKhorm(){
		System.out.println(q.getId()+"++++++++++++++++++");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//System.out.println(this.getQ().getStatus());
		
		
	}
	@FXML
	public void ShowMore (ActionEvent event) throws IOException
	{
		QuotationMode mode=q.getMode();
		TypesMicroInsurance type=q.getProduct().getType();
		Quotation.Status status=q.getStatus();
		if(status.equals(q.getStatus().Untreated))
		{
		if(mode.equals(q.getMode().ExpressQuotation))
		{
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(" No more Details ");
            alert.setHeaderText("Express Quotation");
            alert.showAndWait();
		}
		
		
		else if(type.equals(q.getProduct().getType().LifeMicroInsurance) )
			{
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/microInsurance/views/LifeDetails.fxml"));
		        Parent root = loader.load();
		        Scene tableViewScene = new Scene(root);
		        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		    	window.setScene(tableViewScene);
		    	window.show();
		        LifeDetailsController lf = loader.getController();
		        Life_quotation q1=(Life_quotation)q;
		        System.out.println(q1.getInitial_amount()+"+++++++++++++++++++++++++++++++");
		    
		        lf.setQ((Life_quotation)q);
					
		  }
		else if(type.equals(q.getProduct().getType().PropretyMicroInsurance) )
		{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/microInsurance/views/PropertyDetails.fxml"));
	        Parent root = loader.load();
	        Scene tableViewScene = new Scene(root);
	        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
	    	window.setScene(tableViewScene);
	    	window.show();
	        PropertyDetailsController lf = loader.getController();
	        lf.setQ((Property_quotation)q);
				
	  }
		else if(type.equals(q.getProduct().getType().HealthMicroInsurance) )
		{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/microInsurance/views/HealthDetails.fxml"));
	        Parent root = loader.load();
	        Scene tableViewScene = new Scene(root);
	        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
	    	window.setScene(tableViewScene);
	    	window.show();
	        HealthDetailsController lf = loader.getController();
	        Heath_quotation q1 = (Heath_quotation)q;
	        lf.setQ(q1);
	        
				
	  }
		else 
			System.out.println("nothing");

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
	private void SetToTreated (ActionEvent event) throws NamingException 
	{
		String jndiName = "MicroInsurance-ear/MicroInsurance-ejb/QuoatationImpl!tn.esprit.microinsurance.Services.Interf.QuoteRemote";
		Context context = new InitialContext();
		QuoteRemote proxy = (QuoteRemote) context.lookup(jndiName);
		if(radiobtnTreated.isSelected()==true)
		{
			System.out.println("hiiiiiiiiiiiiiiiiiii");
			
			Status s=q.getStatus().Treated;
			q.setStatus(s);
			proxy.updateStatus(q);
			try
			{
			MailSender.sendMail("smtp.gmail.com","587","PICAKXE.MICROINSURANCE@gmail.com","PICAKXE.MICROINSURANCE@gmail.com","MICROESPRIT2019",q.getMail(),"MicroInsurance","Your quotation is Treated ! You can consult it when ever you want");
			}catch(MessagingException e)
			{
				e.printStackTrace();
				
			}
			status.setText(""+s);
			//MailSender.sendMail("smtp.gmail.com","587","rania.laouini@esprit.tn","rania.laouini@esprit.tn","lrania@esprit","abir.lahmar@esprit.tn","MicroInsurance","Your quotation is Treated ! You can consult it when ever you want");
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("INFO");
            alert.setHeaderText("STATUS CHANGED");
            alert.showAndWait();
	    }
   }
	 @FXML
	    void Return(ActionEvent event) throws IOException {
		   
	    	FXMLLoader root = new FXMLLoader(getClass().getResource("/tn/esprit/microInsurance/Views/Quotesview.fxml"));
			AnchorPane x = root.load();
			System.out.println("c bn");
			//UntreatedController c = root.getController();
			objet.getChildren().clear();
			objet.getChildren().add((Node) x);

	    	

	    }
		
	
	
	
	
}

