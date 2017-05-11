package it.polito.tdp.dizionario.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.dizionario.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DizionarioController {

	Model model;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TextArea txtResult;

	@FXML
	private TextField inputNumeroLettere;

	@FXML
	private TextField inputParola;

	@FXML
	private Button btnGeneraGrafo;

	@FXML
	private Button btnTrovaVicini;

	@FXML
	private Button btnTrovaGradoMax;

	@FXML
	private Button btnTrovaTuttiVicini;

	@FXML
	void doReset(ActionEvent event) {
		txtResult.clear();
		inputNumeroLettere.clear();
		inputParola.clear();
		inputNumeroLettere.setDisable(false);
		inputParola.setDisable(true);
		btnGeneraGrafo.setDisable(false);
		btnTrovaVicini.setDisable(true);
		btnTrovaGradoMax.setDisable(true);
		btnTrovaTuttiVicini.setDisable(true);
	}

	@FXML
	void doGeneraGrafo(ActionEvent event) {
		txtResult.clear();
		inputParola.clear();

		try {
			int numeroLettere = Integer.parseInt(inputNumeroLettere.getText());
			System.out.println("numero di Lettere: " + numeroLettere);

			List<String> parole = model.createGraph(numeroLettere);

			if (parole != null) {
				txtResult.setText("Trovate " + parole.size() + " parole di lunghezza " + numeroLettere);
			} else {
				txtResult.setText("Trovate 0 parole di lunghezza: " + numeroLettere);
			}

			inputNumeroLettere.setDisable(true);
			btnGeneraGrafo.setDisable(true);
			inputParola.setDisable(false);
			btnTrovaVicini.setDisable(false);
			btnTrovaGradoMax.setDisable(false);
			btnTrovaTuttiVicini.setDisable(false);

		} catch (NumberFormatException nfe) {
			txtResult.setText("Inserire un numero corretto di lettere!");
		} catch (RuntimeException re) {
			txtResult.setText(re.getMessage());
		}
	}

	@FXML
	void doTrovaVicini(ActionEvent event) {
		try {
			String parolaInserita = inputParola.getText();
			if (parolaInserita == null || parolaInserita.length() == 0) {
				txtResult.setText("Inserire una parola da cercare");
				return;
			}

			List<String> parole = model.displayNeighbours(parolaInserita);
			if (parole != null) {
				txtResult.setText(parole.toString());
			} else {
				txtResult.setText("Non è stato trovato nessun risultato");
			}
		} catch (RuntimeException re) {
			txtResult.setText(re.getMessage());
		}
	}

	@FXML
	void doTrovaGradoMax(ActionEvent event) {
		try {
			txtResult.setText(model.findMaxDegree());

		} catch (RuntimeException re) {
			txtResult.setText(re.getMessage());
		}
	}

	@FXML
	void doTrovaTuttiVicini(ActionEvent event) {
		try {
			String parolaInserita = inputParola.getText();
			if (parolaInserita == null || parolaInserita.length() == 0) {
				txtResult.setText("Inserire una parola da cercare");
				return;
			}

			List<String> parole = model.displayAllNeighboursJGraphT(parolaInserita);
			if (parole != null) {
				txtResult.setText(parole.toString());
			} else {
				txtResult.setText("Non è stato trovato nessun risultato");
			}
		} catch (RuntimeException re) {
			txtResult.setText(re.getMessage());
		}
	}

	public void setModel(Model model) {
		this.model = model;
	}

	@FXML
	void initialize() {
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Dizionario.fxml'.";
		assert inputNumeroLettere != null : "fx:id=\"inputNumeroLettere\" was not injected: check your FXML file 'Dizionario.fxml'.";
		assert inputParola != null : "fx:id=\"inputParola\" was not injected: check your FXML file 'Dizionario.fxml'.";
		assert btnGeneraGrafo != null : "fx:id=\"btnGeneraGrafo\" was not injected: check your FXML file 'Dizionario.fxml'.";
		assert btnTrovaVicini != null : "fx:id=\"btnTrovaVicini\" was not injected: check your FXML file 'Dizionario.fxml'.";
		assert btnTrovaGradoMax != null : "fx:id=\"btnTrovaGradoMax\" was not injected: check your FXML file 'Dizionario.fxml'.";
		assert btnTrovaTuttiVicini != null : "fx:id=\"btnTrovaTuttiVicini\" was not injected: check your FXML file 'Dizionario.fxml'.";

		inputParola.setDisable(true);
		btnTrovaVicini.setDisable(true);
		btnTrovaGradoMax.setDisable(true);
		btnTrovaTuttiVicini.setDisable(true);
	}
}