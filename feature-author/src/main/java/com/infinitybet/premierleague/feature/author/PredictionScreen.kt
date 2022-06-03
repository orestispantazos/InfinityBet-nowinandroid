package com.infinitybet.premierleague.feature.author

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.infinitybet.premierleague.core.model.data.FollowablePrediction
import com.infinitybet.premierleague.core.model.data.Prediction
import com.infinitybet.premierleague.core.ui.LoadingWheel
import com.infinitybet.premierleague.core.ui.component.NiaFilterChip
import com.infinitybet.premierleague.core.ui.newsResourceCardItems
import com.infinitybet.premierleague.feature.author.PredictionUiState.Loading
import com.infinitybet.premierleague.feature.author.R.string

@Composable
fun PredictionRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PredictionViewModel = hiltViewModel(),
) {
    val uiState: PredictionScreenUiState by viewModel.uiState.collectAsState()

    PredictionScreen(
        predictionState = uiState.predictionState,
        newsState = uiState.proNewsState,
        modifier = modifier,
        onBackClick = onBackClick,
        onFollowClick = viewModel::followPredictionToggle,
    )
}

@OptIn(ExperimentalFoundationApi::class)
@VisibleForTesting
@Composable
internal fun PredictionScreen(
    predictionState: PredictionUiState,
    newsState: ProNewsUiState,
    onBackClick: () -> Unit,
    onFollowClick: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Spacer(
                // TODO: Replace with windowInsetsTopHeight after
                //       https://issuetracker.google.com/issues/230383055
                Modifier.windowInsetsPadding(
                    WindowInsets.safeDrawing.only(WindowInsetsSides.Top)
                )
            )
        }
        when (predictionState) {
            Loading -> {
                item {
                    LoadingWheel(
                        modifier = modifier,
                        contentDesc = stringResource(id = string.author_loading),
                    )
                }
            }
            PredictionUiState.Error -> {
                TODO()
            }
            is PredictionUiState.Success -> {
                item {
                    PredictionToolbar(
                        onBackClick = onBackClick,
                        onFollowClick = onFollowClick,
                        uiState = predictionState.followablePrediction,
                    )
                }
                predictionBody(
                    prediction = predictionState.followablePrediction.prediction,
                    news = newsState
                )
            }
        }
        item {
            Spacer(
                // TODO: Replace with windowInsetsBottomHeight after
                //       https://issuetracker.google.com/issues/230383055
                Modifier.windowInsetsPadding(
                    WindowInsets.safeDrawing.only(WindowInsetsSides.Bottom)
                )
            )
        }
    }
}

private fun LazyListScope.predictionBody(
    prediction: Prediction,
    news: ProNewsUiState
) {
    item {
        PredictionHeader(prediction)
    }

    predictionCards(news)
}

@Composable
private fun PredictionHeader(prediction: Prediction) {
    Column(
        modifier = Modifier.padding(horizontal = 24.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .padding(bottom = 12.dp)
                .size(216.dp)
                .align(Alignment.CenterHorizontally)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
            model = prediction.team1,
            contentDescription = "Prediction profile picture",
        )
        Text(prediction.matchName, style = MaterialTheme.typography.displayMedium)
        if (prediction.matchName.isNotEmpty()) {
            Text(
                text = prediction.matchName,
                modifier = Modifier.padding(top = 24.dp),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

private fun LazyListScope.predictionCards(news: ProNewsUiState) {
    when (news) {
        is ProNewsUiState.Success -> {
            newsResourceCardItems(
                items = news.news,
                newsResourceMapper = { it },
                isBookmarkedMapper = { /* TODO */ false },
                onToggleBookmark = { /* TODO */ },
                itemModifier = Modifier.padding(24.dp)
            )
        }
        is ProNewsUiState.Loading -> item {
            LoadingWheel(contentDesc = "Loading news") // TODO
        }
        else -> item {
            Text("Error") // TODO
        }
    }
}

@Composable
private fun PredictionToolbar(
    uiState: FollowablePrediction,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onFollowClick: (Boolean) -> Unit = {},
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 32.dp)
    ) {
        IconButton(onClick = { onBackClick() }) {
            Icon(
                imageVector = Filled.ArrowBack,
                contentDescription = stringResource(id = R.string.back)
            )
        }
        val selected = uiState.isFollowed
        NiaFilterChip(
            modifier = Modifier.padding(horizontal = 16.dp),
            checked = selected,
            onCheckedChange = onFollowClick,
        ) {
            if (selected) {
                Text(stringResource(id = string.author_following))
            } else {
                Text(stringResource(id = string.author_not_following))
            }
        }
    }
}

@Preview
@Composable
private fun PredictionBodyPreview() {
    MaterialTheme {
        LazyColumn {
            predictionBody(
                prediction = Prediction(
                    id = "0",
                    matchName = "Match vs Match",
                    matchDate = "2022-01-01",
                    status = "",
                    team1 = "",
                    team2 = "",
                    uniqueTip = "",
                ),
                news = ProNewsUiState.Success(emptyList())
            )
        }
    }
}
