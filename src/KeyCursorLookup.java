import java.awt.event.KeyEvent;
import java.util.Hashtable;
import java.util.TreeMap;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;

public class KeyCursorLookup {
	
    
	//TreeMap
    private static TreeMap<Integer, Integer> treemap = null; 

    private KeyCursorLookup(){}
	
	public static int lookup(int key) {
        if (treemap == null) {
        	treemap = new TreeMap<>();
            treemap.put(NativeKeyEvent.VC_UP, KeyEvent.VK_UP);
            treemap.put(NativeKeyEvent.VC_LEFT, KeyEvent.VK_LEFT);
            treemap.put(NativeKeyEvent.VC_CLEAR, KeyEvent.VK_CLEAR);
            treemap.put(NativeKeyEvent.VC_RIGHT, KeyEvent.VK_RIGHT);
            treemap.put(NativeKeyEvent.VC_DOWN, KeyEvent.VK_DOWN);

        }
        if(treemap.get(key) != null) {
        	return treemap.get(key);
        } else {
        	return 0;
        }
	}
	
}
