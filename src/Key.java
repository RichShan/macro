import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;

public class Key {
	static Robot robot;
	NativeKeyEvent e;
	
	
	public static void main(String args[]) {
		try {
			robot = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		robot.keyPress(KeyEvent.VK_Q);
		
		
	}
	public Key(NativeKeyEvent e) {
		this.e = e;
//		k = keyPress;
	}
	
	public void doPress() {
		robot.keyPress(e.getKeyCode());
		robot.delay(3);
		robot.keyRelease(e.getKeyCode());
	}
	
}
