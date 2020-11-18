package app.classes;

import app.classes.filmovi;

public class Animirani extends filmovi {
	public Animirani(){
		super();
	}


	public Animirani (int id ){
		super(id);
	}

	public Animirani(int id, String naziv, double cijena,String show) {
		super(id,naziv,cijena);
		this.show=show;
	}

	public Animirani(int id, String naziv, String zanr, int trajanje, double cijena , String show) {
		super(id,naziv,zanr,trajanje,cijena,show);
	}

	public Animirani(String naziv,String zanr, int trajanje, double cijena){
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
