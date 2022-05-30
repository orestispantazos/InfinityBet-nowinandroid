package com.google.samples.apps.nowinandroid.navigation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.google.samples.apps.nowinandroid.feature.author.navigation.AuthorDestination
import com.google.samples.apps.nowinandroid.feature.author.navigation.authorGraph
import com.google.samples.apps.nowinandroid.feature.author.navigation.predictionGraph
import com.google.samples.apps.nowinandroid.feature.foryou.navigation.ForYouDestination
import com.google.samples.apps.nowinandroid.feature.foryou.navigation.forYouGraph
import com.google.samples.apps.nowinandroid.feature.interests.navigation.interestsGraph
import com.google.samples.apps.nowinandroid.feature.topic.navigation.TopicDestination
import com.google.samples.apps.nowinandroid.feature.topic.navigation.topicGraph

/**
 * Top-level navigation graph. Navigation is organized as explained at
 * https://d.android.com/jetpack/compose/nav-adaptive
 *
 * The navigation graph defined in this file defines the different top level routes. Navigation
 * within each route is handled using state and Back Handlers.
 */
@Composable
fun NiaNavHost(
    windowSizeClass: WindowSizeClass,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = ForYouDestination.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        forYouGraph(
            windowSizeClass = windowSizeClass
        )
        interestsGraph(
            navigateToTopic = { navController.navigate("${TopicDestination.route}/$it") },
            navigateToAuthor = { navController.navigate("${AuthorDestination.route}/$it") },
            navigateToPrediction = { navController.navigate("${AuthorDestination.route}/$it") },
            nestedGraphs = {
                topicGraph(onBackClick = { navController.popBackStack() })
                authorGraph(onBackClick = { navController.popBackStack() })
                predictionGraph(onBackClick = { navController.popBackStack() })
            }
        )
    }
}
