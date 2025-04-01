package com.szaniszlo.yettelhomeassignment.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.szaniszlo.yettelhomeassignment.R
import com.szaniszlo.yettelhomeassignment.ui.core.theme.Typography
import java.text.NumberFormat
import java.util.Locale

@Composable
fun TotalCostComponent(
    totalCost: Float,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier,
    ) {
        Text(
            text = stringResource(id = R.string.amount_to_pay),
            fontWeight = FontWeight.Bold
        )

        val format = NumberFormat.getNumberInstance(Locale.getDefault())
        Text(
            text = stringResource(id = R.string.amount,format.format(totalCost)),
            style = Typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun TotalCostComponentHungarianPreview(
    totalCost: Float,
) {
    // for some reason the locale parameter in the preview does not work...
    CompositionLocalProvider(
        LocalConfiguration provides Configuration().apply {
            setLocale(Locale("hu", "HU")) // Set Hungarian Locale
        }
    ) {
        TotalCostComponent(
            totalCost = 21_899f
        )
    }
}