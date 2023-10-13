package kodachi_CSCI201_Assignment2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;




public class Main {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		boolean valid = false;
		JoesTable jt = new JoesTable();
		String fn = "";
		while (!valid) {
			System.out.println("What is the name of the file containing the restaurant information?");
			fn = in.nextLine();
			try {
				// "json-simple documentation (used as reference, nothing directly copied) 25 Sep. 2023
				// http://alex-public-doc.s3.amazonaws.com/json_simple-1.1/index.html
				// "How to create correct JSONArray in Java using JSONObject" prompt (3 lines), StackOverflow 25 Sep. 2023
				// https://stackoverflow.com/questions/18983185/how-to-create-correct-jsonarray-in-java-using-jsonobject
				JSONParser parser = new JSONParser();
		        JSONObject obj = ((JSONObject)parser.parse(new FileReader(fn)));
		        JSONArray data = (JSONArray)obj.get("data");
		        for (Object o: data) {
		        	JSONObject temp = (JSONObject)o;
		        	Restaurant rest = new Restaurant();
		        	rest.setN((String)(temp).get("name"));
		        	rest.setAddress((String)(temp).get("address"));
	                rest.setLat((double)temp.get("latitude"));
	                rest.setLon((double)temp.get("longitude"));
	                // "JSON - simple get an Integer instead of Long" prompt (1 line), StackOverflow 25 Sep. 2023
	                // https://stackoverflow.com/questions/20869138/json-simple-get-an-integer-instead-of-long
	                rest.setDrivers((int)(long)temp.get("drivers"));
	                JSONArray menu = (JSONArray)temp.get("menu");
	                for(Object o2: menu) {
	                    rest.addFood((String)o2);
	                }
	                jt.search.put(rest.getN().trim().toLowerCase(),rest);
	                jt.restaurants.add(rest);
		        }
		        if (!jt.valid()) {
					System.out.println("The file is missing one or more parameters. ");
					valid = false;
				}
				else valid = true;
		        
			} catch (FileNotFoundException fnfe) {
				System.out.println("The file " + fn + " could not be found.");
			} catch (org.json.simple.parser.ParseException pe) {
				System.out.println("The file " + fn + " is not formatted properly.");
			} catch (IOException ioe) {
				System.out.println("The file " + fn + " could not be found.");
			}
			finally {
				System.out.println("");
			}
		}
		valid = false;
		fn = "";
		while(!valid) {
			System.out.println("What is the name of the file containing the schedule information?");
			fn = in.nextLine();
			try {
				FileReader fr = new FileReader(fn);
				BufferedReader br = new BufferedReader(fr);
				String ln = "";
				// "How can I read a large text file line by line using Java?" prompt (2 lines), StackOverflow 25 Sep. 2023
				// https://stackoverflow.com/questions/5868369/how-can-i-read-a-large-text-file-line-by-line-using-java
				while ((ln = br.readLine()) != null) {
					String[] line = ln.split(",");
					Restaurant r = jt.getRestaurant(line[1].trim().toLowerCase());
					Order temp = new Order(Integer.parseInt(line[0]),line[1].trim(),line[2].trim(),r);
					jt.addOrder(temp);
				}
				fr.close();
				br.close();
				valid = true;
			} catch (FileNotFoundException fnfe) {
				System.out.println("The file " + fn + " could not be found.");
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		while (true) {
			try {
				System.out.print("What is the latitude? ");
				jt.setLat(Double.parseDouble(in.nextLine()));
				break;
			} catch (InputMismatchException ime) {
				System.out.print("Latitude needs to be of type double. \n");
			} catch (NullPointerException npe) {
				System.out.print("Latitude needs to be of type double. \n");
			} catch (NumberFormatException nfe) {
				System.out.print("Latitude needs to be of type double. \n");
			}
		}
		while (true) {
			try {
				System.out.println("");
				System.out.print("What is the longitude? ");
				jt.setLon(Double.parseDouble(in.nextLine()));
				break;
			} catch (InputMismatchException ime) {
				System.out.print("Longitude needs to be of type double. \n");
			}catch (NullPointerException npe) {
				System.out.print("Longitude needs to be of type double. \n");
			} catch (NumberFormatException nfe) {
				System.out.print("Longitude needs to be of type double. \n");
			}
		}
//		jt.setLat(34.021160);
//		jt.setLon(-118.287132);
		jt.init();
		System.out.println("");
		System.out.println("Starting execution of program...");
		// SleepingBarber.java from Lab4 (9 lines) 25 Sep. 2023
		ExecutorService executors = Executors.newCachedThreadPool();
		Restaurant.setStart(System.currentTimeMillis());
		for (int i=0;i<jt.schedule.size();i++) {
			executors.execute(jt.schedule.get(i));
		}
		executors.shutdown();
		while(!executors.isTerminated()) {
			Thread.yield();
		}
		System.out.println("All orders complete!");
		in.close();
//		jt.printR();
//		jt.printS();
	}
}
