package tn.esprit.microInsurance.controllers;
//import java.awt.event.ActionEvent;
import java.net.URL;
//import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import tn.esprit.microinsurance.Entities.Quotation;
import tn.esprit.microinsurance.Entities.Quotation.Status;
import tn.esprit.microinsurance.Services.Interf.QuoteRemote;


public class QuotationController implements Initializable {


	ObservableList<String> items = FXCollections.observableArrayList();
	List<Quotation> QuotationRequests=null;


    @FXML
    private Button btnPackages;

    @FXML
    private Button btnSettings;

    @FXML
    private Button btnExit1;

    @FXML
    private Label TodaysRequest;

    @FXML
    private Label TotalRequests;

    @FXML
    private Label TreatedRequests;

    @FXML
    private Label lblUntreatedRequests;

    @FXML
    private TextField txtSearch;

    @FXML
    private ImageView imgSearch;

    @FXML
    private ListView<String> listQuotationRequests;

    @FXML
    private Button btnTreated;

    @FXML
    private Button btnUntreated;
    @FXML
    private ComboBox<Status> comboBoxStatus;
    
    public ObservableList<Status> comboList = FXCollections.observableArrayList(Status.Treated,Status.Untreated);
  	    int todaysRequest =0;
	
		int UntreatedRequests = 0;
		
