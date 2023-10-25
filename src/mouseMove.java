import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.Toolkit;
import com.github.kwhat.jnativehook.keyboard.SwingKeyAdapter;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;

public class mouseMove implements Action{
//	MouseCorrectRobot mcr;
    final Dimension ScreenSize;
	static Robot robot;
	
	NativeMouseEvent e;
	long delay;
	
	int button, x, y;
	
	public mouseMove(NativeMouseEvent me, long delay) {
        ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
		delay = this.delay;
		try {
//			mcr = new MouseCorrectRobot();
			robot = new Robot();
			robot.setAutoDelay((int) delay/2);
		} catch (AWTException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		e = me;
	}
	@Override
	public void press() {
//		robot.mouseMove(e.getX(), e.getY());
//		robot.delay((int) delay);
//		mcr.MoveMouseControlled(e.getX(), e.getY());	
		MouseCoordinatesConverter.move(e);

		
	}

}
