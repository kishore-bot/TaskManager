package com.example.task_manger.presentation.stars

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.task_manger.R
import com.example.task_manger.presentation.utils.TaskTopBar

@Composable
fun StarsScreen(
    onClick: () -> Unit,
    stars: String,
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.success))

    Column {
        TaskTopBar(title = "Stars Page") {
            onClick()
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(text = "You Earned :", fontWeight = FontWeight.Bold, fontSize = 30.sp)
            Spacer(modifier = Modifier.width(10.dp))
            Icon(
                imageVector = Icons.Default.Star, contentDescription = null, tint = colorResource(
                    id = R.color.star
                ), modifier = Modifier.size(50.dp)
            )

            Text(text = stars, fontWeight = FontWeight.Bold, fontSize = 30.sp)
        }
        Spacer(modifier = Modifier.width(20.dp))
        LottieAnimation(
            composition = composition,
            iterations = LottieConstants.IterateForever,
        )

    }
}
