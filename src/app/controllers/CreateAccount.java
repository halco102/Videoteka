package app.controllers;

import java.sql.SQLException;

import app.classes.PopUp;
import app.database.databaseUsers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class CreateAccount {

	databaseUsers User = new databaseUsers();

	@FXML
	private TextField username;

	@FXML
	private PasswordField password;

	@FXML
	private PasswordField repeatPassword;

	@FXML
	void btnCreate(ActionEvent event) throws SQLException {
		String user, pass, repeat;
		user = username.getText();
		pass = password.getText();
		repeat = repeatPassword.getText();

		User.upisNovihKorisnika(user, pass, repeat);
		if (User.getUspjesno() == true) {
			PopUp.showAlertInformation("Uspjesno ste napravili account");
			((Node) event.getSource()).getScene().getWindow().hide();
		}

	}

}
