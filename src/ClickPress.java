import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;

public class ClickPress implements Action{
	static Robot robot;
	
	NativeMouseEvent e;
	long delay;
	
	int button, x, y;
	
	public ClickPress(NativeMouseEvent me, long delay) {
		e = me;
		delay = this.delay;
	}
	
//	public Click(int buttonNum, int scaledXCoord, int scaledYCoord) {
//		
//		try {
//			robot = new Robot();
//		} catch (AWTException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		button = buttonNum;
//		x = scaledXCoord;
//		y = scaledYCoord;
//	}
	
	@Override
	public void press() {
		robot.delay((int) delay);
		robot.mousePress(e.getButton());
		
//		if(e.getButton() == 1) {
//			robot.mousePress(InputEvent.BUTTON1_MASK);
//			robot.mouseRelease(InputEvent.BUTTON1_MASK);
//		}
//		
//		if(button == 2) {
//			robot.mousePress(InputEvent.BUTTON3_MASK);
//			robot.mouseRelease(InputEvent.BUTTON3_MASK);
//		}
		
	}


	
}
