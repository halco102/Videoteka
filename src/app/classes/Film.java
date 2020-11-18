package app.classes;

public class Film extends filmovi {


	public Film(){
		super();
	}


	public Film (int id ){
		super(id);
	}

	public Film(int id, String naziv, double cijena,String show) {
		super(id,naziv,cijena);
		this.show=show;
	}

	public Film(int id, String naziv, String zanr, int trajanje, double cijena , String show) {
		super(id,naziv,zanr,trajanje,cijena,show);
	}

	public Film(String naziv,String zanr, int trajanje, double cijena){
		super(naziv,zanr,trajanje,cijena);
		
	}
	
	@Override
	public void setShow(String show) {
		this.show=show;
	
	}
	
	@Override
	public String getShow() {
		return this.show;
	}
	
	
	
	
}
