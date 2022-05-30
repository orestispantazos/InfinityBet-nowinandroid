package com.google.samples.apps.nowinandroid.feature.interests.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.infinitybet.premierleague.core.navigation.NiaNavigationDestination
import com.google.samples.apps.nowinandroid.feature.interests.InterestsRoute

object InterestsDestination : NiaNavigationDestination {
    override val route = "interests_route"
    override val destination = "interests_destination"
}

fun NavGraphBuilder.interestsGraph(
    navigateToTopic: (String) -> Unit,
    navigateToAuthor: (String) -> Unit,
    navigateToPrediction: (String) -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit

) {
    navigation(
        route = InterestsDestination.route,
        startDestination = InterestsDestination.destination
    ) {
        composable(route = InterestsDestination.destination) {
            InterestsRoute(
                navigateToTopic = navigateToTopic,
                navigateToAuthor = navigateToAuthor,
                navigateToPrediction = navigateToPrediction,
            )
        }
        nestedGraphs()
    }
}
