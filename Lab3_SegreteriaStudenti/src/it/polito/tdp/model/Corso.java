package it.polito.tdp.model;

import java.util.ArrayList;
import java.util.List;

public class Corso implements Comparable<Corso> {

	private List<Studente> studenti;
	private String codins;
	private String nome;
	private int crediti;
	private int pd;

	public Corso() {
		
	}
	
	public Corso(String codins) {
		this.codins = codins;
	}

	public Corso(String codins, int crediti, String nome, int pd) {
		this.codins = codins;
		this.crediti = crediti;
		this.nome = nome;
		this.pd = pd;
	}

	/*
	 * Getters and Setters
	 */

	public String getCodins() {
		if (codins == null)
			return "";
		return codins;
	}

	public void setCodins(String codins) {
		this.codins = codins;
	}

	public String getNome() {
		if (nome == null)
			return "";
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getCrediti() {
		return crediti;
	}

	public void setCrediti(int crediti) {
		this.crediti = crediti;
	}

	public int getPd() {
		return pd;
	}

	public void setPd(int pd) {
		this.pd = pd;
	}

	public List<Studente> getStudenti() {
		if (studenti == null) {
			return new ArrayList<Studente>();
		}
		return studenti;
	}

	public void setStudenti(List<Studente> studenti) {
		this.studenti = studenti;
	}

	/*
	 * Hash Code and Equals
	 */

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		Corso other = (Corso) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}
	
	/*
	 * Usato per ordinare alfabeticamente i nomi dei corsi.
	 */
	
	@Override
	public int compareTo(Corso corsoInput) {
		return this.nome.compareTo(corsoInput.nome);
	}
	
	/*
	 * Metodo toString()
	 */
	@Override
	public String toString() {
		return nome;
	}
}
