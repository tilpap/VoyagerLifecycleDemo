package com.example.voyagerlifecycledemo.android

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import cafe.adriel.voyager.navigator.Navigator
import com.example.voyagerlifecycledemo.Greeting
import com.example.voyagerlifecycledemo.HomeScreen

class MainActivity : ComponentActivity() {


    override fun onStart() {
        super.onStart()
        Log.d("MainActivity", "onStart")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivity", "onDestroy")
    }

    override fun onPause() {
        Log.d("MainActivity", "onPause")
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        Log.d("MainActivity", "onResume")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {

                /**
                 * Using Voyager (Home Screen wrapped by Navigator) leads to problems with lifecycle handling
                 * and configuration changes.
                 *
                 * Particularly, if a configuration change happens like Screen Rotation, the HomeScreen observer
                 * becomes unable to observe lifecycle events like the app getting in the background (ON_PAUSE).
                 *
                 * This does NOT happen if voyager is not used (for example use GreetingView and see the logs).
                 *
                 *
                 * Testing process:
                 *
                 *  --- Voyager usage with Home Screen ---
                 * 1. Run the app
                 * 2. Open Logs
                 * 3. Put the app on the background and back to the foreground
                 * 4. See the logs. Both HomeScreen and MainActivity onPause events are intercepted correctly.
                 * 5. Now do some configuration changes like Screen Rotation
                 * 6. Notice that  HomeScreen cannot intercept any more the ON_PAUSE event.
                 * 6. Put the app on the background and back to the foreground
                 * 7. See the logs. HomeScreen does not intercept ON_STOP, ON_PAUSE or ON_RESUME events.
                 *
                 *
                 *  --- App without Voyager | Use GreetingView ---
                 * 1. Run the app
                 * 2. Open Logs
                 * 3. Put the app on the background and back to the foreground
                 * 4. See the logs. Both HomeScreen and MainActivity onPause events are intercepted correctly.
                 * 5. Now do some configuration changes like Screen Rotation
                 * 6. All events are intercepted correctly.
                 * 6. Put the app on the background and back to the foreground
                 * 7. See the logs. HomeScreen intercepts ON_STOP, ON_PAUSE or ON_RESUME events correctly.
                 *
                 *
                 */


                // --- Voyager usage with Home Screen ---
                Navigator(
                    HomeScreen()
                )


                // --- App without Voyager | Use GreetingView ---
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    GreetingView(Greeting().greet())
//                }


            }
        }
    }
}


@Composable
fun GreetingView(text: String) {

    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> {
                    Log.d("GreetingView", "Lifecycle.Event.ON_PAUSE")
                }
                Lifecycle.Event.ON_RESUME -> {
                    Log.d("GreetingView", "Lifecycle.Event.ON_RESUME")
                }
                else -> {
                    Log.d("GreetingView", "Lifecycle.Event: $event")
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            Log.d("GreetingView", "Observer removed")
        }

    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        Text(text = text)
    }

}




@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        GreetingView("Hello, Android!")
    }
}
