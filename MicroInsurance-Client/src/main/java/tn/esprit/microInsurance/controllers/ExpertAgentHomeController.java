package tn.esprit.microInsurance.controllers;



import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.util.Callback;
import tn.esprit.microinsurance.Entities.Indemnity_Request;
import tn.esprit.microinsurance.Entities.Sinister;
import tn.esprit.microinsurance.services.Interfaces.IIndemnityRequestServiceRemote;

public class ExpertAgentHomeController implements Initializable{
	
	ObservableList<String> items = FXCollections.observableArrayList();
	List<Indemnity_Request> sninistersRequest = null;
	
	 	@FXML
	    private Button btn;
	 
	   // @FXML
	   //private ListView<Indemnity_Request> listsinistersRequests;
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
	    private Button btnSignout;
	    
	    @FXML
	    private Label lblTodaysRequest;
	    
	    @FXML
	    private Label lblTotalRequests;

	    @FXML
	    private Label lblUntratedRequests;
	    
	    @FXML
	    private Label lblTreatedRequests;
	    
	    @FXML
	    private Button btnTreated;

	    @FXML
	    private Button btnUntreated;
	    
	    @FXML
	    private TextField txtSearch;
	    
	    @FXML
	    private ImageView imgSearch;

	    @FXML
	    private ListView<String> listSinisterRequests;

	    

	   
    
    
    public List<Indemnity_Request> ServiceGetAllIndemnityRequest() throws Exception
	{
		String jndiName = "MicroInsurance-ear/MicroInsurance-ejb/IndemnityRequestServiceImpl!tn.esprit.microinsurance.services.Interfaces.IIndemnityRequestServiceRemote";
		Context context = new InitialContext();
		IIndemnityRequestServiceRemote proxy = (IIndemnityRequestServiceRemote) context.lookup(jndiName);
		
		
		return proxy.getAllIndemnityRequest();
			
	}
    
    
    
    
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	
		
    	try {
			sninistersRequest = ServiceGetAllIndemnityRequest();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	
    	for(Indemnity_Request i : sninistersRequest ){
    			
    		items.add(""+i.getUser().getPrenom() +" "+ i.getUser().getNom()+"       "+i.getIndemnity_date()+"       " + i.getIndemnity_Description() + "       " + i.getUser().getMail() + "       " + i.getUser().getPhone_Number() ); 
    		
    		System.out.println(i.getIndemnityRequest_id());
    						
    		listSinisterRequests.setItems(items);
    		listSinisterRequests.getItems();
    		
    		
    		
    		
    		listSinisterRequests.setOnMouseClicked(new EventHandler<MouseEvent>() {

        	    @Override
        	    public void handle(MouseEvent click) {

        	        if (click.getClickCount() == 2) {
        	           //Use ListView's getSelected Item
        	          String currentItemSelected = listSinisterRequests.getSelectionModel()
        	                                                    .getSelectedItem();
        	          
        	          System.out.println(currentItemSelected);
        	          System.out.println(i.getIndemnityRequest_id());
        	           //use this to do whatever you want to. Open Link etc.
        	        }
        	    }
        	});
    		
    		
    		

        }
    	
    	lblTotalRequests.setText(sninistersRequest.size()+"");
    	
    	
    	int todaysRequest =0;
    	int UntreatedRequest =0;
    	int treatedRequests =0;
    	
    	for(Indemnity_Request i : sninistersRequest)
    	{
    		
    		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    		Date date = new Date();
    		
    		String dateNow = formatter.format(date);
    		String dateSinisterRequest = i.getIndemnity_date().toString();
    		
    		if(dateNow.equals(dateSinisterRequest))
    			todaysRequest++;
    		
    		if(!i.isValide())
    			UntreatedRequest++;
    		
    		if(i.isValide())
    			treatedRequests++;
    		   		
    	}
    	
    	lblTodaysRequest.setText(todaysRequest+"");
    	lblUntratedRequests.setText(UntreatedRequest+"");
    	lblTreatedRequests.setText(treatedRequests+"");
    	
    	
    	listSinisterRequests.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> p) {
                return new ListCell<String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                        	
                            setText(item);
                            setFont(Font.font(14));
                            
                        }
                    }
                };
            }
        });
    	
    	
    	
    
    	List<Indemnity_Request> ir=null;
    	
    	imgSearch.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
            	
            	
            	
                //System.out.println("Search Tile pressed ");
            	
            	String searchRequest = txtSearch.getText();
            	System.out.println(searchRequest);
            	
            	//listSinisterRequests.getItems().clear();
            	
     
       	
                event.consume();
            }
       });
    	
    	
    	
    	
    	
    	
    	
		
	}
    
    
    
    
    @FXML
    void GetTreatedRequests(ActionEvent event) {
    	
    	listSinisterRequests.getItems().clear();
    	
    	try {
			sninistersRequest = ServiceGetAllIndemnityRequest();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	
    	for(Indemnity_Request i : sninistersRequest ){
    		
    		if(i.isValide())
    		{
    			
    		items.add(""+i.getUser().getPrenom() +" "+ i.getUser().getNom()+"       "+i.getIndemnity_date()+"       " + i.getIndemnity_Description() + "       " + i.getUser().getMail() + "       " + i.getUser().getPhone_Number() ); 
    		
    		System.out.println(i.getIndemnityRequest_id());
    						
    		listSinisterRequests.setItems(items);
    		listSinisterRequests.getItems();
    		
    		
    		
    		
    		listSinisterRequests.setOnMouseClicked(new EventHandler<MouseEvent>() {

        	    @Override
        	    public void handle(MouseEvent click) {

        	        if (click.getClickCount() == 2) {
        	           //Use ListView's getSelected Item
        	          String currentItemSelected = listSinisterRequests.getSelectionModel()
        	                                                    .getSelectedItem();
        	          
        	          System.out.println(currentItemSelected);
        	          System.out.println(i.getIndemnityRequest_id());
        	           //use this to do whatever you want to. Open Link etc.
        	        }
        	    }
        	});
    		
    		
    		}

        }

    }
    
    
    
    

    @FXML
    void GetUntreatedRequests(ActionEvent event) {
    	
    	listSinisterRequests.getItems().clear();
    	
    	try {
			sninistersRequest = ServiceGetAllIndemnityRequest();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	
    	for(Indemnity_Request i : sninistersRequest ){
    		
    		if(!i.isValide())
    		{
    			
    		items.add(""+i.getUser().getPrenom() +" "+ i.getUser().getNom()+"       "+i.getIndemnity_date()+"       " + i.getIndemnity_Description() + "       " + i.getUser().getMail() + "       " + i.getUser().getPhone_Number() ); 
    		
    		System.out.println(i.getIndemnityRequest_id());
    						
    		listSinisterRequests.setItems(items);
    		listSinisterRequests.getItems();
    		
    		
    		
    		
    		listSinisterRequests.setOnMouseClicked(new EventHandler<MouseEvent>() {

        	    @Override
        	    public void handle(MouseEvent click) {

        	        if (click.getClickCount() == 2) {
        	           //Use ListView's getSelected Item
        	          String currentItemSelected = listSinisterRequests.getSelectionModel()
        	                                                    .getSelectedItem();
        	          
        	          System.out.println(currentItemSelected);
        	          System.out.println(i.getIndemnityRequest_id());
        	           //use this to do whatever you want to. Open Link etc.
        	        }
        	    }
        	});
    		
    		
    		}

        }

    }
    
    
    
    
    
    
    
    


	
	



	
	
	 
	 
    

}
