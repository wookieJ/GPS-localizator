package gpsApp.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class ContentPaneController implements Initializable
{
	@FXML
	private Label statusLabel;

	@FXML
	private TextArea textArea;

	@FXML
	private Label satalitesLabel;

	@FXML
	private Label satalitesAmountLabel;

	private int textAreaRows;

	public int getTextAreaRows()
	{
		return textAreaRows;
	}

	public void setTextAreaRows(int textAreaRows)
	{
		this.textAreaRows = textAreaRows;
	}

	public Label getStatusLabel()
	{
		return statusLabel;
	}

	public TextArea getTextArea()
	{
		return textArea;
	}

	public Label getSatalitesLabel()
	{
		return satalitesLabel;
	}

	public void setSatalitesLabel(Label satalitesLabel)
	{
		this.satalitesLabel = satalitesLabel;
	}

	public Label getSatalitesAmountLabel()
	{
		return satalitesAmountLabel;
	}

	public void setSatalitesAmountLabel(Label satalitesAmountLabel)
	{
		this.satalitesAmountLabel = satalitesAmountLabel;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		getStatusLabel().setText("Brak po³¹czenia!");
		setTextAreaRows(0);
		
		getSatalitesLabel().setText("Stan satelity: Brak zasiêgu!");
		getSatalitesAmountLabel().setText("Brak satelit!");
	}

	public void sendToTerminal(String text)
	{
		if (getTextAreaRows() < textArea.getHeight() / 18)
		{
			getTextArea().appendText(text + "\r\n");
			setTextAreaRows(getTextAreaRows() + 1);
		}
		else
		{
			StringBuilder strB = new StringBuilder("");

			String[] array = textArea.getText().split("\n");

			for (int j = 1; j < array.length; j++)
				strB.append(array[j] + "\r\n");

			strB.append(text + "\r\n");

			textArea.setText(strB.toString());
		}
	}
}
