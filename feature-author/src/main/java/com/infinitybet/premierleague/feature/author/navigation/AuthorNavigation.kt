package com.google.samples.apps.nowinandroid.feature.author.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.infinitybet.premierleague.core.navigation.NiaNavigationDestination
import com.google.samples.apps.nowinandroid.feature.author.AuthorRoute

object AuthorDestination : NiaNavigationDestination {
    override val route = "author_route"
    override val destination = "author_destination"
    const val authorIdArg = "authorId"
}

fun NavGraphBuilder.authorGraph(
    onBackClick: () -> Unit
) {
    composable(
        route = "${AuthorDestination.route}/{${AuthorDestination.authorIdArg}}",
        arguments = listOf(
            navArgument(AuthorDestination.authorIdArg) {
                type = NavType.StringType
            }
        )
    ) {
        AuthorRoute(onBackClick = onBackClick)
    }
}
