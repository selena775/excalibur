package com.example.excalibur

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import navigation.MyApp
import navigation.inner.NestedProductDetailFlow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enableEdgeToEdge()
        setContent {
            // Apply the Material Design theme to your app
            MyApp()
           // NestedProductDetailFlow("banana", {})

        }
    }
}