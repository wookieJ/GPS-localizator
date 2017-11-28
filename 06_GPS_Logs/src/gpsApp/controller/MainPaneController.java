package gpsApp.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.TooManyListenersException;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;
import gpsApp.log.Gpgga;
import gpsApp.log.Gprmc;
import gpsApp.log.GpsBasicData;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class MainPaneController implements Initializable, SerialPortEventListener
{
	@FXML
	private WebViewPaneController webViewPaneController;

	@FXML
	private ControlPaneController controlPaneController;

	@FXML
	private ContentPaneController contentPaneController;
	
	private InputStream inputStream;
	private SerialPort serialPort;

	private IntegerProperty fixQualityProperty;
	private IntegerProperty satalitesAmountProperty;

	private Instant i1;
	private Instant i2;
	private Duration duration;

	private String stringBuffer;
	private StringBuilder stringBuilderBuffer;

	private GpsBasicData gpsBasicData;
	private Gprmc gpmrcBuffor;
	private Gpgga gpggaBuffor;

	WebEngine engine;

	public InputStream getInputStream()
	{
		return inputStream;
	}

	public SerialPort getSerialPort()
	{
		return serialPort;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		configureBuffor();
		configureProperties();
		configureConnection();
		configureWebEngine();
	}

	private void configureConnection()
	{
		ComboBox<String> comPortComboBox = controlPaneController.getComPortComboBox();
		ComboBox<Integer> baudRateComboBox = controlPaneController.getBaudRateComboBox();
		Button connectButton = controlPaneController.getConnectButton();
		Button disconnectButton = controlPaneController.getDisconnectButton();
		Label statusLabel = contentPaneController.getStatusLabel();
		Label satalitesLabel = contentPaneController.getSatalitesLabel();
		Label satalitesAmountLabel = contentPaneController.getSatalitesAmountLabel();
		ImageView imageView = controlPaneController.getConnectionStatusImageView();
		ToggleButton showOnMap = controlPaneController.getShowPositionButton();
		TextArea textArea = contentPaneController.getTextArea();

		fixQualityProperty.addListener((obs, oldVal, newVal) ->
		{
			int value = (Integer) newVal;

			Platform.runLater(new Runnable()
			{
				@Override
				public void run()
				{
					if (value > 0)
					{
						System.out.println("hereLISTENER");
						int minutes = 0, seconds = 0;
						if (duration != null)
						{
							minutes = (int)duration.toMinutes();
							seconds = (int)duration.getSeconds();
						}
						satalitesLabel.setText("Stan satelity: Po³¹czono [" + minutes + "min " + seconds % 60 + "s ]");
						imageView.setImage(new Image("gpsApp/res/connected.png"));
						showOnMap.setDisable(false);
					}
					else
					{
						satalitesLabel.setText("Stan satelity: Brak zasiêgu!");
						imageView.setImage(new Image("gpsApp/res/disconnected.png"));
						if(showOnMap.isSelected())
							showOnMap.selectedProperty().set(false);
						showOnMap.setDisable(true);
					}
				}
			});

		});

		satalitesAmountProperty.addListener((obs, oldVal, newVal) ->
		{
			int value = (Integer) newVal;

			Platform.runLater(new Runnable()
			{
				@Override
				public void run()
				{
					if (value > 0)
						satalitesAmountLabel.setText("Liczba satelit [" + value + "]");
					else
						satalitesAmountLabel.setText("Brak satelit!");
				}
			});
		});

		comPortComboBox.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>()
		{
			public void handle(MouseEvent event)
			{
				comPortComboBox.getItems().clear();
				comPortComboBox.getItems().addAll(portsList());
			};
		});

		connectButton.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent event)
			{
				if (portOpen(comPortComboBox.getValue(), baudRateComboBox.getValue()))
				{
					statusLabel.setText("Aktywne po³¹czenie [" + comPortComboBox.getValue() + ", "
							+ baudRateComboBox.getValue() + "]");
					i1 = Instant.now();
				}
			}
		});

		disconnectButton.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent event)
			{
				if (portClose())
				{
					statusLabel.setText("Brak po³¹czenia!");
					imageView.setImage(new Image("gpsApp/res/disconnected.png"));
				}
			}
		});

		textArea.textProperty().addListener(new ChangeListener<String>()
		{
			public void changed(javafx.beans.value.ObservableValue<? extends String> observable, String oldValue,
					String newValue)
			{

				Platform.runLater(new Runnable()
				{
					@Override
					public void run()
					{
						if (gpggaBuffor.getFixQuality() > 0 && showOnMap.isSelected() && (!showOnMap.isDisable()))
						{
							engine.executeScript("document.goToLocation(\"" + gpsBasicData.getLatitude() + ", "
									+ gpsBasicData.getLongitude() + "\")");
						}
					}
				});
			}
		});
