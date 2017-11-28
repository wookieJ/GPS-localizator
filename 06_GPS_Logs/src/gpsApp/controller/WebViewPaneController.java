package gpsApp.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.web.WebView;

public class WebViewPaneController implements Initializable
{
	@FXML
	private ToggleButton satelliteButton;

	@FXML
	private ToggleButton hybridButton;

	@FXML
	private ToggleButton terrainButton;

	@FXML
	private ToggleButton roadButton;

	@FXML
	private Button searchButton;

	@FXML
	private TextField searchTextField;

	public TextField getSearchTextField()
	{
		return searchTextField;
	}

	public void setSearchTextField(TextField searchTextField)
	{
		this.searchTextField = searchTextField;
	}

	public Button getSearchButton()
	{
		return searchButton;
	}

	public void setSearchButton(Button searchButton)
	{
		this.searchButton = searchButton;
	}

	public ToggleButton getSatelliteButton()
	{
		return satelliteButton;
	}

	public void setSatelliteButton(ToggleButton satelliteButton)
	{
		this.satelliteButton = satelliteButton;
	}

	public ToggleButton getHybridButton()
	{
		return hybridButton;
	}

	public void setHybridButton(ToggleButton hybridButton)
	{
		this.hybridButton = hybridButton;
	}

	public ToggleButton getTerrainButton()
	{
		return terrainButton;
	}

	public void setTerrainButton(ToggleButton terrainButton)
	{
		this.terrainButton = terrainButton;
	}

	public ToggleButton getRoadButton()
	{
		return roadButton;
	}

	public void setRoadButton(ToggleButton roadButton)
	{
		this.roadButton = roadButton;
	}

	@FXML
	private WebView webView;

	public WebView getWebView()
	{
		return webView;
	}

	public void setWebView(WebView webView)
	{
		this.webView = webView;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{

	}
}
