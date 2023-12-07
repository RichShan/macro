import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import com.github.kwhat.jnativehook.keyboard.SwingKeyAdapter;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;

public class ClickRelease implements Action{
	
	static Robot robot;
	
	NativeMouseEvent e;
	long delay;
	
	int button, x, y;
	
	// Constructor
	public ClickRelease(NativeMouseEvent me, long delay) {
		delay = this.delay;
		try {
			robot = new Robot();
			robot.setAutoDelay(0);
			} catch (AWTException e1) {
			e1.printStackTrace();
		}
		e = me;
		this.delay = delay;
	}
	@Override
	// implemented from Action interface
	public void press() {
		robot.delay((int) delay);		
		if(e.getButton() == 1) { // left click
			robot.mouseRelease(KeyEvent.BUTTON1_DOWN_MASK);
		} else if(e.getButton() == 2) { // right click
			robot.mouseRelease(KeyEvent.BUTTON3_DOWN_MASK);
		} else if(e.getButton() == 3) { // middle click
			robot.mousePress(KeyEvent.BUTTON2_DOWN_MASK);
		}
		
	}

}
