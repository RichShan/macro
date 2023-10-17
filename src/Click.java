import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

public class Click {
	static Robot robot;
	
	int button, x, y;
	public Click(int buttonNum, int scaledXCoord, int scaledYCoord) {
		
		try {
			robot = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		button = buttonNum;
		x = scaledXCoord;
		y = scaledYCoord;
	}
	
	public void doClick() {
		robot.mouseMove(x, y);
		robot.delay(5);
		
		if(button == 1) {
			robot.mousePress(InputEvent.BUTTON1_MASK);
			robot.mouseRelease(InputEvent.BUTTON1_MASK);
		}
		
		if(button == 2) {
			robot.mousePress(InputEvent.BUTTON3_MASK);
			robot.mouseRelease(InputEvent.BUTTON3_MASK);
		}
		
	}
	
}
