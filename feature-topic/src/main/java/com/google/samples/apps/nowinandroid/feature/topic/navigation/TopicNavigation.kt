package com.google.samples.apps.nowinandroid.feature.topic.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.samples.apps.nowinandroid.core.navigation.NiaNavigationDestination
import com.google.samples.apps.nowinandroid.feature.topic.TopicRoute

object TopicDestination : NiaNavigationDestination {
    override val route = "topic_route"
    override val destination = "topic_destination"
    const val topicIdArg = "topicId"
}

fun NavGraphBuilder.topicGraph(
    onBackClick: () -> Unit
) {
    composable(
        route = "${TopicDestination.route}/{${TopicDestination.topicIdArg}}",
        arguments = listOf(
            navArgument(TopicDestination.topicIdArg) {
                type = NavType.StringType
            }
        )
    ) {
        TopicRoute(onBackClick = onBackClick)
    }
}
