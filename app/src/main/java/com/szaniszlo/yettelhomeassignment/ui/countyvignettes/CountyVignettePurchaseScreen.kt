@file:OptIn(ExperimentalMaterial3Api::class,ExperimentalMaterial3Api::class)

package com.szaniszlo.yettelhomeassignment.ui.countyvignettes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.szaniszlo.yettelhomeassignment.R
import com.szaniszlo.yettelhomeassignment.ui.core.YettelAppBar

@Composable
fun CountyVignettePurchaseScreen(
    viewModel: CountyVignettePurchaseViewModel = hiltViewModel(),
    onBack: () -> Unit,
    onContinue: () -> Unit,
) {
    CountyVignettePurchaseScreenContent(
        onBackClick = onBack,
        onContinueClick = onContinue,
    )
}

@Composable
private fun CountyVignettePurchaseScreenContent(
    onBackClick: () -> Unit,
    onContinueClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            YettelAppBar(
                onBackClicked = onBackClick,
            )
        }
    ) {
        Column(
            modifier = Modifier.padding(paddingValues = it)
        ) {
            Text(
                text = stringResource(id = R.string.yearly_country_vignettes)
            )
            Button(
                onClick = onContinueClick,
            ) {
                Text(text = stringResource(id = R.string.proceed))
            }
        }
    }
}

@Composable
@Preview
private fun CountyVignettePurchaseScreenContentPreview() {
    CountyVignettePurchaseScreenContent(
        onBackClick = {},
        onContinueClick = {}
    )
}