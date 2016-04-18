package it.polito.tdp.lab3.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.model.Corso;
import it.polito.tdp.model.Studente;

public class StudenteDAO {

	/*
	 * Controllo se uno studente (matricola) è iscritto ad un corso (codins)
	 */
	public boolean isStudenteIscrittoACorso(Studente studente, Corso corso) {

		final String sql = "SELECT * FROM iscrizione where codins=? and matricola=?";

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, corso.getCodins());
			st.setInt(2, studente.getMatricola());

			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				return true;

			} else {
				return false;
			}

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

	/*
	 * Data una matricola ottengo la lista dei corsi (codins) a cui è iscritto
	 */
	public boolean getCorsiFromStudente(Studente studente) {

		final String sql = "SELECT * FROM iscrizione, corso WHERE iscrizione.codins=corso.codins AND matricola=?";

		List<Corso> corsi = new LinkedList<Corso>();

		boolean returnValue = false;

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, studente.getMatricola());
			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				returnValue = true;

				Corso corso = new Corso(rs.getString("codins"), rs.getInt("crediti"), rs.getString("nome"),
						rs.getInt("pd"));

				corsi.add(corso);
			}

			studente.setCorsi(corsi);

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}

		return returnValue;
	}

	/*
	 * Data una matricola ottengo lo studente.
	 */
	public boolean getStudente(Studente studente) {

		final String sql = "SELECT * FROM studente where matricola=?";

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, studente.getMatricola());

			ResultSet rs = st.executeQuery();

			if (rs.next()) {

				studente.setNome(rs.getString("nome"));
				studente.setCognome(rs.getString("cognome"));
				studente.setCds(rs.getString("cds"));
				return true;

			} else {
				return false;
			}

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

}
