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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		robot.keyPress(KeyEvent.VK_Q);
		
		
	}
	public KeyPress(NativeKeyEvent ke, long delay) {
		try {
			robot = new Robot();
			robot.setAutoDelay((int) delay);
		} catch (AWTException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		e = ke;
		delay = this.delay;
	}

	@Override
	public void press() {
//		try {
//			Thread.sleep(delay);
			robot.keyPress(ska.getJavaKeyEvent(e).getKeyCode());
//		} catch (InterruptedException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		robot.setAutoDelay(5);
//		robot.delay((int) delay);
//		System.out.println(delay);
	}
	
}