//
//		showOnMap.setOnAction(new EventHandler<ActionEvent>()
//		{
//			@Override
//			public void handle(ActionEvent event)
//			{
//				if (showOnMap.isSelected())
//				{
//
//				}
//				else
//				{
//
//				}
//			}
//		});
	}

	private String[] portsList()
	{
		Enumeration<?> ports = CommPortIdentifier.getPortIdentifiers();
		ArrayList<String> portsArrayList = new ArrayList<>();

		while (ports.hasMoreElements())
		{
			CommPortIdentifier port = (CommPortIdentifier) ports.nextElement();
			if (port.getPortType() == CommPortIdentifier.PORT_SERIAL)
				portsArrayList.add(port.getName());
		}
		String portArray[];
		portArray = (String[]) portsArrayList.toArray(new String[0]);
		return portArray;
	}

	public boolean portOpen(String getPort, Integer baudRate)
	{
		CommPortIdentifier portIdentifier;

		if (baudRate == null)
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error dialog");
			alert.setContentText("Nie wybrano prêdkoœci transmisji!");

			alert.showAndWait();
			return false;
		}
		if (getPort == null)
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error dialog");
			alert.setContentText("Nie wybrano portu COM!");

			alert.showAndWait();
			return false;
		}

		boolean portState = false;
		try
		{
			portIdentifier = CommPortIdentifier.getPortIdentifier(getPort);
		}
		catch (NoSuchPortException e)
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error dialog");
			alert.setContentText(e.toString());

			alert.showAndWait();
			return false;
		}

		if (portIdentifier == null)
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error dialog");
			alert.setContentText("Nie mo¿na otworzyæ portu " + getPort + "!");

			alert.showAndWait();
			return false;
		}
		if (portIdentifier.isCurrentlyOwned())
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error dialog");
			alert.setContentText("Port " + getPort + "jest zajêty!");

			alert.showAndWait();
			return false;
		}
		else
		{
			CommPort commPort = null;
			try
			{
				commPort = portIdentifier.open("port", 2000);
			}
			catch (PortInUseException e)
			{
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error dialog");
				alert.setContentText("Port " + getPort + "jest u¿ywany!");

				alert.showAndWait();
				return false;
			}

			serialPort = (SerialPort) commPort;

			try
			{
				inputStream = serialPort.getInputStream();
			}
			catch (IOException e)
			{
				e.printStackTrace();
				portState = true;
			}

			if (portState)
			{
				portClose();
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error dialog");
				alert.setContentText("Port " + getPort + "jest zajêty!");

				alert.showAndWait();
				return false;
			}
			else
			{
				serialPort.notifyOnBreakInterrupt(false);
				serialPort.notifyOnFramingError(false);
				serialPort.notifyOnParityError(false);
				serialPort.notifyOnOverrunError(false);
				serialPort.notifyOnCarrierDetect(false);
				serialPort.notifyOnRingIndicator(false);
				serialPort.notifyOnDSR(false);
				serialPort.notifyOnCTS(false);
				serialPort.notifyOnOutputEmpty(false);
				serialPort.notifyOnDataAvailable(true);

				try
				{
					serialPort.setSerialPortParams((int) baudRate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
							SerialPort.PARITY_NONE);
					serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
				}
				catch (UnsupportedCommOperationException e)
				{
					portClose();
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Warning dialog");
					alert.setContentText(e.toString());

					alert.showAndWait();
					return false;
				}

				try
				{
					serialPort.addEventListener(this);
				}
				catch (TooManyListenersException ex)
				{
					portClose();
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Warning dialog");
					alert.setContentText(ex.toString());

					alert.showAndWait();
					return false;
				}
				return true;
			}
		}
	}

	public boolean portClose()
	{
		if (inputStream != null)
		{
			try
			{
				inputStream.close();
			}
			catch (IOException ex)
			{
				portClose();
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Warning dialog");
				alert.setContentText(ex.toString());

				alert.showAndWait();

				return false;
			}
		}

		if (serialPort != null)
		{
			serialPort.removeEventListener();
			serialPort.close();
			return true;
		}

		return false;
	}

	@Override
	public void serialEvent(SerialPortEvent arg0)
	{
		switch (arg0.getEventType())
		{
			case SerialPortEvent.BI:
				break;
			case SerialPortEvent.OE:
				break;
			case SerialPortEvent.FE:
				break;
			case SerialPortEvent.PE:
				break;
			case SerialPortEvent.CD:
				break;
			case SerialPortEvent.CTS:
				break;
			case SerialPortEvent.DSR:
				break;
			case SerialPortEvent.RI:
				break;
			case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
				break;
			case SerialPortEvent.DATA_AVAILABLE:
				try
				{
					int nb = inputStream.available(); // iloœæ bajtów

					while (nb > 0) // odczyt buforowany danych ze strumienia
					{
						byte[] readBuffer = new byte[nb];
						inputStream.read(readBuffer);
						stringBuffer = new String(readBuffer);
						
						System.out.println(stringBuffer);

						stringBuffer = stringBuffer.replaceAll("\r\n", " ");
						stringBuffer = stringBuffer.trim();

						if (stringBuffer.contains("$"))
						{
							stringBuilderBuffer.append(stringBuffer.substring(0, stringBuffer.indexOf("$")));

							if (stringBuilderBuffer.toString().contains("$GPRMC"))
							{
								gpmrcBuffor = new Gprmc(stringBuilderBuffer.toString());
							}
							else if (stringBuilderBuffer.toString().contains("$GPGGA"))
							{
								gpggaBuffor = new Gpgga(stringBuilderBuffer.toString());

								satalitesAmountProperty.set(gpggaBuffor.getAmountOfSatelites());
								fixQualityProperty.set(gpggaBuffor.getFixQuality());

								if (gpggaBuffor.getFixQuality() > 0)
								{
									if (i2 == null)
									{
										i2 = Instant.now();
										if (i1 != null)
										{
											System.out.println("here");
											duration = Duration.between(i1, i2);
										}
									}
								}
								else
								{
//									if (i2 != null)
//									{
//										i1 = Instant.now();
//										i2 = null;
//									}
								}

								if (gpmrcBuffor != null && gpggaBuffor != null)
								{
									gpsBasicData = new GpsBasicData(gpggaBuffor, gpmrcBuffor);
									stringBuilderBuffer = new StringBuilder(gpsBasicData.getTime() + " : " + "Lat="
											+ gpsBasicData.getLatitude() + ", Long=" + gpsBasicData.getLongitude()
											+ ", Alt=" + gpsBasicData.getAltitude());
									contentPaneController.sendToTerminal(stringBuilderBuffer.toString());
								}
							}

							stringBuilderBuffer = new StringBuilder(
									stringBuffer.substring(stringBuffer.indexOf("$"), stringBuffer.length()));
						}
						else
							stringBuilderBuffer.append(stringBuffer);

						nb = inputStream.available();
					}
				}
				catch (IOException ex)
				{
					ex.printStackTrace();
				}

				break;
		}
	}

	private void configureWebEngine()
	{
		WebView webView = webViewPaneController.getWebView();
		engine = webView.getEngine();
		engine.load(getClass().getResource("../view/googlemap.html").toString());
		
		TextField textField = webViewPaneController.getSearchTextField();
		Button searchButton = webViewPaneController.getSearchButton();
		
		searchButton.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent event)
			{
				engine.executeScript("document.goToLocation(\"" + textField.getText() + "\")");
			}
		});
	}

	private void configureBuffor()
	{
		stringBuffer = "";
		stringBuilderBuffer = new StringBuilder();

		gpmrcBuffor = null;
		gpggaBuffor = null;
		gpsBasicData = null;

		i1 = null;
		i2 = null;
		duration = null;
	}

	private void configureProperties()
	{
		fixQualityProperty = new SimpleIntegerProperty(0);
		satalitesAmountProperty = new SimpleIntegerProperty(0);
	}
}