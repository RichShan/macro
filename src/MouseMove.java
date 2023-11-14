import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.Toolkit;
import com.github.kwhat.jnativehook.keyboard.SwingKeyAdapter;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;

public class MouseMove implements Action{
//	MouseCorrectRobot mcr;
    final Dimension ScreenSize;
	static Robot robot;
	
	NativeMouseEvent e;
	long delay;
	
	int button, x, y;
	
	public MouseMove(NativeMouseEvent me, long delay) {
        ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.delay = delay;
		try {
//			mcr = new MouseCorrectRobot();
			robot = new Robot();
//			robot.setAutoDelay(0);
//			robot.setAutoDelay((int) delay);
			} catch (AWTException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		e = me;
	}
	@Override
	public void press() {
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
//		robot.setAutoDelay((int) delay);
//		robot.mouseMove(e.getX(), e.getY());
//		robot.delay((int) delay);
//		mcr.MoveMouseControlled(e.getX(), e.getY());	
		MouseCoordinatesConverter.move(e);

		
	}

}
