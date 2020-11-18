package app.classes;

import app.classes.filmovi;

public class Serije extends filmovi {

	
	public Serije(){
		super();
	}
	
	
	public Serije (int id ){
		super(id);
	}

	public Serije(int id, String naziv, double cijena,String show) {
		super(id,naziv,cijena);
		this.show=show;
	}

	public Serije(int id, String naziv, String zanr, int trajanje, double cijena , String show) {
		super(id,naziv,zanr,trajanje,cijena,show);
	}

	public Serije(String naziv,String zanr, int trajanje, double cijena){
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
