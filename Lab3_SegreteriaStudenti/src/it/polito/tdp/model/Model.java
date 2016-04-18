package it.polito.tdp.model;

import java.util.List;

import it.polito.tdp.lab3.DAO.CorsoDAO;
import it.polito.tdp.lab3.DAO.StudenteDAO;

public class Model {

	StudenteDAO studenteDao;
	CorsoDAO corsoDao;

	public Model() {
		studenteDao = new StudenteDAO();
		corsoDao = new CorsoDAO();
	}

	public Studente getStudente(int matricola) {

		Studente studente = new Studente(matricola, null, null, null);
		if(studenteDao.getStudente(studente))
			return studente;
		else
			return null;
	}

	public List<Studente> studentiIscrittiAlCorso(String codins) {
		
		Corso corso = new Corso(codins);
		corsoDao.getStudentiIscrittiAlCorso(corso);
		return corso.getStudenti();
	}

	public List<Corso> cercaCorsiDatoStudente(int matricola) {

		Studente studente = new Studente(matricola);
		studenteDao.getCorsiFromStudente(studente);
		return studente.getCorsi();
	}

	/*
	 * Ritorna TRUE se lo studente è iscritto al corso, FALSE altrimenti
	 */
	public boolean isStudenteIscrittoACorso(int matricola, String codins) {

		Studente studente = new Studente(matricola);
		Corso corso = new Corso(codins);
		return studenteDao.isStudenteIscrittoACorso(studente, corso);
	}

	/*
	 * Iscrive una studente ad un corso. Ritorna TRUE se l'operazione va a buon
	 * fine.
	 */
	public boolean inscriviStudenteACorso(int matricola, String codins) {

		Studente studente = new Studente(matricola);
		Corso corso = new Corso(codins);
		return corsoDao.inscriviStudenteACorso(studente, corso);
	}

	/*
	 * Ritorna tutti i corsi
	 */
	public List<Corso> getTuttiICorsi() {

		return corsoDao.getTuttiICorsi();
	}

}
