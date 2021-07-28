package execute;
import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Robo {
	
	private Robot robby;
	static int SCREEN_WIDTH = 1366;
	static int SCREEN_HEIGHT = 768;
	int windowWidth = SCREEN_HEIGHT*4/3;
	int windowHeight = SCREEN_HEIGHT;
	int windowXOffset = (windowWidth-windowHeight)/2;
	int windowYOffset = 0;
	
	int gridX = 400;
	int gridY = 100;
	int gridRows = 3;
	int gridColumns = 3;
	int gridColumnWidth = 100;
	int gridRowHeight = gridColumnWidth;
	
	int[] commands;
	
	int resetDelay = 400;
	int moveDelay = 10;	// Delay between moves, in ms
	long START_TIME;
	long TIMEOUT_IN_MINUTES = 600;	// Edit this variable to change timeout
	long TIMEOUT_IN_MS = TIMEOUT_IN_MINUTES * 60 * 1000;
	
	public Robo() {
		START_TIME = System.currentTimeMillis();
		try {
			robby = new Robot();
		} catch(AWTException e) {
			e.printStackTrace();
		}
	}
	
	public Robo(int[] commands) {
		this();
		this.commands = commands;
	}
	
	public void initialWait() {
		robby.delay(2000);
	}
	
	public void screenShot(String filename) {
		try {
			capture(filename, windowXOffset, windowYOffset,
					windowWidth, windowHeight);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void capture(String filename, int x, int y, int width, int height) throws IOException {
		BufferedImage capture = robby.createScreenCapture(
				new Rectangle(x, y, width, height));
		
		File outputFile = new File(filename);
		robby.delay(1);
		ImageIO.write(capture, "jpg", outputFile);
		
	}
	
	public void test() {
		try {
			Runtime.getRuntime().exec("rundll32 user32.dll,LockWorkStation");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	public void switchWindow() {
		robby.keyPress(KeyEvent.VK_ALT);
		robby.delay(1000);
		robby.keyPress(KeyEvent.VK_TAB);
		//robby.keyRelease(KeyEvent.VK_TAB);
		robby.delay(1000);
		robby.keyPress(KeyEvent.VK_TAB);
		//robby.keyRelease(KeyEvent.VK_TAB);
		robby.delay(1000);
		robby.keyRelease(KeyEvent.VK_ALT);	
		
		robby.delay(1000);
		
	}
	*/
	
	// Prevents the program from running forever
	private void checkTimeout() {
		if (System.currentTimeMillis() > START_TIME + TIMEOUT_IN_MS)
			throw new RuntimeException();
	}
	
	private void pressKey(int event) {
		robby.keyPress(event);
		delay(moveDelay);
		robby.keyRelease(event);
	}
	
	public int executeRandomMove() {
		int rand = (int) (Math.random() * commands.length);
		pressKey(commands[rand]);
		return rand;
	}
	
	public void executeMove(int move) {
		pressKey(commands[move]);
	}
	
	public int getRandomCommand() {
		return commands[(int) (Math.random() * commands.length)];
	}
	
	public void executeEvent(Event event) {
		robby.delay(event.getMillisecondsBeforeEvent());
		
		if (event.isKeyPress()) {
			robby.keyPress(event.getKeyCode());
			robby.delay(event.getDurationOfEvent());
			robby.keyRelease(event.getKeyCode());
		} else if (event.isMouseClick()) {
			robby.mouseMove(event.getX(), event.getY());
			robby.mousePress(InputEvent.BUTTON1_MASK);
			robby.delay(event.getDurationOfEvent());
			robby.mouseRelease(InputEvent.BUTTON1_MASK);
		}
		
	}
	
	public void moveMouse(int x, int y, int delay) {
		robby.mouseMove(x, y);
		robby.delay(delay);
	}
	
	public void executeSolution(ArrayList<Integer> moves) {
		for (int move : moves)
			pressKey(commands[move]);
	}
	
	public void resetGame() {
		pressKey(KeyEvent.VK_ESCAPE);
		delay(resetDelay);
		pressKey(KeyEvent.VK_T);
		delay(resetDelay);
		pressKey(KeyEvent.VK_ENTER);
		delay(resetDelay);
		delay(resetDelay);
	}
	
	public void delay(int ms) {
		robby.delay(ms);
		checkTimeout();
	}

	public int getGridX() {
		return gridX;
	}

	public void setGridX(int gridX) {
		this.gridX = gridX;
	}

	public int getGridY() {
		return gridY;
	}

	public void setGridY(int gridY) {
		this.gridY = gridY;
	}

	public int getGridRows() {
		return gridRows;
	}

	public void setGridRows(int gridRows) {
		this.gridRows = gridRows;
	}

	public int getGridColumns() {
		return gridColumns;
	}

	public void setGridColumns(int gridColumns) {
		this.gridColumns = gridColumns;
	}

	public int getGridColumnWidth() {
		return gridColumnWidth;
	}

	public void setGridColumnWidth(int gridColumnWidth) {
		this.gridColumnWidth = gridColumnWidth;
	}

	public int getGridRowHeight() {
		return gridRowHeight;
	}

	public void setGridRowHeight(int gridRowHeight) {
		this.gridRowHeight = gridRowHeight;
	}

	public int getResetDelay() {
		return resetDelay;
	}

	public void setResetDelay(int resetDelay) {
		this.resetDelay = resetDelay;
	}

	public int getMoveDelay() {
		return moveDelay;
	}

	public void setMoveDelay(int moveDelay) {
		this.moveDelay = moveDelay;
	}

}
