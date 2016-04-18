package it.polito.tdp.lab3.controller;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.model.Corso;
import it.polito.tdp.model.Model;
import it.polito.tdp.model.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class SegreteriaStudentiController {

	private Model model;
	List<Corso> corsi = new LinkedList<Corso>();

	@FXML
	private ComboBox<Corso> comboCorso;

	@FXML
	private Button btnCerca;

	@FXML
	private Button btnCercaNome;

	@FXML
	private TextArea txtResult;

	@FXML
	private Button btnIscrivi;

	@FXML
	private TextField txtMatricola;

	@FXML
	private Button btnReset;

	@FXML
	private TextField txtNome;

	@FXML
	private TextField txtCognome;

	public void setModel(Model model) {

		this.model = model;

		// Ottieni tutti i corsi dal model
		corsi = model.getTuttiICorsi();

		// Aggiungo un corso finto
		Corso corsoFinto = new Corso();
		corsoFinto.setCodins(" ");
		corsoFinto.setNome(" ");
		corsi.add(corsoFinto);

		// Aggiungi tutti i corsi alla ComboBox
		Collections.sort(corsi);
		comboCorso.getItems().addAll(corsi);
	}

	@FXML
	void doReset(ActionEvent event) {
		txtMatricola.clear();
		txtResult.clear();
		txtNome.clear();
		txtCognome.clear();
		comboCorso.getSelectionModel().clearSelection();
	}

	@FXML
	void doCercaNome(ActionEvent event) {

		txtResult.clear();
		txtNome.clear();
		txtCognome.clear();

		try {
			int matricola = Integer.parseInt(txtMatricola.getText());
			Studente studente = model.getStudente(matricola);

			if (studente == null) {
				txtResult.appendText("Nessun risultato: matricola inesistente");
				return;
			}

			txtNome.setText(studente.getNome());
			txtCognome.setText(studente.getCognome());

		} catch (RuntimeException e) {
			txtResult.setText("ERRORE DI CONNESSIONE AL DATABASE!");
		}
	}

	@FXML
	void doCerca(ActionEvent event) {

		txtResult.clear();
		txtNome.clear();
		txtCognome.clear();

		try {

			if (txtMatricola.getText().isEmpty()
					&& (comboCorso.getValue() == null || comboCorso.getValue().getCodins().compareTo(" ") == 0)) {
				txtResult.appendText("Occorre selezionare un corso o introdurre una matricola");
				comboCorso.getSelectionModel().clearSelection();
				return;
			}

			if (txtMatricola.getText().compareTo("") != 0 && comboCorso.getValue() != null
					&& comboCorso.getValue().getCodins().compareTo(" ") != 0) {

				case1();
			}

			if (txtMatricola.getText().compareTo("") == 0 && comboCorso.getValue() != null) {

				case2();
			}

			if (txtMatricola.getText().compareTo("") != 0
					&& (comboCorso.getValue() == null || comboCorso.getValue().getCodins().compareTo(" ") == 0)) {

				case3();
			}

		} catch (RuntimeException e) {
			txtResult.setText("ERRORE DI CONNESSIONE AL DATABASE!");
		}
	}

	void case1() {

		int matricola = Integer.parseInt(txtMatricola.getText());
		Studente studente = model.getStudente(matricola);

		if (studente == null) {
			txtResult.appendText("Matricola inesistente");
			comboCorso.getSelectionModel().clearSelection();
			return;
		}
		Corso corso = comboCorso.getValue();

		if (model.isStudenteIscrittoACorso(matricola, corso.getCodins())) {
			txtResult.appendText("Studente Iscritto al corso");
			return;
		}

		txtResult.appendText("Studente non scritto al corso");
		return;
	}

	/*
	 * Dato un corso, devo stamapre le informazioni su tutti gli studenti.
	 */
	void case2() {
		List<Studente> studenti = model.studentiIscrittiAlCorso(comboCorso.getValue().getCodins());

		StringBuilder sb = new StringBuilder();

		for (Studente studente : studenti) {

			sb.append(String.format("%-10s ", studente.getMatricola()));
			sb.append(String.format("%-20s ", studente.getCognome()));
			sb.append(String.format("%-20s ", studente.getNome()));
			sb.append(String.format("%-10s ", studente.getCds()));
			sb.append("\n");
		}

		txtResult.appendText(sb.toString());
	}

	/*
	 * Data una matricola devo stampare tutti i corsi a cui lo studente è
	 * iscritto.
	 */
	void case3() {

		// Prendo la matricola in input
		int matricola = Integer.parseInt(txtMatricola.getText());
		List<Corso> corsi = model.cercaCorsiDatoStudente(matricola);

		StringBuilder sb = new StringBuilder();

		for (Corso corso : corsi) {
			sb.append(String.format("%-8s ", corso.getCodins()));
			sb.append(String.format("%-4s ", corso.getCrediti()));
			sb.append(String.format("%-45s ", corso.getNome()));
			sb.append(String.format("%-4s ", corso.getPd()));
			sb.append("\n");
		}
		txtResult.appendText(sb.toString());
	}

	@FXML
	void doIscrivi(ActionEvent event) {

		txtResult.clear();

		try {

			if (!txtMatricola.getText().isEmpty() && comboCorso.getValue() != null
					&& comboCorso.getValue().getCodins().compareTo(" ") != 0) {

				// Prendo la matricola in input
				int matricola = Integer.parseInt(txtMatricola.getText());

				// Inserisco il Nome e Cognome dello studente nell'interfaccia
				// (opzionale)
				Studente studente = model.getStudente(matricola);
				if (studente == null) {
					txtResult.appendText("Nessun risultato: matricola inesistente");
					return;
				}
				txtNome.setText(studente.getNome());
				txtCognome.setText(studente.getCognome());

				// Ottengo il nome del corso
				Corso corso = comboCorso.getValue();

				// Controllo se lo studente è già iscritto al corso
				if (model.isStudenteIscrittoACorso(matricola, corso.getCodins())) {
					txtResult.appendText("Studente già iscritto a questo corso");
					return;
				}

				// Iscrivo lo studente al corso -- controllo che l'inserimento
				// vada a buon fine.
				if (!model.inscriviStudenteACorso(matricola, corso.getCodins())) {
					txtResult.appendText("Errore inserimento dati");
					return;
				} else {
					txtResult.appendText("Studente iscritto al corso!");
				}
			}

		} catch (RuntimeException e) {
			txtResult.setText("ERRORE DI CONNESSIONE AL DATABASE!");
		}
	}

	@FXML
	void initialize() {
		assert comboCorso != null : "fx:id=\"comboCorso\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
		assert btnCerca != null : "fx:id=\"btnCerca\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
		assert btnCercaNome != null : "fx:id=\"btnCercaNome\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
		assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
		assert txtCognome != null : "fx:id=\"txtCognome\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
		assert btnIscrivi != null : "fx:id=\"btnIscrivi\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
		assert txtMatricola != null : "fx:id=\"txtMatricola\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
		assert btnReset != null : "fx:id=\"btnReset\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";

		txtResult.setStyle("-fx-font-family: monospace");
	}

}
