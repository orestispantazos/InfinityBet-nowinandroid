package com.google.samples.apps.nowinandroid.feature.author.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.infinitybet.premierleague.core.navigation.NiaNavigationDestination
import com.google.samples.apps.nowinandroid.feature.author.PredictionRoute

object PredictionDestination : NiaNavigationDestination {
    override val route = "prediction_route"
    override val destination = "prediction_destination"
    const val predictionIdArg = "predictionId"
}

fun NavGraphBuilder.predictionGraph(
    onBackClick: () -> Unit
) {
    composable(
        route = "${PredictionDestination.route}/{${PredictionDestination.predictionIdArg}}",
        arguments = listOf(
            navArgument(AuthorDestination.authorIdArg) {
                type = NavType.StringType
            }
        )
    ) {
        PredictionRoute(onBackClick = onBackClick)
    }
}
