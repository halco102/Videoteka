package app.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import app.classes.filmovi;
import app.classes.user_filmovi;
import app.controllers.Main_controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class database extends DatabaseParent{ // ZA FILMOVE

	public boolean brisanje = false;
	private int searchID;
	private String searchIme;
	private static final String URL = "jdbc:mysql://localhost:3306/Videoteka?useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	private static final String USER = "root";
	private static final String PASS = "halco1002";
	private static int[] IDFilma;

	private ObservableList<app.classes.filmovi> filmovi = FXCollections.observableArrayList();
	private ObservableList<user_filmovi> userFilmovi = FXCollections.observableArrayList();
	private static ObservableList<filmovi> tempFilmovi = FXCollections.observableArrayList();


	public database() {
		super(URL,USER,PASS);
	}

	/*
	 * public ObservabList<user_filmovi> getUserFilmovi() throws SQLException{
	 * rs=statement.executeQuery("Select * from dignutiFilmovi"); }
	 */

	// treba prvo kupac Setat filmove_useranme pa onda se salje u tableView nalazi
	// se u zapisi

	// end
	// test da mi ispise sta je kupac sve kupio(UPDATE Radi)
	public ObservableList<user_filmovi> prazno() {
		return userFilmovi;
	}

	public ObservableList<user_filmovi> ispis() throws SQLException {

		rs = statement.executeQuery(
				"Select a.id,c.username,b.ime from dignutifilmovi a inner join filmovi b on b.id = a.id_filma inner join login.users c on c.id = a.id_usera order by c.username ASC");
		while (rs.next()) {
			userFilmovi.add(new user_filmovi(Integer.parseInt(rs.getString(1)), rs.getString(2), rs.getString(3)));
		}
		return userFilmovi;
	}

	// end

	public ObservableList<filmovi> getFilmovi() throws SQLException { // yay radi

		rs = statement.executeQuery("Select * from filmovi");
		while (rs.next()) {
			filmovi.add(new filmovi(Integer.parseInt(rs.getString(1)), rs.getString(2), rs.getString(3),
					Integer.parseInt(rs.getString(4)), Double.parseDouble(rs.getString(5)), rs.getString(6)));
		}
		return filmovi;
	}

	public int getID() {
		return this.searchID;
	}
	public String getIme(){return this.searchIme;}

	public boolean search(String naziv_filma) throws SQLException { // treba napravit da selecta nadjen film
		rs = statement.executeQuery("Select id,ime from filmovi where ime like '" + naziv_filma +'%'+ "'");
		if (rs.next()) {
			this.searchID = rs.getInt(1);
			this.searchIme=rs.getString(2);
			// rs.close(); //??
			// statement.close();//??

			return true;
		} else {

			// rs.close();//??
			// statement.close();//??
			return false;
		}
	}

	public static void izbrisi(int id) throws SQLException {
		staticSt.execute("Delete from dignutifilmovi where id_filma = '" + id + "'");
		staticSt.execute("Delete from filmovi where id = '" + id + "'");
	}


	// TEST (getat id filma)
	/*
	 * public static void setFilmID(String naziv) { try { results=
	 * 
	 * }catch(Exception e) { System.out.println(e); } }
	 */
	// end

	public static int[] getIDFilma() {
		return IDFilma;
	}

	public static void setIDFilma(int[] id_filma) {
		IDFilma = new int[id_filma.length];
		for (int i = 0; i < id_filma.length; i++) {
			IDFilma[i] = id_filma[i];
		}
	}

	// test za povezivanje 2 baze, id usera se stavlja i id filma
	public static void zapisi(int id_usera, int[] id_filma) { // uspjesno zapisuje u tabelu dignutiFilmovi
		try {

			for (int i = 0; i < id_filma.length; i++) {
				staticSt.execute("Insert into dignutifilmovi(id_usera,id_filma) values ('" + id_usera + "','"
						+ id_filma[i] + "')");
			}
			System.out.println("Uspjesno dodano.");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// end

	// provjerit imal istih filmova
	public boolean duplikat(String naziv) {
		try {
			String query = "Select ime from filmovi where ime = '" + naziv + "'";
			rs = statement.executeQuery(query);
			if (rs.next()) {
				return true;
			} else
				return false;

		} catch (Exception e) {
			System.out.println(e);
			return false;
		}

	}

	// end

	// refresh table
	public void refreshTable() throws SQLException { // uspjesno doda real time u tabelu i ispise
		String query = "Select * from filmovi";
		rs = statement.executeQuery(query);
		filmovi.clear(); // koristi se da bi se iz observable izbrisalo sve, jer ce se sve opet dodat +
							// novi objekat
		while (rs.next()) {

			filmovi.add(new filmovi(Integer.parseInt(rs.getString(1)), rs.getString(2), rs.getString(3),
					Integer.parseInt(rs.getString(4)), 
					Double.parseDouble(rs.getString(5)), rs.getString(6)));
		}
	}

	// end
	public void addFilmovi(String naziv, String zanr, int minute, double cijena, String show) { // za admina
		try {
			String query = "Insert into filmovi (ime,zanr,trajanje,cijena,VrsteFilmova)" + "values (?,?,?,?,?)";
			// statement.execute("Insert into Filmovi (ime,zanr,trajanje,cijena)
			// values('"+naziv+"','"+zanr+"','"+minute+"','"+cijena+"')");
			prepared = connection.prepareStatement(query);
			prepared.setString(1, naziv);
			prepared.setString(2, zanr);
			prepared.setInt(3, minute);
			prepared.setDouble(4, cijena);
			prepared.setString(5, show);
			prepared.execute();

			System.out.println("Uspjesno dodano");
		} catch (SQLException exception) {
			System.out.println("\n*** SQLException ***\n");

			while (exception != null) {
				System.out.println("SQLState:    " + exception.getSQLState());
				System.out.println("Message:     " + exception.getMessage());
				System.out.println("Error code:  " + exception.getErrorCode());
				exception = exception.getNextException();
				System.out.println("");
			}
		} catch (java.lang.Exception exception) {
			exception.printStackTrace();
		}

	}

	// napravit metodu da getam ID radi brisanja(tesna faza)
	public void getID(int ID) throws SQLException {
		boolean exists = false;
		rs = statement.executeQuery("Select id from filmovi where id = '" + ID + "'");
		if (rs.next()) {
			exists = true;
		} else {
			System.out.println("Film ID ne postoji");
		}
		if (exists == true) {
			statement.execute("delete from filmovi where id='" + ID + "'");
		}
	}

	// end

	public void setTemp(int ID) throws SQLException {
		String query = "insert into tempspremanje (id_filma) values ('" + ID + "')";
		statement.execute(query);
		// sam id ne da da budu duplikati
	}

	public void brisanjeTemp() throws SQLException {

		statement.execute("Delete from tempspremanje where id >0");
		// statement.close();

	}

	public static void getTemp() throws SQLException {
		String query = "select a.id_filma, b.ime , b.cijena from tempspremanje a inner join filmovi b on b.id=a.id_filma";
		results = staticSt.executeQuery(query);
		// koristi se da bi se iz observable izbrisalo sve, jer ce se sve opet dodat +
		// novi objekat
		while (results.next()) {
			Main_controller.setObs(Integer.parseInt(results.getString(1)), results.getString(2),
					Double.parseDouble(results.getString(3)));

		}

	}

	public void filter() throws SQLException {
		String query = " Select id_filma from tempspremanje";
		rs = statement.executeQuery(query);
		int br = 0;
		while (rs.next()) {
			br++;
		}
		System.out.println(br);
	}

	// test may(29) za obl
	public void setTest(List<Integer> test) throws SQLException {
		int stored = 0;
		int help = 0;
		for (int i = help; i < test.size(); i++) {
			stored = test.get(i);
			String query = "Insert into tempspremanje (id_filma) values ('" + stored + "')";
			statement.execute(query);
		}

	}
	// end

}