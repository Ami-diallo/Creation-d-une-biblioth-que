
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Bibliodb {

	
	private String url, user, password;

	private Connection connexion;
	
	
	public Bibliodb(String url, String user, String password) {
			
		this.url = url;
		this.user = user;
		this.password = password;
		connexion = null;
		
	}
	
	public Connection getConnection() {
		
		try {
			this.connexion = DriverManager.getConnection(url, user, password);
		
		 System.out.println("La connexion avec le SGBD est bien établi....");
		
		return connexion;
		
		} catch (SQLException e) {	
			System.out.println("ERROR...");
			e.printStackTrace();
		}

		return null; 
	}

	
	public Object[] getQ1Title(){
		
		try {
			Statement stm = this.connexion.createStatement();
			
			
			ResultSet result = stm.executeQuery("select nom_auteur as auteur, count(no_livre) as nb_scifi from biblio.Auteur, biblio.Livre where genre = 'Science fiction' and livre.auteur = auteur.no_auteur GROUP BY no_auteur");
			
			ResultSetMetaData MM = result.getMetaData(); 
			
			Object[]  tmp = new Object[MM.getColumnCount()]; 

			for(int i=1; i<=MM.getColumnCount();i++) tmp[i-1] = MM.getColumnName(i);

			return tmp;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}
	
	public Object[] getQ2Title(){
		
		try {
			Statement stm = this.connexion.createStatement();
			
			
			ResultSet result = stm.executeQuery("select distinct(nom_adherant) as mtl_adherant from biblio.Adherant, biblio.Emprunt, biblio.Livre where emprunt.no_adherant = adherant.no_adherant and emprunt.no_livre = livre.no_livre and ville = 'Montreal' and genre = 'Horreur' and date_emprunt < '2020-06-16';");
			
			ResultSetMetaData MM = result.getMetaData(); 
			
			Object[]  tmp = new Object[MM.getColumnCount()]; 

			for(int i=1; i<=MM.getColumnCount();i++) tmp[i-1] = MM.getColumnName(i);

			return tmp;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}
	
	public Object[] getQ3Title(){
		
		try {
			Statement stm = this.connexion.createStatement();
			
			
			ResultSet result = stm.executeQuery("select nom_adherant as nom, date_emprunt from biblio.Adherant natural join biblio.Emprunt where date_retour is NULL AND date_emprunt + 14 < CURRENT_DATE;");
			
			ResultSetMetaData MM = result.getMetaData(); 
			
			Object[]  tmp = new Object[MM.getColumnCount()]; 

			for(int i=1; i<=MM.getColumnCount();i++) tmp[i-1] = MM.getColumnName(i);

			return tmp;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}
	
	public Object[] getQ4Title(){
		
		try {
			Statement stm = this.connexion.createStatement();
			
			
			ResultSet result = stm.executeQuery("with R1 as (select count(*) as nb_livre, no_adherant from biblio.emprunt group by no_adherant) "
					+ "SELECT distinct(nom_adherant), no_rue, rue, ville, cp, nb_livre from biblio.emprunt natural join biblio.Adherant natural join R1 where nb_livre > 1;");
			
			ResultSetMetaData MM = result.getMetaData(); 
			
			Object[]  tmp = new Object[MM.getColumnCount()]; 

			for(int i=1; i<=MM.getColumnCount();i++) tmp[i-1] = MM.getColumnName(i);

			return tmp;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}
	
	public Object[][] getQ1(){
		
		try {
			
			int count;
			Statement s = this.connexion.createStatement();
			
			ResultSet result1 = s.executeQuery("select count(*) from (select nom_auteur as auteur, count(no_livre) as nb_scifi from biblio.Auteur, biblio.Livre where genre = 'Science fiction' and livre.auteur = auteur.no_auteur GROUP BY no_auteur) as R1");
			
			if(result1.next()) {
				count = result1.getInt(1);
			}else {
				count = 0;
			}
			
			Statement stm = this.connexion.createStatement();
			
			
			ResultSet result = stm.executeQuery("select nom_auteur as auteur, count(no_livre) as nb_scifi from biblio.Auteur, biblio.Livre where genre = 'Science fiction' and livre.auteur = auteur.no_auteur GROUP BY no_auteur");
			
			ResultSetMetaData MM = result.getMetaData(); 
			
			Object[][] tmp = new Object[count][MM.getColumnCount()];

			int j = 0;
			while(result.next()) {
				for(int i=1; i<=MM.getColumnCount();i++) tmp[j][i-1] = result.getString(i);
				j++;
			}

			return tmp;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}

	public Object[][] getQ2(){
		
			try {
				
				int count;
				Statement s = this.connexion.createStatement();
				
				ResultSet result1 = s.executeQuery("select count(*) from (select distinct(nom_adherant) as mtl_adherant from biblio.Adherant, biblio.Emprunt, biblio.Livre where emprunt.no_adherant = adherant.no_adherant and emprunt.no_livre = livre.no_livre and ville = 'Montreal' and genre = 'Horreur' and date_emprunt < '2020-06-16') as R1;");
				
				if(result1.next()) {
					count = result1.getInt(1);
				}else {
					count = 0;
				}
				
				Statement stm = this.connexion.createStatement();
				
				
				ResultSet result = stm.executeQuery("select distinct(nom_adherant) as mtl_adherant from biblio.Adherant, biblio.Emprunt, biblio.Livre where emprunt.no_adherant = adherant.no_adherant and emprunt.no_livre = livre.no_livre and ville = 'Montreal' and genre = 'Horreur' and date_emprunt < '2020-06-16';");
				
				ResultSetMetaData MM = result.getMetaData(); 
				
				Object[][] tmp = new Object[count][MM.getColumnCount()];

				int j = 0;
				while(result.next()) {
					for(int i=1; i<=MM.getColumnCount();i++) tmp[j][i-1] = result.getString(i);
					j++;
				}

				return tmp;
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			return null;
	}
	
	
	public Object[][] getQ3(){
		
		try {
			
			int count;
			Statement s = this.connexion.createStatement();
			
			ResultSet result1 = s.executeQuery("select count(*) from (select nom_adherant as nom, date_emprunt from biblio.Adherant natural join biblio.Emprunt where date_retour is NULL AND date_emprunt + 14 < CURRENT_DATE) AS R1");
			
			if(result1.next()) {
				count = result1.getInt(1);
			}else {
				count = 0;
			}
			
			Statement stm = this.connexion.createStatement();
			
			
			ResultSet result = stm.executeQuery("select nom_adherant as nom, date_emprunt from biblio.Adherant natural join biblio.Emprunt where date_retour is NULL AND date_emprunt + 14 < CURRENT_DATE;");
			
			ResultSetMetaData MM = result.getMetaData(); 
			
			Object[][] tmp = new Object[count][MM.getColumnCount()];

			int j = 0;
			while(result.next()) {
				for(int i=1; i<=MM.getColumnCount();i++) tmp[j][i-1] = result.getString(i);
				j++;
			}

			return tmp;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}
	
	
	public Object[][] getQ4(){

		try {
			
			int count;
			Statement s = this.connexion.createStatement();
			
			ResultSet result1 = s.executeQuery("select count(*) from (with R1 as (select count(*) as nb_livre, no_adherant from biblio.emprunt group by no_adherant) "
								+ "SELECT distinct(nom_adherant), no_rue, rue, ville, cp, nb_livre from biblio.emprunt natural join biblio.Adherant natural join R1 where nb_livre > 1) as R");
			
			if(result1.next()) {
				count = result1.getInt(1);
			}else {
				count = 0;
			}
			
			Statement stm = this.connexion.createStatement();
			
			
			ResultSet result = stm.executeQuery("with R1 as (select count(*) as nb_livre, no_adherant from biblio.emprunt group by no_adherant) "
								+ "SELECT distinct(nom_adherant), no_rue, rue, ville, cp, nb_livre from biblio.emprunt natural join biblio.Adherant natural join R1 where nb_livre > 1;");
			
			ResultSetMetaData MM = result.getMetaData(); 
			
			Object[][] tmp = new Object[count][MM.getColumnCount()];

			int j = 0;
			while(result.next()) {
				for(int i=1; i<=MM.getColumnCount();i++) tmp[j][i-1] = result.getString(i);
				j++;
			}

			return tmp;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}

	
	public Connection getConnexion() {
		return connexion;
	}

	public void setConnexion(Connection connexion) {
		this.connexion = connexion;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	} 
	
}
	
	
	
	