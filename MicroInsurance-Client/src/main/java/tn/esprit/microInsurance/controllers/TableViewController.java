package tn.esprit.microInsurance.controllers;

import java.io.IOException;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import java.net.URL;
import java.text.ParseException;
import java.util.List;
import java.util.ResourceBundle;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
//import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
//import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.Node;
import tn.esprit.microinsurance.Entities.ProductType;
import tn.esprit.microinsurance.Entities.Quotation;
import tn.esprit.microinsurance.Entities.TypesMicroInsurance;
import tn.esprit.microinsurance.Entities.Quotation.Status;
//import tn.esprit.microinsurance.Entities.MicroInsurance;
//import tn.esprit.microinsurance.Entities.Product;
//import tn.esprit.microinsurance.Entities.ProductType;
import tn.esprit.microinsurance.Services.Interf.QuoteRemote;

public class TableViewController implements Initializable {
	@FXML
	private AnchorPane objet;

	@FXML
	private Button btnPackages;

	@FXML
	private Button btnSettings;

	@FXML
	private Button btnExit1;

	@FXML
	private Button Remove;

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
    private Button Return;

	@FXML
	private TableView<Quotation> TableViewQuote;
	@FXML
	private TableColumn<Quotation, String> name;
	@FXML
	private TableColumn<Quotation, String> surname;
	 @FXML
	    private Button summary;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@FXML
	private TableColumn<Quotation, TypesMicroInsurance> insurance_type;
	@FXML
	private TableColumn<Quotation, ProductType> insurance_product;
	@FXML
	private TableColumn<Quotation, String> phone_number;

	int todaysRequest = 0;

	int UntreatedRequests = 0;

	int treatedRequests = 0;
	private int id;

	public List<Quotation> ServiceGetAllQuotations() throws Exception {
		String jndiName = "MicroInsurance-ear/MicroInsurance-ejb/QuoatationImpl!tn.esprit.microinsurance.Services.Interf.QuoteRemote";
		Context context = new InitialContext();
		QuoteRemote proxy = (QuoteRemote) context.lookup(jndiName);
		return proxy.findAllQuotations();

	}
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// TableViewQuote.getItems().clear();

		List<Quotation> QuotationRequests = null;
		
		try {
			QuotationRequests = ServiceGetAllQuotations();
			for (Quotation q : QuotationRequests) {
				System.out.println(q.getId());
			}
			System.out.println(QuotationRequests.size());

			name.setCellValueFactory(new PropertyValueFactory<>("Nom"));
			surname.setCellValueFactory(new PropertyValueFactory<>("Prenom"));
			insurance_type.setCellValueFactory(
					new Callback<TableColumn.CellDataFeatures<Quotation, TypesMicroInsurance>, ObservableValue<TypesMicroInsurance>>() {

						@Override
						public ObservableValue<TypesMicroInsurance> call(
								CellDataFeatures<Quotation, TypesMicroInsurance> param) {
							return new SimpleObjectProperty<>(param.getValue().getProduct().getType());
						}

					});

			insurance_product.setCellValueFactory(
					new Callback<TableColumn.CellDataFeatures<Quotation, ProductType>, ObservableValue<ProductType>>() {

						@Override
						public ObservableValue<ProductType> call(CellDataFeatures<Quotation, ProductType> param) {
							// TODO Auto-generated method stub
							return new SimpleObjectProperty<>(param.getValue().getProduct().getLabel());
						}
					});

			phone_number.setCellValueFactory(new PropertyValueFactory<>("Phone_Number"));

			/*
			 * TableViewQuote.getSelectionModel().selectedItemProperty().
			 * addListener(new ChangeListener<Quotation>() {
			 * 
			 * @Override public void changed(ObservableValue<? extends
			 * Quotation> observable, Quotation oldValue, Quotation newValue) {
			 * setId(observable.getValue().getId()); System.out.println(id);
			 * 
			 * }
			 * 
			 * });
			 */
			ObservableList<Quotation> items = FXCollections.observableArrayList(QuotationRequests);


			TableViewQuote.setItems(items);
			TableViewQuote.getItems();
			TableViewQuote.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Quotation>() {

				@Override
				public void changed(ObservableValue<? extends Quotation> observable, Quotation oldValue,
						Quotation newValue) {

					if (observable.getValue().getStatus().equals(Status.Treated)) {
						FXMLLoader root = new FXMLLoader(
								getClass().getResource("/tn/esprit/microInsurance/views/TreatedDetails.fxml"));
						AnchorPane x;
						try {
							x = root.load();

							System.out.println("c bn");
							TreatedDetailsController c = root.getController();
							System.out.println(observable.getValue().getId());
							c.setQ(observable.getValue());
							c.setKhorm();
							Quotation q = new Quotation();
							q.setPhone_Number(TableViewQuote.getSelectionModel().getSelectedItem().getPhone_Number());

							objet.getChildren().clear();
							objet.getChildren().add((Node) x);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					} else {
						FXMLLoader root = new FXMLLoader(
								getClass().getResource("/tn/esprit/microInsurance/views/UntreatedDetails.fxml"));
						AnchorPane x;
						try {
							x = root.load();

							System.out.println("c bn");
							UntreatedDetailsController c = root.getController();
							System.out.println(observable.getValue().getId());
							System.out.println(observable.getValue().getStatus());
							c.setQ(observable.getValue());
							c.setKhorm();
							objet.getChildren().clear();
							objet.getChildren().add((Node) x);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				}
			});

			TotalRequests.setText(QuotationRequests.size() + "");

			for (Quotation i : QuotationRequests) {

				if (i.getStatus() == Status.Untreated)
					UntreatedRequests++;

				if (i.getStatus() == Status.Treated)
					treatedRequests++;
			}

			// System.out.println("traite"+todaysRequest);
			// System.out.println("nn traite"+ UntreatedRequests);
			// System.out.println("traite"+ treatedRequests);
			TodaysRequest.setText(todaysRequest + "");
			lblUntreatedRequests.setText(UntreatedRequests + "");
			TreatedRequests.setText(treatedRequests + "");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ObservableList<Quotation> items = FXCollections.observableArrayList(QuotationRequests);
		FilteredList<Quotation> filteredData = new FilteredList<>(items, p -> true);
	        
	        // 2. Set the filter Predicate whenever the filter changes.
	        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
	            filteredData.setPredicate(Quotation -> {
	                // If filter text is empty, display all persons.
	                if (newValue == null || newValue.isEmpty()) {
	                    return true;
	                }
	                
	                // Compare first name and last name of every person with filter text.
	                String lowerCaseFilter = newValue.toLowerCase();
	             
	                
	                if (Quotation.getProduct().getDescripition().toLowerCase().contains(lowerCaseFilter)) {
	                    return true; // Filter matches first name.
	                } else
	                return false; // Does not match.
	            });
	        });
	        
	        // 3. Wrap the FilteredList in a SortedList. 
	        SortedList<Quotation> sortedData = new SortedList<>(filteredData);
	        
	        // 4. Bind the SortedList comparator to the TableView comparator.
	        sortedData.comparatorProperty().bind(TableViewQuote.comparatorProperty());
	     
	        
	        // 5. Add sorted (and filtered) data to the table.
	        TableViewQuote.setItems(sortedData);


	}
	@FXML
	void summary(ActionEvent event) throws IOException
	{
		FXMLLoader root = new FXMLLoader(getClass().getResource("/tn/esprit/microInsurance/views/chartView.fxml"));
		AnchorPane x = root.load();
		objet.getChildren().clear();
		objet.getChildren().add((Node) x);
		
	}

