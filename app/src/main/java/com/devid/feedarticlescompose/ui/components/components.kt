package com.devid.feedarticlescompose.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devid.feedarticlescompose.ui.theme.FeedArticlesComposeTheme
import com.devid.feedarticlescompose.ui.theme.PrimaryColor


@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {

    Button(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor),
        shape = RoundedCornerShape(30.dp),
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 15.dp)
    ) {
        Text(
            text = text,
            color = Color.White,
            textAlign = TextAlign.Center,
            fontSize = 22.sp,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(horizontal = 16.dp, vertical = 5.dp)
        )

    }
}

@Composable
fun AuthScreen(titleText: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = titleText,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun MyTextField(
    value: String,
    label: String,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = false,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        visualTransformation = if (isPassword) PasswordVisualTransformation()else VisualTransformation.None,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedTextColor = PrimaryColor,
            unfocusedTextColor = PrimaryColor,
            focusedLabelColor = PrimaryColor,
            unfocusedLabelColor = PrimaryColor,
            cursorColor = PrimaryColor,
            focusedIndicatorColor = Color.Black,
            unfocusedIndicatorColor = Color.Gray
        ),
        modifier = modifier.fillMaxWidth()
    )
}


@Preview(showBackground = true)
@Composable
fun PrimaryBtnPreview() {
    FeedArticlesComposeTheme {
        PrimaryButton("Login", onClick = { /*TODO*/ })
    }
}