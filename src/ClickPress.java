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
	
	// Constructor
	public ClickPress(NativeMouseEvent me, long delay) {
		e = me;
		this.delay = delay;
		try {
			robot = new Robot();
		} catch (AWTException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	// implemented from Action interface
	public void press() {
		robot.setAutoDelay((int) delay);
		if(e.getButton() == 1) { // left click
			robot.mousePress(KeyEvent.BUTTON1_DOWN_MASK);
		} else if(e.getButton() == 2) { // right click
			robot.mousePress(KeyEvent.BUTTON3_DOWN_MASK);
		} else if(e.getButton() == 3) { // middle click
			robot.mousePress(KeyEvent.BUTTON2_DOWN_MASK);
		}
				
	}


	
}
