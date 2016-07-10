package it.polito.tdp.lab3.model;

import java.util.ArrayList;
import java.util.List;

public class Studente {
	
	private List<Corso> corsi ;
	private int matricola ; 
	private String nome ;
	private String cognome  ;
	private String cds ;

	public Studente(int matricola) {
		// TODO Auto-generated constructor stub
		this.matricola = matricola ; 
	}
	
	public Studente(int matricola, String cognome, String nome, String cds){
		this.matricola = matricola ;
		this.cognome = cognome ; 
		this.nome = nome ; 
		this.cds = cds ;
		
	}

	public List<Corso> getCorsi() {
		if( corsi == null)
			return new ArrayList<Corso>() ;
		return corsi;
	}

	public void setCorsi(List<Corso> corsi) {
		this.corsi = corsi;
	}

	public int getMatricola() {
		return matricola;
	}

	public void setMatricola(int matricola) {
		this.matricola = matricola;
	}

	public String getNome() {
		if(nome == null)
			return "" ;
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		if( cognome == null) 
			return "" ;
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getCds() {
		if( cds == null ) 
			return "";
		return cds;
	}

	public void setCds(String cds) {
		this.cds = cds;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + matricola;
		return result;
	}

	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Studente other = (Studente) obj;
		if (matricola != other.matricola)
			return false;
		return true;
	}

	
	@Override
	public String toString() {
		return "Studente [matricola=" + matricola + "]";
	}
	
	

}
