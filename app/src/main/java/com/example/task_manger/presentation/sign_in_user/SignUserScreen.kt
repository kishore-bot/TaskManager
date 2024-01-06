package com.example.task_manger.presentation.sign_in_user

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.task_manger.R
import com.example.task_manger.domian.model.User
import com.example.task_manger.presentation.utils.PassWordTextFiled
import com.example.task_manger.presentation.utils.TaskButton
import com.example.task_manger.presentation.utils.TaskTextField
import com.example.task_manger.presentation.utils.showToastMessage
import kotlinx.coroutines.delay

@Composable
fun SignUserScreen(
    viewModel: SignUserViewModel,
    onSuccess: () -> Unit,
    onLogin: () -> Unit,
) {
    val activity = LocalContext.current as? Activity
    val name = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val result by viewModel.result.observeAsState(initial = "")

    LaunchedEffect(result) {
        if (result != "") {
            if (result != "Error") {
                if (activity != null) {
                    showToastMessage(message = result, activity = activity)
                }
                delay(200)
                onSuccess()
            } else {
                if (activity != null) {
                    showToastMessage(message = result, activity = activity)
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize()
            .statusBarsPadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Create A New Account",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.text)
        )
        Spacer(modifier = Modifier.height(20.dp))
        TaskTextField(textState = name, text = "Enter Your Name")
        Spacer(modifier = Modifier.height(10.dp))
        TaskTextField(textState = email, text = "Email")
        Spacer(modifier = Modifier.height(10.dp))
        PassWordTextFiled(passwordState = passwordState)
        Spacer(modifier = Modifier.height(20.dp))
        TaskButton(title = "SignIn") {
            if (activity != null) {
                signIn(
                    name = name.value,
                    email = email.value,
                    password = passwordState.value,
                    activity = activity,
                    viewModel = viewModel
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Are you already present? ",
                fontSize = 15.sp,
                color = colorResource(id = R.color.text)
            )
            TextButton(onClick = {
                onLogin()
            }) {
                Text(
                    text = "LogIn ",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.text)
                )

            }
        }
    }
}


private fun signIn(
    name: String,
    email: String,
    password: String,
    activity: Activity,
    viewModel: SignUserViewModel,

    ) {
    if (email == "" || password == "" || name == "") {
        showToastMessage(message = "Select All Field", activity = activity)
    } else {
        val user = User(
            name = name,
            email = email,
            password = password,
        )
        viewModel.onEvent(SignUserEvent.GetUser(user = user))
        viewModel.onEvent(SignUserEvent.SignUser)
    }
}