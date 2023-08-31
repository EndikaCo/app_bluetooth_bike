package com.example.bluetooth_bike.login

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bluetooth_bike.R
import com.example.bluetooth_bike.Routes

@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Header(Modifier.align(Alignment.TopEnd))
        Body(Modifier.align(Alignment.Center), navController, viewModel)
        Footer(Modifier.align(Alignment.BottomCenter))
    }
}

@Composable
fun Header(modifier: Modifier) {
    val activity = LocalContext.current as Activity
    Icon(
        imageVector = Icons.Default.Close,
        contentDescription = "close app",
        modifier = modifier.clickable { activity.finish() })
}

@Composable
fun Body(modifier: Modifier, navController: NavController, viewModel: LoginViewModel) {
    val email by viewModel.email.observeAsState(initial = "")
    val password by viewModel.password.observeAsState(initial = "")
    val isLoginEnabled by viewModel.isLoginEnabled.observeAsState(initial = false)

    Column(modifier = modifier) {
        ImageLogo(Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.size(16.dp))
        Email(email) {
            viewModel.onLoginChanged(email = it, password = password)
        }
        Spacer(modifier = Modifier.size(4.dp))
        PassWord(password) {
            viewModel.onLoginChanged(password = it, email = email)
            viewModel.enableLogin(email, password)
        }

        Spacer(modifier = Modifier.size(16.dp))
        ForgotPassword(Modifier.align(Alignment.End))
        Spacer(modifier = Modifier.size(16.dp))
        LoginButton(loginEnabled = isLoginEnabled, navController = navController)
        Spacer(modifier = Modifier.size(16.dp))
        LoginDivider(Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.size(16.dp))
        SocialLogin()
    }
}

@Composable
fun Footer(modifier: Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        Divider(
            Modifier
                .background(Color(0xFFF9F9F9))
                .height(1.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.size(24.dp))
        SignUp()
        Spacer(modifier = Modifier.size(24.dp))
    }
}

@Composable
fun ImageLogo(modifier: Modifier) {
    Image(
        painter = painterResource(id = R.drawable.ebike_24),
        contentDescription = "logo",
        modifier = modifier
            .height(100.dp)
            .width(100.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Email(email: String, onTextChanged: (String) -> Unit) {
    TextField(
        value = email,
        onValueChange = { onTextChanged(it) },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Email") },
        maxLines = 1,
        singleLine = true, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color(0xFF222020),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PassWord(password: String, onTextChanged: (String) -> Unit) {

    var passwordVisibility by remember {
        mutableStateOf(false)
    }
    TextField(
        value = password,
        onValueChange = { onTextChanged(it) },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Password") },
        maxLines = 1,
        singleLine = true, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color(0xFF222020),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        trailingIcon = {
            IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                if (passwordVisibility) {
                    Icon(painterResource(id = R.drawable.visibility_24), contentDescription = "")
                } else {
                    Icon(
                        painterResource(id = R.drawable.visibility_off_24),
                        contentDescription = ""
                    )
                }
            }
        }, visualTransformation = if (passwordVisibility) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        }
    )
}

@Composable
fun ForgotPassword(modifier: Modifier) {
    Text(
        text = "Forgot password",
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = Color(R.color.black),
        modifier = modifier,
        textDecoration = TextDecoration.Underline
    )
}

@Composable
fun LoginButton(loginEnabled: Boolean, navController: NavController) {
    Button(
        onClick = { navController.navigate(Routes.DevicesScreen.routes) },
        enabled = loginEnabled,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(R.color.light_black),
            disabledContainerColor = Color(0x80646464),
            disabledContentColor = Color.White,
            contentColor = Color.White
        )
    ) {
        Text(text = "Log In", fontSize = 20.sp)
    }
}

@Composable
fun LoginDivider(modifier: Modifier) {
    Text(text = "OR", modifier = modifier, fontSize = 14.sp, color = Color(R.color.grey))
}

@Composable
fun SocialLogin() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_facebook),
            contentDescription = "Social login",
            modifier = Modifier.size(16.dp)
        )
        Text(
            text = "Continue with Facebook",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 8.dp),
            color = Color(R.color.black),
            textDecoration = TextDecoration.Underline

        )
    }
}

@Composable
fun SignUp() {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {

        Text(
            text = "Don't have an account?",
            fontSize = 15.sp,
            color = Color(0xFF8B8C8F)
        )
        Text(
            text = "Sign up",
            fontSize = 15.sp,
            modifier = Modifier.padding(horizontal = 8.dp),
            color = Color(R.color.black),
            fontWeight = FontWeight.Bold,
            textDecoration = TextDecoration.Underline
        )
    }
}

