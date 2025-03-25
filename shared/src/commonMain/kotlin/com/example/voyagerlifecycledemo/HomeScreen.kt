package com.example.voyagerlifecycledemo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.uniqueScreenKey


private const val TAG = "HomeScreen"

class HomeScreen: Screen {
    override val key = uniqueScreenKey


    @Composable
    override fun Content() {
        androidx.compose.foundation.layout.Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Text("This is Home")
        }

        val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current

        DisposableEffect(lifecycleOwner) {
            val observer = LifecycleEventObserver { _, event ->
                when (event) {
                    Lifecycle.Event.ON_PAUSE -> {
                        println("HomeScreen --> Lifecycle.Event.ON_PAUSE")
                    }
                    Lifecycle.Event.ON_RESUME -> {
                        println("HomeScreen --> Lifecycle.Event.ON_RESUME")
                    }
                    else -> { /* no-op */
                        println("HomeScreen --> Lifecycle.Event: $event")
                    }
                }
            }
            lifecycleOwner.lifecycle.addObserver(observer)

            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
                println("Lifecycle-HomeScreen --> Observer removed")
            }

        }
    }




}