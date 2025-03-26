package com.szaniszlo.yettelhomeassignment.ui.core

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.szaniszlo.yettelhomeassignment.R
import com.szaniszlo.yettelhomeassignment.ui.core.theme.Typography
import com.szaniszlo.yettelhomeassignment.ui.core.theme.yettelGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YettelAppBar(onBackClicked: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = yettelGreen,
                shape = RoundedCornerShape(bottomStart = 24.dp,bottomEnd = 24.dp)
            )
    ) {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.header_title),
                    style = Typography.titleMedium
                )
            },
            navigationIcon = {
                IconButton(onClick = onBackClicked) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.back_content_description)
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent
            ),
        )
    }
}