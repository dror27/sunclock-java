package com.kescom.subclock;

import java.util.Calendar;

import com.luckycatlabs.sunrisesunset.SunriseSunsetCalculator;
import com.luckycatlabs.sunrisesunset.dto.Location;

public class SunClock {
	
	static private long				MSEC_PER_DAY = (1000 * 60 * 60 * 24);
	
	private SunClockConf			conf;
	public SunClockConf getConf() {
		return conf;
	}

	public SunClockLocale getLocale() {
		return locale;
	}

	private SunClockLocale			locale;
	
	private SunriseSunsetCalculator	calc;

	public SunClock(SunClockLocale locale, SunClockConf conf)
	{
		// save
		this.locale = locale;
		this.conf = conf;
		
		// build calculator
		this.calc = new SunriseSunsetCalculator(
											new Location(locale.getLatitude(), locale.getLongitude()),
											locale.getTimeZone().getID());
	}
	
	public SunClock(SunClockLocale locale)
	{
		this(locale, new SunClockConf());
	}
	
	public SunClock()
	{
		this(new SunClockLocale());
	}
	
	public Calendar calcSunrise(Calendar date)
	{
		return calc.getOfficialSunriseCalendarForDate(date);
	}
	
	public Calendar calcSunset(Calendar date)
	{
		return calc.getOfficialSunsetCalendarForDate(date);
	}
	
	public double calcSunClockForCalendar(Calendar date) throws SunClockException
	{
		// get sunrise & sunset
		Calendar			sunrise = calcSunrise(date);
		Calendar			sunset = calcSunset(date);
		
		// if provided date is between sunrise and sunset, it is mapped to [0..0.5]
		if ( sunrise.compareTo(date) <= 0 && sunset.compareTo(date) >= 0 )
		{
			long			range = sunset.getTime().getTime() - sunrise.getTime().getTime();
			long			portion = date.getTime().getTime() - sunrise.getTime().getTime();
			
			return 0.5 * portion / range;
		}
		
		// moving exactly one day back and forth can be problematic if taking in to account
		// the changing sunrise/sunset times a chance might happen that we move 0 or 2 days
		
		// if provided date is before sunrise, extrapolate from yesterday's sunset
		if ( sunrise.compareTo(date) >= 0 )
		{
			// move to yesterday
			Calendar		yesterday = getCalendarAddDays(date, -1);
			sunset = calcSunset(yesterday);
			
			long			range = sunrise.getTime().getTime() - sunset.getTime().getTime();
			long			portion = sunrise.getTime().getTime() - date.getTime().getTime();
			
			return -0.5 * portion / range;
		}
		
		// else provided date is after sunset, extrapolate form tomorrow's sunrise
		if ( sunset.compareTo(date) <= 0 )
		{
			// move to tomorrow
			Calendar		tomorrow = getCalendarAddDays(date, 1);
			sunrise = calcSunrise(tomorrow);

			long			range = sunrise.getTime().getTime() - sunset.getTime().getTime();
			long			portion = date.getTime().getTime() - sunset.getTime().getTime();
			
			return 0.5 + 0.5 * portion / range;
		}
		
		// if here, we are in error
		throw new SunClockException("could not determine date relation to sunrise/sunset");
	}
	
	public Calendar calcSunClockDateForSunClock(double sc) throws SunClockException
	{
		// start with the current date
		Calendar		date = locale.getCalendar();
		long			delta;
		boolean			sunsetBased = false;
		
		
		if ( !sunsetBased )
		{
			// move back to midnight
			date.set(Calendar.HOUR_OF_DAY, 0);
			date.set(Calendar.MINUTE, 0);
			date.set(Calendar.MILLISECOND, 0);
			
			// move to sunrise
			delta = Math.round(conf.getScuAtSunrise() / conf.getScuPerDay() * MSEC_PER_DAY);
			date.add(Calendar.MILLISECOND, (int)delta);
		}
		else
		{
			// move to actual sunrise
			date = calcSunset(date);
		}
		
		
		// daylight?
		if ( sc >= 0 && sc <= 0.5 )
		{
			delta = Math.round(2 * sc * (conf.getScuAtSunset() - conf.getScuAtSunrise()) / conf.getScuPerDay() * MSEC_PER_DAY);
			date.add(Calendar.MILLISECOND, (int)delta);
			
			return date;
		}
		else if ( sc <= 0 )
		{
			delta = Math.round(2 * sc * (conf.getScuAtSunrise() + conf.getScuPerDay() - conf.getScuAtSunset() ) / conf.getScuPerDay() * MSEC_PER_DAY);
			
			date.add(Calendar.MILLISECOND, (int)delta);
			
			return date;
		}
		else if ( sc >= 0.5 )
		{
			// move to sunset
			delta = Math.round((conf.getScuAtSunset() - conf.getScuAtSunrise()) / conf.getScuPerDay() * MSEC_PER_DAY);
			date.add(Calendar.MILLISECOND, (int)delta);

			delta = Math.round(2 * (sc - 0.5) * (conf.getScuAtSunrise() + conf.getScuPerDay() - conf.getScuAtSunset()) / conf.getScuPerDay() * MSEC_PER_DAY);
			date.add(Calendar.MILLISECOND, (int)delta);
			
			return date;
		}
		
		// if here, we are in error
		throw new SunClockException("could not determine date relation to sunrise/sunset");
	}
	
	private Calendar getCalendarAddDays(Calendar date, int days)
	{
		Calendar		result = locale.getCalendar();
		
		result.setTime(date.getTime());
		result.add(Calendar.DATE, days);

		return result;
	}
}
