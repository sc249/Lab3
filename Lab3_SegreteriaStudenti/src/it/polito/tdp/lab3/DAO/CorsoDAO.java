package it.polito.tdp.lab3.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.lab3.model.Corso;
import it.polito.tdp.lab3.model.Studente;

public class CorsoDAO {

	public CorsoDAO() {
		// TODO Auto-generated constructor stub
	}
	
	//tutti i corsi presenti nel DB
	public List<Corso> getCorsi(){
		
		final String sql  = "SELECT DISTINCT * FROM corso;";
		List<Corso> corsi = new ArrayList<Corso>() ;
		
		try{
			Connection conn = ConnectDB.getConnection() ;
			Statement st = conn.createStatement() ;
			ResultSet rs = st.executeQuery(sql) ;
			
			while(rs.next()){
				
				Corso c = new Corso(rs.getString("codins"), rs.getInt("crediti"), rs.getString("nome"), rs.getInt("pd"));
				corsi.add(c);
			}
			
			return corsi ;
			
		}catch(SQLException e){
			e.printStackTrace();
			throw new RuntimeException("Errore DB") ;
		}
	}

	//tutti gli studenti iscritti al corso
	public void getStudenti(Corso corso){
		
		final String sql = "SELECT distinct studente.matricola , studente.cognome, studente.nome, studente.CDS FROM iscrizione, studente WHERE codins=? AND iscrizione.matricola=studente.matricola;";
		
		List<Studente> studenti = new ArrayList<Studente>() ;
		
		try{
			Connection conn = ConnectDB.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setString(1, corso.getCodins());
			ResultSet rs = st.executeQuery();
			
			while(rs.next()){
				Studente s = new Studente (rs.getInt("matricola"), rs.getString("cognome"), rs.getString("nome"), rs.getString("CDS"));
				studenti.add(s) ;
			}
			corso.setStudenti(studenti);
			
		}catch(SQLException e){
			e.printStackTrace();
			throw new RuntimeException("Errore DB") ;
		}
		
		
	}

	//iscrivere studente ad un corso
	public boolean addStudenteACorso( Studente studente, Corso corso ){
		
		
		final String sql = "INSERT INTO `iscritticorsi`.`iscrizione` (`matricola`,`codins`) VALUES (`"+ studente.getMatricola() +"`, `"+corso.getCodins()+" `);";
		
		try{
			
			Connection conn = ConnectDB.getConnection() ;
			Statement st = conn.createStatement();
			int res = st.executeUpdate(sql);
			
			if(res==1)
				return true ;
			else
				return false;
			
		}catch(SQLException e){
			e.printStackTrace();
			throw new RuntimeException("Errore DB");
			
		}
		
	}

}
