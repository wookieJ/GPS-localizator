package gpsApp.log;

public class GpsPosition
{
	private double latitude; // KML format, N
	private double longitude; // KML format, E
	private double altitude;

	public double getLatitude()
	{
		return latitude;
	}

	public void setLatitude(double latitude)
	{
		this.latitude = latitude;
	}

	public double getLongitude()
	{
		return longitude;
	}

	public void setLongitude(double longitude)
	{
		this.longitude = longitude;
	}

	public double getAltitude()
	{
		return altitude;
	}

	public void setAltitude(double altitude)
	{
		this.altitude = altitude;
	}

	@Override
	public String toString()
	{
		return "latitude=" + latitude + ", longitude=" + longitude + ", altitude=" + altitude;
	}

	public GpsPosition(double latitude, double longitude, double altitude)
	{
		this.latitude = latitude;
		this.longitude = longitude;
		this.altitude = altitude;
	}
	
	public GpsPosition()
	{
	}
}
