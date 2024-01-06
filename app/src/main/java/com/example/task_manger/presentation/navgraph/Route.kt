package com.example.task_manger.presentation.navgraph

sealed class Route(
    val route: String,
) {
    data object OnGoingScreen : Route(route = "ongoingScreen")
    data object CompletedScreen : Route(route = "completedScreen")
    data object PendingScreen : Route(route = "pendingScreen")

    data object DetailScreen : Route("details/{taskId}")
    data object CreateTaskScreen : Route(route = "createTaskScreen")
    data object AddSubTaskScreen : Route(route = "addSubScreen/{taskId}")
    data object StarScreen : Route(route = "starScreen/{stars}")

    data object SignInScreen:Route(route = "signInScreen")
    data object LogInScreen:Route(route = "logInScreen")
    data object TaskNavigatorScreen:Route(route = "taskNavScreen")
    data object TaskNavigation : Route(route = "taskNavigation")

    data object AppStartNavigation : Route(route = "appStartNavigation")

}
