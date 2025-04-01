package com.szaniszlo.yettelhomeassignment.ui.components

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.szaniszlo.yettelhomeassignment.R
import com.szaniszlo.yettelhomeassignment.ui.core.theme.YettelAppTheme
import com.szaniszlo.yettelhomeassignment.ui.core.theme.yettelBlue

@Composable
fun ErrorLayout(
    @StringRes message: Int = R.string.generic_error_message,
    onRetryClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(all = 16.dp)
    ) {
        Text(
            text = stringResource(id = message),
            modifier = Modifier.align(Alignment.Center)
        )

        Button(
            onClick = onRetryClicked,
            colors = ButtonDefaults.buttonColors().copy(
                containerColor = yettelBlue
            ),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            Text(text = stringResource(id = R.string.retry))
        }
    }
}

@Composable
@Preview(showBackground = true,showSystemUi = true)
private fun ErrorLayoutPreview() {
    YettelAppTheme {
        Scaffold(
            topBar = { YettelAppBar(onBackClicked = {}) }
        ) {
            ErrorLayout(
                onRetryClicked = {},
                modifier = Modifier.padding(it)
            )
        }
    }
}