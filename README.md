# autoClicker
Executable that will save macros of mouse clicks and keyboard presses, then replay them later

This is an automatic clicker program. The basic idea is to record a series of mouse clicks (or keyboard presses) and replay that again later

Code version: "1.0.0"
File storage version: "1"

This is the first code release. As such I have focused on minimum functionality at the expense of some user friendliness/functionality.

More events can be added by copying/pasting them. The file version is written in an approximation of JSON format. Be careful when copying/pasting events as the file parser is not very robust and a slight error in formatting can render the macro useless. Keep a working copy of macro.txt soewhere safe before making major modifications

The program has two different functionalities created so far. You can execute a macro, or get the x/y coordinates of a mouse position. Follow the prompts supplied within the program to access these features.


USAGE

Copy the autoClicker .jar file as well as the macro.txt text file into the same directory.

The macro.txt file stores the "recorded" macro in plain text format. As the recording functionality has not been implemented yet, you will need to open the macro.txt file in a text editor and edit it manually in order to create the macro you want. Here are the values you can modify:

"fileVersion" - This is a read only property that determines how the program should parse the macro file. DO NOT MODIFY as it may corrupt your macro or crash the program

"numberOfRepetitions" - determines how many times to play the macro (list of mouse click envents) in a row before terminating

"introduceVariability" - This is a true/false value that makes the macro a little less stale. It will add a slight amount of randomness by swinging the x/y coordinates by +/-4 and the delays by +/-5%.

"Event" - A list of mouse click events which constitute the macro

"millisecondsBeforeEvent" - number of miliseconds to wait before executing the mouse event

"type" - currently only MOUSE_CLICK event is implemented/tested. Modify this parameter at your own risk

"x" - x coordinate for mouse click event

"y" - y coordinate for mouse click event

"durationOfEvent" - how long the mouse click is pressed for. A mouse click consists of a keyDown event followed by a keyUp event. This duration is the time in between keyDown and keyUp
