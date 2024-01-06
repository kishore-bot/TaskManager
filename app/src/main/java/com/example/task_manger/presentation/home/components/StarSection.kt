package com.example.task_manger.presentation.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.task_manger.R

@Composable
fun StarSection(
    stars: String,
    onStarsClick: () -> Unit,
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Spacer(modifier = Modifier.width(20.dp))
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Descriptive text about the logo",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .size(30.dp)
                .clip(shape = RoundedCornerShape(4.dp))

        )
        Spacer(modifier = Modifier.width(290.dp))
        Box(modifier = Modifier.clickable {
            onStarsClick()
        }) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "",
                    tint = colorResource(id = R.color.star),
                    modifier = Modifier
                        .size(30.dp)
                        .width(20.dp)
                        .height(20.dp)
                )
                Text(
                    text = stars,
                    fontSize = 25.sp,
                    color = colorResource(id = R.color.text),
                    fontWeight = FontWeight.Bold
                )

            }
        }
        Spacer(modifier = Modifier.width(20.dp))

    }
}