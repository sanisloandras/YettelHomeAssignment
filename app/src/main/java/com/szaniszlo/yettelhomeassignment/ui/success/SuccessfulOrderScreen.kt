package com.szaniszlo.yettelhomeassignment.ui.success

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.szaniszlo.yettelhomeassignment.R

@Composable
fun SuccessfulOrderScreen(
    onOkClicked: () -> Unit,
) {
    SuccessfulOrderScreenContent(
        onOkClicked = onOkClicked,
    )
}

@Composable
private fun SuccessfulOrderScreenContent(
    onOkClicked: () -> Unit,
) {
    Scaffold {
        Column(
            modifier = Modifier.padding(it)
        ) {
            Text(text = stringResource(id = R.string.successful_order))

            Button(
                onClick = onOkClicked
            ) {
                Text(text = stringResource(id = R.string.ok))
            }
        }
    }
}

@Composable
@Preview
private fun SuccessfulOrderScreenContentPreview() {
    SuccessfulOrderScreenContent(
        onOkClicked = {}
    )
}