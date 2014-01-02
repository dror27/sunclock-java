package com.kescom.subclock.test;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.kescom.subclock.SunClock;
import com.kescom.subclock.SunClockException;

public class TestMain2 {

	public static void main(String[] args) throws SunClockException
	{
		SimpleDateFormat		formatFull = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		SimpleDateFormat		formatTime = new SimpleDateFormat("HH:mm:ss");
		SimpleDateFormat		formatDate = new SimpleDateFormat("dd/MM/yyyy");
		SunClock				sunClock = new SunClock();
		Calendar				now = Calendar.getInstance();
		double					sc = sunClock.calcSunClockForCalendar(now);
		Calendar				scDate = sunClock.calcSunClockDateForSunClock(sc);
		Calendar				sunrise = sunClock.calcSunrise(now);
		Calendar				sunset = sunClock.calcSunset(now);
		
		// standard time
		System.out.println("now:      " + formatFull.format(now.getTime()));
		System.out.println("sunrise:  " + formatFull.format(sunClock.calcSunrise(now).getTime()));
		System.out.println("sunset:   " + formatFull.format(sunClock.calcSunset(now).getTime()));
		System.out.println("");
		
		// sun clock time
		System.out.println("sc:       " + sc);
		System.out.println("scu:      " + formatFull.format(scDate.getTime()));
		System.out.println("");
		
		// print table for today
		System.out.println("data for: " + formatDate.format(now.getTime()));
		System.out.println("conf:     " + sunClock.getConf());
		System.out.println("locale:   " + sunClock.getLocale());
		System.out.println("sunrise:  " + formatFull.format(sunrise.getTime()));
		System.out.println("sunset:   " + formatFull.format(sunset.getTime()));
		System.out.println("");
		Calendar				dayStarts = Calendar.getInstance();
		dayStarts.setTime(now.getTime());
		dayStarts.set(Calendar.HOUR_OF_DAY, 0);
		dayStarts.set(Calendar.MINUTE, 0);
		dayStarts.set(Calendar.SECOND, 0);
		dayStarts.set(Calendar.MILLISECOND, 0);
		Calendar				date = Calendar.getInstance();
		date.setTime(dayStarts.getTime());
		Calendar				stopDate = Calendar.getInstance();
		stopDate.setTime(date.getTime());
		stopDate.add(Calendar.DAY_OF_MONTH, 1);
		System.out.println("StdClock SunClock Sc");
		System.out.println("======== ======== =========");
		for ( ; stopDate.after(date) ; date.add(Calendar.MINUTE, 60) )
		{
			sc = sunClock.calcSunClockForCalendar(date);
			scDate = sunClock.calcSunClockDateForSunClock(sc);
			
			String			mark = "?";
			if ( date.before(sunrise) )
				mark = "<";
			else if ( !date.before(sunrise) && !date.after(sunset) )
				mark = "*";
			else if ( date.after(sunset) )
				mark = ">";

			System.out.println(String.format("%1$s %2$s %3$+f %4$s", 
										formatTime.format(date.getTime()),
										formatTime.format(scDate.getTime()),
										sc,
										mark));
										
		}
		
		
	}
}
