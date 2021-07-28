package execute;
import java.io.Serializable;
import java.util.Scanner;

public class Event {
	
	private int millisecondsBeforeEvent;
	private Type type;
	private int x, y;
	private int keyCode;
	private int durationOfEvent;

	public enum Type {MOUSE_CLICK, KEYBOARD}
	
	private Event() {
		super();
		type = null;
	}
	
	public Event(int millis, int keyCode, int duration) {
		this();
		this.millisecondsBeforeEvent = millis;
		type = Type.KEYBOARD;
		this.keyCode = keyCode;
		this.durationOfEvent = duration;
	}
	
	public Event(int millis, int x, int y, int duration) {
		this();
		this.millisecondsBeforeEvent = millis;
		type = Type.MOUSE_CLICK;
		this.x = x;
		this.y = y;
		this.durationOfEvent = duration;
	}
	
	public boolean isKeyPress() {
		if (type == Type.KEYBOARD)
			return true;
		else return false;
	}
	
	public boolean isMouseClick() {
		if (type == Type.MOUSE_CLICK)
			return true;
		else return false;
	}
	
	public String toString() {
		String result = "\n\t\"Event\": {\n";
		result += "\t\t\"millisecondsBeforeEvent\": " + millisecondsBeforeEvent + " ,\n";
		result += "\t\t\"type\": " + type + " ,\n";
		if (type == Type.MOUSE_CLICK)
			result += "\t\t\"x\": " + x + " ,\n\t\t\"y\": " + y + " ,\n";	// Mouse event
		else result += "\t\t\"keyCode\": " + keyCode + " ,\n";	// Keyboard event
		result += "\t\t\"durationOfEvent\": " + durationOfEvent + " ,\n";
		result += "\t}";
		
		return result;
	}
	
	public static Event fromString(String str) { 
		if (str == null || str.isEmpty())
			return null;
		
		Event event = new Event();
		Scanner scan = new Scanner(str);
		
//		System.out.println("%%%%" + str);
		while(scan.hasNext()) {
			String next = scan.next();
//			System.out.println("\t****" + next);
			
			switch(next) {
				case "\"millisecondsBeforeEvent\":":
					event.setmillisecondsBeforeEvent(scan.nextInt());
					break;
				case "\"type\":":
					event.setType(Type.valueOf(scan.next()));
					break;
				case "\"x\":":
					event.setX(scan.nextInt());
					break;
				case "\"y\":":
					event.setY(scan.nextInt());
					break;
				case "\"keyCode\":":
					event.setKeyCode(scan.nextInt());
					break;
				case "\"durationOfEvent\":":
					event.setDurationOfEvent(scan.nextInt());
					break;
			}
		}
		
		scan.close();
		
		return event;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (!(obj instanceof Event))
			return false;
		
		Event event = (Event)obj;
		
		if (this.millisecondsBeforeEvent == event.getMillisecondsBeforeEvent()
				&& this.type == event.getType()
				&& this.x == event.getX()
				&& this.y == event.getY()
				&& this.keyCode == event.getKeyCode()
				&& this.durationOfEvent == event.getDurationOfEvent())
			return true;
		else return false;
	}
	
	public int getMillisecondsBeforeEvent() {
		return millisecondsBeforeEvent;
	}

	public void setmillisecondsBeforeEvent(int millisecondsBeforeEvent) {
		this.millisecondsBeforeEvent = millisecondsBeforeEvent;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getKeyCode() {
		return keyCode;
	}

	public void setKeyCode(int keyCode) {
		this.keyCode = keyCode;
	}

	public int getDurationOfEvent() {
		return durationOfEvent;
	}

	public void setDurationOfEvent(int durationOfEvent) {
		this.durationOfEvent = durationOfEvent;
	}

	/*
	public String toString() {
		String result = "{" + " ";
		result += millisecondsBeforeEvent + " ";
		result += type + " ";
		if (type == Type.MOUSE_CLICK)
			result += x + " " + y + " ";	// Mouse event
		else result += keyCode + " ";	// Keyboard event
		result += durationOfEvent + " ";
		result += "}";
		
		return result;
	}
	
	public static Event fromString(String str) {
		Event event = new Event();
		Scanner scan = new Scanner(str);
		scan.next();	// Consume the opening bracket
		
		event.millisecondsBeforeEvent = scan.nextInt();
		event.type = Type.valueOf(scan.next());
		
		if (event.type == Type.MOUSE_CLICK) {	// Mouse event
			event.x = scan.nextInt();
			event.y = scan.nextInt();
		} else {						// Keyboard event
			event.keyCode = scan.nextInt();
		}
		event.durationOfEvent = scan.nextInt();
		
		scan.close();
		
		return event;
	}
	*/
}
