// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import androidx.compose.desktop.Window
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.*
import com.futronic.SDKHelper.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.savedinstancestate.savedInstanceState
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.ExperimentalKeyInput
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


fun main() = Window(title = "Compose Biometry Desktop", size = IntSize(1200,1000)) {
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
                },modifier = Modifier.offset(325.dp, 0.dp) )
                {
                Text("Biometria")
                }
                Button(onClick = {
                    navItemState.value = NavType.CARD
                },modifier = Modifier.offset(425.dp, 0.dp) )
                {
                    Text("Comandas")
                }
                Button(onClick = {
                    navItemState.value = NavType.HALLTABLE
                },modifier = Modifier.offset(525.dp, 0.dp) )
                {
                    Text("Mesa")
                }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.width(width = 200.dp).offset(1000.dp, 0.dp).clickable { darkTheme.value = !darkTheme.value }.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Mudar Tema", style = MaterialTheme.typography.h6.copy(fontSize = 14.sp), color = MaterialTheme.colors.onSurface)
            if (darkTheme.value) {
                Icon(imageVector = Icons.Default.Star, tint = Color.Yellow)
            } else {
                Icon(imageVector = Icons.Default.Star, tint = MaterialTheme.colors.onSurface)
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
