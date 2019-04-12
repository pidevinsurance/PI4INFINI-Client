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
import tn.esprit.microinsurance.Entities.Life_quotation;
//import tn.esprit.microinsurance.Entities.Quotation;
import tn.esprit.microinsurance.Entities.QuotationMode;
import tn.esprit.microinsurance.Services.Interf.QuoteRemote;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import javafx.event.ActionEvent;

public class LifeDetailsController implements Initializable {
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
	private TextField initial_amount;
	@FXML
	private TextField payment_mode;

	@FXML
	private AnchorPane objet;
	@FXML
	private Button CalculateQuotation;
	@FXML
	private Button InsuranceRequest;
	private static int score;

	private Life_quotation q;
	private static float amount;

	public int CalculateScore() {
		if(q.getIncome()>1000)
		{
			score+=5;
		}
		else
		{
			score++;
		}
		if(q.getAge()<30)
		{
			score+=10;
		}
		else
		{
			score+=5;
		}
		if (q.getLifeMode() == q.getLifeMode().annuel) {
			score += 5;
		}
		if (q.getLifeMode() == q.getLifeMode().mensuel) {
			score += 35;
		}
		if (q.getLifeMode() == q.getLifeMode().semestriel) {
			score += 10;
		}
		if (q.getLifeMode() == q.getLifeMode().trimestriel) {
			score += 29;
		}
		return score;

	}

	public static int getScore() {
		return score;
	}

	public static void setScore(int score) {
		LifeDetailsController.score = score;
	}

	@FXML
	public void ExitTheApp(ActionEvent event) {
		Stage stage;
		stage = (Stage) btnExit1.getScene().getWindow();
		stage.close();
	}

	// Event Listener on Button[#CalculateQuotation].onAction
	@FXML
	public void CalculateQuotation(ActionEvent event) throws NamingException {
		String jndiName = "MicroInsurance-ear/MicroInsurance-ejb/QuoatationImpl!tn.esprit.microinsurance.Services.Interf.QuoteRemote";
		Context context;
		context = new InitialContext();
		QuoteRemote proxy = (QuoteRemote) context.lookup(jndiName);
		float risk_rate=CalculateScore()/100;
		
			amount=(float) (q.getIncome()*0.3/risk_rate);
			q.setAmount(amount);
	    	proxy.updateStatus(q);
	    	
	    	 
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	public Life_quotation getQ() {
		return q;
	}

	public void setQ(Life_quotation q) {
		this.q = q;
		System.out.println(q.getInitial_amount());
		initial_amount.setText("" + q.getInitial_amount());
		payment_mode.setText("" + q.getLifeMode());

		score = CalculateScore();

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

	@FXML
	public void InsuranceRequest(ActionEvent event) throws IOException {
		QuotationMode mode = q.getMode();

		if (mode.equals(q.getMode().CustomisedQuotation)) {

			FXMLLoader root = new FXMLLoader(
					getClass().getResource("/tn/esprit/microInsurance/views/InsuranceRequestView.fxml"));

			System.out.println("ahlaaaa");
			// System.out.println(ir.getRequest_id());
			// c.setIr(ir);
			AnchorPane x = root.load();

			InsuranceRequestViewController c = root.getController();
			System.out.println(q.getId());
			c.setId(q.getId());
			c.SetScore(score);
			// c.setIr(ir.getRequest_id());
			c.init();

			System.out.println("c bn");

			objet.getChildren().clear();
			objet.getChildren().add((Node) x);
		} else {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle(" Express Quotation");
			alert.setHeaderText("There is no insrance request!");
			alert.showAndWait();
		}

	}
}
