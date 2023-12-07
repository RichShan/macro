import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;

public class KeyRelease implements Action{
	static Robot robot;
	NativeKeyEvent e;
	long delay;
	
	//Used for converting between VC and VK keycodes for use by different libs
	SwingKeyAdapter ska = new SwingKeyAdapter();

	
	public static void main(String args[]) {
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}		
		
	}
	public KeyRelease(NativeKeyEvent ke, long delay) {
		try {
			robot = new Robot();
			robot.setAutoDelay((int) delay);
			} catch (AWTException e1) {
			e1.printStackTrace();
		}
		e = ke;
		this.delay = delay;
	}

	@Override
	// implemented from Action interface
	public void press() {
		robot.delay((int) delay);
		robot.keyRelease(ska.getJavaKeyEvent(e).getKeyCode());

	}
	
}
