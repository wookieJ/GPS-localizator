package gpsApp.log;

public class Gprmc
{
	private double time; // UTC
	private String activeState;
	private double latitude;
	private String latitudeDirection;
	private double longitude;
	private String longitudeDirection;
	private double speedInKnots;
	private double trackAngle;
	private String date; // DD/MM/YY
	private String checkSum;

	public double getTime()
	{
		return time;
	}

	public String getActiveState()
	{
		return activeState;
	}

	public double getLatitude()
	{
		return latitude;
	}

	public String getLatitudeDirection()
	{
		return latitudeDirection;
	}

	public double getLongitude()
	{
		return longitude;
	}

	public String getLongitudeDirection()
	{
		return longitudeDirection;
	}

	public double getSpeedInKnots()
	{
		return speedInKnots;
	}

	public double getTrackAngle()
	{
		return trackAngle;
	}

	public String getDate()
	{
		return date;
	}

	public String getCheckSum()
	{
		return checkSum;
	}

	double getKMLLatitude()
	{
		double latitudeKML = (double) ((int) getLatitude() / 100);
		double latitudeKMLMin = (getLatitude() - latitudeKML * 100) / 60;
		latitudeKML += latitudeKMLMin;

		if (getLatitudeDirection() == "S")
		{
			latitudeKML *= (-1);
		}

		return latitudeKML;
	}

	double getKMLLongitude()
	{
		double longitudeKML = (double) ((int) getLongitude() / 100);
		double longitudeKMLMin = (getLongitude() - longitudeKML * 100) / 60;
		longitudeKML += longitudeKMLMin;

		if (getLongitudeDirection() == "W")
		{
			longitudeKML *= (-1);
		}

		return longitudeKML;
	}

	@Override
	public String toString()
	{
		return "Gprmc [time=" + time + ", activeState=" + activeState + ", latitude=" + latitude
				+ ", latitudeDirection=" + latitudeDirection + ", longitude=" + longitude + ", longitudeDirection="
				+ longitudeDirection + ", speedInKnots=" + speedInKnots + ", trackAngle=" + trackAngle + ", date="
				+ date + ", checkSum=" + checkSum + "]";
	}

	public Gprmc(double time, String activeState, double latitude, String latitudeDirection, double longitude,
			String longitudeDirection, double speedInKnots, double trackAngle, String date, String checkSum)
	{
		super();
		this.time = time;
		this.activeState = activeState;
		this.latitude = latitude;
		this.latitudeDirection = latitudeDirection;
		this.longitude = longitude;
		this.longitudeDirection = longitudeDirection;
		this.speedInKnots = speedInKnots;
		this.trackAngle = trackAngle;
		this.date = date;
		this.checkSum = checkSum;
	}

	public Gprmc(String gprmc)
	{
		String[] arrayGPS = gprmc.split(",");

		try
		{
			if (!arrayGPS[1].equals("") && arrayGPS[1] != null)
				this.time = Double.parseDouble(arrayGPS[1]);
			if (!arrayGPS[2].equals("") && arrayGPS[2] != null)
				this.activeState = arrayGPS[2];
			if (!arrayGPS[3].equals("") && arrayGPS[3] != null)
				this.latitude = Double.parseDouble(arrayGPS[3]);
			if (!arrayGPS[4].equals("") && arrayGPS[4] != null)
				this.latitudeDirection = arrayGPS[4];
			if (!arrayGPS[5].equals("") && arrayGPS[5] != null)
				this.longitude = Double.parseDouble(arrayGPS[5]);
			if (!arrayGPS[6].equals("") && arrayGPS[6] != null)
				this.longitudeDirection = arrayGPS[6];
			if (!arrayGPS[7].equals("") && arrayGPS[7] != null)
				this.speedInKnots = Double.parseDouble(arrayGPS[7]);
			if (!arrayGPS[8].equals("") && arrayGPS[8] != null)
				this.trackAngle = Double.parseDouble(arrayGPS[8]);
			if (!arrayGPS[9].equals("") && arrayGPS[9] != null)
				this.date = arrayGPS[9];
			if (!arrayGPS[12].equals("") && arrayGPS[12] != null)
				this.checkSum = arrayGPS[12];
		}
		catch (NumberFormatException e)
		{
			e.printStackTrace();
		}
	}

	public Gprmc()
	{
	}

}