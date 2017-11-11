/**
 * Skeleton for 'Borders.fxml' Controller Class
 */

package it.polito.tdp.borders;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class BordersController {

	Model model;

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="txtAnno"
	private TextField txtAnno; // Value injected by FXMLLoader

	@FXML // fx:id="txtResult"
	private TextArea txtResult; // Value injected by FXMLLoader

	@FXML
	void doCalcolaConfini(ActionEvent event) {
		
		String yearToString = txtAnno.getText();
		System.out.println("<doCalcolaConfini> yearToString: " + yearToString);
		if(yearToString==null || "".equals(yearToString)){
			txtResult.setText("Inserire l'anno!");
		}
		else{
			try{
				int year = Integer.valueOf(yearToString);
				if(year < 1816 || year > 2016){
					txtResult.setText("Inserire un anno compreso tra 1816 e 2016!");
				}
				else{
					List<Country> countries = model.retrieveConfini(year);
					for(Country c : countries){
						txtResult.appendText(c.toString());
					}
				}
				
			}catch(NumberFormatException nfe){
				txtResult.setText("Inserire un anno valido!");
			}
				
		}
		
	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Borders.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Borders.fxml'.";
	}

	public void setModel(Model m) {
		this.model = m;
	}
}
