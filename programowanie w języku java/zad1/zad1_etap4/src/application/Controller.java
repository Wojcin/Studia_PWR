package application;

import java.io.FileNotFoundException;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import zad1.Instancja;
import zad1.Wynik;

public class Controller implements Initializable {
	@FXML
	CheckBox checkBoxBF;
	@FXML
	CheckBox checkBoxGr;
	@FXML
	TextField readTextField;

	String filename;

	String algorithm;

	public void exitButtonClicked() {
		System.exit(0);
	}

	public void readDataButtonClicked(ActionEvent event) throws IOException {
		if (checkBoxBF.isSelected() && checkBoxGr.isSelected()) {
			AnchorPane enterData = (AnchorPane) FXMLLoader.load(getClass().getResource("ReadData.fxml"));
			Scene scene = new Scene(enterData, 600, 400);
			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

			window.setScene(scene);
			window.show();

			ReadDataController.setAlg(1, 1);

		} else if (checkBoxBF.isSelected()) {

			AnchorPane enterData = (AnchorPane) FXMLLoader.load(getClass().getResource("ReadData.fxml"));
			Scene scene = new Scene(enterData, 600, 400);
			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

			window.setScene(scene);
			window.show();

			ReadDataController.setAlg(1, 0);

		} else if (checkBoxGr.isSelected()) {
			AnchorPane enterData = (AnchorPane) FXMLLoader.load(getClass().getResource("ReadData.fxml"));
			Scene scene = new Scene(enterData, 600, 400);
			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

			window.setScene(scene);
			window.show();

			ReadDataController.setAlg(0, 1);
		} else {
			new Alert(Alert.AlertType.INFORMATION, "Wybierz algorytm!").showAndWait();
		}
	}

	public void enterDataButtonClicked() {
		if (checkBoxBF.isSelected() && checkBoxGr.isSelected()) {
			new Alert(Alert.AlertType.INFORMATION, "Wybrano BF i GR").showAndWait();
		} else if (checkBoxBF.isSelected()) {
			new Alert(Alert.AlertType.INFORMATION, "Wybrano BF").showAndWait();
		} else if (checkBoxGr.isSelected()) {
			new Alert(Alert.AlertType.INFORMATION, "Wybrano GR").showAndWait();
		} else {
			new Alert(Alert.AlertType.INFORMATION, "Wybierz algorytm!").showAndWait();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

}
