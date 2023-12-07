import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyEvent.*;
import java.awt.event.InputEvent;


import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;

public class KeyPress implements Action{
	static Robot robot;
	NativeKeyEvent e;
	long delay;
	SwingKeyAdapter ska = new SwingKeyAdapter();
	
	
	public static void main(String args[]) {
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		
		
	}
	public KeyPress(NativeKeyEvent ke, long delay) {
		try {
			robot = new Robot();
		} catch (AWTException e1) {
			e1.printStackTrace();
		}
		e = ke;
		this.delay = delay;
	}

	@Override
	// implemented from Action interface
	public void press() {
		robot.setAutoDelay((int) delay);
		robot.keyPress(ska.getJavaKeyEvent(e).getKeyCode());
	}
	
}
