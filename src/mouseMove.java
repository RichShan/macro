import java.awt.Robot;

import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;

public class mouseMove implements Action{
	
	static Robot robot;
	
	NativeMouseEvent e;
	long delay;
	
	int button, x, y;
	
	public mouseMove(NativeMouseEvent me, long delay) {
		e = me;
		delay = this.delay;
	}
	@Override
	public void press() {
		robot.delay((int) delay);
		robot.mouseMove(e.getX(), e.getY());
		
	}

}
