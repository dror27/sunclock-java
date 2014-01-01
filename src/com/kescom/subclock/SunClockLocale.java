package com.kescom.subclock;

import java.util.Calendar;
import java.util.TimeZone;

public class SunClockLocale {
	
	private double		latitude;
	private double		longitude;
	private TimeZone	timeZone;
	
	public TimeZone getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(TimeZone timeZone) {
		this.timeZone = timeZone;
	}

	public SunClockLocale(double latitude, double longitude, TimeZone timeZone)
	{
		this.latitude = latitude;
		this.longitude = longitude;
		this.timeZone = timeZone;
	}
	
	public SunClockLocale(double latitude, double longitude)
	{
		this(latitude, longitude, TimeZone.getDefault());
	}
	
	public SunClockLocale()
	{
		this(32.0666700, 34.7666700);
	}
	
	public Calendar getCalendar()
	{
		return Calendar.getInstance(timeZone);
	}
	
	public double getLatitude() {
		return latitude;
	}
	
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	public double getLongitude() {
		return longitude;
	}
	
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	

}
