import java.awt.Desktop.Action;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Robot;
import java.awt.Toolkit;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.*;
import com.github.kwhat.jnativehook.mouse.*;


public class macro extends JPanel implements NativeKeyListener,NativeMouseInputListener {
	
	static ArrayList<Object> recordedActions = new ArrayList<>();

    private String path;

	
  public macro() {
	  
	  path = System.getProperty("user.dir");
	    
	  
	  
	  
	Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
	 
    JFrame frame = new JFrame();
    frame.getContentPane().add(new macro());

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.dispose();
//
    frame.setSize(size);
    frame.setVisible(true);
    
  }
  
  public static void main(String args[]) {
	  
	  
      Logger logger = Logger.getLogger(GlobalScreen.class.getPackage()
              .getName());
      logger.setLevel(Level.OFF);
      try {
          GlobalScreen.registerNativeHook();
      } catch (NativeHookException ex) {
          System.err
                  .println("There was a problem registering the native hook.");
          System.err.println(ex.getMessage());

          System.exit(1);
      }
      MyHook hook = new MyHook();
      GlobalScreen.addNativeKeyListener(hook);
      GlobalScreen.addNativeMouseListener(hook);
      GlobalScreen.addNativeMouseMotionListener(hook);
      
      
  }
      
      
//      try {
//		Thread.sleep(500);
//	} catch (InterruptedException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//      
//      System.out.println(recordedActions.get(0));
//  }

  @Override
  public void nativeKeyPressed(NativeKeyEvent e) {
      System.out.println("Key Pressed: "
              + NativeKeyEvent.getKeyText(e.getKeyCode()));
      write("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
      
//      recordedActions.add(new Key(e));
//      System.out.println(recordedActions[0]);
      
      if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
          try {
              GlobalScreen.unregisterNativeHook();
          } catch (NativeHookException e1) {
              // TODO Auto-generated catch block
              e1.printStackTrace();
              
          }
      }
  }
  @Override
  public void nativeKeyReleased(NativeKeyEvent e) {
      System.out.println("Key Released: "
              + NativeKeyEvent.getKeyText(e.getKeyCode()));
      write("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
  }

  @Override
  public void nativeKeyTyped(NativeKeyEvent e) {
//      System.out.println("Key Typed: "
//              + NativeKeyEvent.getKeyText(e.getKeyCode()));
//      write("Key Typed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
  }

  @Override
  
  public void nativeMouseClicked(NativeMouseEvent e) {
//      System.out.println("sakdljf");

    write("mouse clicked:" + e.getX() + ", " + e.getY());
//    System.out.println("murica");
      
//      Click myClick = new Click(e.getButton(), e.getX(), e.getY());
//      System.out.println(myClick.x);
//      myClick.doClick();

  }

  @Override
  public void nativeMousePressed(NativeMouseEvent e) {
      write("mouse Press:" + e.getButton());
  }


  
  @Override
  public void nativeMouseReleased(NativeMouseEvent e) {
      
      write("mouse Release:" + e.getButton());
  }

  @Override
  public void nativeMouseMoved(NativeMouseEvent e) {
  	int storedX, storedY;
      
  	storedX = e.getX();
  	storedY = e.getY();
  	
  	write("Mouse moved. x: " + e.getX() + " y: " + e.getY());
  	
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}


  }

  @Override
  public void nativeMouseDragged(NativeMouseEvent e) {
      
      write("mouse Dragged:" + e.getX() + "," + e.getY());
  }

  private void write(String i) {
      BufferedWriter bufferedWriter = null;
      Date date = Calendar.getInstance().getTime();
      try {
          bufferedWriter = new BufferedWriter(
                  new OutputStreamWriter(new FileOutputStream(path
                          + "\\log.txt", true), "BIG5"));
          bufferedWriter.write(date.toString() + ":" + i + "\r\n");
          close(bufferedWriter);
      } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
      } finally {
          close(bufferedWriter);
      }

  }
  private void close(BufferedWriter w) {
      try {
          if (w != null) {
              w.close();
          }
      } catch (IOException e) {
          e.printStackTrace();
      }
  }
  
}

