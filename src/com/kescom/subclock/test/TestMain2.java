package com.kescom.subclock.test;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.kescom.subclock.SunClock;
import com.kescom.subclock.SunClockException;

public class TestMain2 {

	public static void main(String[] args) throws SunClockException
	{
		SimpleDateFormat		format = new SimpleDateFormat();
		SunClock				sunClock = new SunClock();
		Calendar				now = Calendar.getInstance();
		double					sc = sunClock.calcSunClockForCalendar(now);
		
		
		System.out.println("now:     " + format.format(now.getTime()));
		System.out.println("sunrise: " + format.format(sunClock.calcSunrise(now).getTime()));
		System.out.println("sunset:  " + format.format(sunClock.calcSunset(now).getTime()));
		
		System.out.println("sc:      " + sc);
		System.out.println("scu:     " + format.format(sunClock.calcSunClockDateForSunClock(sc).getTime()));
	}
}
