import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.dispatcher.SwingDispatchService;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.github.kwhat.jnativehook.keyboard.SwingKeyAdapter;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseInputListener;
import com.github.kwhat.jnativehook.mouse.NativeMouseListener;
import com.github.kwhat.jnativehook.mouse.NativeMouseMotionListener;
import com.github.kwhat.jnativehook.mouse.NativeMouseWheelEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseWheelListener;
import java.awt.event.KeyEvent;
import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.ItemSelectable;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import java.util.*;
//import java.io.*;
import java.io.*;
import java.awt.Robot;

/**
 * @author Richard Shan
 * https://richard-shan.github.io/coding/macro/
 * 
 */
public class GUILogger extends JFrame implements ActionListener, ItemListener, NativeKeyListener,
		NativeMouseInputListener, NativeMouseWheelListener, WindowListener {

	static Robot robot;	
	
	SwingKeyAdapter ska = new SwingKeyAdapter();
	
	static PrintWriter pw;

//	ArrayList<Stack> recordedSequences = new ArrayList<>();
	ArrayList<Stack> recordedSequences = new ArrayList<Stack>();
	
//	Stack<Action> recordedActions = new Stack<Action>();
	Stack<Action> recordedActions = new Stack<Action>();
	
	int currentSeq = 0;
	
	long time = System.currentTimeMillis();	
	
	private static final long serialVersionUID = 1541183202160543102L;
	
	boolean recording;

	private final JMenu menuSubListeners;

	private final JMenuItem menuItemQuit;

	private final JMenuItem menuItemClear;
	
	private JMenuItem menuItemStartRec;
	
	private JMenuItem menuItemStopRec;
	
	private JMenuItem menuItemReplay1;	
	
	private final JCheckBoxMenuItem menuItemEnable;

	private final JCheckBoxMenuItem menuItemKeyboardEvents;

	private final JCheckBoxMenuItem menuItemButtonEvents;

	private final JCheckBoxMenuItem menuItemMotionEvents;

	private final JCheckBoxMenuItem menuItemWheelEvents;

	private final JTextArea txtEventInfo;



	private static final Logger log = Logger.getLogger(GlobalScreen.class.getPackage().getName());

	public GUILogger() {
		
		try {
			pw = new PrintWriter("logger.out");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		setTitle("Log");
		setLayout(new BorderLayout());
		setDefaultCloseOperation(2);
		setSize(600, 300);

		addWindowListener(this);
		JMenuBar menuBar = new JMenuBar();
		JMenu menuFile = new JMenu("File");
		menuFile.setMnemonic(70);
		menuBar.add(menuFile);

		this.menuItemQuit = new JMenuItem("Quit", 81);
		this.menuItemQuit.addActionListener(this);
		this.menuItemQuit.setAccelerator(KeyStroke.getKeyStroke(115, 512));
		this.menuItemQuit.getAccessibleContext().setAccessibleDescription("Exit the program");

		menuFile.add(this.menuItemQuit);
		JMenu menuView = new JMenu("View");
		menuView.setMnemonic(86);
		menuBar.add(menuView);
		this.menuItemClear = new JMenuItem("Clear", 67);
		this.menuItemClear.addActionListener(this);
		this.menuItemClear.setAccelerator(KeyStroke.getKeyStroke(67, 192));
		this.menuItemClear.getAccessibleContext().setAccessibleDescription("Clear the screen");
		menuView.add(this.menuItemClear);
		menuView.addSeparator();

		this.menuItemEnable = new JCheckBoxMenuItem("Enable Native Hook");
		this.menuItemEnable.addItemListener(this);
		this.menuItemEnable.setMnemonic(72);
		this.menuItemEnable.setAccelerator(KeyStroke.getKeyStroke(72, 192));
		menuView.add(this.menuItemEnable);

		this.menuSubListeners = new JMenu("Listeners");
		this.menuSubListeners.setMnemonic(76);
		menuView.add(this.menuSubListeners);

		this.menuItemKeyboardEvents = new JCheckBoxMenuItem("Keyboard Events");
		this.menuItemKeyboardEvents.addItemListener(this);
		this.menuItemKeyboardEvents.setMnemonic(75);
		this.menuItemKeyboardEvents.setAccelerator(KeyStroke.getKeyStroke(75, 192));
		this.menuSubListeners.add(this.menuItemKeyboardEvents);

		this.menuItemButtonEvents = new JCheckBoxMenuItem("Button Events");
		this.menuItemButtonEvents.addItemListener(this);
		this.menuItemButtonEvents.setMnemonic(66);
		this.menuItemButtonEvents.setAccelerator(KeyStroke.getKeyStroke(66, 192));
		this.menuSubListeners.add(this.menuItemButtonEvents);

		this.menuItemMotionEvents = new JCheckBoxMenuItem("Motion Events");
		this.menuItemMotionEvents.addItemListener(this);
		this.menuItemMotionEvents.setMnemonic(77);
		this.menuItemMotionEvents.setAccelerator(KeyStroke.getKeyStroke(77, 192));
		this.menuSubListeners.add(this.menuItemMotionEvents);

		this.menuItemWheelEvents = new JCheckBoxMenuItem("Wheel Events");
		this.menuItemWheelEvents.addItemListener(this);
		this.menuItemWheelEvents.setMnemonic(87);
		this.menuItemWheelEvents.setAccelerator(KeyStroke.getKeyStroke(87, 192));
		this.menuSubListeners.add(this.menuItemWheelEvents);
		setJMenuBar(menuBar);

		this.txtEventInfo = new JTextArea();
		this.txtEventInfo.setEditable(false);
		this.txtEventInfo.setBackground(new Color(255, 255, 255));
		this.txtEventInfo.setForeground(new Color(0, 0, 0));
		this.txtEventInfo.setText("");

		JMenu menuRecord = new JMenu("Record");
		menuBar.add(menuRecord);
		
		this.menuItemStartRec = new JMenuItem("Start Recording", 82);
		this.menuItemStartRec.addActionListener(this);
		this.menuItemStartRec.setAccelerator(KeyStroke.getKeyStroke(82, 192));
		menuRecord.add(menuItemStartRec);
		menuRecord.addSeparator();
		
		this.menuItemStopRec = new JMenuItem("Stop Recording", 83);
		this.menuItemStopRec.addActionListener(this);
		this.menuItemStopRec.setAccelerator(KeyStroke.getKeyStroke(83, 192));
		menuRecord.add(menuItemStopRec);
		menuRecord.addSeparator();
		
		
		JMenu menuReplay1 = new JMenu("Replay");
		menuBar.add(menuReplay1);
		
		this.menuItemReplay1 = new JMenuItem("Replay Macro 1", 49);
		this.menuItemReplay1.addActionListener(this);
		this.menuItemReplay1.setAccelerator(KeyStroke.getKeyStroke(49, 192));
		menuReplay1.add(menuItemReplay1);
		menuReplay1.addSeparator();
		
		
		JScrollPane scrollPane = new JScrollPane(this.txtEventInfo);
		scrollPane.setPreferredSize(new Dimension(375, 125));
		add(scrollPane, "Center");
		log.setUseParentHandlers(false);
		log.setLevel(Level.INFO);
		ConsoleHandler handler = new ConsoleHandler();
		handler.setLevel(Level.ALL);
		log.addHandler(handler);
		GlobalScreen.setEventDispatcher((ExecutorService) new SwingDispatchService());
		setVisible(true);
	}

	public static void main(String[] args) throws AWTException {
		robot = new Robot();

		try {
			pw = new PrintWriter("C:/Users/richa/Desktop/logger.out");
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new GUILogger();
			}
		});
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.menuItemQuit) {
			dispose();  
		} else if (e.getSource() == this.menuItemClear) {
			recordedActions.clear();
			recordedSequences.clear();
			this.txtEventInfo.setText("Cleared");
		} else if(e.getSource() == this.menuItemStartRec) {
			if(recording == true) {
				write("Already recording.");
			} else {
//				recordedActions.remove(0);
//				recordedActions.remove(1);
//				recordedActions.remove(2); 
			    recording = true;
			    write("Recording Started");
			}
		} else if(e.getSource() == this.menuItemStopRec) {
			if(recording == false) {
				write("Recording already stopped.");
			} else {
//				recordedActions.remove(recordedActions.size());
//				recordedActions.remove(recordedActions.size()-1);
//				recordedActions.remove(recordedActions.size()-2);
			    recording = false; 
				recordedSequences.add(recordedActions);
//				recordedActions.clear();
//				recordedActions.clear();
				write("Recording ended");
			}
		} else if(e.getSource() == this.menuItemReplay1) {
		    write("Replaying");
		    robot.keyRelease(KeyEvent.VK_CONTROL);
		    robot.keyRelease(KeyEvent.VK_SHIFT);
		    robot.keyRelease(KeyEvent.VK_1);
			for(Action i : (Stack<Action>) recordedSequences.get(0)) {
				i.press();
//				try {
//					Thread.sleep(i.delay);
//				} catch (InterruptedException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
			}
		}
	}

	public void itemStateChanged(ItemEvent e) {
		ItemSelectable item = e.getItemSelectable();
		if (item == this.menuItemEnable) {
			try {
				if (e.getStateChange() == 1) {  
					GlobalScreen.registerNativeHook();
				} else {
					GlobalScreen.unregisterNativeHook();
				}
			} catch (NativeHookException ex) {
				this.txtEventInfo.append("Error: " + ex.getMessage() + "\n");
			}
			this.menuItemEnable.setState(GlobalScreen.isNativeHookRegistered());
			this.menuSubListeners.setEnabled(this.menuItemEnable.getState());
		} else if (item == this.menuItemKeyboardEvents) {
			if (e.getStateChange() == 1) {
				GlobalScreen.addNativeKeyListener(this);
			} else {
				GlobalScreen.removeNativeKeyListener(this);
			}
		} else if (item == this.menuItemButtonEvents) {
			if (e.getStateChange() == 1) {
				GlobalScreen.addNativeMouseListener((NativeMouseListener) this);
			} else {
				GlobalScreen.removeNativeMouseListener((NativeMouseListener) this);
			}
		} else if (item == this.menuItemMotionEvents) {
			if (e.getStateChange() == 1) {
				GlobalScreen.addNativeMouseMotionListener((NativeMouseMotionListener) this);
			} else {
				GlobalScreen.removeNativeMouseMotionListener((NativeMouseMotionListener) this);
			}
		} else if (item == this.menuItemWheelEvents) {
			if (e.getStateChange() == 1) {
				GlobalScreen.addNativeMouseWheelListener(this);
			} else {
				GlobalScreen.removeNativeMouseWheelListener(this);
			}
		}
	}

	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {
		if(recording) {
//			System.out.println("Time: " + System.currentTimeMillis());
//			System.out.println("OLD: " + time);
			recordedActions.add(new KeyPress(e, Math.abs(System.currentTimeMillis()-time)));
			time = System.currentTimeMillis();
		} 
		
//		if(recording) {
//			recordedActions.add(new KeyPress((e), System.currentTimeMillis(), false));
//		}
		
//      System.out.println("Key Pressed: "
//              + NativeKeyEvent.getKeyText(e.getKeyCode()));
//		write(System.currentTimeMillis() + ": " + "Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
		
		
//      recordedActions.add(new Key(e));
//      System.out.println(recordedActions[0]);

//		if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
//			try {
//				GlobalScreen.unregisterNativeHook();
////              System.exit(ABORT);
//			} catch (NativeHookException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//
//			}
//		} 
	}
	@Override
	public void nativeKeyReleased(NativeKeyEvent e) {
		
		if(recording) {
//			System.out.println(Math.abs(System.currentTimeMillis()-time));
			recordedActions.add(new KeyRelease(e, Math.abs(System.currentTimeMillis()-time)));
			time = System.currentTimeMillis();
		}
		
//		if(recording) {
//			recordedActions.add(new KeyPress((e), System.currentTimeMillis(), true));
//		}
		
//      System.out.println("Key Released: "
//              + NativeKeyEvent.getKeyText(e.getKeyCode()));
//		write(System.currentTimeMillis() + ": " + "Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
		pw.println(System.currentTimeMillis() + ": " + "Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent e) {
//      System.out.println("Key Typed: "
//              + NativeKeyEvent.getKeyText(e.getKeyCode()));
//      write("Key Typed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
	}

	@Override
	public void nativeMouseClicked(NativeMouseEvent e) {
////    System.out.println("sakdljf");
//
//		write(System.currentTimeMillis() + ": " + "Mouse clicked. x: " + e.getX() + ", y: " + e.getY() + " with Button "
//				+ e.getButton());
//		pw.println(System.currentTimeMillis() + ": " + "Mouse clicked. x: " + e.getX() + ", y: " + e.getY() + " with Button "
//				+ e.getButton());		
//		recordedActions.add(new Click(e.getButton(), e.getX(), e.getY()));
		// System.out.println("murica");

//    Click myClick = new Click(e.getButton(), e.getX(), e.getY());
//    System.out.println(myClick.x);
//    myClick.doClick();

	}

	@Override
  public void nativeMousePressed(NativeMouseEvent e) {
	if(recording) {
		recordedActions.add(new ClickPress(e, System.currentTimeMillis()-time));
		time = System.currentTimeMillis();
	}
//    write("Mouse Pressed: " + e.getX() +", " + e.getY());
  }
	@Override
  public void nativeMouseReleased(NativeMouseEvent e) {
	if(recording) {
		recordedActions.add(new ClickRelease(e, System.currentTimeMillis()-time));
		time = System.currentTimeMillis();
	}		
//	write("Mouse Released: " + e.getX() +", " + e.getY());
	
  }

	@Override
	public void nativeMouseMoved(NativeMouseEvent e) {
		if(recording) {
			recordedActions.add(new mouseMove(e, System.currentTimeMillis()-time));
			time = System.currentTimeMillis();
		}
// 	int storedX, storedY;
//     
// 	storedX = e.getX();
// 	storedY = e.getY();
// 	
// 	long ct = System.currentTimeMillis();
// 	
// 	while(System.currentTimeMillis() -100 >= ct == false) {
// 		
// 	}
// 	
// 	int newX, newY;
// 	newX = e.getX();
// 	newY = e.getY();
// 	
// 	if(newX != storedX || newY != storedY) {
//		write(System.currentTimeMillis() + ": " + "Mouse moved. x: " + e.getX() + " y: " + e.getY());
		pw.println(System.currentTimeMillis() + ": " + "Mouse moved. x: " + e.getX() + " y: " + e.getY());
//		try {
//			Thread.sleep(5);
//		} catch (InterruptedException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}

	}

//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}

// }

	@Override
	public void nativeMouseDragged(NativeMouseEvent e) {

//		write(System.currentTimeMillis() + ": " + "Mouse Dragged:" + e.getX() + "," + e.getY());
		pw.println(System.currentTimeMillis() + ": " + "Mouse Dragged:" + e.getX() + "," + e.getY());
	}

//  public void nativeMouseWheelMoved(NativeMouseWheelEvent e) {
////    write(e.paramString());
//    write(Integer.toString(e.getWheelDirection()));
//  }

	private void write(String output) {
		this.txtEventInfo.append("\n" + output);
		try {
			if (this.txtEventInfo.getLineCount() > 100)
				this.txtEventInfo.replaceRange("", 0,
						this.txtEventInfo.getLineEndOffset(this.txtEventInfo.getLineCount() - 1 - 100));
			this.txtEventInfo
					.setCaretPosition(this.txtEventInfo.getLineStartOffset(this.txtEventInfo.getLineCount() - 1));
		} catch (BadLocationException ex) {
			this.txtEventInfo.setCaretPosition(this.txtEventInfo.getDocument().getLength());
		}
	}

	public void windowActivated(WindowEvent e) {
	}

	public void windowClosing(WindowEvent e) {
	}

	public void windowDeactivated(WindowEvent e) {
	}

	public void windowDeiconified(WindowEvent e) {
	}

	public void windowIconified(WindowEvent e) {
	}

	public void windowOpened(WindowEvent e) {
		requestFocusInWindow();
		this.txtEventInfo.setText("Auto Repeat Rate: " + System.getProperty("jnativehook.key.repeat.rate"));
		write("Auto Repeat Delay: " + System.getProperty("jnativehook.key.repeat.delay"));
		write("Double Click Time: " + System.getProperty("jnativehook.button.multiclick.iterval"));
		write("Pointer Sensitivity: " + System.getProperty("jnativehook.pointer.sensitivity"));
		write("Pointer Acceleration Multiplier: " + System.getProperty("jnativehook.pointer.acceleration.multiplier"));
		write("Pointer Acceleration Threshold: " + System.getProperty("jnativehook.pointer.acceleration.threshold"));
		this.menuItemEnable.setSelected(true);
		try {
			this.txtEventInfo
					.setCaretPosition(this.txtEventInfo.getLineStartOffset(this.txtEventInfo.getLineCount() - 1));
		} catch (BadLocationException ex) {
			this.txtEventInfo.setCaretPosition(this.txtEventInfo.getDocument().getLength());
		}
		this.menuItemKeyboardEvents.setSelected(true);
		this.menuItemButtonEvents.setSelected(true);
		this.menuItemMotionEvents.setSelected(true);
		this.menuItemWheelEvents.setSelected(true);
	}

	@SuppressWarnings("removal")
	public void windowClosed(WindowEvent e) {
		
		pw.close();

		try {
			GlobalScreen.unregisterNativeHook();
		} catch (NativeHookException ex) {
			ex.printStackTrace();
		}
		System.runFinalization();
		System.exit(0);
	}


}
