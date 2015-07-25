//import java.util.Timer;
import java.io.IOException;

import javax.swing.*; 


public class WorkDistractEntry {
	private String wOrD;
	private String name; 
	private double time; 
	
	public WorkDistractEntry(String wOrD, String name, double time){
		this.wOrD = wOrD; 
		this.name = name; 
		this.time = time; 
	}
	
	public String toPrint(){
		return wOrD + ": " + name + " " + minutes() + "\n"; 
	}
	
	public String minutes(){
		String minutes = String.format("%.4g%n", time);
		return minutes; 
	}
	
	
}
