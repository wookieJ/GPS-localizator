package gpsApp.main;

import gpsApp.controller.MainPaneController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application
{
	private final String title = "GPS Logg App";
//	private static InputStream inputStream;
//	private static SerialPort serialPort;
	private MainPaneController mainController;
	
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		Parent parent = (Parent) FXMLLoader.load(getClass().getResource("/gpsApp/view/MainPane.fxml"));
		Scene scene = new Scene(parent);
		primaryStage.setScene(scene);
		primaryStage.setTitle(title);
		primaryStage.getIcons().add(new Image("/gpsApp/res/windowIcon.png"));
		primaryStage.show();
	}
	
	@Override
	public void stop() throws Exception
	{
		if(mainController.portClose())
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning dialog");
			alert.setContentText("ZAMKNIÊTO");

			alert.showAndWait();
		}
		else
		{
//			Alert alert = new Alert(AlertType.ERROR);
//			alert.setTitle("Warning dialog");
//			alert.setContentText("NIE UDA£O SIÊ ZAMKNAÆ PORTU");
//
//			alert.showAndWait();
		}
	}
	
	public static void main(String[] args)
	{
		
//		inputStream = mainController.getInputStream();
//		serialPort = mainController.getSerialPort();
		
		launch(args);
	}
	
	public Main()
	{
		mainController = new MainPaneController();
	}
}