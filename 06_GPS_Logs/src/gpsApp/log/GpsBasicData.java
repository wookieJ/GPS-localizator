package gpsApp.log;

public class GpsBasicData extends GpsPosition
{
	private String time;
	private String date;

	private double speed; // km/h
	private int amountOfSatelites;
	private int fixQuality;
	private double trackAngle;

	public String getTime()
	{
		return time;
	}

	public void setTime(String time)
	{
		this.time = time;
	}

	public String getDate()
	{
		return date;
	}

	public void setDate(String date)
	{
		this.date = date;
	}

	public double getSpeed()
	{
		return speed;
	}

	public void setSpeed(double speed)
	{
		this.speed = speed;
	}

	public int getAmountOfSatelites()
	{
		return amountOfSatelites;
	}

	public void setAmountOfSatelites(int amountOfSatelites)
	{
		this.amountOfSatelites = amountOfSatelites;
	}

	public int getFixQuality()
	{
		return fixQuality;
	}

	public void setFixQuality(int fixQuality)
	{
		this.fixQuality = fixQuality;
	}

	public double getTrackAngle()
	{
		return trackAngle;
	}

	public void setTrackAngle(double trackAngle)
	{
		this.trackAngle = trackAngle;
	}

	@Override
	public String toString()
	{
		return "GpsBasicData [time=" + time + ", date=" + date + ", latitude=" + getLatitude() + ", longitude="
				+ getLongitude() + ", altitude=" + getAltitude() + ", speed=" + speed + ", amountOfSatelites="
				+ amountOfSatelites + ", fixQuality=" + fixQuality + ", trackAngle=" + trackAngle + "]";
	}

	public GpsBasicData(double latitude, double longitude, double altitude, String time, String date,
			GpsPosition position, double speed, int amountOfSatelites, int fixQuality)
	{
		super(latitude, longitude, altitude);
		this.time = time;
		this.date = date;
		this.speed = speed;
		this.amountOfSatelites = amountOfSatelites;
		this.fixQuality = fixQuality;
		// this.trackAngle = trackAngle;
	}

	public GpsBasicData(Gpgga gpgga, Gprmc gprmc)
	{
		super(gprmc.getKMLLatitude(), gprmc.getKMLLongitude(), gpgga.getAveAltitude());

		String tmpSeconds = "0", tmpHours = "0", tmpMinutes = "0";

		if ((Double) gprmc.getTime() != null)
		{
			tmpHours = Integer.toString((int) (gprmc.getTime() / 10000)); // hours
			tmpMinutes = Integer.toString((int) ((gprmc.getTime() % 10000) / 100)); // minutes
			if (tmpMinutes.length() == 1)
			{
				tmpMinutes = "0" + tmpMinutes;
			}

			tmpSeconds = Integer.toString((int) (gprmc.getTime() % 100));
			if (tmpSeconds.length() == 1)
			{
				tmpSeconds = "0" + tmpSeconds;
			}
		}

		this.time = tmpHours + ":" + tmpMinutes + ":" + tmpSeconds;

		StringBuilder strBuilder = new StringBuilder("0");
		if (gprmc.getDate() != null)
		{
			strBuilder = new StringBuilder(gprmc.getDate());
			strBuilder.insert(2, "/");
			strBuilder.insert(5, "/");
		}
		this.date = strBuilder.toString();

		// if((Integer)gpgga.getFixQuality() != null)
		this.fixQuality = gpgga.getFixQuality();
		this.amountOfSatelites = gpgga.getAmountOfSatelites();
		this.speed = gprmc.getSpeedInKnots() * 1.852;
		// this.trackAngle = gprmc.getTrackAngle();
	}

	public GpsBasicData()
	{
	}
}
