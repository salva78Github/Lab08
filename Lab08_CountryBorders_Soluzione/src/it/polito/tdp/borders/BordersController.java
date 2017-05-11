/**
 * Skeleton for 'Borders.fxml' Controller Class
 */

package it.polito.tdp.borders;

import java.net.URL;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.Map;
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

		Integer anno;
		String annotxt = txtAnno.getText();

		if (annotxt.length() == 0) {
			txtResult.appendText("Inserire un anno\n");
			return;
		}

		try {
			anno = Integer.parseInt(annotxt);
			
		} catch (IllegalFormatException e) {
			txtResult.appendText("Inserire un numero valido\n");
			return;
		}

		try {
			model.createGraph(anno);
			
		} catch (RuntimeException e) {
			txtResult.appendText("Errore: " + e.getMessage() + "\n");
			return;
		}

		txtResult.clear();
		
		txtResult.appendText(String.format("Numero componenti connesse: %d\n", model.getNumberOfConnectedComponents()));
		
		Map<Country, Integer> stats = model.getCountryCounts();
		for (Country country : stats.keySet())
			txtResult.appendText(String.format("%s %d\n", country, stats.get(country)));
	}
	
	public void setModel(Model model) {
		this.model = model;
	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Borders.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Borders.fxml'.";
	}
}
