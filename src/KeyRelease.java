import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;

public class KeyRelease implements Action{
	static Robot robot;
	NativeKeyEvent e;
	long delay;
	
	SwingKeyAdapter ska = new SwingKeyAdapter();

	
	public static void main(String args[]) {
		try {
			robot = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		robot.keyPress(KeyEvent.VK_Q);
		
		
	}
	public KeyRelease(NativeKeyEvent ke, long delay) {
		try {
			robot = new Robot();
//			robot.setAutoDelay(0);
//			robot.setAutoDelay((int) delay);
			} catch (AWTException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		e = ke;
		this.delay = delay;
	}

	@Override
	public void press() {
//		try {
//			Thread.sleep(delay);
		robot.delay((int) delay);
		robot.keyRelease(ska.getJavaKeyEvent(e).getKeyCode());
//
//		} catch (InterruptedException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
////		robot.delay((int) delay);

	}
	
}
