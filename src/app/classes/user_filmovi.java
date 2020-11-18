package app.classes;

public class user_filmovi {
	private int ID;
	private String username;
	private String nazivFilma;

	public user_filmovi() {

	}

	public user_filmovi(int ID, String username, String nazivFilma) {
		this.ID = ID;
		this.username = username;
		this.nazivFilma = nazivFilma;
	}

	public void setID(int id) {
		this.ID = id;
	}

	public int getID() {
		return this.ID;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setFilm(String nazivFilma) {
		this.nazivFilma = nazivFilma;
	}

	public String getUsername() {
		return this.username;
	}

	public String getNazivFilma() {
		return this.nazivFilma;
	}

}
