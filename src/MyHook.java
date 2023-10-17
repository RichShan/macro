
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


public class MyHook implements NativeKeyListener,
        NativeMouseInputListener {

    private String path;
    
    
    
    public MyHook() {
        path = System.getProperty("user.dir");
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

    public void nativeKeyPressed(NativeKeyEvent e) {
        System.out.println("Key Pressed: "
                + NativeKeyEvent.getKeyText(e.getKeyCode()));
        write("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
        if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
            try {
                GlobalScreen.unregisterNativeHook();
            } catch (NativeHookException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        System.out.println("Key Released: "
                + NativeKeyEvent.getKeyText(e.getKeyCode()));
        write("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
    }

    public void nativeKeyTyped(NativeKeyEvent e) {
        System.out.println("Key Typed: "
                + NativeKeyEvent.getKeyText(e.getKeyCode()));
        write("Key Typed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
    }

    @Override
    public void nativeMouseClicked(NativeMouseEvent e) {
        
        System.out.println("mouse clicked:" + e.getX() + ", " + e.getY());
    }

    @Override
    public void nativeMousePressed(NativeMouseEvent e) {
        System.out.println("mouse Press:" + e.getButton());
    }


    
    @Override
    public void nativeMouseReleased(NativeMouseEvent e) {
        
        System.out.println("mouse Release:" + e.getButton());
    }

    @Override
    public void nativeMouseMoved(NativeMouseEvent e) {
    	int storedX, storedY;
        
    	storedX = e.getX();
    	storedY = e.getY();
    	
    	System.out.println("Mouse moved. x: " + e.getX() + " y: " + e.getY());
    	
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}


    }

    @Override
    public void nativeMouseDragged(NativeMouseEvent e) {
        
        System.out.println("mouse Dragge:" + e.getX() + "," + e.getY());
    }

    private void write(String i) {
        BufferedWriter bufferedWriter = null;
        Date date = Calendar.getInstance().getTime();
        try {
            bufferedWriter = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(path
                            + "\\log2.txt", true), "BIG5"));
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