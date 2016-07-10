package it.polito.tdp.lab3.controller;

import java.net.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.lab3.model.Corso;
import it.polito.tdp.lab3.model.Model;
import it.polito.tdp.lab3.model.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class SegreteriaStudentiController {

	private Model model ;
	
	List<Corso> corsi = new ArrayList<Corso>() ;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Corso> comboCorso;

    @FXML
    private TextField txtMatricola;

    @FXML
    private Button btnCercaNome;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtCognome;

    @FXML
    private Button btnCerca;

    @FXML
    private Button btnIscrivi;

    @FXML
    private TextArea txtResult;

    @FXML
    private Button btnReset;

    @FXML
    void doCerca(ActionEvent event) {
    	
    	this.txtResult.clear();
    	this.txtNome.clear();
    	this.txtCognome.clear();
    	
    	try{
    		
    		if(this.txtMatricola.getText().isEmpty() && (this.comboCorso.getValue() == null 
    				|| this.comboCorso.getValue().getCodins().compareTo("")==0)){
    			this.txtResult.setText("Occorre selezionare un corso o introdurre una matricola");
    			this.comboCorso.getSelectionModel().clearSelection();
    			return ;
    		}
    		
    		if(this.txtMatricola.getText().compareTo("") != 0 
    				&& this.comboCorso.getValue() != null
    				&& this.comboCorso.getValue().getCodins().compareTo("") !=0)
    			case1() ;
    		if(this.txtMatricola.getText().compareTo("") ==0 
    				&& this.comboCorso.getValue() != null )
    			case2() ;
    		if( this.txtMatricola.getText().compareTo("") != 0 
    				&& (this.comboCorso.getValue() == null 
    				|| this.comboCorso.getValue().getCodins().compareTo("") ==0 ))
    			case3() ;
    	} catch (RuntimeException e){
    		this.txtResult.setText("ERRORE DI CONNESSIONE AL DB");
    	}

    }
    
    // dato un corso e una matricola vedo se vi appartiene
    void case1(){
    	
    	int matricola = Integer.parseInt(this.txtMatricola.getText() ) ;
    	
    	Studente studente = model.getStudente(matricola) ;
    	
    	if( studente == null ){
    		this.txtResult.appendText("Matricola inesistente");
    		this.comboCorso.getSelectionModel().clearSelection(); 
    		return ;
    	}
    	
    	Corso corso = this.comboCorso.getValue() ;
    	
    	if(model.isStudenteIscrittoACorso(matricola, corso.getCodins())){
    		this.txtResult.appendText("Studente iscritto al corso");
    		return ;
    	}
    	this.txtResult.appendText("Studente non iscritto al corso");
    	return ;
    }
    
    //Dato un corso determino tutti gli iscritti
    void case2(){
    	
    	List<Studente> studenti = model.studentiIscrittiAlCorso(this.comboCorso.getValue().getCodins()) ;
    	
    	StringBuilder sb = new StringBuilder() ;
    	
    	for( Studente studente : studenti ){
    		sb.append(String.format("%-10s", studente.getMatricola())) ;
    		sb.append(String.format("%-20s", studente.getCognome())) ;
    		sb.append(String.format("%-20s", studente.getNome())) ;
    		sb.append(String.format("%-10s", studente.getCds())) ;
    		sb.append("\n") ;
    	}
    	
    	this.txtResult.appendText(sb.toString());
    
    }

    //Data matricola trovare tutti i corsi
    void case3(){
    	
    	int matricola = Integer.parseInt(this.txtMatricola.getText()) ;
    	
    	List<Corso> corsi = model.cercaCorsiDatoStudente(matricola) ;
    	
    	StringBuilder sb = new StringBuilder();
    	
    	for( Corso c : corsi ){
    		sb.append(String.format("%-8s", c.getCodins()));
    		sb.append(String.format("%-4", c.getCrediti()));
    		sb.append(String.format("%-4", c.getNome()));
    		sb.append(String.format("%-4", c.getPd()));
    		sb.append("\n");
    	}
    	this.txtResult.appendText(sb.toString());
    }
    
    @FXML
    void doCercaNome(ActionEvent event) {
    	
    	this.txtResult.clear();
    	this.txtCognome.clear();
    	this.txtNome.clear();
    	
    	try{
    		
    		int matricola = Integer.parseInt(this.txtMatricola.getText());
    		
    		Studente studente = model.getStudente(matricola) ;
    		
    		if( studente == null ){
    			this.txtResult.appendText("Nessun risultato: matricola inesistente");
    			return ;
    		}
    		this.txtNome.setText(studente.getNome()) ;
    		this.txtCognome.setText(studente.getCognome()) ;
    		
    	}catch(RuntimeException e){
    		this.txtResult.setText("ERRORE DI CONNESSIONE AL DB");
    	}

    }

    @FXML
    void doIscrivi(ActionEvent event) {
    	
    	this.txtResult.clear();
    	
    	try{
    		
    		if(!this.txtMatricola.getText().isEmpty() && this.comboCorso.getValue() != null 
    				&& this.comboCorso.getValue().getCodins().compareTo("") != 0){
    			int matricola = Integer.parseInt(this.txtMatricola.getText()); 
    			
    			Studente studente = model.getStudente(matricola) ;
    			
    			if(studente == null){
    				this.txtResult.appendText("Matricola inesistente");
    				return ;	
    			}
    			
    			this.txtNome.setText(studente.getNome());
    			this.txtCognome.setText(studente.getCognome());
    			
    			Corso corso = this.comboCorso.getValue() ;
    			
    			if(model.isStudenteIscrittoACorso(matricola, corso.getCodins())){
    				this.txtResult.appendText("Studente già iscritto");
    				return ;
    			}
    			
    			if(!model.iscriviStudenteACorso(matricola, corso.getCodins())){
    				this.txtResult.appendText("Errore inserimento dati");
    				return ;
    			}else {
    				this.txtResult.appendText("Studente inserito");
    			}
    			
    		}
    	}catch(RuntimeException e){
    		this.txtResult.appendText("ERRORE DI CONNESSIONE AL DB");
    	}

    }

    @FXML
    void doReset(ActionEvent event) {
    	
    	this.txtCognome.clear() ; 
    	this.txtMatricola.clear() ;
    	this.txtNome.clear() ;
    	this.txtResult.clear() ;
    	this.comboCorso.getSelectionModel().clearSelection();
    	

    }
    
    
    public void setModel(Model model) {
    	// TODO Auto-generated method stub
    	this.model = model ;
    	
    	//tutti i corsi dal model
    	this.corsi = model.getTuttiICorsi() ;
    	
    	this.comboCorso.getItems().addAll(corsi) ;
		
		
	}
    
   
    @FXML
    void initialize() {
        assert comboCorso != null : "fx:id=\"comboCorso\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtMatricola != null : "fx:id=\"txtMatricola\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert btnCercaNome != null : "fx:id=\"btnCercaNome\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtCognome != null : "fx:id=\"txtCognome\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert btnCerca != null : "fx:id=\"btnCerca\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert btnIscrivi != null : "fx:id=\"btnIscrivi\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert btnReset != null : "fx:id=\"btnReset\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";

    }

	
}

