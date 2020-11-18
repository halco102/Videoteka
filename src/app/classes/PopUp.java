package app.classes;

import javafx.application.Platform;
import javafx.scene.control.Alert;

public class PopUp {
	public static void showAlertInformation(String poruka) {
		Platform.runLater(new Runnable() {
			public void run() {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Error");
				alert.setHeaderText(poruka);
				// alert.setContentText("I have a great message for you!");
				alert.showAndWait();
			}
		});
	}

	public static void showAlertWarning(String poruka) {
		Platform.runLater(new Runnable() {
			public void run() {
				Alert alert = new Alert(Alert.AlertType.WARNING);
				alert.setTitle("Error");
				alert.setHeaderText(poruka);
				// alert.setContentText("I have a great message for you!");
				alert.showAndWait();
			}
		});
	}

	public static void showAlertConfirmation(String poruka) {
		Platform.runLater(new Runnable() {
			public void run() {
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setTitle("Error");
				alert.setHeaderText(poruka);
				// alert.setContentText("I have a great message for you!");
				alert.showAndWait();
			}
		});
	}

	public static void showAlertError(String poruka) {
		Platform.runLater(new Runnable() {
			public void run() {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Warning");
				alert.setHeaderText(poruka);
				// alert.setContentText("I have a great message for you!");
				alert.showAndWait();
			}
		});
	}

}
