package it.polito.tdp.lab3.model;

import java.util.List;

import it.polito.tdp.lab3.DAO.CorsoDAO;
import it.polito.tdp.lab3.DAO.StudenteDAO;

public class Model {
	
	private StudenteDAO studenteDAO ; 
	private CorsoDAO corsoDAO ;

	public Model() {
		// TODO Auto-generated constructor stub
		this.studenteDAO = new StudenteDAO() ;
		this.corsoDAO = new CorsoDAO() ;
	}
	
	public Studente getStudente (int matricola) {
		
		Studente studente = new Studente(matricola,null, null, null);
		
		if(this.studenteDAO.getStudente(studente))
			return studente ;
		else
			return null ;
	}

	public List<Studente> studentiIscrittiAlCorso(String codins){
		
		Corso corso = new Corso(codins);
		this.corsoDAO.getStudenti(corso);
		return corso.getStudenti() ;
	}
	
	public List<Corso> cercaCorsiDatoStudente(int matricola){
		
		Studente studente = new Studente(matricola);
		
		this.studenteDAO.getCorsiFromStudente(studente);
		
		return studente.getCorsi();
	}

	public boolean isStudenteIscrittoACorso(int matricola, String codins){
		
		Studente studente = new Studente(matricola) ;
		Corso corso = new Corso(codins) ;
		return this.studenteDAO.isStudenteIscrittoACorso(studente, corso) ;
		
	}

	public boolean iscriviStudenteACorso(int matricola, String codins){
		
		Studente studente = new Studente(matricola) ; 
		Corso corso = new Corso(codins) ;
		
		return this.corsoDAO.addStudenteACorso(studente, corso) ;
		
	}

	public List<Corso> getTuttiICorsi(){
		return this.corsoDAO.getCorsi();
	}
	
}
