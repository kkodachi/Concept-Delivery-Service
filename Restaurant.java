package kodachi_CSCI201_Assignment2;

import java.util.Vector;
import java.util.concurrent.Semaphore;



public class Restaurant{
	private String n;
	private String address;
	private double latitude;
	private double longitude;
	private int drivers;
	private Vector<String> menu = new Vector<String>();
	private static long startTime;
	
	
	private double dist;
	Semaphore sem;
	
	public Restaurant() {
		
	}
	
	public Restaurant(String n,String addy,double lat,double lon,int drivers,Vector<String> menu) {
		this.n = n;
		this.address = addy;
		this.latitude = lat;
		this.longitude = lon;
		this.drivers = drivers;
		this.menu = menu;
		sem = new Semaphore(drivers);
	}
	public static void setStart(long ms) {
		startTime = ms;
	}
	public static long getStart() {
		return startTime;
	}
	public void setN(String n) {
		this.n = n;
	}
	public String getN() {
		return this.n;
	}
	public void setAddress(String a) {
		this.address = a;
	}
	public void setLat(double lat) {
		this.latitude = lat;
	}
	public void setLon(double lon) {
		this.longitude = lon;
	}
	public void setDrivers(int d) {
		this.drivers = d;
		sem = new Semaphore(d);
	}
	public void addFood(String f) {
		this.menu.add(f);
	}
	
	public double getDist() {
		return this.dist;
	}
	
	public void setDist(double dist) {
		this.dist = dist;
	}
	
	public int getDrivers() {
		return this.drivers;
	}
	
	// need to check invalid drivers
	public boolean validcheck() {
		if (this.n == null) return false;
		if (this.address == null) return false;
		if (this.latitude == 0.0) return false;
		if (this.longitude == 0.0) return false;
		if (this.menu.isEmpty()) return false;
		if (drivers < 0) return false;
		return true;
	}
	
	public void print() {
		System.out.println("Name: " + this.n);
		System.out.println("Addy: " + this.address);
		System.out.println("Lat: " + Double.toString(this.latitude));
		System.out.println("Lon: " + Double.toString(this.longitude));
		System.out.println("Drivers: " + Integer.toString(this.drivers));
		System.out.println("Dist: " + Double.toString(this.dist));
		System.out.println("Menu: ");
		for (String i: menu) {
			System.out.println(i);
		}
		System.out.println("");
	}
	// copied from my Assignment 1
	public double calcDist(double lat, double lon) {
		double lat1 = Math.toRadians(lat);
		double lat2 = Math.toRadians(this.latitude);
		double lon1 = Math.toRadians(lon);
		double lon2 = Math.toRadians(this.longitude);
		double distance = 3963.0 * Math.acos((Math.sin(lat1) * Math.sin(lat2)) 
		                              + Math.cos(lat1) * Math.cos(lat2) 
		                              * Math.cos(lon2-lon1));
		return (Math.round(distance * 10.0) / 10.0);
	}
	
	
}