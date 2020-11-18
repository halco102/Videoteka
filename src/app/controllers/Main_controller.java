package app.controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import app.classes.PopUp;
import app.database.database;
import app.database.databaseUsers;
import app.classes.filmovi;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main_controller implements Initializable {

	@FXML
	private Label lbl_user, lbl_ispisUser;
	@FXML
	private TextField txt_search;
	@FXML
	private Button btn_search, btn_kupi;
	@FXML
	private TableView<filmovi> table;
	@FXML
	private TableColumn<filmovi, Integer> id;
	@FXML
	private TableColumn<filmovi, String> ime;
	@FXML
	private TableColumn<filmovi, String> zanr;
	@FXML
	private TableColumn<filmovi, Integer> trajanje;
	@FXML
	private TableColumn<filmovi, Double> cijena;
	@FXML
	private TableColumn<filmovi, String> show;
	
	database data = new database();
//start

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
		stage.setFullScreen(true);

	}

	@FXML
	private void min(MouseEvent e) {

		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		stage.setIconified(true);
	}

	@FXML
	private void close(MouseEvent e) throws SQLException {

		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		stage.close();
	}
	// end



	public void btn_search() throws SQLException { // treba selectad sam nadjeni film
		String search = txt_search.getText();

		if (data.search(search) == true) {
			System.out.println("Postoji film  ID = " + data.getID());
			PopUp.showAlertInformation("Postoji film ID :" + data.getID() + "\nNaziv filma :" + data.getIme());
		} else {
			PopUp.showAlertError("Ne postoji");
			System.out.println("Ne postoji");
		}

	}

	public int id_help = 0;
	public int storage = 0;

	public void btn_kupi(ActionEvent event) throws SQLException {
		filmovi film = table.getSelectionModel().getSelectedItem();
		store_id.add(film.getId());// stora IDs
	}

	public void storing() throws SQLException {

		filter = store_id.stream().distinct().collect(Collectors.toList());

		data.setTest(filter);
		data.getTemp();

		// data.setTemp(storage);
	}

	// test may(29)
	public ObservableList<filmovi> test = FXCollections.observableArrayList();

	// end

	public List<Integer> filter = new ArrayList<>();
	public static int broj = 0;
	// test(uspjesno prosao )

	public static ArrayList<Integer> store_id = new ArrayList<>();
	// end

	// test za ubacivanje u ob list (maj)
	public static void setObs(int ID, String naziv, double cijena){
		
		temp.add(new filmovi(ID, naziv, cijena));
	}

	public static ObservableList<filmovi> temp = FXCollections.observableArrayList();

	// end

	public void btn_cart() throws SQLException {
		storing();
		data.brisanje = true;
		data.brisanjeTemp();

		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/cartPage.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.initStyle(StageStyle.TRANSPARENT);
			stage.setScene(new Scene(root1));
			stage.show();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		lbl_ispisUser.setText(databaseUsers.getUsername());
		id.setCellValueFactory(new PropertyValueFactory<filmovi, Integer>("id"));
		ime.setCellValueFactory(new PropertyValueFactory<filmovi, String>("naziv"));
		zanr.setCellValueFactory(new PropertyValueFactory<filmovi, String>("zanr"));
		trajanje.setCellValueFactory(new PropertyValueFactory<filmovi, Integer>("trajanje"));
		cijena.setCellValueFactory(new PropertyValueFactory<filmovi, Double>("cijena"));
		show.setCellValueFactory(new PropertyValueFactory<filmovi, String>("show"));
		
		try {
			table.setItems(data.getFilmovi());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}