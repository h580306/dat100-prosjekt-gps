package no.hvl.dat100ptc.oppgave2;
import static java.lang.Integer.*;
import static java.lang.String.*;

import no.hvl.dat100ptc.oppgave1.GPSPoint;

import static java.lang.Double.*;
public class GPSDataConverter {

	
	private static int TIME_STARTINDEX = 11; 

	public static int toSeconds(String timestr) {
		
		int secs;
		int hr, min, sec;
		
		hr = parseInt(timestr.substring(TIME_STARTINDEX, 13)) * 3600;
		min = parseInt(timestr.substring(14, 16)) * 60;
		sec = parseInt(timestr.substring(17, 19));
		secs = hr + min + sec;
		return secs;
		
		
	}

	public static GPSPoint convert(String timeStr, String latitudeStr, String longitudeStr, String elevationStr) {
		
		int time = toSeconds(timeStr);
		double latitude = parseDouble(latitudeStr);
		double longitude = parseDouble(longitudeStr);
		double elevation = parseDouble(elevationStr);
		
		GPSPoint gps = new GPSPoint(time, latitude, longitude, elevation);
		
		return gps;
	}
	
}
