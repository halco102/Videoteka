package app.controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import app.database.database;
import app.classes.user_filmovi;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class evidencijaController implements Initializable {

	@FXML
	private TableView<user_filmovi> table;
	@FXML
	private TableColumn<user_filmovi, Integer> id;
	@FXML
	private TableColumn<user_filmovi, String> username;
	@FXML
	private TableColumn<user_filmovi, String> naziv;
	@FXML
	private Button btn_evidencija;
	@FXML
	private Label lbl_evidencija;

	database test = new database();

	public void btn_evidencija() throws SQLException {
		table.setItems(test.ispis());
	}

	public void initialize(URL arg0, ResourceBundle arg1) {

		id.setCellValueFactory(new PropertyValueFactory<user_filmovi, Integer>("ID"));
		username.setCellValueFactory(new PropertyValueFactory<user_filmovi, String>("username"));
		naziv.setCellValueFactory(new PropertyValueFactory<user_filmovi, String>("nazivFilma"));
		try {
			table.setItems(test.prazno());
		} catch (Exception e) {
			System.out.println(e);
		}

	}

}
