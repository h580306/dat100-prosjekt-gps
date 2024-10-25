package no.hvl.dat100ptc.oppgave4;

import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave2.GPSData;
import no.hvl.dat100ptc.oppgave2.GPSDataConverter;
import no.hvl.dat100ptc.oppgave2.GPSDataFileReader;
import no.hvl.dat100ptc.oppgave3.GPSUtils;

import no.hvl.dat100ptc.TODO;

public class GPSComputer {

	private GPSPoint[] gpspoints;

	public GPSComputer(String filename) {

		GPSData gpsdata = GPSDataFileReader.readGPSFile(filename);
		gpspoints = gpsdata.getGPSPoints();

	}

	public GPSComputer(GPSPoint[] gpspoints) {
		this.gpspoints = gpspoints;
	}

	public GPSPoint[] getGPSPoints() {
		return this.gpspoints;
	}

	public double totalDistance() {

		double distance = 0;

		for (int i = 0; i < gpspoints.length - 1; i++) {
			distance += GPSUtils.distance(gpspoints[i], gpspoints[i + 1]);
		}

		return distance;

	}

	public double totalElevation() {

		double totalElevation = 0;

		for (int i = 1; i < gpspoints.length; i++) {
			double elevationDiff = gpspoints[i].getElevation() - gpspoints[i - 1].getElevation();

			// Only add positive elevation gains
			if (elevationDiff > 0) {
				totalElevation += elevationDiff;
			}
		}

		return totalElevation;

	}

	public int totalTime() {
		int startTime = gpspoints[0].getTime();
		int endTime = gpspoints[gpspoints.length - 1].getTime();

		return endTime - startTime;
	}

	public double[] speeds() {

		double[] speeds = new double[gpspoints.length - 1];

		for (int i = 0; i < speeds.length; i++) {
			speeds[i] = GPSUtils.speed(gpspoints[i], gpspoints[i + 1]);
		}
		return speeds;
	}

	public double maxSpeed() {

		double maxspeed = 0;

		for (int i = 0; i < gpspoints.length - 1; i++) {

			double distance = GPSUtils.distance(gpspoints[i], gpspoints[i + 1]);

			int timeDiff = gpspoints[i + 1].getTime() - gpspoints[i].getTime();

			if (timeDiff > 0) {

				double speed = distance / timeDiff;

				if (speed > maxspeed) {
					maxspeed = speed;
				}
			}
		}
		return maxspeed;
	}

	public double averageSpeed() {

		double totalDistance = totalDistance();

		int totalTime = gpspoints[gpspoints.length - 1].getTime() - gpspoints[0].getTime();

		if (totalTime > 0) {
			return totalDistance / totalTime;
		} else {
			return 0.0;
		}
	}

	// conversion factor m/s to miles per hour (mps)
	public static final double MS = 2.23;

	public double kcal(double weight, int secs, double speed) {

		double kcal;

		double met = 0;
		double speedmph = speed * MS;

		if (speedmph < 10) {
			met = 4.0;
		} else if (speedmph < 12) {
			met = 6.0;
		} else if (speedmph < 14) {
			met = 8.0;
		} else if (speedmph < 16) {
			met = 10.0;
		} else if (speedmph < 20) {
			met = 12.0;
		} else {
			met = 16.0;
		}

		double hours = secs / 3600.0;

		return met * weight * hours;
	}

	public double totalKcal(double weight) {

		double totalkcal = 0;

		for (int i = 0; i < gpspoints.length - 1; i++) {

			double distance = GPSUtils.distance(gpspoints[i], gpspoints[i + 1]);

			int timeDiff = gpspoints[i + 1].getTime() - gpspoints[i].getTime();

			double speed = distance / timeDiff;

			totalkcal += kcal(weight, timeDiff, speed);
		}

		return totalkcal;

	}

	private static double WEIGHT = 80.0;

	public void displayStatistics() {

		int totalTimeSecs = totalTime();
		double totalDistanceKm = totalDistance()/1000;
		double totalElevation = totalElevation();
		double maxSpeedKmh = maxSpeed() * 3.6;
		double avgSpeedKmh = averageSpeed() * 3.6;
		double totalKcal = totalKcal(WEIGHT);
		
		int hours = totalTimeSecs / 3600;
		int minutes = (totalTimeSecs % 3600) / 60;
		int seconds = totalTimeSecs % 60;
		
		System.out.println("==============================================");
	    System.out.printf("Total Time     :   %02d:%02d:%02d%n", hours, minutes, seconds);
	    System.out.printf("Total distance :   %8.2f km%n", totalDistanceKm);
	    System.out.printf("Total elevation:   %8.2f m%n", totalElevation);
	    System.out.printf("Max speed      :   %8.2f km/t%n", maxSpeedKmh);
	    System.out.printf("Average speed  :   %8.2f km/t%n", avgSpeedKmh);
	    System.out.printf("Energy         :   %8.2f kcal%n", totalKcal);
	    System.out.println("==============================================");

	}

}
