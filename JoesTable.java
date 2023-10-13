package kodachi_CSCI201_Assignment2;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class JoesTable {
	Vector<Restaurant> restaurants = new Vector<Restaurant>();
    Vector<Order> schedule = new Vector<Order>();
    Map<String,Restaurant> search = new HashMap<>();
    private double lat;
    private double lon;
    
    public void setLat(double lat) {
        this.lat = lat;
    }
    public void setLon(double lon) {
        this.lon = lon;
    }
    public double getLat() {
        return this.lat;
    }
    public double getLon() {
        return this.lon;
    }
    
    public boolean valid() {
        if (restaurants.isEmpty()) return false;
        for (Restaurant temp: restaurants) {
            if (!temp.validcheck()) return false;
        }
        return true;
    }	
	
    public void init() {
        for (Restaurant temp: this.restaurants) {
            temp.setDist(temp.calcDist(this.lat, this.lon));
        }
    }
    
    public Restaurant getRestaurant(String n) {
        return search.get(n);
    }
    
    public void addOrder(Order o) {
        this.schedule.add(o);
    }
    
    public void printR() {
        for (Restaurant r: restaurants) {
            r.print();
        }
    }

    public void printS() {
        for (Order o: schedule) {
            o.print();
        }
    }
}
