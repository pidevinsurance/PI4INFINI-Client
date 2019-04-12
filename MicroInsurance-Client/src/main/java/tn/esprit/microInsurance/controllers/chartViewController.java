package tn.esprit.microInsurance.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;

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

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tn.esprit.microinsurance.Entities.ProductType;
import tn.esprit.microinsurance.Services.Interf.QuoteRemote;

public class chartViewController implements Initializable {
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
	private AreaChart<?,?> area;
	@FXML
    private Button  Return;

	@FXML
	private ScatterChart<?, ?> scatter;
    
    @FXML
    private LineChart<?, ?> LineChart;

	// Event Listener on Button[#btnExit1].onAction
	@FXML
	public void ExitTheApp(ActionEvent event) {
		Stage stage;
        stage=(Stage) btnExit1.getScene().getWindow();
    	stage.close();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		System.out.println("helloooooooooooo");
		String jndiName = "MicroInsurance-ear/MicroInsurance-ejb/QuoatationImpl!tn.esprit.microinsurance.Services.Interf.QuoteRemote";
		Context context;
		try {
			context = new InitialContext();
			QuoteRemote proxy = (QuoteRemote) context.lookup(jndiName);
			XYChart.Series series = new XYChart.Series(); 
			XYChart.Series series1 = new XYChart.Series(); 
			XYChart.Series series2 = new XYChart.Series(); 

			List<Object[]> list=proxy.goodQuotation();
			List<Object[]> list1=proxy.badQuotation();
			List<Object[]> list2 = proxy.DemandedProducts();
			
			for(Object[] o :list)
			{
				Number id=  (Number) o[0];
			
				Number score=(Number)o[1];
				
				series.getData().add(new XYChart.Data<>(id.toString(),score));	
				series.setName("Good Quotation");
			}
			for(Object[] o :list1)
			{
				Number id=  (Number) o[0];
				System.out.println(id);
			
				Number score=(Number)o[1];
				System.out.println(score);
				
				series1.getData().add(new XYChart.Data<>(id.toString(),score));	
				series1.setName("Bad Quotation");
			}
			area.getData().addAll(series1,series);
			scatter.getData().addAll(series1,series);
			
			for (Object[] o : list2) {

				Number nb = (Number) o[0];
				System.out.println(nb);

				String product = (String) o[1];

				System.out.println(product);
				series2.getData().add(new XYChart.Data<>(product,nb));	
				series2.setName("Best sellers");
				


			}
			LineChart.getData().add(series2);
			LineChart.setTitle("MOST DEMANDED PRODUCTS");
			

			
		
			
					
			
			
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
	
		
	}
	@FXML
    void Return(ActionEvent event) throws IOException {
	   objet.getChildren().clear();
    	FXMLLoader root = new FXMLLoader(getClass().getResource("/tn/esprit/microInsurance/Views/Quotesview.fxml"));
		AnchorPane x = root.load();
		System.out.println("c bn");
		//UntreatedController c = root.getController();
		objet.getChildren().clear();
		objet.getChildren().add((Node) x);

    	

    }
}
