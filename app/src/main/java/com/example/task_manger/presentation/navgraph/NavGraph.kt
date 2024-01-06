package com.example.task_manger.presentation.navgraph

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.task_manger.presentation.log_in_user.LogInScreen
import com.example.task_manger.presentation.log_in_user.LogInViewModel
import com.example.task_manger.presentation.sign_in_user.SignUserScreen
import com.example.task_manger.presentation.sign_in_user.SignUserViewModel
import com.example.task_manger.presentation.task_navigator.TaskNavigator

@Composable
fun NavGraph(
    startDestination: String,
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        navigation(
            route = Route.AppStartNavigation.route, startDestination = Route.LogInScreen.route
        ) {
            composable(
                route = Route.LogInScreen.route
            ) {
                val viewModel: LogInViewModel = hiltViewModel()
                LogInScreen(viewModel = viewModel, onSignIn = {
                    navController.navigate(route = Route.SignInScreen.route )
                }, onSuccess = {
                    navController.navigate(route = Route.TaskNavigatorScreen.route )
                })
            }
            composable(
                route = Route.SignInScreen.route
            ) {
                val viewModel: SignUserViewModel = hiltViewModel()
                SignUserScreen(viewModel = viewModel, onSuccess = {}, onLogin = {
                    navController.navigate(route = Route.LogInScreen.route )
                })
            }
        }
        navigation(
            route = Route.TaskNavigation.route, startDestination = Route.TaskNavigatorScreen.route
        ) {
            composable(
                route = Route.TaskNavigatorScreen.route
            ) {
                TaskNavigator()
            }

        }
    }
}