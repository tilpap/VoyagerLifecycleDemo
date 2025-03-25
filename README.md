This is a Demo Project to illustrate a basic problem with Voyager, lifecycle handling and configuration changes.

Using Voyager (Home Screen wrapped by Navigator) leads to problems with lifecycle handling and configuration changes.

Particularly, if a configuration change happens like screen rotation, the HomeScreen observer becomes unable to observe lifecycle events like the app getting in the background (ON_PAUSE).

This does not happen if Voyager is not used (for example, use GreetingView and see the logs).

⸻

Testing process:

— Voyager usage with Home Screen —
	1.	Run the app
	2.	Open Logs
	3.	Put the app in the background and back to the foreground
	4.	See the logs. Both HomeScreen and MainActivity onPause events are intercepted correctly.
	5.	Now do some configuration changes like screen rotation
	6.	Notice that HomeScreen cannot intercept ON_PAUSE events anymore.
	7.	Put the app in the background and back to the foreground
	8.	See the logs. HomeScreen does not intercept ON_STOP, ON_PAUSE, or ON_RESUME events.

— App without Voyager | Use GreetingView —
	1.	Run the app
	2.	Open Logs
	3.	Put the app in the background and back to the foreground
	4.	See the logs. Both HomeScreen and MainActivity onPause events are intercepted correctly.
	5.	Now do some configuration changes like screen rotation
	6.	All events are intercepted correctly.
	7.	Put the app in the background and back to the foreground
	8.	See the logs. HomeScreen intercepts ON_STOP, ON_PAUSE, and ON_RESUME events correctly.
 
