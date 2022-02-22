// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.animation.Crossfade
import androidx.compose.desktop.Window
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.savedinstancestate.savedInstanceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.ExperimentalKeyInput
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.keyInputFilter
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import domain.*
import databaseinterface.DBIUser as dbiUser
import util.DataUtil


fun main() = Window(title = "Compose Biometry Desktop", size = IntSize(1200, 1000)) {
    val darkTheme = savedInstanceState { false }
    MaterialTheme(colors = if (darkTheme.value) DarkGreenColorPalette else LightGreenColorPalette) {
        app(darkTheme)
    }
}

@OptIn(ExperimentalKeyInput::class)
@Composable
fun app(darkTheme: MutableState<Boolean>) {
    val navItemState = savedInstanceState { NavType.LOGIN }
    var enabled by remember { mutableStateOf(false) }
    var userLogged = remember { mutableStateOf<User?>(User()) }
    var password by remember { mutableStateOf("") }
    var text by remember { mutableStateOf("Informe sua senha") }
    val dbiUser = dbiUser()
    var userList = dbiUser.getList()
    var user: User? = null
    fun checkPassword (password: String){
        if (password == "") {
            text = "Senha inválida"
        }
        var pass = DataUtil().getDBPassword(password)
        if (pass == null) {
            text = "Senha inválida"
        }
        var idUser = DataUtil().getUserToLogin(pass!!)
        if( idUser == 0) {
            text = "Senha inválida"
        } else {
            enabled = true
            navItemState.value = NavType.HOME
            userLogged.value = databaseinterface.DBIUser().getSingleUser(idUser)
        }
    }
    Box {
        Row {
            Button(
                onClick = {
                    navItemState.value = NavType.HOME
                }, modifier = Modifier.offset(275.dp, 15.dp),
                enabled = enabled
            )
            {
                Text("Biometria")
            }
            Button(
                onClick = {
                    navItemState.value = NavType.CARD
                }, modifier = Modifier.offset(375.dp, 15.dp),
                enabled = enabled
            )
            {
                Text("Comandas")
            }
            Button(
                onClick = {
                    navItemState.value = NavType.HALLTABLE
                }, modifier = Modifier.offset(475.dp, 15.dp),
                enabled = enabled
            )
            {
                Text("Mesa")
            }

            Button(
                onClick = {
                    navItemState.value = NavType.CHECKOUT
                }, modifier = Modifier.offset(575.dp, 15.dp),
                enabled = enabled
            )
            {
                Text("CheckOut")
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.width(width = 200.dp).offset(1000.dp, 0.dp)
                .clickable { darkTheme.value = !darkTheme.value }.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "Mudar Tema",
                style = MaterialTheme.typography.h6.copy(fontSize = 14.sp),
                color = MaterialTheme.colors.onSurface
            )
            if (darkTheme.value) {
                Icon(imageVector = Icons.Default.Star, tint = Color.Yellow)
            } else {
                Icon(imageVector = Icons.Default.Star, tint = MaterialTheme.colors.onSurface)
            }

        }
        if (navItemState.value == NavType.LOGIN) {
            Row(modifier = Modifier.offset(0.dp, 50.dp)) {

                Column(modifier = Modifier.fillMaxSize()) {
                    TextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Senha") },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .offset(0.dp, 475.dp)
                            .keyInputFilter {
                                if (it.key == Key.Enter) {
                                    checkPassword(password)
                                    true
                                } else {
                                    false
                                }
                            }
                    )
                    Button(
                        onClick = {
                            checkPassword(password)
                        }, modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .offset(0.dp, 525.dp)
                    )
                    {
                        Text("Login")
                    }
                    Text(text, modifier = Modifier.align(Alignment.CenterHorizontally).offset(0.dp, 575.dp))
                }
            }
        } else {
            Row(modifier = Modifier.offset(0.dp, 50.dp)) { bodyContent(navItemState.value, userLogged, navItemState) }
        }
    }
}

@OptIn(ExperimentalKeyInput::class)
@Composable
fun bodyContent(navType: NavType, userLogged: MutableState<User?>, navItemState: MutableState<NavType>) {
    var idTableCard = remember { mutableStateOf("") }
    Crossfade(current = navType) { navType ->
        when (navType) {
            NavType.HOME -> biometry(userLogged, idTableCard)
            NavType.CARD -> cardInterface(idTableCard, navItemState)
            NavType.HALLTABLE -> hallTableInterface(idTableCard, navItemState)
            NavType.CHECKOUT -> checkout()
        }
    }
}
