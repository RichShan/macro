import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.Toolkit;
import com.github.kwhat.jnativehook.keyboard.SwingKeyAdapter;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;

public class MouseMove implements Action{
    final Dimension ScreenSize;
	static Robot robot;
	
	NativeMouseEvent e;
	long delay;
	
	int button, x, y;
	
	public MouseMove(NativeMouseEvent me, long delay) { //Constructor
        ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.delay = delay;
		try {
			robot = new Robot();
			} catch (AWTException e1) {
			e1.printStackTrace();
		}
		e = me;
	}
	@Override
	// implemented from Action interface
	public void press() {
		//moves to scaled coordinates via use of MouseCoordinatesConverter
		MouseCoordinatesConverter.move(e, (int) delay);

		
	}

}
