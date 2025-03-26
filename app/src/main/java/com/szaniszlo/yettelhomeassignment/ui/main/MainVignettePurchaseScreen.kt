package com.szaniszlo.yettelhomeassignment.ui.main

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
fun MainVignettePurchaseScreen(
    viewModel: MainVignettePurchaseViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onPurchaseClicked: () -> Unit,
    onCountyVignettesClicked: () -> Unit,
) {
    MainVignettePurchaseScreenContent(
        onBackClicked = onBackClick,
        onPurchaseClicked = onPurchaseClicked,
        onCountyVignettesClicked = onCountyVignettesClicked,
    )
}

@Composable
private fun MainVignettePurchaseScreenContent(
    onBackClicked: () -> Unit,
    onPurchaseClicked: () -> Unit,
    onCountyVignettesClicked: () -> Unit,
) {
    Scaffold(
        topBar = {
            YettelAppBar(onBackClicked = onBackClicked)
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier.padding(contentPadding)
        ) {
            Text(text = stringResource(id = R.string.country_vignettes))

            Button(
                onClick = onPurchaseClicked
            ) {
                Text(text = stringResource(id = R.string.purchase))
            }

            Button(
                onClick = onCountyVignettesClicked
            ) {
                Text(text = stringResource(id = R.string.yearly_country_vignettes))
            }
        }
    }
}

@Composable
@Preview
private fun MainVignettePurchaseScreenContentPreview() {
    MainVignettePurchaseScreenContent(
        onBackClicked = {},
        onPurchaseClicked = {},
        onCountyVignettesClicked = {},
    )
}