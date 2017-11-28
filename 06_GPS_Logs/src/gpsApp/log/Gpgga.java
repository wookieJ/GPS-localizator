package gpsApp.log;

public class Gpgga
{
	private double time; // UTC
	private double latitude;
	private String latitudeDirection;
	private double longitude;
	private String longitudeDirection;
	private int fixQuality;
	private int amountOfSatelites;
	private double horizontalDilutionOfPosition;
	private double aveAltitude;
	private String aveAltitudeUnit;
	private double altitude;
	private String altitudeUnit;
	private String checkSum;

	public double getTime()
	{
		return time;
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

	public int getFixQuality()
	{
		return fixQuality;
	}

	public int getAmountOfSatelites()
	{
		return amountOfSatelites;
	}

	public double getHorizontalDilutionOfPosition()
	{
		return horizontalDilutionOfPosition;
	}

	public double getAveAltitude()
	{
		return aveAltitude;
	}

	public String getAveAltitudeUnit()
	{
		return aveAltitudeUnit;
	}

	public double getAltitude()
	{
		return altitude;
	}

	public String getAltitudeUnit()
	{
		return altitudeUnit;
	}

	public String getCheckSum()
	{
		return checkSum;
	}

	@Override
	public String toString()
	{
		return "Gpgga [time=" + time + ", latitude=" + latitude + ", latitudeDirection=" + latitudeDirection
				+ ", longitude=" + longitude + ", longitudeDirection=" + longitudeDirection + ", fixQuality="
				+ fixQuality + ", amountOfSatelites=" + amountOfSatelites + ", horizontalDilutionOfPosition="
				+ horizontalDilutionOfPosition + ", aveAltitude=" + aveAltitude + ", aveAltitudeUnit=" + aveAltitudeUnit
				+ ", altitude=" + altitude + ", altitudeUnit=" + altitudeUnit + ", checkSum=" + checkSum + "]";
	}

	public Gpgga(double time, double latitude, String latitudeDirection, double longitude, String longitudeDirection,
			int fixQuality, int amountOfSatelites, double horizontalDilutionOfPosition, double aveAltitude,
			String aveAltitudeUnit, double altitude, String altitudeUnit, String checkSum)
	{
		this.time = time;
		this.latitude = latitude;
		this.latitudeDirection = latitudeDirection;
		this.longitude = longitude;
		this.longitudeDirection = longitudeDirection;
		this.fixQuality = fixQuality;
		this.amountOfSatelites = amountOfSatelites;
		this.horizontalDilutionOfPosition = horizontalDilutionOfPosition;
		this.aveAltitude = aveAltitude;
		this.aveAltitudeUnit = aveAltitudeUnit;
		this.altitude = altitude;
		this.altitudeUnit = altitudeUnit;
		this.checkSum = checkSum;
	}

	public Gpgga(String gpgga)
	{
		String[] arrayGPS = gpgga.split(",");

		try
		{
			if (!arrayGPS[1].equals("") && arrayGPS[1] != null)
				this.time = Double.parseDouble(arrayGPS[1]);
			if (!arrayGPS[2].equals("") && arrayGPS[2] != null)
				this.latitude = Double.parseDouble(arrayGPS[2]);
			if (!arrayGPS[3].equals("") && arrayGPS[3] != null)
				this.latitudeDirection = arrayGPS[3];
			if (!arrayGPS[4].equals("") && arrayGPS[4] != null)
				this.longitude = Double.parseDouble(arrayGPS[4]);
			if (!arrayGPS[5].equals("") && arrayGPS[5] != null)
				this.longitudeDirection = arrayGPS[5];
			if (!arrayGPS[6].equals("") && arrayGPS[6] != null)
				this.fixQuality = Integer.parseInt(arrayGPS[6]);
			if (!arrayGPS[7].equals("") && arrayGPS[7] != null)
				this.amountOfSatelites = Integer.parseInt(arrayGPS[7]);
			if (!arrayGPS[8].equals("") && arrayGPS[8] != null)
				this.horizontalDilutionOfPosition = Double.parseDouble(arrayGPS[8]);
			if (!arrayGPS[9].equals("") && arrayGPS[9] != null)
				this.aveAltitude = Double.parseDouble(arrayGPS[9]);
			if (!arrayGPS[10].equals("") && arrayGPS[10] != null)
				this.aveAltitudeUnit = arrayGPS[10];
			if (!arrayGPS[11].equals("") && arrayGPS[11] != null)
				this.altitude = Double.parseDouble(arrayGPS[11]);
			if (!arrayGPS[12].equals("") && arrayGPS[12] != null)
				this.altitudeUnit = arrayGPS[12];
			if (!arrayGPS[14].equals("") && arrayGPS[14] != null)
				this.checkSum = arrayGPS[14];
		}
		catch (NumberFormatException e)
		{
			e.printStackTrace();
		}
	}

	public Gpgga()
	{
	}
}
