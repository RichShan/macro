import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;

public class KeyPress implements Action{
	static Robot robot;
	NativeKeyEvent e;
	long delay;
	
	
	public static void main(String args[]) {
		try {
			robot = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		robot.keyPress(KeyEvent.VK_Q);
		
		
	}
	public KeyPress(NativeKeyEvent ke, long delay) {
		ke = e;
		delay = this.delay;
	}

	@Override
	public void press() {
		robot.delay((int) delay);
		robot.keyPress(e.getKeyCode());
	}
	
}
