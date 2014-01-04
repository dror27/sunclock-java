package com.kescom.sunclock;

public class SunClockConf {

	private double			scuPerDay;			// number of sun-clock-units per day
	private double			scuAtSunrise;		// scu when the sun rises: normally 6 (a.k.a 6am)
	private double			scuAtSunset;		// scy when the sun sets: normally 18 (a.k.a 6pm)
	
	public SunClockConf()
	{
		// install default values
		scuPerDay = 24;
		scuAtSunrise = 7;
		scuAtSunset = 20;
	}

	@Override
	public String toString()
	{
		return String.format("effective window: %1$.1f/%3$.1f to %2$.1f/%3$.1f", scuAtSunrise, scuAtSunset, scuPerDay);
	}
	
	public double getScuPerDay() {
		return scuPerDay;
	}

	public void setScuPerDay(double scuPerDay) {
		this.scuPerDay = scuPerDay;
	}

	public double getScuAtSunrise() {
		return scuAtSunrise;
	}

	public void setScuAtSunrise(double scuAtSunrise) {
		this.scuAtSunrise = scuAtSunrise;
	}

	public double getScuAtSunset() {
		return scuAtSunset;
	}

	public void setScuAtSunset(double scuAtSunset) {
		this.scuAtSunset = scuAtSunset;
	}
}


