package app.controllers;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ResourceBundle;

import app.classes.PopUp;
import app.classes.filmovi;
import app.database.database;
import app.database.databaseUsers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class cartClass implements Initializable {
	@FXML
	private Button btn_izbrisi, btn_potvrdi, btn_kupi, btn_Nazad;
	@FXML
	private TableView<filmovi> table;
	@FXML
	private TableColumn<filmovi, String> naziv;
	@FXML
	private TableColumn<filmovi, Double> cijena;
	@FXML
	private TableColumn<filmovi, Integer> id;
	@FXML
	private Label lbl_cijena, lbl_ispis;

	// reusable
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
		nazad();
		stage.close();
	}
	// end

	private int[] getIDFilma() { // cuva se u array ID filma
		int[] ima = new int[Main_controller.temp.size()];
		int i = 0;
		for (filmovi o : table.getItems()) {
			ima[i] = o.getId();
			i++;
		}
		return ima;
	}

	public void btn_kupi() {
		// radi upis u tabelu dignutiFilmovi da admin vidi sta je sve uzeto od nekog
		// Kupca
		database.setIDFilma(getIDFilma());
		int[] niz = database.getIDFilma();
		database.zapisi(databaseUsers.getID(), niz);
		// end

	}

	private void nazad() {
		Main_controller.temp.clear();
		table.setItems(Main_controller.temp);
		table.getItems().clear();
		System.out.println("Velicina tempa " + Main_controller.temp.size());

	}

	public void btn_Nazad(ActionEvent e) {
		/*
		 * Main_controller.temp.clear(); table.setItems(Main_controller.temp);
		 * table.getItems().clear(); System.out.println("Velicina tempa " +
		 * Main_controller.temp.size());
		 * ((Node)(e.getSource())).getScene().getWindow().hide();
		 */
		nazad();
		((Node) (e.getSource())).getScene().getWindow().hide();

	}

	public void btn_izbrisi() {
		filmovi film = table.getSelectionModel().getSelectedItem();
		PopUp.showAlertInformation("Izbrisat cete Film Id : " + film.getNaziv());
		for (int i = 0; i < Main_controller.store_id.size(); i++) {
			if (film.getId() == Main_controller.store_id.get(i)) {

				Main_controller.store_id.remove(i);
				table.getItems().removeAll(table.getSelectionModel().getSelectedItem());
			}
		}

	}

	NumberFormat formatter = new DecimalFormat("##.00");

	public void btn_potvrdi() {
		lbl_ispis.setText(formatter.format(izracunaj()));

	}

	private double izracunaj() {
		double suma = 0;
		for (filmovi o : table.getItems()) {
			suma += cijena.getCellData(o);
		}
		return suma;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		lbl_ispis.setText("0");
		id.setCellValueFactory(new PropertyValueFactory<filmovi, Integer>("id"));
		naziv.setCellValueFactory(new PropertyValueFactory<filmovi, String>("naziv"));
		cijena.setCellValueFactory(new PropertyValueFactory<filmovi, Double>("cijena"));

		table.setItems(Main_controller.temp);

	}

}