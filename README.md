# Macro Maker 
### Description
The Macro project was my final project for Java Data Structures (H) in 10th grade. The final product allows the user to start and end a global recording of all inputs to the computer. The global recording will inputs including mouse movements; mouse dragging; mouse clicking, releasing, and holding; key presses, releases, and hold times; and any simultaneous combination of events. The actions are also logged in the logger.out file. After the user begins recording, (CTRL + SHIFT+ R), all henceforth input actions are stored as objects in a stack until the user stops recording (CTRL + SHIFT + S). Upon replaying the stored actions, the program will iterate through the stack and call each object's press() method to execute the action exactly as performed. The press() methods also delays the action's execution as to match the delays between actions of the original recording. The program does not interfere with input actions or other applications in any way.


<center>
<br> 

</center>
### Download 
<center>

##### The github repo can be found <a href="https://github.com/richard-shan/macro" download="Macro Maker.jar">here</a>.
##### The jar executable can be downloaded <a href="../macro.jar" download="Macro Maker.jar">here</a>.
<br>
<p align="center">
**Instructions for Use**

Start Recording Keystrokes: CTRL + SHIFT + R

Stop Recording Keystrokes and Save: CTRL + SHIFT + S

Replay Saved Keystroke Sequence: CTRL + SHIFT + 1
</p>

<br> <br>
</center>

### Initial Planning Chart

<p align="center">
<img src="/bin/macroPlanningChart.jpg" alt="macroMaker_flow_chart" width="450"/>
</p>


##### Key Elements
- Action interface implemented by all the action/event classes: ClickPress, ClickRelease, KeyPress, KeyRelease, MouseMove, etc.
    - Every action class follows the command archetype, allowing the encapsulation of a request as an object.
- KeyLocationLookup 
    - Singleton - single instance of the lookup object that is referenced every time a key conversion is needed
    - Hashtable inside KeyLocationLookup to store NativeKeyEvent KeyLocations and corresponding KeyEvent integer values for conversion
- Mouse and Keyboard Adapters to listen for actions within the global screen. Those adapters also function as observers for the GlobalScreen.
- Stack to store the recorded actions
- Custom MouseCoordinatesConverter component that will convert true x,y mouse coordinates to relative x, y mouse coordinates scaled to each computer's unique screen size. Also functions as an adapter.
- State machine under GUILogger handling action transitions and entries when a key/mouse is pressed
- TreeMap under KeyCursorLookup to store NativeKeyEvent VC values and corresponding KeyEvent VK values
