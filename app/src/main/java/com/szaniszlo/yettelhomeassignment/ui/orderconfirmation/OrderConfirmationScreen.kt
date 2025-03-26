package com.szaniszlo.yettelhomeassignment.ui.orderconfirmation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.szaniszlo.yettelhomeassignment.R
import com.szaniszlo.yettelhomeassignment.ui.core.YettelAppBar

@Composable
fun OrderConfirmationScreen(
    viewModel: OrderConfirmationViewModel = hiltViewModel(),
    onOrderConfirmed: () -> Unit,
    onCancel: () -> Unit,
) {
    OrderConfirmationScreenContent(
        onOrderConfirmed = onOrderConfirmed,
        onCancel = onCancel,
    )
}

@Composable
private fun OrderConfirmationScreenContent(
    onOrderConfirmed: () -> Unit,
    onCancel: () -> Unit,
) {
    Scaffold(
        topBar = {
            YettelAppBar(
                onBackClicked = onCancel,
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(paddingValues = it)
        ) {
            Text(
                text = stringResource(id = R.string.confirm_order)
            )

            Button(
                onClick = onOrderConfirmed,
            ) {
                Text(text = stringResource(id = R.string.proceed))
            }

            Button(
                onClick = onCancel,
            ) {
                Text(text = stringResource(id = R.string.cancel))
            }
        }
    }
}

@Composable
@Preview
private fun OrderConfirmationScreenPreview() {
    OrderConfirmationScreenContent(
        onOrderConfirmed = {},
        onCancel = {}
    )
}