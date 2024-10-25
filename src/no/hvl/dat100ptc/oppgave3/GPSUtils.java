package no.hvl.dat100ptc.oppgave3;

import static java.lang.Math.*;

import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave2.GPSDataConverter;
import no.hvl.dat100ptc.TODO;

public class GPSUtils {

	public static double findMax(double[] da) {

		double max;

		max = da[0];

		for (double d : da) {
			if (d > max) {
				max = d;
			}
		}

		return max;
	}

	public static double findMin(double[] da) {

		double min;

		min = da[0];

		for (double m : da) {
			if (m < min) {
				min = m;
			}

		}
		return min;
	}

	public static double[] getLatitudes(GPSPoint[] gpspoints) {

		double[] lat = new double[gpspoints.length];

		for (int i = 0; i < gpspoints.length; i++) {
			lat[i] = gpspoints[i].getLatitude();

		}
		return lat;
	}

	public static double[] getLongitudes(GPSPoint[] gpspoints) {

		double[] longi = new double[gpspoints.length];

		for (int i = 0; i < gpspoints.length; i++) {
			longi[i] = gpspoints[i].getLongitude();

		}
		return longi;

	}

	private static final int R = 6371000; // jordens radius

	public static double distance(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		double latitude1 = Math.toRadians(gpspoint1.getLatitude());
		double longitude1 = Math.toRadians(gpspoint1.getLongitude());
		double latitude2 = Math.toRadians(gpspoint2.getLatitude());
		double longitude2 = Math.toRadians(gpspoint2.getLongitude());

		double deltaphi = latitude2 - latitude1;
		double deltadelta = longitude2 - longitude1;

		double a = compute_a(latitude1, latitude2, deltaphi, deltadelta);

		double c = compute_c(a);

		double distance = R * c;

		return distance;
	}

	private static double compute_a(double phi1, double phi2, double deltaphi, double deltadelta) {

		double a = Math.sin(deltaphi / 2) * Math.sin(deltaphi / 2)
				+ Math.cos(phi1) * Math.cos(phi2) * Math.sin(deltadelta / 2) * Math.sin(deltadelta / 2);
		return a;
	}

	private static double compute_c(double a) {

		return 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	}

	public static double speed(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		double distance = GPSUtils.distance(gpspoint1, gpspoint2);

		int time1 = gpspoint1.getTime();
		int time2 = gpspoint2.getTime();

		int timeDifference = time2 - time1;

		double speed = distance / timeDifference;

		return speed;

	}

	public static String formatTime(int secs) {

		String timestr;
		String TIMESEP = ":";
		
		int tall = secs;

		int timer = tall / 3600;
		tall = tall % 3600;

		int minutter = tall / 60;
		tall = tall % 60;
		
		timestr = String.format("  " + "%02d" + TIMESEP + "%02d" + TIMESEP + "%02d",  timer, minutter, tall);

        return timestr;

	}

	private static int TEXTWIDTH = 10;

	public static String formatDouble(double d) {

		String str = String.format("%10.2f", d);
		
		str = str.replace(',', '.');
		
		return str;

	}
}
