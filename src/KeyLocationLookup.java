import java.awt.event.KeyEvent;
import java.util.Hashtable;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;

public class KeyLocationLookup {
	
	//Singleton instance
    private static KeyLocationLookup instance;
    
	//HASHTABLE
    private static Hashtable<Integer, Integer> hashtable = new Hashtable<>();

    private KeyLocationLookup(){}

    public static KeyLocationLookup getInstance() {
        if (instance == null) {
            instance = new KeyLocationLookup();
            hashtable.put(NativeKeyEvent.KEY_LOCATION_STANDARD, KeyEvent.KEY_LOCATION_STANDARD);
            hashtable.put(NativeKeyEvent.KEY_LOCATION_NUMPAD, KeyEvent.KEY_LOCATION_NUMPAD);
            hashtable.put(NativeKeyEvent.KEY_LOCATION_LEFT, KeyEvent.KEY_LOCATION_STANDARD);
            hashtable.put(NativeKeyEvent.KEY_LOCATION_RIGHT, KeyEvent.KEY_LOCATION_RIGHT);
        }
        return instance;
    }
	
	
	public int lookup(int key) {
 
		return hashtable.get(key);
	}
	
	
	
	
}
