package gpsApp.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ControlPaneController implements Initializable
{
	@FXML
	private Button disconnectButton;

	@FXML
	private ComboBox<String> comPortComboBox;

	@FXML
	private ImageView connectionStatusImageView;

	@FXML
	private ComboBox<Integer> baudRateComboBox;

	@FXML
	private Button connectButton;

	@FXML
	private ToggleButton showPositionButton;

	public ToggleButton getShowPositionButton()
	{
		return showPositionButton;
	}

	public Button getDisconnectButton()
	{
		return disconnectButton;
	}

	public ComboBox<String> getComPortComboBox()
	{
		return comPortComboBox;
	}

	public ImageView getConnectionStatusImageView()
	{
		return connectionStatusImageView;
	}

	public ComboBox<Integer> getBaudRateComboBox()
	{
		return baudRateComboBox;
	}

	public Button getConnectButton()
	{
		return connectButton;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		getShowPositionButton().setDisable(true);
		getConnectionStatusImageView().setImage(new Image("gpsApp/res/disconnected.png"));
		
		Integer[] baudRates = new Integer[5];
		baudRates[0] = 9600;
		baudRates[1] = 19200;
		baudRates[2] = 38400;
		baudRates[3] = 57600;
		baudRates[4] = 115200;

		getBaudRateComboBox().getItems().addAll(baudRates);
	}
}
