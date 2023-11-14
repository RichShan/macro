import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.MouseEvent;

import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;

import java.awt.Robot;

public class MouseCoordinatesConverter {
	
	static NativeMouseEvent e;
	
	static int delay;
	
    public MouseCoordinatesConverter(NativeMouseEvent nme, int del) {
		// TODO Auto-generated constructor stub
    	delay = del;
    	e = nme;
	}

	public static void move(NativeMouseEvent me, int delayay) {
    	e = me;
        // Get the physical screen resolution
        Dimension physicalScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int physicalScreenWidth = physicalScreenSize.width;
        int physicalScreenHeight = physicalScreenSize.height;

        // Get the default graphics environment
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

        // Get the default screen device
        GraphicsDevice gd = ge.getDefaultScreenDevice();

        // Get the current display mode, which includes screen dimensions
        int screenWidth = gd.getDisplayMode().getWidth();
        int screenHeight = gd.getDisplayMode().getHeight();

        // Get the scaling factor applied to the screen width and height
        double scaleFactorX = (double) screenWidth / physicalScreenWidth;
        double scaleFactorY = (double) screenHeight / physicalScreenHeight;

        // Calculate the offsets
        int offsetX = 0; // Adjust as needed
        int offsetY = 0; // Adjust as needed

        // Get the mouse event or physical coordinates

        // Calculate the scaled coordinates
        int physicalX = e.getX();
        int physicalY = e.getY();

        int scaledX = (int) (physicalX / scaleFactorX + offsetX);
        int scaledY = (int) (physicalY / scaleFactorY + offsetY);

        // Use the scaled coordinates with Robot.mouseMove
        try {
            Robot robot = new Robot();
            robot.setAutoDelay(delayay);
            robot.mouseMove(scaledX, scaledY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
