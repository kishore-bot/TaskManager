package com.example.task_manger.presentation.utils

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import com.example.task_manger.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskTopBar(
    title: String,
    onBackClick: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(text = title, color = colorResource(id = R.color.text))
        },
        modifier = Modifier.fillMaxWidth(),
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = Color.Transparent,
            actionIconContentColor =colorResource(id = R.color.text),
            navigationIconContentColor = colorResource(id = R.color.white)
        ),
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null, tint = colorResource(id = R.color.text))

            }
        },
    )
}