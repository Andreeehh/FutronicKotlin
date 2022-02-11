// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import androidx.compose.desktop.Window
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.*
import com.futronic.SDKHelper.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.savedinstancestate.savedInstanceState
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.ExperimentalKeyInput
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp


fun main() = Window(title = "Compose Biometry Desktop", size = IntSize(800,800)) {
    val darkTheme = savedInstanceState { true }
    MaterialTheme(colors = if (darkTheme.value) DarkGreenColorPalette else LightGreenColorPalette) {
        app(darkTheme)
    }
}

@Composable
fun app(darkTheme: MutableState<Boolean>) {
    val navItemState = savedInstanceState { NavType.HOME }
    Box {
        Row {
                Button(onClick = {
                navItemState.value = NavType.HOME
                },modifier = Modifier.offset(25.dp, 0.dp) )
                {
                Text("Biometria")
                }
                Button(onClick = {
                    navItemState.value = NavType.CARD
                },modifier = Modifier.offset(25.dp, 0.dp) )
                {
                    Text("Comandas")
                }
                Button(onClick = {
                    navItemState.value = NavType.HALLTABLE
                },modifier = Modifier.offset(25.dp, 0.dp) )
                {
                    Text("Mesa")
                }
        }
        Row(modifier = Modifier.offset(0.dp,50.dp)) { bodyContent(navItemState.value) }

    }
}

@OptIn(ExperimentalKeyInput::class)
@Composable
fun bodyContent(navType: NavType) {
        Crossfade(current = navType) { navType ->
            when (navType) {
                NavType.HOME -> Biometry()
                NavType.CARD -> cardInterface()
                NavType.HALLTABLE -> hallTableInterface()
            }
        }
}
