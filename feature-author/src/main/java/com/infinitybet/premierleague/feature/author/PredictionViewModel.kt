package com.google.samples.apps.nowinandroid.feature.author

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.infinitybet.premierleague.core.data.repository.NewsRepository
import com.infinitybet.premierleague.core.data.repository.PredictionsRepository
import com.infinitybet.premierleague.core.model.data.FollowablePrediction
import com.infinitybet.premierleague.core.model.data.NewsResource
import com.infinitybet.premierleague.core.model.data.Prediction
import com.infinitybet.premierleague.core.result.Result
import com.infinitybet.premierleague.core.result.asResult
import com.google.samples.apps.nowinandroid.feature.author.navigation.PredictionDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class PredictionViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val predictionsRepository: PredictionsRepository,
    newsRepository: NewsRepository
) : ViewModel() {

    private val predictionId: String = checkNotNull(
        savedStateHandle[PredictionDestination.predictionIdArg]
    )

    // Observe the followed predictions, as they could change over time.
    private val followedPredictionIdsStream: Flow<Result<Set<String>>> =
        predictionsRepository.getFollowedPredictionIdsStream().asResult()

    // Observe prediction information
    private val prediction: Flow<Result<Prediction>> = predictionsRepository.getPredictionStream(
        id = predictionId
    ).asResult()

    // Observe the News for this prediction
    private val newsStream: Flow<Result<List<NewsResource>>> =
        newsRepository.getNewsResourcesStream(
            filterAuthorIds = setOf(element = predictionId),
            filterTopicIds = emptySet()
        ).asResult()

    val uiState: StateFlow<PredictionScreenUiState> =
        combine(
            followedPredictionIdsStream,
            prediction,
            newsStream
        ) { followedPredictionsResult, predictionResult, newsResult ->
            val prediction: PredictionUiState =
                if (predictionResult is Result.Success && followedPredictionsResult is Result.Success) {
                    val followed = followedPredictionsResult.data.contains(predictionId)
                    PredictionUiState.Success(
                        followablePrediction = FollowablePrediction(
                            prediction = predictionResult.data,
                            isFollowed = followed
                        )
                    )
                } else if (
                    predictionResult is Result.Loading || followedPredictionsResult is Result.Loading
                ) {
                    PredictionUiState.Loading
                } else {
                    PredictionUiState.Error
                }

            val news: ProNewsUiState = when (newsResult) {
                is Result.Success -> ProNewsUiState.Success(newsResult.data)
                is Result.Loading -> ProNewsUiState.Loading
                is Result.Error -> ProNewsUiState.Error
            }

            PredictionScreenUiState(prediction, news)
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = PredictionScreenUiState(PredictionUiState.Loading, ProNewsUiState.Loading)
            )

    fun followPredictionToggle(followed: Boolean) {
        viewModelScope.launch {
            predictionsRepository.toggleFollowedPredictionId(predictionId, followed)
        }
    }
}

sealed interface PredictionUiState {
    data class Success(val followablePrediction: FollowablePrediction) : PredictionUiState
    object Error : PredictionUiState
    object Loading : PredictionUiState
}

sealed interface ProNewsUiState {
    data class Success(val news: List<NewsResource>) : ProNewsUiState
    object Error : ProNewsUiState
    object Loading : ProNewsUiState
}

data class PredictionScreenUiState(
    val predictionState: PredictionUiState,
    val proNewsState: ProNewsUiState
)
