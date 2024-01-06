package com.example.task_manger.presentation.task_navigator.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.task_manger.R

@Composable
fun TaskBottomBar(
    items: List<BottomNavigationItem>, selected: Int, onItemClick: (Int) -> Unit
) {
    NavigationBar(
        containerColor = colorResource(R.color.backGroundLight),
        modifier = Modifier.fillMaxWidth().height(100.dp),
        tonalElevation = 20.dp
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = index == selected,
                onClick = { onItemClick(index) },
                icon = {
                    Column(
                        horizontalAlignment = CenterHorizontally
                    ) {
                        Icon(
                            imageVector = item.imageVectorIcon,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(text = item.text, style = MaterialTheme.typography.labelSmall)
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = colorResource(id = R.color.selected),
                    selectedTextColor = colorResource(id = R.color.selected),
                    unselectedIconColor = colorResource(id = R.color.unselected),
                    unselectedTextColor = colorResource(id = R.color.unselected)
                )
            )
        }
    }
}

data class BottomNavigationItem(
    val text: String, val imageVectorIcon: ImageVector
)