package com.example.sem_nova

import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.unit.dp
import com.example.sem_nova.ui.theme.SEM_NOVATheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SEM_NOVATheme {
                var LoginText by remember { mutableStateOf(getString(R.string.login_hint)) }
                var PasswdText by remember { mutableStateOf(getString(R.string.password_hint)) }
                val focusRequester = remember { FocusRequester() }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)

                ) {
                    TextField(
                        value = LoginText,
                        onValueChange = { newText -> LoginText = newText },
                        placeholder = {
                            Text(getString(R.string.login_hint))
                        },
                        modifier = Modifier
                            .padding(45.dp,15.dp)
                            .fillMaxWidth()
                            .focusRequester(focusRequester)
                            .onFocusChanged { focusState ->
                                if (focusState.isFocused) {
                                    LoginText = getString(R.string._hint)
                                } else if (LoginText.isEmpty()) {
                                    LoginText = getString(R.string.login_hint)
                                }
                            }
                    )

                    //Spacer(modifier = Modifier.height(16.dp))

                    TextField(
                        value = PasswdText,
                        onValueChange = { newText -> PasswdText = newText },
                        placeholder = {
                            Text(getString(R.string.password_hint))
                        },
                        modifier = Modifier
                            .padding(45.dp,15.dp)
                            .fillMaxWidth()
                            .focusRequester(focusRequester)
                            .onFocusChanged { focusState ->
                                if (focusState.isFocused) {
                                    PasswdText = getString(R.string._hint)
                                } else if (PasswdText.isEmpty()) {
                                    PasswdText = getString(R.string.password_hint)
                                }
                            }
                    )


                    Button(
                        onClick = { /* Handle login button click */ },
                        modifier = Modifier
                            .padding(45.dp,15.dp)
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text(text = getString(R.string.login_hint))
                    }



                }
            }
        }
    }
}
