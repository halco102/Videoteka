package app.controllers;

import java.sql.SQLException;

import app.classes.PopUp;
import app.database.databaseUsers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class loginController {
	databaseUsers connect = new databaseUsers();

	@FXML
	Button btn_signin, btn_create;
	@FXML
	PasswordField txt_pass;
	@FXML
	TextField txt_username;
//reusable kod za fx komande
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

	private void pozivanjeMain() {
		try {

			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/mainPage.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.initStyle(StageStyle.TRANSPARENT);
			stage.setScene(new Scene(root1));
			stage.show();

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void pozivanjeAdminPage() {
		try {

			// FXMLLoader fxmlLoader = new
			// FXMLLoader(getClass().getResource("/adminPage.fxml"));
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/adminPage.fxml"));

			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.initStyle(StageStyle.TRANSPARENT);
			stage.setScene(new Scene(root1));
			stage.show();

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void pozivanjeCreateAccount() {
		try {

			// FXMLLoader fxmlLoader = new
			// FXMLLoader(getClass().getResource("/adminPage.fxml"));
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/CreateAccount.fxml"));

			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(root1));
			stage.show();

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private boolean provjeraKorisnika() {
		String username = txt_username.getText();
		String password = txt_pass.getText();

		if (connect.provjeraKorisnika(username, password) == true) {
			databaseUsers.setID(username);
			return true;
		}
		return false;

	}

	private boolean provjeraAdmina() {
		String username = txt_username.getText();
		String password = txt_pass.getText();
		if (connect.provjeraAdmin(username, password) == true) {
			return true;
		}
		return false;
	}

	public void btn_signin(ActionEvent e) throws SQLException {

		if (provjeraKorisnika() == true) {
			((Node) e.getSource()).getScene().getWindow().hide(); // Izadje iz main Stage (tj,loginPage)
			connect.closeDatabase();
			pozivanjeMain();

		}

		else if (provjeraAdmina() == true) {
			((Node) e.getSource()).getScene().getWindow().hide(); // Izadje iz main Stage (tj,loginPage)
			connect.closeDatabase();
			pozivanjeAdminPage();

		} else {
			PopUp.showAlertWarning("Wrong username or password");

		}

	}

	public void btn_create() {
		pozivanjeCreateAccount();

	}

}
