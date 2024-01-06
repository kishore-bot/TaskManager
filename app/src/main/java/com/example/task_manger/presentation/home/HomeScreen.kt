package com.example.task_manger.presentation.home


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.task_manger.presentation.home.components.StarSection
import com.example.task_manger.presentation.home.components.TaskList
import com.example.task_manger.presentation.navgraph.Route


@Composable
fun HomeScreen(
    status: String,
    state: HomeState,
    event: (HomeEvent) -> Unit,
    message: String,
    navController: NavController,

    ) {
    LaunchedEffect(Unit) {
        event(HomeEvent.ChangeStatus(status = status))
        event(HomeEvent.GetTask)
        event(HomeEvent.GetStars)

    }

    val tasksList = state.tasks?.collectAsState(initial = emptyList())
    val stars = state.stars
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {

        Spacer(modifier = Modifier.height(20.dp))
        if (stars != null) {
            StarSection(stars = stars,
                onStarsClick = { navController.navigate(Route.StarScreen.route + "/${stars}") })
        }
        Spacer(modifier = Modifier.height(20.dp))
        TaskList(message = message, tasksList = tasksList, onClick = { selectedTask ->
            navController.navigate(route = Route.DetailScreen.route + "/${selectedTask.id}")
        })
    }
}
