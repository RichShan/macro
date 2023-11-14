import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import com.github.kwhat.jnativehook.keyboard.SwingKeyAdapter;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;

public class ClickPress implements Action{
	static Robot robot;
	
	NativeMouseEvent e;
	long delay;
	
	int button, x, y;
	
	public ClickPress(NativeMouseEvent me, long delay) {
		e = me;
		this.delay = delay;
		try {
			robot = new Robot();
//			robot.setAutoDelay(0);
//			robot.setAutoDelay((int) delay);
		} catch (AWTException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
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
		System.out.println("press");
//		robot.delay((int) delay);
		robot.setAutoDelay((int) delay);
		if(e.getButton() == 1) {
			robot.mousePress(KeyEvent.BUTTON1_DOWN_MASK);
		} else if(e.getButton() == 2) {
//			System.out.println("RIGHT CLICK");
			robot.mousePress(KeyEvent.BUTTON3_DOWN_MASK);
		}
		
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
