package app.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class databaseUsers extends DatabaseParent {

	private final String naziv = "User";
	private static String user;
	private static final String URL = "jdbc:mysql://localhost:3306/login?useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	private static final String USER = "root";
	private static final String PASS = "halco1002";
	private static int ID;



	public databaseUsers() { // Uspjesno poveze (user login info se nalazi u ovoj bazi)
		super(URL,USER,PASS);
	}

	private void setUsername(String ime) {
		user = ime;

	}

	public static String getUsername() {
		return user;
	}

//test


	public static void setID(String username) {
		try {
			results = staticSt.executeQuery("Select id,username from users where username = '" + username + "'");
			if (results.next()) {
				ID = results.getInt(1);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static int getID() { // getat ID usera
		return ID;
	}
	// end

	// za admin
	public boolean provjeraAdmin(String ime, String password) {
		try {
			rs = statement.executeQuery("Select username,pass from users where username = '" + ime + "' AND pass='"
					+ password + "' and id = 1 ");
			if (rs.next()) {
				return true;
			} else
				return false;

		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}
	// kad se napravi title bar


	// end

	// treba uradit provjeru ne mogu 2 ista accaunta postojat

	private boolean provjeraUsera(String username) throws SQLException { // kad se kreira provjeraje jel ima u bazi taj// vec
		try {
			rs = statement.executeQuery(
					"Select username from users where username = '" + username + "' and username not like 'admin' ");
			if (rs.next()) {

				return true;
			}

			return false;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}

	}

	private boolean uspjesno = false;

	public boolean getUspjesno() {
		return this.uspjesno;
	}

	// end
	public void upisNovihKorisnika(String username, String password, String repeatPassword) throws SQLException {
		// Kada se upise novi korisnik, ne moze se uradit Login jer je database closed
		// !!
		// Mora se to prepraviti
		// Update (Uradjeno je)
		// boolean uspjesno = false;
		try {

			if (provjeraUsera(username) == true) {
				System.out.println("User vec postoji pokusajte ponovo");
			} else {
				if (password.equals(repeatPassword)) {
					statement.execute("Insert into users(username,pass,naziv) values ('" + username + "','" + password
							+ "','" + this.naziv + "')");
					System.out.println("Uspjesno ste kreirali novi account");
					uspjesno = true;
				} else {
					System.out.println("Password doesnt match");
				}
			}

			// connection.close();
		} catch (SQLException exception) {
			System.out.println("\n*** SQLException ***\n");

			while (exception != null) {
				System.out.println("SQLState:    " + exception.getSQLState());
				System.out.println("Message:     " + exception.getMessage());
				System.out.println("Error code:  " + exception.getErrorCode());
				exception = exception.getNextException();
				System.out.println("");
			}
		}

		catch (java.lang.Exception exception) {
			exception.printStackTrace();
		}

	}

	public boolean provjeraKorisnika(String ime, String password) { // uspjesno radi

		try {
			String query = "Select * from users where username = '" + ime + "'";
			// statement.execute("Select * from users where ime = '" +ime+ "' ");
			rs = statement.executeQuery("Select username,pass from users where username = '" + ime + "' AND pass='"
					+ password + "' and id > 1 ");

			if (rs.next()) {

				setUsername(ime);

				// rs.close(); //??
				// statement.close();//??

				return true;
			} else {
				// rs.close();//??
				// statement.close();//??
				return false;
			}

		} catch (SQLException exception) {
			System.out.println("\n*** SQLException ***\n");

			while (exception != null) {
				System.out.println("SQLState:    " + exception.getSQLState());
				System.out.println("Message:     " + exception.getMessage());
				System.out.println("Error code:  " + exception.getErrorCode());
				exception = exception.getNextException();
				System.out.println("");
			}
			return false;
		} catch (java.lang.Exception exception) {
			exception.printStackTrace();
			return false;
		}

	}

}
