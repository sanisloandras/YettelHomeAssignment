package com.szaniszlo.yettelhomeassignment.ui.success

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.szaniszlo.yettelhomeassignment.R
import com.szaniszlo.yettelhomeassignment.ui.core.theme.Typography
import com.szaniszlo.yettelhomeassignment.ui.core.theme.yettelBlue
import com.szaniszlo.yettelhomeassignment.ui.core.theme.yettelGreen

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
    Scaffold(
        containerColor = yettelGreen,
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
            ) {
                SuccessfulOrderLabel(modifier = Modifier.align(Alignment.Center))

                Image(
                    painter = painterResource(id = R.drawable.confetti),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth()
                )

                Image(
                    painter = painterResource(id = R.drawable.yettel_dude),
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.BottomEnd)

                )
            }

            Button(
                onClick = onOkClicked,
                colors = ButtonDefaults.buttonColors().copy(
                    containerColor = yettelBlue
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 32.dp)
            ) {
                Text(text = stringResource(id = R.string.ok))
            }
        }
    }
}

@Composable
private fun SuccessfulOrderLabel(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(id = R.string.successful_order),
        style = Typography.headlineLarge,
        fontWeight = FontWeight.ExtraBold,
        modifier = modifier
            .padding(horizontal = 32.dp)
    )
}

@Composable
@Preview(showSystemUi = true)
private fun SuccessfulOrderScreenContentPreview() {
    SuccessfulOrderScreenContent(
        onOkClicked = {}
    )
}