	@FXML
	void GetAllRequests(ActionEvent event) {

	}

	@FXML
	void GetTreatedRequests(ActionEvent event) throws Exception {
		
		FXMLLoader root = new FXMLLoader(getClass().getResource("/tn/esprit/microInsurance/views/TreatedQuote.fxml"));
		AnchorPane x = root.load();
		System.out.println("c bn");
		//TreatedController c = root.getController();
		objet.getChildren().clear();
		objet.getChildren().add((Node) x);
		

	}

	@FXML
	void GetUntreatedRequests(ActionEvent event) throws IOException {
		FXMLLoader root = new FXMLLoader(getClass().getResource("/tn/esprit/microInsurance/views/UntreatedQuote.fxml"));
		AnchorPane x = root.load();
		System.out.println("c bn");
		//UntreatedController c = root.getController();
		objet.getChildren().clear();
		objet.getChildren().add((Node) x);

	}

	@FXML
	void SearchQuotation(ActionEvent event) {
	}

	@FXML
	void ExitTheApp(ActionEvent event) {

		Stage stage;
		stage = (Stage) btnExit1.getScene().getWindow();
		stage.close();

	}

    
	/*
	 * @FXML void RemoveQuotations(ActionEvent event) throws Exception {
	 * /*List<Quotation> QuotationRequests = ServiceGetAllQuotations();
	 * 
	 * String jndiname=
	 * "MicroInsurance-ear/MicroInsurance-ejb/QuoatationImpl!tn.esprit.microinsurance.Services.Interf.QuoteRemote";
	 * Context context=null; try { context = new InitialContext(); } catch
	 * (NamingException e1) {
	 * 
	 * e1.printStackTrace(); } try { QuoteRemote proxy=(QuoteRemote)
	 * context.lookup(jndiname); int i =
	 * TableViewQuote.getSelectionModel().getSelectedIndex(); //index int
	 * id=ServiceGetAllQuotations().get(i).getId(); for(Quotation q :
	 * QuotationRequests) {if(q.getId()==id)
	 * 
	 * System.out.println("index"+i);
	 * if(ServiceGetAllQuotations().get(i).getStatus()==Status.Untreated)
	 * {proxy.removeQuote(id); System.out.println("index"+i);} else { Alert
	 * alert = new Alert(Alert.AlertType.WARNING);
	 * alert.setTitle(" Status Removing Error ");
	 * alert.setHeaderText(" An Untreated Quotation can't be removed");
	 * alert.showAndWait(); } } } catch (NamingException e) {
	 * 
	 * e.printStackTrace(); }
	 */

	/*
	 * String jndiname=
	 * "MicroInsurance-ear/MicroInsurance-ejb/QuoatationImpl!tn.esprit.microinsurance.Services.Interf.QuoteRemote";
	 * Context context=null; try { context = new InitialContext(); QuoteRemote
	 * proxy=(QuoteRemote) context.lookup(jndiname);
	 * 
	 * int i = TableViewQuote.getSelectionModel().getSelectedIndex(); //index
	 * Quotation.Status s=ServiceGetAllQuotations().get(i).getStatus();
	 * System.out.println("******"+s); Status q = Status.Untreated; if (
	 * s.equals(q)){ System.out.println("untreated"); Alert alert = new
	 * Alert(Alert.AlertType.WARNING);
	 * alert.setTitle(" Status Removing Error ");
	 * alert.setHeaderText(" An Untreated Quotation can't be removed");
	 * alert.showAndWait(); } else{ int
	 * id=ServiceGetAllQuotations().get(i).getId(); proxy.removeQuoteees(id);
	 * System.out.println("okkk"+id); } } catch (NamingException e1) {
	 * 
	 * e1.printStackTrace(); } }
	 */

}