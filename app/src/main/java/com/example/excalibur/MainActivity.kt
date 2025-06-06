package com.example.excalibur

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.NavKey
import com.example.excalibur.Screen.Home.screensToShow
import com.example.excalibur.ui.theme.ExcaliburTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enableEdgeToEdge()
        setContent {
            // Apply the Material Design theme to your app
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    // Create a back stack, specifying the key the app should start with
    val backStack = rememberSaveable { mutableStateListOf<NavKey>(Screen.Home) }

    // Your app's theme
    ExcaliburTheme {
        NavigationSuiteScaffold(
            navigationSuiteItems = {
                screensToShow.forEach {
                    item(
                        icon = {
                            Icon(
                                it.icon,
                                contentDescription = it.name
                            )
                        },
                        label = { Text(it.name) },
                        selected = it.javaClass == backStack.last().javaClass,
                        onClick = { backStack.add(it) }
                    )
                }
            },
        ) {

            NavExample(backStack, Modifier.fillMaxSize())
        }
    }
}




