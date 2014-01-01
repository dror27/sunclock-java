package com.kescom.subclock;

public class SunClockConf {

	private double			scuPerDay;			// number of sun-clock-units per day
	private double			scuAtSunrise;		// scu when the sun rises: normally 6 (a.k.a 6am)
	private double			scuAtSunset;		// scy when the sun sets: normally 18 (a.k.a 6pm)
	
	public SunClockConf()
	{
		// install default values
		scuPerDay = 24;
		scuAtSunrise = 6;
		scuAtSunset = 18;
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


