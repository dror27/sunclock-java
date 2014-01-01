package com.kescom.subclock.test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import com.luckycatlabs.sunrisesunset.SunriseSunsetCalculator;
import com.luckycatlabs.sunrisesunset.dto.Location;

public class TestMain1 {

	public static void main(String[] args) 
	{
		if ( args.length < 2 )
		{
			System.err.println("usage: TestMain1 <latitude> <longitude> [<timezone>]");
			System.exit(-1);
		}
		
		TimeZone						timeZone = TimeZone.getDefault();
		Location						location = new Location(args[0], args[1]);
		SunriseSunsetCalculator			calculator = new SunriseSunsetCalculator(location, args.length >= 3 ? args[2] : timeZone.getID());
		Calendar						now = Calendar.getInstance();
		
		SimpleDateFormat				format = new SimpleDateFormat();
		
		// show some results
		System.out.println("getOfficialSunriseCalendarForDate: " + format.format(calculator.getOfficialSunriseCalendarForDate(now).getTime()));
		System.out.println("getOfficialSunsetCalendarForDate: " + format.format(calculator.getOfficialSunsetCalendarForDate(now).getTime()));
		System.out.println("getAstronomicalSunriseCalendarForDate: " + format.format(calculator.getAstronomicalSunriseCalendarForDate(now).getTime()));
		System.out.println("getAstronomicalSunsetCalendarForDate: " + format.format(calculator.getAstronomicalSunsetCalendarForDate(now).getTime()));
		
		// test performance
		Date							startedAt = new Date();
		int								iter = 10000;
		for ( int n = 0 ; n < iter ; n++  )
		{
			calculator.getOfficialSunriseCalendarForDate(now);
		}
		Date							endedAt = new Date();
		long							msec = (endedAt.getTime() - startedAt.getTime());
		System.out.println("" + iter + " iterations in " + msec + " msec, " + 1000.0 * iter / msec + " per sec");
	}

}
