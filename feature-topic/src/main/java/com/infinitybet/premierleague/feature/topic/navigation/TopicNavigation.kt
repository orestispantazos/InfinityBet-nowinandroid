package com.infinitybet.premierleague.feature.topic.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.infinitybet.premierleague.core.navigation.NiaNavigationDestination
import com.infinitybet.premierleague.feature.topic.TopicRoute

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
