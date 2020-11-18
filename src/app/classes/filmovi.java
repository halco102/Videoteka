package app.classes;

public class filmovi {
	private int id;
	private String naziv, zanr;
	private int trajanje;
	private double cijena;
	protected String show;

	// za store data
	// Da vidim mozel vratit samo id
	public filmovi(int id) {
		this.id = id;
	}
	// end

	public filmovi(int id, String naziv, double cijena) {
		this.id = id;
		this.naziv = naziv;
		this.cijena = cijena;
	}

	// end

	public filmovi() {
		this.id = 0;
		this.naziv = "";
		this.zanr = "";
		this.trajanje = 0;
		this.cijena = 0;
		this.show="";
	};

	public filmovi(int id, String naziv, String zanr, int trajanje, double cijena, String show) {
		this.id = id;
		this.naziv = naziv;
		this.zanr = zanr;
		this.trajanje = trajanje;
		this.cijena = cijena;
		this.show=show;
	}

	//novi konstruktor za adminKlass i Serije/film/anime class
	public filmovi(String naziv, String zanr, int trajanje, double cijena){
			this.naziv=naziv;
			this.zanr=zanr;
			this.trajanje=trajanje;
			this.cijena=cijena;
		}
		
	//end
	
	public void setShow(String show) {
		this.show=show;
	}
	
	public String getShow() {
		return this.show;
	}
	
	public void setCijena(double cijena) {
		this.cijena = cijena;
	}

	public double getCijena() {
		return this.cijena;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public void setZanr(String zanr) {
		this.zanr = zanr;
	}

	public void setTrajanje(int trajanje) {
		this.trajanje = trajanje;
	}

	public int getId() {
		return this.id;
	}

	public String getNaziv() {
		return this.naziv;
	}

	public String getZanr() {
		return this.zanr;
	}

	public int getTrajanje() {
		return this.trajanje;
	}

}
