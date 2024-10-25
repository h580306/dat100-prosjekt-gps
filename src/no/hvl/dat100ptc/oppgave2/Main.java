package no.hvl.dat100ptc.oppgave2;

import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class Main {

	
	public static void main(String[] args) {
		
		GPSData gpsData = new GPSData(2);
		
		new Main(gpsData);
	}
	
	public  Main(GPSData gpsdata) {
		GPSPoint gpspoint = new GPSPoint(7, 3.0, 2.0, 7.0);
		GPSPoint gps = new GPSPoint(10, 5.0, 4.0, 12.0);

		gpsdata.insertGPS(gpspoint);
		gpsdata.insertGPS(gps);
		
		gpsdata.print();
	}
}
