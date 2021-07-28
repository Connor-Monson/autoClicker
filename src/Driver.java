import java.awt.AWTException;
import java.awt.Frame;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
//import org.jnativehook.GlobalScreen;

import execute.Event;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

import javax.swing.JOptionPane;

/*
 * Author: Connor Monson
 * Version: 1.0.0
 */
public class Driver {
	
	private static Robot robby;
	static MouseListener mListener;
	static KeyListener kListener;
	static long startTime;
	static long INITIAL_DELAY = 3000;
	static long TIMEOUT_IN_SECONDS = 60;
	static long TIMEOUT_IN_MILLISECONDS = INITIAL_DELAY + (TIMEOUT_IN_SECONDS * 1000);
	static long START_TIME;
	
	public static void main(String[] args) throws InterruptedException {
		
		init();

		String macroName = "macro";
        String fileName = macroName + ".txt";
        		
		loadDialogBox(fileName);
				
		System.out.println("Macro ended");
		
		terminate();
	}
	
	static void loadDialogBox(String fileName) {

		Object[] options = {
				"Get x/y coordinates",
				"Run Macro",
        		"Exit program"
		};
		final JOptionPane optionPane = new JOptionPane(
				"Filler Message",
        		JOptionPane.QUESTION_MESSAGE,
                JOptionPane.YES_NO_CANCEL_OPTION
        );

		Frame frame = new Frame();
		Boolean finished = false;
		
		while(!finished) {
						
			int selection = JOptionPane.showOptionDialog(frame,
				    "What would you like to do?",
		    	    "autoClicker",
		    	    JOptionPane.YES_NO_CANCEL_OPTION,
		    	    JOptionPane.QUESTION_MESSAGE,
		    	    null,
		    	    options,
		    	    options[2]
		    );

			switch(selection) {
			case -1:
				// User clicked "X" in corner of dialog
				finished = true;
				break;
			case JOptionPane.YES_OPTION:
				// Get x/y coordinates
				getXYCoordinates();
				break;
			case JOptionPane.NO_OPTION:
				// Run macro
				Macro macro = loadMacro(fileName);
				playMacro(macro);
				break;
			default:
				// "Exit Program"
				finished = true;
			}
			
		}
		
	}
	
	public static void getXYCoordinates() {

		try {
			JOptionPane.showMessageDialog(null, "After clicking \"OK\", You will have three seconds to position the mouse. Then the mouse's x/y coordinates will be displayed");
			Thread.sleep(3000);
			
	        int mouseX = MouseInfo.getPointerInfo().getLocation().x;
			int mouseY = MouseInfo.getPointerInfo().getLocation().y;
			
			JOptionPane.showMessageDialog(null, "X: " + mouseX + ", y: " + mouseY + "\nYou can open macro.txt in a text editor and replace the x and y values of an event with these x and y values.");
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, "An error has occured");
		}
		
	}
	
	public static void executeMacro(Macro macro) {
		for(int numRepetitions = macro.getNumberOfRepetitions(); numRepetitions > 0; numRepetitions--) {
			for(int i = 0; i < macro.getEvents().size(); i++) {
				executeEvent(macro.getEvents().get(i), macro.getIntroduceVariability());
			}
		}
	}
	
	public static void executeEvent(Event event, Boolean introduceVariability) {
		int xVar = 0, yVar = 0, beforeVar = 0, durationVar = 0;
		
		if(introduceVariability) {
			xVar = (int) (Math.random() * 7 - 3) + (int) (Math.random() * 7 - 3);
			yVar = (int) (Math.random() * 7 - 3) + (int) (Math.random() * 7 - 3);
			
			int swing1 = (int) (event.getMillisecondsBeforeEvent() * 0.05);
			int swing2 = (int) (event.getDurationOfEvent() * 0.05);
			int randomDelay1 = (int) (Math.random() * swing1 - (swing1/2)) + (int) (Math.random() * swing1 - (swing1/2));
			int randomDelay2 = (int) (Math.random() * swing2 - (swing2/2)) + (int) (Math.random() * swing2 - (swing2/2));
			beforeVar = event.getMillisecondsBeforeEvent() + randomDelay1;
			durationVar = event.getMillisecondsBeforeEvent() + randomDelay2;
		}
		
		robby.delay(event.getMillisecondsBeforeEvent());
		
		if (event.isKeyPress()) {
			robby.keyPress(event.getKeyCode());
			robby.delay(event.getDurationOfEvent()+beforeVar);
			robby.keyRelease(event.getKeyCode());
		} else if (event.isMouseClick()) {
			robby.mouseMove(event.getX()+xVar, event.getY()+yVar);
			robby.mousePress(InputEvent.BUTTON1_MASK);
			robby.delay(event.getDurationOfEvent()+durationVar);
			robby.mouseRelease(InputEvent.BUTTON1_MASK);
		}
		
	}
	
	public static boolean saveMacro(String filename, Macro macro) {
		System.out.println("Saving file");
		
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(new File(filename)))) {
			bw.write(macro.toString());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public static Macro loadMacro(String filename) {
		System.out.println("Loading file");
		
		File file = new File(filename);
		Macro macro = new Macro();
		
		try(Scanner scan = new Scanner(file)) {
			String fileContents = "";
			
			while(scan.hasNext()) {
				fileContents += scan.nextLine();
			}
			
			macro = Macro.fromString(fileContents);
			
		} catch (FileNotFoundException e) {

			
			try {
				file.createNewFile();
				saveMacro(filename, new Macro());
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, "Error occured. This is likely because file macro.txt is missing or is not in this directory");
				return null;
			}
			return null;
		}
		
		return macro;
	}
	
    public static void save(String filepath, Macro macro) {
   	 
        try {
 
            FileOutputStream fileOut = new FileOutputStream(filepath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(macro.toString());
            objectOut.close();
            System.out.println("The Object  was succesfully written to a file");
 
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
 
    public static Macro load(String filepath) {
 
        try {
 
            FileInputStream fileIn = new FileInputStream(filepath);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
 
            Macro macro = Macro.fromString(objectIn.readObject().toString());
 
            System.out.println("The Object has been read from the file");
            objectIn.close();
            return macro;
 
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
		
	static void init() {
//		createListeners();
		
		START_TIME = System.currentTimeMillis();
		try {
			robby = new Robot();
		} catch(AWTException e) {
			e.printStackTrace();
		}
		
		startTime = System.currentTimeMillis();
	}
	
	static void terminate() {
		// Empty
	}
	
	static void recordMacro() throws InterruptedException {
		System.out.println("Macro will start recording in five seconds");
		Thread.sleep(INITIAL_DELAY);
		
		System.out.println("START!");
		
		while(System.currentTimeMillis() < startTime + TIMEOUT_IN_MILLISECONDS) {
			
		}
		
		// TODO save macro
	}
	
	static void playMacro(Macro macro) {
		try {
			JOptionPane.showMessageDialog(null, "After clicking \"OK\", Macro will begin in three seconds");
			System.out.println("Macro will start playing in three seconds");
			Thread.sleep(INITIAL_DELAY);
		
			executeMacro(macro);
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Error occured");
		}
	}
	
	static void createListeners() {
		mListener = new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				System.out.println("Time since started: " + (System.currentTimeMillis() - startTime));
				System.out.println("Mouse clicked " + arg0.getClickCount() + " at: " + arg0.getX() + ", " + arg0.getY());
				System.out.println();
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		};
		
		kListener = new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				System.out.println("Time since started: " + (System.currentTimeMillis() - startTime));
				System.out.println("Key typed: " + arg0.getKeyChar());
				System.out.println();
			}
			
		};
	}
}
