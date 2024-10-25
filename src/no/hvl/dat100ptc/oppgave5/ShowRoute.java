package no.hvl.dat100ptc.oppgave5;

import javax.swing.JOptionPane;

import easygraphics.EasyGraphics;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave3.GPSUtils;
import no.hvl.dat100ptc.oppgave4.GPSComputer;

import no.hvl.dat100ptc.TODO;

public class ShowRoute extends EasyGraphics {

    private static int MARGIN = 50;
    private static int MAPXSIZE = 800;
    private static int MAPYSIZE = 800;

    private GPSPoint[] gpspoints;
    private GPSComputer gpscomputer;

    private double minlon, minlat, maxlon, maxlat;

    private double xstep, ystep;

    public ShowRoute() {

        String filename = JOptionPane.showInputDialog("GPS data filnavn: ");
        gpscomputer = new GPSComputer(filename);

        gpspoints = gpscomputer.getGPSPoints();

    }

    public static void main(String[] args) {
        launch(args);
    }

    public void run() {

        makeWindow("Route", MAPXSIZE + 2 * MARGIN, MAPYSIZE + 2 * MARGIN);

        minlon = GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints));
        minlat = GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints));

        maxlon = GPSUtils.findMax(GPSUtils.getLongitudes(gpspoints));
        maxlat = GPSUtils.findMax(GPSUtils.getLatitudes(gpspoints));

        xstep = scale(MAPXSIZE, minlon, maxlon);
        ystep = scale(MAPYSIZE, minlat, maxlat);

        showRouteMap(MARGIN + MAPYSIZE);

        replayRoute(MARGIN + MAPYSIZE);

        showStatistics();
    }
public double scale(int maxsize, double minval, double maxval) {

        double step = maxsize / (Math.abs(maxval - minval));

        return step;
    }

    public void showRouteMap(int ybase) {

        setColor(255, 0, 0);
        for(GPSPoint point : gpspoints) {

            int x = (int) ((point.getLongitude() - minlon) * ystep);
            int y = (int) (ybase - (point.getLatitude() - minlat) * ystep);

            drawCircle(x, y, 5);
        }
    }

    public void showStatistics() {

        int TEXTDISTANCE = 20;

        setColor(0,0,0);
        setFont("Courier",12);

        double totalDistance = gpscomputer.totalDistance() / 1000;
        double totalElevation = gpscomputer.totalElevation();
        double maxSpeed = gpscomputer.maxSpeed() * 3.6;
        double averageSpeed = gpscomputer.averageSpeed() * 3.6;
        double totalKcal = gpscomputer.totalKcal(80);

          drawString(String.format("Total Time     : %02d:%02d:%02d",
                    gpscomputer.totalTime() / 3600, 
                    (gpscomputer.totalTime() % 3600) / 60, 
                    gpscomputer.totalTime() % 60), MARGIN, MARGIN);
                drawString(String.format("Total distance : %.2f km", totalDistance), MARGIN, MARGIN + 20);
                drawString(String.format("Total elevation: %.2f m", totalElevation), MARGIN, MARGIN + 40);
                drawString(String.format("Max speed      : %.2f km/t", maxSpeed), MARGIN, MARGIN + 60);
                drawString(String.format("Average speed  : %.2f km/t", averageSpeed), MARGIN, MARGIN + 80);
                drawString(String.format("Energy         : %.2f kcal", totalKcal), MARGIN, MARGIN + 100);
            }
public void replayRoute(int ybase) {

        setColor(0, 0, 255);
        int circleSize = 10;
        int currentX = (int) ((gpspoints[0].getLongitude() - minlon) * xstep);
        int currentY = (int) (ybase - (gpspoints[0].getLatitude() - minlat) * ystep);

        drawCircle(currentX, currentY, circleSize);

        setSpeed(5);
        for(int i = 1; i < gpspoints.length; i++) {

            int nextX = (int) ((gpspoints[i].getLongitude() - minlon) * xstep);
            int nextY = (int) (ybase - (gpspoints[i].getLatitude() - minlat) * ystep);

            // Flytt sirkelen til neste punkt
            drawCircle(currentX, currentY, circleSize);


            pause(100);

            currentX = nextX;
            currentY = nextY;
        }
    }

}