import java.awt.AWTException;
import java.awt.Robot;

import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;

public class mouseMove implements Action{
	
	static Robot robot;
	
	NativeMouseEvent e;
	long delay;
	
	int button, x, y;
	
	public mouseMove(NativeMouseEvent me, long delay) {
		try {
			robot = new Robot();
		} catch (AWTException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		e = me;
		delay = this.delay;
	}
	@Override
	public void press() {
		robot.delay((int) delay);
		robot.mouseMove(e.getX(), e.getY());
		
	}

}
