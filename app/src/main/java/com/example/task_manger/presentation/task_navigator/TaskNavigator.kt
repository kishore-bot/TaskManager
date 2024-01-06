package com.example.task_manger.presentation.task_navigator


import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.task_manger.R
import com.example.task_manger.presentation.add_sub_task.AddSubTaskScreen
import com.example.task_manger.presentation.add_sub_task.AddSubTaskViewModel
import com.example.task_manger.presentation.details.DetailsScreen
import com.example.task_manger.presentation.details.DetailsViewModel
import com.example.task_manger.presentation.home.HomeScreen
import com.example.task_manger.presentation.home.HomeViewModel
import com.example.task_manger.presentation.create_task.CreateTaskScreen
import com.example.task_manger.presentation.create_task.CreateTaskViewModel

import com.example.task_manger.presentation.navgraph.Route
import com.example.task_manger.presentation.stars.StarsScreen
import com.example.task_manger.presentation.task_navigator.components.BottomNavigationItem
import com.example.task_manger.presentation.task_navigator.components.TaskBottomBar

@Composable
fun TaskNavigator() {
    val bottomNavigationItem = remember {
        listOf(
            BottomNavigationItem(imageVectorIcon = Icons.Default.Home, text = "OnGoing"),
            BottomNavigationItem(imageVectorIcon = Icons.Default.CheckCircle, text = "Completed"),
            BottomNavigationItem(imageVectorIcon = Icons.Rounded.Warning, text = "Pending"),
        )
    }
    val navController = rememberNavController()
    val backStackState = navController.currentBackStackEntryAsState().value
    var selectedItem by remember {
        mutableIntStateOf(0)
    }
    selectedItem = remember(key1 = backStackState) {
        when (backStackState?.destination?.route) {
            Route.OnGoingScreen.route -> 0
            Route.CompletedScreen.route -> 1
            Route.PendingScreen.route -> 2
            else -> 0
        }
    }
    val isBottomBarVisible = remember(
        key1 = backStackState
    ) {
        backStackState?.destination?.route == Route.PendingScreen.route || backStackState?.destination?.route == Route.CompletedScreen.route || backStackState?.destination?.route == Route.OnGoingScreen.route
    }
    Scaffold(containerColor = colorResource(id = R.color.backGround), bottomBar = {
        if (isBottomBarVisible) {
            TaskBottomBar(items = bottomNavigationItem, selected = selectedItem, onItemClick = {
                when (it) {
                    0 -> navigateTo(
                        navController, Route.OnGoingScreen.route
                    )

                    1 -> navigateTo(
                        navController, Route.CompletedScreen.route
                    )

                    2 -> navigateTo(
                        navController, Route.PendingScreen.route
                    )

                }

            })
        }
    }, floatingActionButton = {
        if (isBottomBarVisible) {
            FloatingActionButton(
                onClick = {
                    navigateTo(navController, Route.CreateTaskScreen.route)
                }, containerColor = colorResource(id = R.color.unselected)
            ) {
                Icon(
                    modifier = Modifier.size(40.dp),  // Optional: Adjust icon size within FAB
                    imageVector = Icons.Default.Add, contentDescription = null
                )
            }
        }

    }) {
        it.calculateBottomPadding()
        NavHost(
            navController = navController, startDestination = Route.OnGoingScreen.route
        ) {
            composable(route = Route.OnGoingScreen.route) {
                val viewModel: HomeViewModel = hiltViewModel()
                val state = viewModel.state.value
                val event = viewModel::onEvent
                HomeScreen(
                    state = state,
                    event = event,
                    status = "inProgress",
                    message = "No Task Found",
                    navController = navController
                )
            }
            composable(route = Route.CompletedScreen.route) {
                val viewModel: HomeViewModel = hiltViewModel()
                val state = viewModel.state.value
                val event = viewModel::onEvent
                HomeScreen(
                    state = state,
                    event = event,
                    status = "completed",
                    message = "No Task Found",
                    navController = navController
                )
            }
            composable(route = Route.PendingScreen.route) {
                val viewModel: HomeViewModel = hiltViewModel()
                val state = viewModel.state.value
                val event = viewModel::onEvent

                HomeScreen(
                    state = state,
                    event = event,
                    status = "pending",
                    message = "No Task Found",
                    navController = navController,
                )
            }
            composable(route = Route.CreateTaskScreen.route) {
                val viewModel: CreateTaskViewModel = hiltViewModel()

                CreateTaskScreen(viewModel, navController = navController)
            }
            composable(
                route = Route.DetailScreen.route + "/{taskId}"
            ) { backStackEntry ->
                val taskId = backStackEntry.arguments?.getString("taskId") ?: ""
                val viewModel: DetailsViewModel = hiltViewModel()
                DetailsScreen(
                    onBackClick = {
                        navController.popBackStack()
                    }, viewModel = viewModel, taskId = taskId, navController = navController
                )
            }
            composable(
                route = Route.AddSubTaskScreen.route + "/{taskId}"
            ) { backStackEntry ->
                val taskId = backStackEntry.arguments?.getString("taskId") ?: ""
                val viewModel: AddSubTaskViewModel = hiltViewModel()
                AddSubTaskScreen(
                    onBackClick = {
                        navController.popBackStack()
                    },
                    viewModel = viewModel, taskId = taskId,
                )
            }
            composable(Route.StarScreen.route + "/{stars}") { backStackEntry ->
                val stars = backStackEntry.arguments?.getString("stars") ?: ""
                StarsScreen(stars = stars, onClick = {
                    navController.popBackStack()
                })
            }
        }
    }
}

private fun navigateTo(navController: NavController, route: String) {
    navController.navigate(route = route) {
        navController.graph.startDestinationRoute?.let { homeScreen ->
            popUpTo(homeScreen) {
                saveState = true
            }
            restoreState = true
            launchSingleTop = true
        }
    }
}