		int treatedRequests = 0;
    public List<Quotation> ServiceGetAllQuotations() throws Exception
	{
		String jndiName = "MicroInsurance-ear/MicroInsurance-ejb/QuoatationImpl!tn.esprit.microinsurance.Services.Interf.QuoteRemote";
		Context context = new InitialContext();
		QuoteRemote proxy = (QuoteRemote) context.lookup(jndiName);
	
		
		return proxy.findAllQuotations();
			
	}
    
    
    
    
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	
		comboBoxStatus.setItems(comboList);
    	try {
    		 QuotationRequests = ServiceGetAllQuotations();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	for(Quotation i : QuotationRequests ){
			 
    		items.add("  "+i.getNom() +"      	           "+ i.getPrenom()+"                 	  "+i.getProduct().getType()+" 	      	                   " + i.getProduct().getLabel() + "   	                              " + i.getPhone_Number() ); 
    		
    		System.out.println(i.getId());
    		System.out.println(items.size());
    						
    		listQuotationRequests.setItems(items);
    		listQuotationRequests.getItems();
    		
    		/*listQuotationRequests.setOnMouseClicked(new EventHandler<MouseEvent>() {
			

        	    @Override
        	    public void handle(MouseEvent click) {

        	        if (click.getClickCount() == 2) {
        	           //Use ListView's getSelected Item
        	          String currentItemSelected = listQuotationRequests.getSelectionModel().getSelectedItem();
        	                                                    
        	         System.out.println(currentItemSelected);
        	          //System.out.println(i.getId());
        	           //use this to do whatever you want to. Open Link etc.
        	          
        	         
        	          
        	        }
        	    }
        	});*/
    		
    	}
    	TotalRequests.setText(QuotationRequests.size()+"");
  
    	for(Quotation i : QuotationRequests)
    	{
    		
    		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    		java.util.Date date = new java.util.Date();
    		
    		String dateNow = formatter.format(date);
    		String dateQuotationRequest = i.getQuotationBegining().toString();
    		
    		if(dateNow.equals(dateQuotationRequest))
    			todaysRequest++;
    		
    		
			if(i.getStatus()==Status.Untreated)
    			UntreatedRequests++ ;
			
		
    	
			if(i.getStatus()==Status.Treated)
    			treatedRequests++;

    	}
    	
    	//System.out.println("traite"+todaysRequest);
    	//System.out.println("nn traite"+ UntreatedRequests);
		//System.out.println("traite"+ treatedRequests);
    	TodaysRequest.setText(todaysRequest+"");
    	lblUntreatedRequests.setText(UntreatedRequests+"");
    	TreatedRequests.setText(treatedRequests+"");
    }
    @FXML
    void GetAllRequests(ActionEvent event) {
    	listQuotationRequests.getItems().clear();
    	
    	try {
    		QuotationRequests= ServiceGetAllQuotations();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
   
    	for(Quotation i : QuotationRequests){
    	
    			
    			items.add("  "+i.getNom() +"      	           "+ i.getPrenom()+"                 	  "+i.getProduct().getType()+" 	      	                   " + i.getProduct().getLabel() + "   	                              " + i.getPhone_Number() ); 
        		
        		listQuotationRequests.setItems(items);
        		listQuotationRequests.getItems();
    	}
    }		
    
    @FXML
    void GetTreatedRequests(ActionEvent event) {
    	listQuotationRequests.getItems().clear();
    	
    	try {
    		QuotationRequests= ServiceGetAllQuotations();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	
    	for(Quotation i : QuotationRequests){
    		
    		if(i.getStatus()==Status.Treated)
    		{
    			
    			items.add("  "+i.getNom() +"      	           "+ i.getPrenom()+"                 	  "+i.getProduct().getType()+" 	      	                   " + i.getProduct().getLabel() + "   	                              " + i.getPhone_Number() ); 
        		
        		//System.out.println(i.getId());
        		//System.out.println(items.size());
       						
        		listQuotationRequests.setItems(items);
        		listQuotationRequests.getItems();
    		}
    	}
    }

    @FXML
    void GetUntreatedRequests(ActionEvent event) {
    	listQuotationRequests.getItems().clear();
    	
    	try {
    		QuotationRequests= ServiceGetAllQuotations();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	
    	for(Quotation i : QuotationRequests){
    		
    		if(i.getStatus()==Status.Untreated)
    		{
    			
    			items.add("  "+i.getNom() +"      	           "+ i.getPrenom()+"                 	  "+i.getProduct().getType()+" 	      	                   " + i.getProduct().getLabel() + "   	                              " + i.getPhone_Number() ); 
        		
        		System.out.println(i.getId());
        		System.out.println(items.size());
       						
        		listQuotationRequests.setItems(items);
        		listQuotationRequests.getItems();
    		}
    	}

    }

    @FXML
    void RemoveQuotation(ActionEvent event) throws Exception
    {
    	QuotationRequests = ServiceGetAllQuotations();
    	
    	String jndiname="MicroInsurance-ear/MicroInsurance-ejb/QuoatationImpl!tn.esprit.microinsurance.Services.Interf.QuoteRemote";
		Context context=null;
		try {
			context = new InitialContext();
		} catch (NamingException e1) {
			
			e1.printStackTrace();
		}
		try {
			QuoteRemote proxy=(QuoteRemote) context.lookup(jndiname);
			int i = listQuotationRequests.getSelectionModel().getSelectedIndex(); //index
			int id=ServiceGetAllQuotations().get(i).getId();
			for(Quotation q : QuotationRequests)
			{if(q.getId()==id)
			
			System.out.println("index"+i);
			if(ServiceGetAllQuotations().get(i).getStatus()==Status.Untreated)
			{proxy.removeQuoteees(id);
			System.out.println("index"+i);}
			else
			{
				Alert alert = new Alert(Alert.AlertType.WARNING);
	            alert.setTitle(" Status Removing Error ");
	            alert.setHeaderText(" An Untreated Quotation can't be removed");
	            alert.showAndWait();
			}
			}
		} catch (NamingException e) {
			
			e.printStackTrace();
		}
    }
	
    @FXML
    void ExitTheApp(ActionEvent event) {
    	
    	Stage stage;
        stage=(Stage) btnExit1.getScene().getWindow();
    	stage.close();

    }
    	
    	
	 @FXML
	    void ActionUpdate(ActionEvent event) throws Exception {
	    	
	    	String jndiname="MicroInsurance-ear/MicroInsurance-ejb/QuoatationImpl!tn.esprit.microinsurance.Services.Interf.QuoteRemote";
			Context context=null;
			try {
				context = new InitialContext();
			} catch (NamingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				int i = listQuotationRequests.getSelectionModel().getSelectedIndex();
				QuoteRemote proxy=(QuoteRemote) context.lookup(jndiname);
				proxy.UpdateQuotationStatus(ServiceGetAllQuotations().get(i).getId(),comboBoxStatus.getValue());
				listQuotationRequests.refresh();
				
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
	    }	
	 
	 @FXML
	 void SearchQuotation(ActionEvent event) {
	    	listQuotationRequests.getItems().clear();
	    	
	    	try {
	    		QuotationRequests= ServiceGetAllQuotations();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	
	    	
	    	for(Quotation i : QuotationRequests){
	    		
	    		if(i.getNom()==txtSearch.getText())
	    		{
	    			System.out.println(txtSearch.getSelectedText() + "text");
	    			
	    			items.add("  "+i.getNom() +"      	           "+ i.getPrenom()+"                 	  "+i.getProduct().getType()+" 	      	                   " + i.getProduct().getLabel() + "   	                              " + i.getPhone_Number() ); 
	        		
	        		System.out.println(i.getId());
	        		System.out.println(items.size());
	       						
	        		listQuotationRequests.setItems(items);
	        		listQuotationRequests.getItems();
	    		}
	    	}
	    }

	 
	
	
	
	
 	

}
