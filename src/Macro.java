import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

import execute.Event;
import execute.Event.Type;

public class Macro {
	
	int fileVersion;
	int numberOfRepetitions;
	boolean introduceVariability = false;
	ArrayList<Event> events;

	Macro() {
		events = new ArrayList<Event>();
		fileVersion = 1;
		numberOfRepetitions = 1;
		introduceVariability = false;
	}
	
	Macro(ArrayList<Event> events) {
		this.events = events;
		fileVersion = 1;
		numberOfRepetitions = 1;
		introduceVariability = false;
	}
	
	public void addEvent(Event event) {
		events.add(event);
	}
	
	public void setEvents(ArrayList<Event> events) {
		this.events = events;
	}

	public ArrayList<Event> getEvents() {
		return events;
	}
	
	public void setFileVersion(int fileVersion) {
		this.fileVersion = fileVersion;
	}
	
	public void setNumberOfRepetitions(int numberOfRepetitions) {
		this.numberOfRepetitions = numberOfRepetitions;
	}
	
	public int getNumberOfRepetitions() {
		return this.numberOfRepetitions;
	}
	
	public void setIntroduceVariability(Boolean introduceVariability) {
		this.introduceVariability = introduceVariability;
	}
	
	public boolean getIntroduceVariability() {
		return this.introduceVariability;
	}
	
	public String toString() {
		String result = "\n\"Macro\": {\n";
		result += "\t\"fileVersion\": " + fileVersion + " ,\n";
		result += "\t\"numberOfRepetitions\": " + numberOfRepetitions + " ,\n";
		result += "\t" + events.toString();
		result += "\n}\n";
		
		return result;
	}
	
	public static Macro fromString(String str) { 
		if (str == null || str.isEmpty())
			return null;
		
		ArrayList<Event> events = new ArrayList<Event>();
		Scanner scan = new Scanner(str);
		Macro macro = new Macro();
		
//		System.out.println(str);
		while(scan.hasNext()) {
			String next = scan.next();
//			System.out.println(next);
			
			switch(next) {
				case "\"Event\":":
					scan.next();	// Consume the "{"
					String temp = "";
					String result = "";
					while (!temp.startsWith("}")) {
						result += " " + temp + " ";
						temp = scan.next();
					}
//					System.out.println("$$$$Get Event from: " + result);
					events.add(Event.fromString(result));
					
					break;
				case "\"fileVersion\":":
					macro.setFileVersion(scan.nextInt());
					break;
				case "\"numberOfRepetitions\":":
					macro.setNumberOfRepetitions(scan.nextInt());
					break;
				case "\"introduceVariability\":":
					macro.setIntroduceVariability(scan.nextBoolean());
					break;
			}
		}
		
		scan.close();
		macro.setEvents(events);
		
		return macro;
	}
	
//	public static Macro fromString(String macroText) {
//		Macro macro = new Macro();
//		
//		macroText = macroText.replace("[", " ");
//		macroText = macroText.replace("]", " ");
//		macroText = macroText.replace(",", " ");
//		
//		Scanner scan = new Scanner(macroText);
//		ArrayList<Event> events = new ArrayList<Event>();
//		
//		while (scan.hasNext()) {
//			try {
//			String temp = scan.next();
//			if (temp.equals(";")) {
//				score = scan.nextInt();
//				break;	// Finished
//			}
//			else moves.add(Integer.parseInt(temp));
//			} catch (NumberFormatException e) {
//				System.out.println("Bad format for: " + macroText);
//				scan.close();
//				return null;
//			}
//		}
//		
//		scan.close();
//		
//		macro.setEvents(events);
//		
//		return macro;
//	}
}
