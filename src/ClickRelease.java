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
	
	public ClickRelease(NativeMouseEvent me, long delay) {
		delay = this.delay;
		try {
			robot = new Robot();
//			robot.setAutoDelay(0);
//			robot.setAutoDelay((int) delay);
			} catch (AWTException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		e = me;
		this.delay = delay;
	}
	@Override
	public void press() {
		robot.delay((int) delay);		
		if(e.getButton() == 1) {
			robot.mouseRelease(KeyEvent.BUTTON1_DOWN_MASK);
		} else if(e.getButton() == 2) {
			robot.mouseRelease(KeyEvent.BUTTON3_DOWN_MASK);
		}
		
	}

}
