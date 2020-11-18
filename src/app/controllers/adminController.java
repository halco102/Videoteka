package app.controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import app.classes.*;
import app.database.database;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class adminController implements Initializable {

	@FXML
    private RadioButton rb_filmovi;

    @FXML
    private RadioButton rb_serije;

    @FXML
    private RadioButton rb_animirani;
	
	@FXML
	private Button btn_izbrisi, btn_dodaj, btn_evidencija;
	@FXML
	private Label lbl_Userlbl_naziv, lbl_zanr, lbl_trajanje, lbl_cijena, lbl_ispisUser;
	@FXML
	private TextField txt_naziv, txt_zanr, txt_trajanje, txt_cijena;
	@FXML
	private TableView<filmovi> table;
	@FXML
	private TableColumn<filmovi, String> naziv, zanr,show;
	@FXML
	private TableColumn<filmovi, Integer> id, trajanje;
	@FXML
	private TableColumn<filmovi, Double> cijena;

	private double x, y;

	@FXML
	void pressed(MouseEvent e) {
		x = e.getSceneX();
		y = e.getSceneY();
	}

	@FXML
	void dragged(MouseEvent e) {
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		stage.setX(e.getScreenX() - x);
		stage.setY(e.getScreenY() - y);

	}

	@FXML
	private void max(MouseEvent e) {
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		stage.setMaximized(true);
		// stage.setFullScreen(true);

	}

	@FXML
	private void min(MouseEvent e) {

		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		stage.setIconified(true);
	}

	@FXML
	private void close(MouseEvent e) {

		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		stage.close();
	}

	// END TITLE
	database baza = new database();

	private void pozivanje() {
		try {

			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/evidencijaPage.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(root1));
			stage.show();

		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

	public void btn_evidencija() {
		pozivanje();
	}

	public void btn_izbrisi() throws SQLException {
		filmovi film = table.getSelectionModel().getSelectedItem(); // izbacuje id selectovanog rowa
		
		//ovo ce ostati isto, jer treba samo brisati Film(Serije,Anime) iz database
		PopUp.showAlertWarning("Izbrisat cete Film ID = " + film.getId());
		database.izbrisi(film.getId());
		baza.refreshTable();
		//End 
		
	}

	private boolean decimal_tr(String cijena) {
		return ((!cijena.equals("")) && (cijena != null)) && (cijena.matches("[0-9]*\\.?[0-9]*"));
	}

	private boolean provjera_tr(String tr) {
		return ((!tr.equals("")) && (tr != null)) && (tr.matches("^[0-9]*$"));
	}

	private boolean provjera_zanra(String zanr) {
		return ((!zanr.equals("")) && (zanr != null)) && (zanr.matches("^[a-zA-Z]*$"));
	}

	
	
	public void btn_dodaj() throws SQLException {
		String naziv, zanr;
		int trajanje;
		String tr;
		String pare;
		double cijena;
		naziv = txt_naziv.getText();
		zanr = txt_zanr.getText();
		tr = txt_trajanje.getText();
		pare = txt_cijena.getText();
		
		if (baza.duplikat(naziv) == true) { // radi provjera
			System.out.println("Film|Serija|Animiran film " + naziv + 
			" vec postoji u bazi, dodajte drugi Film|Serija|Animiran film");
		} else {
			if (provjera_tr(tr) == true && decimal_tr(pare) == true && provjera_zanra(zanr) == true) {
				trajanje = Integer.parseInt(tr);
				cijena = Double.parseDouble(pare);
				if(rb_serije.isSelected()) {
					
					filmovi obj= new Serije(naziv,zanr,trajanje,cijena);
					obj.setShow("Serija");
					baza.addFilmovi(obj.getNaziv(), obj.getZanr(),obj.getTrajanje(), obj.getCijena(),obj.getShow());
				}else if(rb_filmovi.isSelected()) {
					filmovi obj = new Film(naziv,zanr,trajanje,cijena);
					obj.setShow("Film");
					baza.addFilmovi(obj.getNaziv(), obj.getZanr(),obj.getTrajanje(), obj.getCijena(),obj.getShow());
				}else if(rb_animirani.isSelected()) {
					filmovi obj = new Animirani(naziv,zanr,trajanje,cijena);
					obj.setShow("Animirani film");
					baza.addFilmovi(obj.getNaziv(), obj.getZanr(),obj.getTrajanje(), obj.getCijena(),obj.getShow());
				}
				
				//baza.addFilmovi(naziv, zanr, trajanje, cijena);
				baza.refreshTable();
			} else {
				if (provjera_tr(tr) == false && decimal_tr(pare) == false && provjera_zanra(zanr) == false) {
					txt_trajanje.clear();
					txt_cijena.clear();
					txt_zanr.clear();
					PopUp.showAlertError("Pogresan unos, pokusajte ponovo");
				} else if (provjera_tr(tr) == false) {
					txt_trajanje.clear();
					PopUp.showAlertError("Trajanje mora biti broj ");
				} else if (decimal_tr(pare) == false) {
					txt_cijena.clear();
					PopUp.showAlertError("Cijena mora biti broj");
				} else {
					txt_zanr.clear();
					PopUp.showAlertError("Zanr mora biti rijec");
				}
			}

		}
		
		
	}
	//Radio Button

	
	
	
	//END

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		lbl_ispisUser.setText("Admin");
		id.setCellValueFactory(new PropertyValueFactory<filmovi, Integer>("id"));
		naziv.setCellValueFactory(new PropertyValueFactory<filmovi, String>("naziv"));
		zanr.setCellValueFactory(new PropertyValueFactory<filmovi, String>("zanr"));
		trajanje.setCellValueFactory(new PropertyValueFactory<filmovi, Integer>("trajanje"));
		cijena.setCellValueFactory(new PropertyValueFactory<filmovi, Double>("cijena"));
		show.setCellValueFactory(new PropertyValueFactory<filmovi, String>("show"));

		try {
			table.setItems(baza.getFilmovi());

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
	}
}
